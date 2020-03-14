package com.lorisleiva.minecravel;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Minecravel.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber
{
    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event)
    {
        //
    }
}
