package mechanics.items;

/**
 * A standard trinket that Players hold.
 * <p>
 * This is an abstract class - Items have
 * been grouped into several categories as
 * seen below. If one wishes to create another
 * class of values, one needs only extend this 
 * class, or one of the below if it's a 
 * subclass.
 * @see HealingItem
 * @see KeyItem
 * @see PromotionItem
 * @see ReviveItem
 * @see StatusItem
 * @author RehdBlob
 * @version 0.93a
 * @since 0.9
 * @since 2011.0701
 */
public abstract class Item 
{
	protected int size; 	
	// 1 = affects single, 2 = affects group
	protected String name = "";
	
	/**
	 * @deprecated
	 */
	public Item()
	{
		
	}
	
	public Item(String n)
	{		
		name = n;
	}

	
	public Item (String n, int s)
	{
		name = n;
		size = s;
	}
	
	public int use()
	{
		return 0;
	}

	@Override
	public String toString() 
	{
		return name;
	}
	
	public boolean equals(Item other)
	{
		return other.toString().equalsIgnoreCase(this.toString());
	}
	
	public boolean equals(String other)
	{
		return other.equalsIgnoreCase(this.toString());
	}
	
}
