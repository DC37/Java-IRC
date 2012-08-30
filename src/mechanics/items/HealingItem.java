package mechanics.items;

/**
 * A standard item that will heal HP when used.
 * @author RehdBlob
 * @version 0.93a
 * @since 0.9
 * @since 2011.0701
 */
public class HealingItem extends Item 
{
	private int healAmt;
	protected int statusHeal = 0;
	//1 means it can heal all status effects
	protected boolean canHealFromKO = false;
		
	public HealingItem(String n, int h)
	{
		super(n);
		healAmt = h;
	}
	
	public HealingItem(String n, int s, int h)
	{
		super(n, s);
		healAmt = h;
	}
	
	public HealingItem(String n, int s, int h, int st)
	{
		super(n, s);
		healAmt = h;
		statusHeal = st;
	}

	public int use()
	{
		return healAmt;
	}
	
}
