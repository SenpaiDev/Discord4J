package sx.blah.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * Created by frostbyte on 5/17/16.
 */
public class AkaneBotTest {
    public static void main(String[] args){
        ClientBuilder clientBuilder = new ClientBuilder();
        try {
            IDiscordClient client = clientBuilder.withLogin("minecraft.net.47@gmail.com","uv53l.mamamm12").login();

        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}
