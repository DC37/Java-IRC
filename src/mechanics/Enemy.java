package mechanics;


/**
 * An enemy class that is complementary to the Player class.
 * <p>
 * For use with the EnemyBot, this is the opposition to the
 * characters in the Paintasy realm.
 * @author RehdBlob
 * @since 0.9a
 * @version 0.93a
 * @since 2011.0702
 */
public class Enemy extends Player 
{
	/**
	 * Determines whether the Enemy's stats are checkable
	 * by the !check function from a Player character.
	 */
	private boolean checkable = true;
	
	/**
	 * Generates a generic Enemy with almost
	 * no stats whatsoever. Use of this method is
	 * not encouraged except for testing purposes.
	 * @deprecated
	 */
	public Enemy()
	{
		super();
		isEnemy = true;
		isNPC = true;
	}

	public boolean isCheckable() {
		return checkable;
	}

	public void setCheckable(boolean tf) {
		checkable = tf;
	}
	
}
