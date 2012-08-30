package mechanics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author RehdBlob
 * @version 0.93a
 * @since 0.93a
 * @since 2011.1222
 */
public class Time {
	/**
	 * @return The current date in MM/dd/yyyy format.
	 * @since 0.93
	 * @since 2011.1205
	 */
	public static String getDay()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String day = dateFormat.format(date).replace('/', '_');
		return day;
	}
	
	/**
	 * @return The current time in HH:mm:ss:SS format.
	 * @since 0.93
	 * @since 2011.1206
	 */
	public static String getTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS z");
		Date date = new Date();
		String time = dateFormat.format(date);
		return time;
	}
}
