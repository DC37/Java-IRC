package mechanics.skills;

import mechanics.exceptions.NoStatsException;

/**
 * A Skill class learnable only through
 * a certain event.
 * @author RehdBlob
 * @since 0.91
 * @version 0.93a
 * @since 2011.0717
 */
public class EventSkill extends Skill 
{
	/**
	 * Tells whether the event skill has been
	 * learned or not.
	 */
	private boolean isLearned;
	
	public EventSkill() 
	{
		
	}

	/**
	 * Initializes a new Event Skill, complete
	 * with whether the skill has been learned or not.
	 * @param s The String representation of the event
	 * skill.
	 * @throws NoStatsException If the temporary array
	 * contains fewer elements than needed, then the 
	 * method will throw this exception.
	 */
	@SuppressWarnings("unchecked")
	public EventSkill(String s) throws NoStatsException 
	{
		super(s);
		if (tempArray.size()<6)
			throw new NoStatsException();
		switch(tempArray.get(5))
		{
		case 0:
			isLearned = false;
			break;
		case 1:
			isLearned = true;
			break;
		}
		bundle.add(new Boolean(isLearned));
	}
	
	@Override
	public String toString()
	{
		String out = super.toString();
		if (isLearned)
			out += " 1";
		else
			out += " 0";
		return out;
	}

}
