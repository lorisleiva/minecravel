package com.lorisleiva.minecravel.client;

import com.lorisleiva.minecravel.Minecravel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Minecravel.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientModEventSubscriber
{
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event)
    {
        //
    }
}
