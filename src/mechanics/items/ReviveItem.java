package mechanics.items;

/**
 * Use one of these to revive a Player
 * or group of Players from KO.
 * @author RehdBlob
 * @since 0.9b
 * @version 0.93a
 * @since 2011.0704
 */
public class ReviveItem extends HealingItem 
{
	public ReviveItem(String n, int hp) 
	{
		super(n, hp);
		canHealFromKO = true;
	}
	
	public ReviveItem(String n, int sz, int hp)
	{
		super (n,sz,hp);
		canHealFromKO = true;
	}
	

}
