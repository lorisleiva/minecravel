package com.lorisleiva.minecravel.network;

import com.lorisleiva.minecravel.Minecravel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketAddVillager extends NetworkPacket
{
    private final String id;
    private final String name;

    public PacketAddVillager(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PacketAddVillager(PacketBuffer buf)
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
        ServerPlayerEntity player = ctx.getSender();
        Minecravel.villagerRepository.addVillager(id, player, name);
    }
}
