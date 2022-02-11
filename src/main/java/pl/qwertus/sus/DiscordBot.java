package pl.qwertus.sus;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import pl.qwertus.sus.listeners.onChatMessage;
import pl.qwertus.sus.listeners.onEditChatMessage;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DiscordBot {

    private final ScheduledExecutorService executorService;
    private static JDA jda;
    private static final String[] blacklist = {"moimserwerze", "debil","nigga","fuck","cum", "porn", "discord.gg", "suka", "suke", "pierdal", "pierdol", "rucha", "suka", "sex", "seks", "rucham", "nigger", "penis", "kutas", "gówno", "sperm","dziwk", "jeb","kurw","pizdo","huj", "nikping.fun", "pedał", "chuj", "cwel", "szmata", "pizda", "cipa", "kondon", "ruchanie"};
    private static ArrayList<Message> messagesToDelete;

    public DiscordBot() throws InterruptedException{
        executorService = Executors.newScheduledThreadPool(10);
        jda = createUser();
        if(jda == null){
            return;
        }
        messagesToDelete = new ArrayList<>();
        CompletableFuture.runAsync(this::deleteMessageTask);
    }

    public static JDA getJda(){
        return jda;
    }

    public static String[] getBlacklist(){
        return blacklist;
    }

    public static ArrayList<Message> getMessagesToDelete() {
        return messagesToDelete;
    }

    JDA createUser(){
        JDABuilder jda = JDABuilder.createDefault("OTQxMjU5NDQ3OTQ1MjY1MTYy.YgTWLA.nJ7sP8CajXzGIfaNGAkwc_A03eM");
        jda.addEventListeners(new Main(), new onChatMessage(), new onEditChatMessage());
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        jda.enableCache(CacheFlag.ACTIVITY);
        try {
            return jda.build();
        } catch (LoginException e){
            e.printStackTrace();
            return DiscordBot.jda;
        }
    }

    public ScheduledExecutorService getExecutorService(){
        return executorService;
    }

    public void deleteMessageTask(){
        messagesToDelete.removeIf(Objects::isNull);
        if(!messagesToDelete.isEmpty() && messagesToDelete.get(0) != null ) {
            messagesToDelete.get(0).delete().queue();
            messagesToDelete.remove(0);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompletableFuture.runAsync(this::deleteMessageTask);
    }

}
