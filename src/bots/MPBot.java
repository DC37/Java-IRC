package bots;


import mechanics.Controller;
import mechanics.Die;
import static mechanics.Time.*;
import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;
import java.util.ArrayList;
import java.util.Hashtable;



/**
 * The IRC bot mechanism for the Mario Paintasy Bot.
 * <p>
 * This IRC bot extends the Pircbot provided graciously by
 * http://www.jibble.org
 * <p>
 * This class is designed to be run as a Thread because
 * of its nature of checking the Controller class. 
 * @version 0.93a
 * @since  0.9
 * @since 2011.0630
 * @author RehdBlob
 *
 */
public class MPBot extends PircBot implements Runnable
{
	
	public Controller control;
	private String CHANNEL;

	public Thread checker = null;
	public Thread logger  = null;
	private Logger chatLog;
	
	private int SLEEP = 10;
	private final String [] opList = new String [] {"Rehd", "RehdBlob", "DC", 
			"JillSandwich93", "JS", "JS93", "AZ", "AbsoluteZero255", "az", "az255"};
	private ArrayList<String> op;
	private Hashtable<String, Command> msg = new Hashtable<String, Command>();
	
	/**
	 * The default constructor that has no name given to the bot.
	 * @deprecated
	 */
	public MPBot() 
	{
		this("Test");
	}
	
	public MPBot(String name) 
	{
		this.setName(name);
		op = new ArrayList<String>();
		for(int i = 0; i< opList.length; i++)
			op.add(opList[i]);
		control = new Controller();
		putCommands();
		chatLog = new Logger();
		logger  = new Thread(chatLog);
		//checker = new Thread(new Checker());
		logger.start();
	}
	
	/**
	 * An enum that is meant to make it easier to 
	 * switch/case the Hashtable lookup of a user
	 * command.
	 * @since 2011.1215
	 * @since 0.93
	 */
	private enum Command {
		start, quit, rolldie, flipcoin, choose, join,
		attack, defend, item, check
	}
	
	
	/**
	 * Sets the channel that the bot will join.
	 * @param s The new channel that the user wishes
	 * the bot to join
	 */
	public void setChannel(String s)
	{
		CHANNEL = s;
	}
	
	/**
	 * Changes the name of the IRC Bot as seen on the 
	 * channel.
	 * @param s The name the IRC Bot is to change to.
	 */
	public void changeName(String s)
	{
		this.setName(s);
	}
	
	/**
	 * When a person is set to operator status, the
	 * IRC bot will recognize a few extra commands from them, 
	 * such as the !quit command.
	 */
	@Override
	protected void onOp(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient)
	{
		if (channel.equalsIgnoreCase(CHANNEL))
		{
			
			if (recipient.equals(this.getName()))
				return;
			else if(op.indexOf(recipient) == -1)
				op.add(recipient);
		}
		else
			return;
	}
	
	
	
	/**
	 * This method determines the command sent to the IRC
	 * bot by the user and then chooses the next appropriate
	 * action.
	 */
	@Override
	protected void onMessage(String channel, String sender,
			String login, String hostname, String message)
	{	
		chatLog.add("[" + getTime() + "] " + sender + ": " + message);
		if (channel.equalsIgnoreCase(CHANNEL))
		{
			if (message.charAt(0) != '!')
				return;
			
			else if (message.startsWith("!quit") && op.indexOf(sender)!=-1)
			{
				control.saveSettings();
				this.quitServer();
			}
			
			else if (message.startsWith("!join") & !control.isPlaying(sender))
			{
				control.addPlayer(sender);
			}
			
			/*else if (message.startsWith("!addmessage"))
			{
				control.addMessage(message.substring((message.indexOf(' '))));
			}*/
			
			else if (message.startsWith("!set delay"))
			{
				message = message.substring(message.indexOf(' ')+1);
				try
				{
					SLEEP = Integer.parseInt(message.substring(message.indexOf(' ')+1));
				}
				catch (NumberFormatException e)
				{
					return;
				}
			}
			
			else if (message.startsWith("!rolldie"))
			{
				String space = message.substring(message.indexOf(' ')+1);
				double sides = 0;
				try
				{
					sides = Double.parseDouble(space);
					if (sides <= 1)
						message(sender + " rolled an illegal die. The die showed: nothing");
					else if (sides - (long)sides == 0)
						message(sender + " rolled a " +  (long)sides + "-sided die. " +
								"The die showed: " + Die.roll((long)sides));
					else
						message(sender + " rolled a " +  sides + "-sided die. " +
								"The die showed: " + Die.roll(sides));
				}
				catch (NumberFormatException e)
				{
					sides = 0;
					message(sender + " rolled an illegal die. The die showed: nothing");
				}
			}
			
			else if (control.isPlaying(sender))
			{
				if (control.isInBattle())
				{
					if (message.startsWith("!check"))
					{
						message(control.checkEnemyStats());
					}
					if (control.isNext(sender))
						{
							if (message.startsWith("!attack"))
								{
									int index = 0;
									control.attack(sender, message.substring
											(message.indexOf(' ')), index);
									message(control.result());
								}
							else if (message.startsWith("!defend"))
								{
									control.defend(sender);
								}
							else if (message.startsWith("!skills"))
								{
									control.openSkillsMenu(sender);
								}
							else if (message.startsWith("!tempo"))
								{
									control.openTempoSkillsMenu(sender);
								}
							else if (message.startsWith("!run"))
								{
									control.escape(sender);
								}
					}
				}
				else
				{
					return;
				}
			}
		}
	}
	
	/**
	 * When a private message is sent to the bot by an
	 * operator, the bot will relay that same message to the 
	 * channel, making it appear as if the bot has just said 
	 * something.
	 */
	@Override
	protected void onPrivateMessage(String sender,	String login, String hostname,
			String message)
	{
		chatLog.add("[" + getTime() + "] <" + sender 
				+ " : " + message + ">");
		if (message.startsWith("!m ") & op.indexOf(sender) != -1)
		{
			message(message.substring(3));
		}
		
		else if (message.startsWith("!start") & op.indexOf(sender) != -1
				&& !control.isInBattle())
		{
			control.startBattle();
		}
	}
	
	
	/**
	 * Sends a message to the channel that the bot is logged
	 * on to. The default color is DARK_BLUE.
	 * @param s The message to be sent.
	 */
	protected void message(String s)
	{
		chatLog.add("[" + getTime() + "] " + getName() + ": " + s);
		sendMessage(CHANNEL, Colors.DARK_BLUE + s);
	}
	
	/**
	 * @since 0.93
	 * @since 2011.1209
	 */
	@Override
	protected void onJoin(String channel, String sender, 
			String login, String hostname) 
	{
		chatLog.add("[" + getTime() + "]" + sender + 
				" (" + login + "@" + hostname + ") has joined " + channel);
	}
	
	/**
	 * @since 0.93a
	 * @since 2011.1222
	 */
	@Override
	protected void onPart(String channel, String sender, String login, String hostname)
	{
		chatLog.add("[" + getTime() + "]" + sender +
				" (" + login + "@" + hostname + ") has left " + channel);
	}
	
	/**
	 * @since 0.93a
	 * @since 2011.1222
	 */
	@Override
	protected void onQuit(String sourceNick, String sourceLogin, 
			String sourceHostname, String reason)
	{
		chatLog.add("[" + getTime() + "]" + sourceNick + " (" + 
				sourceLogin + "@" + sourceHostname + ") has quit (" 
				+ reason + ")");
	}
	
	/**
	 * @since 0.93a
	 * @since 2011.1222
	 */
	@Override
	protected void onDisconnect() {
		return;
	}
	
	/**
	 * Stops the MPBot thread.
	 */
	public void stop()
	{
		if (checker != null)
	      {  
			checker.interrupt(); 
	        checker = null;
	      }
	}
	
	/**
	 * During this method, the IRC bot checks
	 * whether the Controller has any new messages
	 * to display. The update frequency is 200 ms
	 * currently, but that may be changed.
	 */
	@Override
	public void run()
	{
		while (true)
		{
			try
				{
					Thread.sleep(SLEEP);
					if(control.hasMessage())
						message(control.nextMessage());
					if (control.hasLotsOfMessages())
						{
							while(control.hasMessage())
								message(control.nextMessage());
						}
					else if(!control.hasMessage())
						SLEEP = 200;
				}
		
			catch (InterruptedException e)
				{
					break;
				} 
		}
		
	}
	
	/**
	 * @since 2011.1215
	 * @since 0.93
	 */
	private void putCommands()
	{
		String [] res = ("start quit rolldie " +
				"flipcoin choose join attack " +
				"defend item check").split("\\s");
		msg.put(res[0], Command.start);
		msg.put(res[1], Command.quit);
		msg.put(res[2], Command.rolldie);
		msg.put(res[3], Command.choose);
		msg.put(res[4], Command.join);
		msg.put(res[5], Command.attack);
		msg.put(res[6], Command.defend);
		msg.put(res[7], Command.item);
		msg.put(res[8], Command.check);
	
	}

}


	