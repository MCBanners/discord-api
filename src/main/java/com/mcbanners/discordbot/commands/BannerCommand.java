package com.mcbanners.discordbot.commands;

import com.mcbanners.discordbot.MCBannersBot;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.ArgName;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Description;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.slash.annotation.Choice;
import dev.triumphteam.cmd.slash.sender.SlashSender;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Command("banner")
public class BannerCommand extends BaseCommand {

    @SubCommand("create")
    @Description("Create a banner!")
    public void create(final SlashSender sender,

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

        try (Response response = MCBannersBot.httpClient.newCall(request).execute()) {
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
