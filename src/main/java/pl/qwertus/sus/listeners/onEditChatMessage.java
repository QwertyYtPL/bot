package pl.qwertus.sus.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.qwertus.sus.DiscordBot;

import java.util.Locale;

public class onEditChatMessage extends ListenerAdapter {
    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT).replace(" ", "").replace("|", "");
        for(String str : DiscordBot.getBlacklist()){
            if(message.contains(str) || !event.getMessage().getInvites().isEmpty()) {
                DiscordBot.getMessagesToDelete().add(event.getMessage());
            }
        }
    }
}
