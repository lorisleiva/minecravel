package com.lorisleiva.minecravel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VillagerRepository
{
    private ServerWorld world;
    private Map<String, VillagerEntity> villagerMap;

    public VillagerRepository(ServerWorld world)
    {
        this.world = world;
        this.villagerMap = new HashMap<>();
    }

    public void addVillager(String id, PlayerEntity player, String name)
    {
        addVillager(id, player.getPosition(), name);
    }

    public void addVillager(String id, BlockPos pos, String name)
    {
        VillagerEntity entity = EntityType.VILLAGER.create(world);
        entity.setCustomName(new StringTextComponent(name));
        entity.setCustomNameVisible(true);
        entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.addEntity(entity);
        villagerMap.put(id, entity);
    }

    public void updateVillager(String id, String newName)
    {
        VillagerEntity entity = villagerMap.get(id);
        if (entity == null) return;
        entity.setCustomName(new StringTextComponent(newName));
    }

    public void removeVillager(String id)
    {
        VillagerEntity entity = villagerMap.get(id);
        if (entity == null) return;
        world.removeEntity(entity);
        villagerMap.remove(id);
    }

    public void removeAllVillagers()
    {
        List<Entity> entities = world.getEntities(EntityType.VILLAGER, entity -> true);
        entities.forEach(world::removeEntity);
    }
}
