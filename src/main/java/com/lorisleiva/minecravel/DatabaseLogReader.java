package com.lorisleiva.minecravel;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class DatabaseLogReader
{
    BinaryLogClient client;
    Map<Long, String> tableMap = new HashMap<>();
    List<Consumer<DatabaseEvent>> observers = new LinkedList<>();

    public DatabaseLogReader(String hostname, Integer port, String username, String password)
    {
        client = new BinaryLogClient(hostname, port, username, password);
    }

    public void register(Consumer<DatabaseEvent> observer)
    {
        observers.add(observer);
    }

    public void register(String table, Consumer<DatabaseEvent> observer)
    {
        observers.add(event -> {
            if (event.table.equals(table)) {
                observer.accept(event);
            }
        });
    }

    public void registerInsert(String table, Consumer<DatabaseInsertEvent> observer)
    {
        observers.add(event -> {
            if (event.table.equals(table) && event instanceof DatabaseInsertEvent) {
                observer.accept((DatabaseInsertEvent) event);
            }
        });
    }

    public void registerUpdate(String table, Consumer<DatabaseUpdateEvent> observer)
    {
        observers.add(event -> {
            if (event.table.equals(table) && event instanceof DatabaseUpdateEvent) {
                observer.accept((DatabaseUpdateEvent) event);
            }
        });
    }

    public void registerDelete(String table, Consumer<DatabaseDeleteEvent> observer)
    {
        observers.add(event -> {
            if (event.table.equals(table) && event instanceof DatabaseDeleteEvent) {
                observer.accept((DatabaseDeleteEvent) event);
            }
        });
    }

    public void notify(DatabaseEvent event)
    {
        observers.forEach(observer -> {
            observer.accept(event);
        });
    }

    public void connect() throws IOException
    {
        client.registerEventListener((Event event) -> {
            EventData data = event.getData();

            if (data instanceof TableMapEventData) {
                TableMapEventData tableData = (TableMapEventData) data;
                tableMap.put(tableData.getTableId(), tableData.getTable());
            }

            else if (data instanceof WriteRowsEventData) {
                WriteRowsEventData eventData = (WriteRowsEventData) data;
                String table = tableMap.get(eventData.getTableId());
                eventData.getRows().forEach(attributes -> {
                    notify(new DatabaseInsertEvent(table, attributes));
                });
            }

            else if (data instanceof UpdateRowsEventData) {
                UpdateRowsEventData eventData = (UpdateRowsEventData) data;
                String table = tableMap.get(eventData.getTableId());
                eventData.getRows().forEach(entry -> {
                    notify(new DatabaseUpdateEvent(table, entry.getValue(), entry.getKey()));
                });
            }

            else if (data instanceof DeleteRowsEventData) {
                DeleteRowsEventData eventData = (DeleteRowsEventData) data;
                String table = tableMap.get(eventData.getTableId());
                eventData.getRows().forEach(attributes -> {
                    notify(new DatabaseDeleteEvent(table, attributes));
                });
            }
        });

        client.connect();
    }

    private static class DatabaseEvent
    {
        public String table;
        public List<String> attributes;

        public DatabaseEvent(String table, List<String> attributes)
        {
            this.table = table;
            this.attributes = attributes;
        }

        public DatabaseEvent(String table, Serializable[] serializedAttributes)
        {
            this.table = table;
            this.attributes = deserializeAttributes(serializedAttributes);
        }

        protected List<String> deserializeAttributes(Serializable[] serializedAttributes)
        {
            List<String> attributes = new ArrayList<>(serializedAttributes.length);

            for (Serializable serializedAttribute: serializedAttributes) {
                attributes.add(String.valueOf(serializedAttribute));
            }

            return attributes;
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + "{" +
                    "table='" + table + '\'' +
                    ", attributes=" + attributes +
                    '}';
        }
    }

    private static class DatabaseInsertEvent extends DatabaseEvent
    {
        public DatabaseInsertEvent(String table, List<String> attributes)
        {
            super(table, attributes);
        }

        public DatabaseInsertEvent(String table, Serializable[] serializedAttributes)
        {
            super(table, serializedAttributes);
        }
    }

    private static class DatabaseUpdateEvent extends DatabaseEvent
    {
        public List<String> oldAttributes;

        public DatabaseUpdateEvent(String table, List<String> attributes, List<String> oldAttributes)
        {
            super(table, attributes);
            this.oldAttributes = oldAttributes;
        }

        public DatabaseUpdateEvent(String table, Serializable[] serializedAttributes, Serializable[] serializedOldAttributes)
        {
            super(table, serializedAttributes);
            this.oldAttributes = deserializeAttributes(serializedOldAttributes);
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + "{" +
                    "table='" + table + '\'' +
                    ", before=" + oldAttributes +
                    ", after=" + attributes +
                    '}';
        }
    }

    private static class DatabaseDeleteEvent extends DatabaseEvent
    {
        public DatabaseDeleteEvent(String table, List<String> attributes)
        {
            super(table, attributes);
        }

        public DatabaseDeleteEvent(String table, Serializable[] serializedAttributes)
        {
            super(table, serializedAttributes);
        }
    }

    public static void main(String[] args) throws IOException
    {
        DatabaseLogReader reader = new DatabaseLogReader("localhost", 3306, "root", "");
        reader.register(System.out::println);
        reader.connect();
    }
}
