package com.lorisleiva.minecravel;

import com.lorisleiva.minecravel.database.DatabaseDeleteEvent;
import com.lorisleiva.minecravel.database.DatabaseInsertEvent;
import com.lorisleiva.minecravel.database.DatabaseLogReader;
import com.lorisleiva.minecravel.database.DatabaseUpdateEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;

import java.io.IOException;

public class UsersAsVillagersManager
{
    ServerWorld world;
    VillagerRepository villagerRepository;
    DatabaseLogReader databaseLogReader;

    public UsersAsVillagersManager(ServerWorld world)
    {
        this.world = world;
        this.villagerRepository = new VillagerRepository(world);
        this.databaseLogReader = new DatabaseLogReader("localhost", 3306, "root", "");
    }

    public void init()
    {
        villagerRepository.removeAllVillagers();
    }

    public void refresh()
    {
        init();
    }

    public void connect() throws IOException
    {
        databaseLogReader.registerInsert("users", this::addVillager);
        databaseLogReader.registerUpdate("users", this::updateVillager);
        databaseLogReader.registerDelete("users", this::removeVillager);
        databaseLogReader.connect();
    }

    private void addVillager(DatabaseInsertEvent event)
    {
        String id = event.attributes.get(0);
        String name = event.attributes.get(1);
        PlayerEntity randomPlayer = world.getRandomPlayer();
        villagerRepository.addVillager(id, randomPlayer, name);
    }

    private void updateVillager(DatabaseUpdateEvent event)
    {
        String id = event.attributes.get(0);
        String newName = event.attributes.get(1);
        villagerRepository.updateVillager(id, newName);
    }

    private void removeVillager(DatabaseDeleteEvent event)
    {
        String id = event.attributes.get(0);
        villagerRepository.removeVillager(id);
    }
}
