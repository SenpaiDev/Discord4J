package Takane.Users;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.IOException;
import java.net.URL;

/**
 * Created by frostbyte on 5/16/16.
 */
public class Lolinator {
	//http://api-en1.akinator.com/ws/
	//http://api-en1.akinator.com/ws/
	public static int getAnswerID(String ans){
		int id = 0;
		switch (ans.toLowerCase()){
			case "yes":{}
			case "y":{
				id = 0;
				break;
			}
			case "no":{}
			case "n":{
				id = 1;
				break;
			}
			case "dontknow":{}
			case "d":{
				id = 2;
				break;
			}
			case "probably":{}
			case "p":{
				id = 3;
				break;
			}
			case "probablynot":{}
			case "pn":{
				id = 4;
				break;
			}
		}
		return id;
	}


	static String Api = "http://api-en1.akinator.com/ws/";
	public static String NewSession = Api+"new_session?partner=1&player=";
	private static ObjectMapper mapper = new ObjectMapper();
	public static void Init(){
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	public static void StartSession(IUser user, IMessage message){
		System.out.println("adsa");
		//Response response = GetResponse(NewSession+user.getID());
		//System.out.println(mapper.readValue());
		//UserHandeleer.GetUser(user).StartLolinatorGame(response, message);
		UserHandler.GetUser(user).StartAkinator(message, message.getContent().contains("Beta"));
	}
	public static Response GetResponse(String url){
		try {
			Response response = mapper.readValue(new URL(url), Response.class);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Lolinator class:: no Response found so returned null");
		return null;
	}
}
