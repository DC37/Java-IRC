/**
 * 
 */
package mechanics;

/**
 * Meant for an NPC bot that will participate in battles alongside 
 * Players. The AI for this class has not been programmed as 
 * of 2011.0724
 * @author RehdBlob
 * @since 0.92
 * @since 2011.0724
 * @version 0.93a
 */
public class NPC extends Player {

	/**
	 * Generates a generic NPC with almost no
	 * stats whatsoever. Use of this method is not
	 * encouraged except for testing purposes.
	 * @deprecated
	 */
	public NPC() 
	{
		super();
		isNPC = true;
	}

}
