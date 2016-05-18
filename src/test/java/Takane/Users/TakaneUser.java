package Takane.Users;

import Takane.TakaneBot;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.MessageUpdateEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;

/**
 * Created by frostbyte on 5/17/16.
 */

public class TakaneUser {
    private String ID;
    private String Name;
    private IUser user;
	//Lolinator
	private boolean AKI_INGAME;
	private Integer AKI_Session;
	private Integer AKI_Signature;
	private Response currentResponse;
	private IChannel Channelwithgame;
	private IMessage GameMessage;
	private boolean AKI_Mode;
	private int Step;

    public TakaneUser(IUser user1){
		this.user = user1;
		this.Name = this.user.getName();
		this.ID = this.user.getID();
    }
	//Lolinator
	public void StartAkinator(IMessage message, boolean mode){
		currentResponse = Lolinator.GetResponse(Lolinator.NewSession+this.ID);
		this.AKI_INGAME = true;
		this.AKI_Session = this.currentResponse.getParameters().getIdentification().getSession();
		this.AKI_Signature = this.currentResponse.getParameters().getIdentification().getSignature();
		TakaneBot.getClient().getDispatcher().registerListener(this);
		this.Step = 0;
		this.Channelwithgame = message.getChannel();
		try {
			message.getChannel().sendMessage("I will now ask you a few Questions, You awnser by editing your last message to your awnser");
			if(mode)
				this.GameMessage = new MessageBuilder(TakaneBot.getClient()).withChannel(message.getChannel()).withContent("@"+getName()+" "+getQuestion()).build();
			else
				this.Channelwithgame.sendMessage("@"+getName()+" "+getQuestion());
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		} catch (HTTP429Exception e) {
			e.printStackTrace();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
			//System.out.println(this.GameMessage);

		//GetGameMessage();
	}
	private double getProgression() {
		if(this.getCurrentResponse().getParameters().getStep_information()==null)
			return this.getCurrentResponse().getParameters().getProgression();
		else
			return this.getCurrentResponse().getParameters().getStep_information().getProgression();
	}
	public Boolean haveGuess(){
		return getProgression() > 90;
	}

	public Response getCurrentResponse() {
		return currentResponse;
	}

	private String getQuestion() {
		if(this.AKI_INGAME) {
			return this.currentResponse.getParameters().getQuestion() != null ?
					this.currentResponse.getParameters().getQuestion() :
					this.currentResponse.getParameters().getStep_information().getQuestion();
		}
		return null;
	}
	public void GetGameMessage(){
		/*
		for(IMessage message : Channelwithgame.getMessages()) {
			System.out.println(message.getContent());
			if (message.getContent().equalsIgnoreCase("@" + getName() + " " + getQuestion() + "[" + getAKI_Signature() + "]")) {
				this.GameMessage = message;
		   		System.out.println(message);
			}
		}
		try {
			this.GameMessage.edit("@"+getName()+" "+getQuestion());
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		} catch (HTTP429Exception e) {
			e.printStackTrace();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
		*/
	}
	public String getguesses(){
		this.currentResponse = Lolinator.GetResponse(Lolinator.Api+"list?session=" + this.getAKI_Session() +
				"&signature=" + this.getAKI_Signature() +
				"&step=" + (this.Step) +
				"&mode_question=0");
		StringBuilder stringBuilder = new StringBuilder();
		if(currentResponse.getParameters().getElements() != null){
			for (Elements element : currentResponse.getParameters().getElements()){
				stringBuilder.append(element.getElement().getAbsolute_picture_path()+"```Name:"+element.getElement().getName()+"\nDesc:"+element.getElement().getDescription()+"\nProb:"+element.getElement().getProba()+"```");
			}
		}
		this.AKI_INGAME = false;
		return stringBuilder.toString();
	}
	public void AskQuestion(boolean editmode) {
		if(editmode == true){
		try {
			this.GameMessage.edit("@" + getName() + " " + getQuestion());
		} catch (MissingPermissionsException e) {
			AskQuestion(false);
			e.printStackTrace();
		} catch (HTTP429Exception e) {
			AskQuestion(false);
			e.printStackTrace();
		} catch (DiscordException e) {
			AskQuestion(false);
			e.printStackTrace();
		}
		if (haveGuess()) {
			try {
				this.GameMessage.edit(getguesses());
			} catch (MissingPermissionsException e) {
				AskQuestion(false);
				e.printStackTrace();
			} catch (HTTP429Exception e) {
				AskQuestion(false);
				e.printStackTrace();
			} catch (DiscordException e) {
				AskQuestion(false);
				e.printStackTrace();
			}
		}
	} else {
			try {
				Channelwithgame.sendMessage("@"+getName()+" "+getQuestion());
			} catch (MissingPermissionsException e) {
				AskQuestion(false);
				e.printStackTrace();
			} catch (HTTP429Exception e) {
				e.printStackTrace();
				AskQuestion(false);

			} catch (DiscordException e) {
				AskQuestion(false);

				e.printStackTrace();
			}
			if(haveGuess()){
				try {
					Channelwithgame.sendMessage("@"+getName()+" "+getguesses());
				} catch (MissingPermissionsException e) {
					AskQuestion(false);

					e.printStackTrace();
				} catch (HTTP429Exception e) {
					AskQuestion(false);

					e.printStackTrace();
				} catch (DiscordException e) {
AskQuestion(false);
					e.printStackTrace();
				}
			}
		}
	}
	public void sendAnswer(String answer, boolean mode) {

		this.currentResponse = Lolinator.GetResponse(Lolinator.Api + "answer?session=" + this.AKI_Session +
				"&signature=" + this.AKI_Signature +
				"&step=" + (this.Step++) +
				"&answer=" + Lolinator.getAnswerID(answer));
		AskQuestion(mode);
	}


	@EventSubscriber
	public void EditMesage(MessageUpdateEvent event){
		if(event.getNewMessage().getAuthor() == getUser() && AKI_Mode){
			sendAnswer(event.getNewMessage().getContent(), true);
		}
	}
	@EventSubscriber
	public void Sendmesage(MessageReceivedEvent event){
		if(event.getMessage().getAuthor() == getUser() && !AKI_Mode){
			sendAnswer(event.getMessage().getContent(), false);
		}
	}

	public Integer getAKI_Session() {
		return AKI_Session;
	}

	public Integer getAKI_Signature() {
		return AKI_Signature;
	}

	public boolean isAKI_INGAME() {
		return AKI_INGAME;
	}


	//other
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
