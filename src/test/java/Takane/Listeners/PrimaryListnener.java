package Takane.Listeners;

import Takane.TakaneBot;
import Takane.Utils.DiscordUtils;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.InviteReceivedEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * Created by frostbyte on 5/17/16.
 */
public class PrimaryListnener {
    @EventSubscriber
    public void onReady(ReadyEvent readyEvent){
        System.out.println("Bot is ready");
        DiscordUtils.Senpai = TakaneBot.getClient().getUserByID("132471651299229696");
        DiscordUtils.SendPm("Senpai~~~", DiscordUtils.getSenpai());
        //TakaneBot.getClient().updatePresence(true, Optional.ofNullable("meme"));
        TakaneBot.getClient().setIdleGame("some loli hentai");

    }
    @EventSubscriber
    public void onMessage(MessageReceivedEvent event){

    }
    @EventSubscriber
    public void onInvite(InviteReceivedEvent event) throws HTTP429Exception, DiscordException, MissingPermissionsException {
        IGuild guild = TakaneBot.getClient().getGuildByID(event.getInvite().details().getGuildID());
        event.getInvite().accept();
        DiscordUtils.SendPm("Hello! Im Takane Enomoto, but you can just call me \"Ene\".\nI have been invite to this guild of yours called "+guild.getName()+".\nI was invited by "+event.getMessage().getAuthor().getName()
                +".\n If you have any questions, dont feel afraid to ask me. I dont bite.... well not often at least",guild.getOwner());
        Long Last = Long.MAX_VALUE;
        for(IChannel channel : guild.getChannels()){
            System.out.println("Last: "+Last);
            if(Last > channel.getCreationDate().toEpochSecond(ZoneOffset.UTC)) {
                System.out.println(channel.getCreationDate().toEpochSecond(ZoneOffset.UTC));
                Last = channel.getCreationDate().toEpochSecond(ZoneOffset.UTC);
            }
        }
        //.sendMessage("Hello Im the magifisant Takane Enomoto or just \"Ene\"\n if you have any questions regarding me and my abilities, please make use of the !Help and !About commands");
    }
}
