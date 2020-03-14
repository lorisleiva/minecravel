package com.lorisleiva.minecravel.database;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;
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
        observers.forEach(observer -> observer.accept(event));
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

    public static void main(String[] args) throws IOException
    {
        DatabaseLogReader reader = new DatabaseLogReader("localhost", 3306, "root", "");
        reader.register(System.out::println);
        reader.connect();
    }
}
