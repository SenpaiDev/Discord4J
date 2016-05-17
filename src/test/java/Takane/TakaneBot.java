package Takane;

import Takane.Listeners.PrimaryListnener;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.Presences;
import sx.blah.discord.util.DiscordException;

import java.util.Optional;

/**
 * Created by frostbyte on 5/17/16.
 */
public class TakaneBot {
    public static IDiscordClient client;
    public static void main(String[] args){
        ClientBuilder clientBuilder = new ClientBuilder();
        try {
            client = clientBuilder.withLogin(args[0], args[1]).login();
            client.getDispatcher().registerListener(new PrimaryListnener());



        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

    public static IDiscordClient getClient() {
        return client;
    }
}
