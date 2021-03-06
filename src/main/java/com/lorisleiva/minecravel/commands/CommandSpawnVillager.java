package com.lorisleiva.minecravel.commands;

import com.lorisleiva.minecravel.network.NetworkManager;
import com.lorisleiva.minecravel.network.PacketAddVillager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;

public class CommandSpawnVillager
{
    public static LiteralArgumentBuilder<CommandSource> build()
    {
        return Commands.literal("villager")
            .requires(cs -> cs.hasPermissionLevel(0))
            .then(buildMessageArgument());
    }

    private static RequiredArgumentBuilder<CommandSource, MessageArgument.Message> buildMessageArgument()
    {
        return Commands.argument("name", MessageArgument.message())
            .executes(CommandSpawnVillager::run);
    }

    private static int run(CommandContext<CommandSource> ctx) throws CommandSyntaxException
    {
        String name = MessageArgument.getMessage(ctx, "name").getString();
        NetworkManager.INSTANCE.sendToServer(new PacketAddVillager(name, name));
        return 0;
    }
}
