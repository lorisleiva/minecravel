package com.lorisleiva.minecravel;

import com.lorisleiva.minecravel.commands.CommandManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Minecravel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber
{
    @SubscribeEvent
    public static void initServerOnly(final FMLServerStartingEvent event)
    {
        CommandManager.registerCommands(event.getCommandDispatcher());
    }
}
