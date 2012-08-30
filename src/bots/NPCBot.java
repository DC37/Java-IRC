package bots;
import org.jibble.pircbot.*;
import java.io.IOException;
import java.util.ArrayList;
//import mechanics.*;

/**
 * @author RehdBlob
 * @since 0.92b
 * @since 2011.0729
 * @version 0.93a
 */
public class NPCBot extends MPBot
{
	private ArrayList<String> op;
	private String SERVER, CHANNEL, NAME;
	//private NPC capsule;
	
	/**
	 * @deprecated
	 */
	public NPCBot() 
	{
	}
	
	public NPCBot(String name) {
		super(name);
	}

	/**
	 * Creates an Enemy instance that will battle the Players
	 * in the IRC chat.
	 * @since 0.92b
	 * @since 2011.0729
	 * @param name The name of the Enemy that will
	 * be created with this bot.
	 */
	public void start(String name)
	{
		NPCBot bot = new NPCBot(NAME);
		
		bot.setVerbose(true);
		
		SERVER = "irc.slashnet.org";
		CHANNEL = "#mpc";
		NAME = "PaintasyBot";
		bot.changeName(NAME);	
		try 
		{
			bot.connect(SERVER);
		} 
		catch (NickAlreadyInUseException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (IrcException e) 
		{
			e.printStackTrace();
		}
		bot.joinChannel(CHANNEL);
	}
	
	public void changeName(String s)
	{
		this.setName(s);
	}
	
	public void onOp(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient)
	{
		if (recipient.equals(this.getName()))
			return;
		else
			op.add(recipient);
	}
	
	@Override
	public void onMessage(String channel, String sender,
			String login, String hostname, String message) 
	{	
		
	}
}


	