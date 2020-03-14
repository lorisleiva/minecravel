package com.lorisleiva.minecravel.network;

import com.lorisleiva.minecravel.Minecravel;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketUpdateVillager extends NetworkPacket
{
    private final String id;
    private final String name;

    public PacketUpdateVillager(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PacketUpdateVillager(PacketBuffer buf)
    {
        this.id = buf.readString();
        this.name = buf.readString();
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeString(id);
        buf.writeString(name);
    }

    public void run(NetworkEvent.Context ctx)
    {
        Minecravel.villagerRepository.updateVillager(id, name);
    }
}
