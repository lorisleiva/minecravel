package com.lorisleiva.minecravel.database;

import java.io.Serializable;
import java.util.List;

class DatabaseInsertEvent extends DatabaseEvent
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
