package com.mcbanners.discordapi.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.discordapi.DiscordAPIApplication;
import com.mcbanners.discordapi.models.server.ServerStatus;
import com.mcbanners.discordapi.types.RequestType;
import dev.triumphteam.cmd.core.annotations.ArgName;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Description;
import dev.triumphteam.cmd.core.annotations.Optional;
import dev.triumphteam.cmd.jda.sender.SlashCommandSender;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

@Command("server")
public class ServerCommand {
    private final ObjectMapper mapper = new ObjectMapper();

    @Command("icon")
    @Description("Get the icon of a server")
    public void icon(final SlashCommandSender sender,
                     @ArgName("host") @Description("The server IP") final String host,
                     @ArgName("port") @Description("The server port") @Optional final String port
    ) {
        final String url = createURL(host, port, RequestType.ICON);
        final Request request = new Request.Builder()
                .url(url)
                .build();

        sender.deferReply().queue();

        try (Response response = DiscordAPIApplication.httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                sender.getHook().editOriginal(url).queue();
            } else {
                sender.getHook().editOriginal("Server not found!").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command("info")
    @Description("Get information about a server")
    public void info(final SlashCommandSender sender,

                     @ArgName("host") @Description("The server IP") final String host,
                     @ArgName("port") @Description("The server port") @Optional final String port
    ) {
        final String url = createURL(host, port, RequestType.INFO);
        final Request request = new Request.Builder()
                .url(url)
                .build();

        sender.deferReply().queue();

        try (Response response = DiscordAPIApplication.httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                final ServerStatus server = mapper.readValue(response.body().string(), ServerStatus.class);
                final EmbedBuilder embed = new EmbedBuilder();

                embed.setTitle(server.getHost());
                embed.setColor(Color.red);
                embed.addField("Version:", server.getVersion(), true);
                embed.addField("Players:", String.format("%d/%d", server.getPlayers().getOnline(), server.getPlayers().getMax()), true);
                embed.addField("MOTD:", server.getMotd().getFormatted(), false);
                embed.setThumbnail(createURL(host, port, RequestType.ICON));

                sender.getHook().sendMessageEmbeds(embed.build()).queue();
            } else {
                sender.getHook().editOriginal("Server not found!").queue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create a URL for the API to utilize
     *
     * @param host The server host
     * @param port The server port
     * @param type The request type
     * @return The formatted URL
     */
    public String createURL(@NotNull final String host, @Nullable final String port, @NotNull final RequestType type) {
        return switch (type) {
            case INFO ->
                    "https://api.mcbanners.com/mc/server?host=" + host + "&port=" + (port == null ? "25565" : port);
            case ICON -> "https://api.mcbanners.com/mc/icon?host=" + host + "&port=" + (port == null ? "25565" : port);
        };
    }
}
