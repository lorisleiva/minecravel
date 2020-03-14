package com.lorisleiva.minecravel.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class DatabaseEvent
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
