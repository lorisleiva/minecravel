package com.lorisleiva.minecravel;

import com.lorisleiva.minecravel.commands.CommandManager;
import com.lorisleiva.minecravel.network.NetworkManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = Minecravel.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber
{
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event)
    {
        NetworkManager.registerPackets();
    }
}
