package mechanics.skills;

import java.util.ArrayList;

import mechanics.exceptions.NoStatsException;


/**
 * More like an attack that is used during
 * battle, but there will be some skills that may 
 * be used "on the field."
 * @author RehdBlob
 * @since 0.9c
 * @version 0.93a
 * @since 2011.0705
 */
public class Skill 
{
	protected String name = "";
	protected String tempString = "";
	protected int levelLearned;
	protected int basePower, baseAccuracy;
	protected ArrayList<Integer> effect, effectAccuracy, tempArray;
	protected int duration;
	protected int element;
	protected boolean isPhysical;
	/**
	 * An ArrayList that contains all the different parameters
	 * of this Skill.
	 */
	@SuppressWarnings("rawtypes")
	protected ArrayList bundle = new ArrayList();

	
	public Skill()
	{
		
	}
	
	/**
	 * Default constructor that creates a Skill given a string
	 * with the correct parameters.
	 * @param s The string to be converted into a Skill.
	 * @throws NoStatsException If the String representation of the
	 * skill does not contain enough parameters.
	 */
	public Skill(String s) throws NoStatsException
	{
		name = s.substring(0,s.indexOf(' '));
		if (name.equals(""))
			throw new NoStatsException();
		tempString = s.substring(s.indexOf(' ')+1);
		if (tempString.length() < 20)
			throw new NoStatsException();
		levelLearned = Integer.parseInt(tempString.substring(0,tempString.indexOf(' ')));
		tempString = tempString.substring(tempString.indexOf(' ')+1);
		effect = chop(tempString);
		effectAccuracy = chop(tempString);
		tempArray = chop(tempString);
		if (tempArray.size() < 5)
			throw new NoStatsException();
		basePower = tempArray.get(0);
		baseAccuracy = tempArray.get(1);
		switch(tempArray.get(2))
			{
			case 0:
				isPhysical = false;
				break;
			case 1:
				isPhysical = true;
				break;
			}
		duration = tempArray.get(3);
		element = tempArray.get(4);
		bundle();
	}
	

	/**
	 * "Slices" a String into its respective Integer values.
	 * 
	 * @param s
	 * @return
	 */
	public ArrayList<Integer> chop(String s)
	{
		tempString = s;
		ArrayList<Integer> chopped = new ArrayList<Integer>();
		while (true)
		{
			if (tempString.indexOf(' ') == -1)
				break;
			else
			{
				Integer addition;
				try
					{
						addition = new Integer(
							tempString.substring(0,tempString.indexOf(' ')));
					}
				catch (NumberFormatException e)
					{
						addition = new Integer(0);
					}
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
	
	/**
	 * @return A compact ArrayList of all of the skill's parameters
	 * for processing.
	 */
	@SuppressWarnings("rawtypes")	
	public ArrayList use()
	{	
		return bundle;
	}
	
	/**
	 * Returns a String that can be re-read by
	 * this same class to create another skill.
	 * Used mostly in save/load functions of the 
	 * Player class.
	 */
	@Override
	public String toString()
	{
		String out = name;
		out += " " + levelLearned;
		for (Integer i : effect)
			out+=" " + i.toString();
		out+=" -2";
		for (Integer i : effectAccuracy)
			out+=" " + i.toString();
		out+=" -2 ";
		out+=basePower + " ";
		out+=baseAccuracy+" ";
		if(isPhysical)
			out+="1 ";
		else
			out+="0 ";
		out+=duration + " ";
		out+=element + " ";
		return out;
	}
	
	/**
	 * Creates an ArrayList holding a compact version of all
	 * the parameters of the Skill.
	 */
	@SuppressWarnings("unchecked")
	protected void bundle() 
	{
		bundle.add(name);
		bundle.add(effect);
		bundle.add(effectAccuracy);
		bundle.add(new Integer(basePower));
		bundle.add(new Integer(baseAccuracy));
		bundle.add(new Boolean(isPhysical));
		bundle.add(new Integer(duration));
		bundle.add(new Integer(element));
	}
	
}
