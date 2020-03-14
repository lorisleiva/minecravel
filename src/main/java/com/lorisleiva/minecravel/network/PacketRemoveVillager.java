package com.lorisleiva.minecravel.network;

import com.lorisleiva.minecravel.Minecravel;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketRemoveVillager extends NetworkPacket
{
    private final String id;

    public PacketRemoveVillager(String id) {
        this.id = id;
    }

    public PacketRemoveVillager(PacketBuffer buf)
    {
        this.id = buf.readString();
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeString(id);
    }

    public void run(NetworkEvent.Context ctx)
    {
        Minecravel.villagerRepository.removeVillager(id);
    }
}
