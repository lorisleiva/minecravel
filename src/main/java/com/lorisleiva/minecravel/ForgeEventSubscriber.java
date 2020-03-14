package com.lorisleiva.minecravel;

import com.lorisleiva.minecravel.commands.CommandManager;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Minecravel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber
{
    @SubscribeEvent
    public static void onServerStarting(final FMLServerStartingEvent event)
    {
        CommandManager.registerCommands(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onServerStarted(final FMLServerStartedEvent event)
    {
        ServerWorld world = event.getServer().getWorld(DimensionType.OVERWORLD);
        Minecravel.usersAsVillagersManager = new UsersAsVillagersManager(world);
        Minecravel.usersAsVillagersThread = new UsersAsVillagersThread();
        Minecravel.usersAsVillagersThread.start();
    }
}
