package com.lorisleiva.minecravel;

import com.lorisleiva.minecravel.database.DatabaseDeleteEvent;
import com.lorisleiva.minecravel.database.DatabaseInsertEvent;
import com.lorisleiva.minecravel.database.DatabaseLogReader;
import com.lorisleiva.minecravel.database.DatabaseUpdateEvent;
import com.lorisleiva.minecravel.network.NetworkManager;
import com.lorisleiva.minecravel.network.PacketAddVillager;
import com.lorisleiva.minecravel.network.PacketRemoveVillager;
import com.lorisleiva.minecravel.network.PacketUpdateVillager;

import java.io.IOException;

public class UsersAsVillagersThread extends Thread
{
    @Override
    public void run()
    {
        DatabaseLogReader databaseLogReader = new DatabaseLogReader("localhost", 3306, "root", "");
        databaseLogReader.registerInsert("users", this::addVillager);
        databaseLogReader.registerUpdate("users", this::updateVillager);
        databaseLogReader.registerDelete("users", this::removeVillager);

        try {
            databaseLogReader.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addVillager(DatabaseInsertEvent event)
    {
        String id = event.attributes.get(0);
        String name = event.attributes.get(1);
        Minecravel.LOGGER.info("Adding villager: " + id + ", " + name);
        NetworkManager.INSTANCE.sendToServer(new PacketAddVillager(id, name));
    }

    private void updateVillager(DatabaseUpdateEvent event)
    {
        String id = event.attributes.get(0);
        String newName = event.attributes.get(1);
        Minecravel.LOGGER.info("Updating villager: " + id + ", " + newName);
        NetworkManager.INSTANCE.sendToServer(new PacketUpdateVillager(id, newName));
    }

    private void removeVillager(DatabaseDeleteEvent event)
    {
        String id = event.attributes.get(0);
        Minecravel.LOGGER.info("Removing villager: " + id);
        NetworkManager.INSTANCE.sendToServer(new PacketRemoveVillager(id));
    }
}
