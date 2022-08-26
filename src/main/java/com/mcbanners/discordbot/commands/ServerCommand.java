package com.mcbanners.discordbot.commands;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.slash.sender.SlashSender;

@Command("ping")
public class PingCommand extends BaseCommand {

    @Default
    public void executor(final SlashSender sender) {
        sender.reply("Pong!").queue();
    }
}
