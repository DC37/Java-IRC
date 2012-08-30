package mechanics.items;

/**
 * Use one of these to promote a Player.
 * @author RehdBlob
 * @since 0.91
 * @version 0.93a
 * @since 2011.0717
 */
public class PromotionItem extends Item 
{
	public PromotionItem(String n)
	{
		super(n);
	}
	
	@Override
	public int use()
	{
		return -1;		
	}
}
