package com.mcbanners.discordbot;

import com.mcbanners.discordbot.commands.BannerCommand;
import com.mcbanners.discordbot.commands.ServerCommand;
import dev.triumphteam.cmd.slash.SlashCommandManager;
import dev.triumphteam.cmd.slash.choices.ChoiceKey;
import dev.triumphteam.cmd.slash.sender.SlashSender;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.util.List;

public class MCBannersBot {
    public static JDA jda;
    public static SlashCommandManager<SlashSender> manager;
    public static OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws LoginException {
        if (args.length == 0) {
            throw new RuntimeException("No token provided!");
        }

        final String token = args[0];

        jda = JDABuilder.createDefault(token)
                .build();

        jda.getPresence().setActivity(Activity.playing("MCBanners.com"));

        manager = SlashCommandManager.create(jda);


        manager.registerChoices(ChoiceKey.of("platforms"), () ->
                List.of("spigot", "sponge", "curseforge", "modrinth", "builtbybit", "polymart"));

        manager.registerChoices(ChoiceKey.of("templates"), () ->
                List.of(
                        "Blue Radial", "Burning Orange", "Mango",
                        "Moonlight Purple", "Orange Radial", "Velvet",
                        "Yellow", "Malachite Green", "Dark Gunmetal",
                        "Purple Taupe"
                ));

        manager.registerCommand(new ServerCommand());
        manager.registerCommand(new BannerCommand());
    }
}
