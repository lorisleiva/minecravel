package com.lorisleiva.minecravel.network;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSpawnVillager extends NetworkPacket
{
    private final BlockPos pos;
    private final String name;

    public PacketSpawnVillager(BlockPos pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    public PacketSpawnVillager(PacketBuffer buf)
    {
        this.pos = buf.readBlockPos();
        this.name = buf.readString();
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
        buf.writeString(name);
    }

    public void run(NetworkEvent.Context ctx)
    {
        ServerWorld world = ctx.getSender().world.getServer().getWorld(DimensionType.OVERWORLD);
        EntityType.VILLAGER.spawn(world, null, new StringTextComponent(name), null, pos, SpawnReason.EVENT, true, true);
    }
}
