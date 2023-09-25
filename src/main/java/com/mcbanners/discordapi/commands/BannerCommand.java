package com.mcbanners.discordapi.commands;

import com.mcbanners.discordapi.DiscordAPIApplication;
import dev.triumphteam.cmd.core.annotations.ArgName;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Description;
import dev.triumphteam.cmd.discord.annotation.Choice;
import dev.triumphteam.cmd.jda.sender.SlashCommandSender;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Command("banner")
public class BannerCommand {

    @Command("create")
    @Description("Create a banner!")
    public void create(final SlashCommandSender sender,

                       @ArgName("type")
                       @Description("The type of banner") final String type,

                       @ArgName("platform")
                       @Description("The platform for the banner")
                       @Choice("platforms") final String platform,

                       @ArgName("id")
                       @Description("The ID of the member/user/team/resource/author") final String id,

                       @ArgName("template")
                       @Description("The template for the banner")
                       @Choice("templates") final String template
    ) {

        final String url = createURL(type, platform, id, template);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = DiscordAPIApplication.httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                sender.reply(url).queue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create a URL for the banner API
     *
     * @param type The type of banner
     * @param platform The platform for the banner
     * @param id The ID of the member/user/team/resource/author
     * @param template The template for the banner
     * @return The formatted URL for the banner API
     */
    private String createURL(@NotNull final String type, @NotNull final String platform, @NotNull final String id, @NotNull final String template) {
        final String updatedTemplate = template.toUpperCase().replace(" ", "_");

        return String.format("https://api.mcbanners.com/banner/%s/%s/%s/banner.png?template=%s", type, platform, id, updatedTemplate);
    }
}
