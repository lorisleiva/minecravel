package com.lorisleiva.minecravel.database;

import java.io.Serializable;
import java.util.List;

public class DatabaseUpdateEvent extends DatabaseEvent
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
