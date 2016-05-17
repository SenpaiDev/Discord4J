package Takane.Users;

import sx.blah.discord.handle.obj.IUser;

/**
 * Created by frostbyte on 5/17/16.
 */
public class TakaneUser {
    public static String ID;
    public static String Name;
    public static IUser user;


    public TakaneUser(IUser user){

    }



    public static String getID() {
        return ID;
    }

    public static String getName() {
        return Name;
    }

    public static IUser getUser() {
        return user;
    }
}
