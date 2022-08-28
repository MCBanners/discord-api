package com.mcbanners.discordapi;

import com.mcbanners.discordapi.commands.BannerCommand;
import com.mcbanners.discordapi.commands.ServerCommand;
import dev.triumphteam.cmd.slash.SlashCommandManager;
import dev.triumphteam.cmd.slash.choices.ChoiceKey;
import dev.triumphteam.cmd.slash.sender.SlashSender;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.security.auth.login.LoginException;
import java.util.List;

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
public class DiscordAPIApplication {
    public static JDA jda;
    public static SlashCommandManager<SlashSender> manager;
    public static OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws LoginException {
        if (args.length == 0) {
            throw new RuntimeException("No token provided!");
        }

        final String token = args[0];

        jda = JDABuilder.create(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setToken(token)
                .enableCache(CacheFlag.ACTIVITY, CacheFlag.ONLINE_STATUS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
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

        SpringApplication.run(DiscordAPIApplication.class, args);
    }
}
