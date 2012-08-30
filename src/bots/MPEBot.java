package bots;
import java.io.IOException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
//import mechanics.*;

/**
 * @author RehdBlob
 * @since 0.9a
 * @since 2011.0702
 * @version 0.93a
 */
public class MPEBot extends MPBot
{
	private String SERVER, CHANNEL, NAME;
	//private Enemy capsule;
	
	/**
	 * @deprecated
	 */
	public MPEBot() 
	{
	}
	
	public MPEBot(String name) {
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
		MPEBot bot = new MPEBot();
		
		bot.setVerbose(true);
		
		SERVER = "irc.slashnet.org";
		CHANNEL = "#mpc";
		NAME = "";
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
	
	@Override
	public void onMessage(String channel, String sender,
			String login, String hostname, String message) 
	{	
		
	}
}


	