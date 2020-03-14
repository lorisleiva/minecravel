package com.lorisleiva.minecravel.database;

import java.io.Serializable;
import java.util.List;

public class DatabaseDeleteEvent extends DatabaseEvent
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
