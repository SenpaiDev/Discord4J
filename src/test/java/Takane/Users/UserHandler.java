package Takane.Users;

import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IUser;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frostbyte on 5/18/16.
 */
public class UserHandler {
	static Map<String, TakaneUser> Usermap = new HashMap();
	public static void adduser(IUser user){
		Usermap.put(user.getID(), new TakaneUser(user));
	}

	public static TakaneUser GetUser(IUser user){
		return Usermap.get(user.getID());
	}

	public static Map<String, TakaneUser> getUsermap() {
		return Usermap;
	}
}
