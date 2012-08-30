package test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Home 
{
	public static void main(String[] args)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String day = dateFormat.format(date).replace('/', '_');
		System.out.println(day);
	}
}
