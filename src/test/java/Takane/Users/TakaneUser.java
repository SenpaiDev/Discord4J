package Takane.Users;

import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by frostbyte on 5/17/16.
 */

public class TakaneUser {
    private String ID;
    private String Name;
    private IUser user;


    public TakaneUser(IUser user1){
		this.user = user1;
		this.Name = this.user.getName();
		this.ID = this.user.getID();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public IUser getUser() {
        return user;
    }
}
