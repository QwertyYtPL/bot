package pl.qwertus.sus;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class Main implements EventListener {
    public static void main(final String[] args) throws InterruptedException{
        new DiscordBot();
    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if(genericEvent instanceof ReadyEvent){
            System.out.println("Bot started!");
        }
    }
}
