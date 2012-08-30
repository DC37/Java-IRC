package bots;


import java.io.IOException;
import javax.swing.JOptionPane;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * Mario Paintasy IRC Bot	
 *  <p>		  
 * Made for Mario Paintasy, created by JillSandwich93 and AbsoluteZero255, aka. "JS" and "AZ"
 *  <p>
 * Precursors to this program include:
 *  <p>
 * -The Character.exe program, last seen at Version 2.00 in 2010. (Developed 2009-2010 in C++)
 *  <p>
 * -The DamageCalc.exe program, last seen in 2009 at Version 4.00. (Developed 2009 in C++)
 *  <p>
 *  Work began on the project on June 30, 2011.
 *  @since 0.9
 *  @since 2011.0630
 *  @author RehdBlob
 *  @version 0.93a
 */
public class Main 
{

	static String CHANNEL, SERVER, NAME;
	
	public static void main(String[] args) 
	{
		//The following sets the server, channel, and bot name
		//This is done with some typical JOptionPane dialog
		//boxes
		SERVER = (String)JOptionPane.showInputDialog(
				"Server:", "irc.slashnet.org");
		CHANNEL = (String)JOptionPane.showInputDialog(
				"Channel:", "#mpc");
		NAME = (String)JOptionPane.showInputDialog(
				"Bot Name:", "PaintasyBot");
		MPBot bot = new MPBot(NAME);
		bot.setVerbose(true);		
		try {
				bot.connect(SERVER);
			} 
			catch (NickAlreadyInUseException e) 
				{
					e.printStackTrace();
					return;
				} 
			catch (IOException e)
				{
					e.printStackTrace();
					return;
				} 
			catch (IrcException e) 
				{
					e.printStackTrace();
					return;
				}

		bot.joinChannel(CHANNEL);
		bot.setChannel(CHANNEL);
		Thread botThread = new Thread(bot);
		botThread.start();
	}

}
