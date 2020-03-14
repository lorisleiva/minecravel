package com.lorisleiva.minecravel.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

abstract public class NetworkPacket
{
    public void toBytes(PacketBuffer buf)
    {
        //
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> this.run(ctx.get()));
        ctx.get().setPacketHandled(true);
    }

    public void run(NetworkEvent.Context ctx)
    {
        //
    }
}
