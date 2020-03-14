package com.lorisleiva.minecravel.network;

import com.lorisleiva.minecravel.VillagerRepository;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

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
        ServerWorld world = player.world.getServer().getWorld(DimensionType.OVERWORLD);
        VillagerRepository villagerRepository = new VillagerRepository(world);
        villagerRepository.addVillager(id, player, name);
    }
}
