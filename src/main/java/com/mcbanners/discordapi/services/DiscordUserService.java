package com.mcbanners.discordapi.services;

import com.mcbanners.discordapi.DiscordAPIApplication;
import com.mcbanners.discordapi.models.user.DiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@CacheConfig(cacheNames = "discord")
public class DiscordUserService {

    @Cacheable(unless = "#result == null")
    public DiscordUser getUser(final String id) {
        final JDA jda = DiscordAPIApplication.jda;

        final Member member = jda.getGuilds()
                .stream().map(Guild::getMembers)
                .flatMap(Collection::stream)
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (member == null) {
            return null;
        }

        final CompletableFuture<DiscordUser> future = new CompletableFuture<>();
        final DiscordUser discordUser = new DiscordUser();
        final User user = member.getUser();

        if (!member.getActivities().isEmpty()) {
            discordUser.setActivity(member.getActivities().get(0).getName());
        }

        try {
            discordUser.setIcon(member.getEffectiveAvatar().download().join().readAllBytes());
        } catch (IOException e) {
            discordUser.setIcon(null);
        }

        discordUser.setStatus(member.getOnlineStatus().getKey());
        discordUser.setId(member.getIdLong());
        discordUser.setName(user.getEffectiveName());
        discordUser.setCreated(user.getTimeCreated().toInstant().toEpochMilli());

        future.complete(discordUser);

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            return null;
        }
    }
}
