package Takane.Utils;

import Takane.TakaneBot;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

/**
 * Created by frostbyte on 5/17/16.
 */
public class DiscordUtils {
    public void Sendmessage(String message){

    }
    public static void SendPm(String message, IUser user){
        try {
            TakaneBot.getClient().getOrCreatePMChannel(user).sendMessage(message);
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
    public static IUser Senpai;

    public static IUser getSenpai() {
        return Senpai;
    }
}
