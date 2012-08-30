package bots;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import static mechanics.Time.*;

/**
 * A chatlogger class that keeps track
 * of what is happening on the IRC chat.
 * @author RehdBlob
 * @version 0.93a
 * @since 0.93a
 * @since 2011.1222
 */
public class Logger implements Runnable {

	private ArrayList<String> log = new ArrayList<String>();
	/**
	 * Logs the chat to a file.
	 * @since 0.93
	 * @since 2011.1205
	 */
	public void log(ArrayList<String> chatLog)  
	{
		Scanner file;
		try
			{
				file = new Scanner(new File(getDay() + ".txt"));
				}
		catch (FileNotFoundException e)
			{
				file = null;
			}
		ArrayList<String> temp = new ArrayList<String>();
		if (file != null)
			{
				while (file.hasNext())
					{
						temp.add(file.nextLine());
					}
				file.close();
			}
		
		PrintStream writer;
		try {
			writer = new PrintStream(getDay() + ".txt");
			for (String s : temp)
				writer.println(s);
			for(String s : chatLog)
				writer.println(s);
				writer.close();
				chatLog.clear();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void add(String piece) {
		log.add(piece);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
				log(log);
				log.clear();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}



	
}
