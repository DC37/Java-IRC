package mechanics.items;

/**
 * This is a key item. It is generally
 * more important than other values, and when
 * used, can have a variety of effects.
 * @author RehdBlob
 * @since 0.9b
 * @version 0.93a
 * @since 2011.0704
 */
public class KeyItem extends Item
{

	public KeyItem(String n) 
	{
		super(n);
	}
	
	public KeyItem (String n, int sz)
	{
		super(n,sz);
	}
	
	public int use()
	{
		return 100;
	}
	
	
}
