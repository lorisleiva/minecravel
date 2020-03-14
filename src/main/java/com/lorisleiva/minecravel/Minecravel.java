package com.lorisleiva.minecravel;

import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Minecravel.MODID)
public class Minecravel
{
    public static final String MODID = "minecravel";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Minecravel()
    {
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // ...

//        MinecraftServer server = event.getServer();
//        ServerWorld world = server.getWorld(DimensionType.OVERWORLD);
//        ServerPlayerEntity player = world.getRandomPlayer();
//        Entity villager = new VillagerEntity(EntityType.VILLAGER, world);
//        villager.setPosition(player.getPosX() + 2,player.getPosY(),player.getPosZ());
//        player.applyOrientationToEntity(villager);
//        world.addEntity(villager);
    }
}
