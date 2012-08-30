package mechanics.items;

/**
 * Use these to heal status effects
 * dealt to Players.
 * @author RehdBlob
 * @version 0.93a
 * @since 0.9
 * @since 2011.0701
 */
public class StatusItem extends Item 
{
	private int healEffect;
	
	public StatusItem(String n) 
	{
		super(n);
	}
	
	public StatusItem(String n, int ef)
	{
		super(n);
		healEffect = ef;
	}
	
	public StatusItem(String n, int sz, int ef)
	{
		super(n,sz);
		healEffect = ef;
	}


	public int use()
	{
		return healEffect;
	}
	
}
