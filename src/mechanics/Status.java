package mechanics;

/**
 * Any status effect that happens to occur to a 
 * Player during battle or out of battle.
 * @author RehdBlob
 * @since 0.9b
 * @version 0.93a
 * @since 2011.0704
 */
public class Status 
{
	private int type = 0;
	private String name = "";
	
	public Status (int i, String n)
	{
		type = i;
		name = n;
	}

	public void setType(int t) 
	{
		type = t;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String n) 
	{
		name = n;
	}
	
	public int effect()
	{
		return type;
	}
	
}
