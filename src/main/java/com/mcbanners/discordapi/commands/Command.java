package com.mcbanners.discordapi.commands;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;

public interface Command {
    void register(@NonNull JDA5CommandManager<JDAInteraction> commandManager);
}
