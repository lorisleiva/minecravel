package com.lorisleiva.minecravel.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class CommandManager
{
    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(CommandSpawnVillager.build());
    }
}
