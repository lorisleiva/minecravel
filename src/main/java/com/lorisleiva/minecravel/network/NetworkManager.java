package com.lorisleiva.minecravel.network;

import com.lorisleiva.minecravel.Minecravel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkManager
{
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Minecravel.MODID, "network"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerPackets()
    {
        INSTANCE.registerMessage(nextID(), PacketAddVillager.class, PacketAddVillager::toBytes, PacketAddVillager::new, PacketAddVillager::handle);
        INSTANCE.registerMessage(nextID(), PacketUpdateVillager.class, PacketUpdateVillager::toBytes, PacketUpdateVillager::new, PacketUpdateVillager::handle);
        INSTANCE.registerMessage(nextID(), PacketRemoveVillager.class, PacketRemoveVillager::toBytes, PacketRemoveVillager::new, PacketRemoveVillager::handle);
    }
}
