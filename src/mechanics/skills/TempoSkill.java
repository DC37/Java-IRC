package mechanics.skills;

import java.util.ArrayList;

import mechanics.exceptions.NoStatsException;


/**
 * Somewhat like the Wild Arms
 * Force meter skills; these are tempo
 * skills for use with the main RPG.
 * @author RehdBlob
 * @since 0.9c
 * @version 0.93a
 * @since 2011.0705
 */
public class TempoSkill 
{
	private int cellNumber;
	private int modifier;
	private String name = "";
	@SuppressWarnings("rawtypes")
	private ArrayList bundle = new ArrayList();
	
	public TempoSkill(String s) throws NoStatsException
	{
		name = s.substring(0, s.indexOf(' '));
		ArrayList<Integer> temp = chop (s.substring(s.indexOf(' ')+1));
		if(temp.size() < 2)
			throw new NoStatsException();
		cellNumber = temp.get(0);
		modifier = temp.get(1);
		bundle();
	}
	
	@SuppressWarnings("unchecked")
	private void bundle() 
	{
		bundle.add(new String(name));
		bundle.add(new Integer(cellNumber));
		bundle.add(new Integer(modifier));
	}

	public ArrayList<Integer> chop(String s)
	{
		String tempString = s;
		ArrayList<Integer> chopped = new ArrayList<Integer>();
		while (true)
		{
			if (tempString.indexOf(' ') == -1)
				break;
			else
			{
				Integer addition = new Integer(
						tempString.substring(0,tempString.indexOf(' ')));
				if (addition == -2)
				{
					if (tempString.indexOf(' ') == -1)
						break;
					else
						{
							tempString = tempString.substring(tempString.indexOf(' ')+1);
							break;
						}
				}
				chopped.add(addition);
				if (tempString.indexOf(' ') == -1)
					break;
				else
					tempString = tempString.substring(tempString.indexOf(' ')+1);
			}

		}
		return chopped;
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList use()
	{
		return bundle;
	}
	
	@Override
	public String toString()
	{
		String out = name + " ";
		out += cellNumber + " ";
		out += modifier + " ";
		return out;
	}
}
