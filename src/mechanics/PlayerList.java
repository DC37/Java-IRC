package mechanics;

import java.util.ArrayList;
import java.util.Collections;



/**
 * A simple list of Players.
 * <p>
 * This class is meant to hold the active,
 * inactive, NPC, and enemy lists for battles and for
 * gameplay. 
 * @author RehdBlob
 * @since 0.9
 * @version 0.93a
 * @since 2011.0701
 */
public class PlayerList 
{
	private ArrayList<Player> pList;
	private ArrayList<Player> turns;
	private ArrayList<String> inactive;
	private ArrayList<Enemy>  enemyList;
	private ArrayList<NPC>    NPCList;
	
	public PlayerList()
	{
		pList = new ArrayList<Player>();
		turns = new ArrayList<Player>();
		inactive = new ArrayList<String>();
		enemyList = new ArrayList<Enemy>();
		NPCList = new ArrayList<NPC>();
	}
	
	public int getSize()
	{
		return turns.size();
	}
	
	public void add(String s)
	{
		Player nP = new Player(s);
		add(nP);
	}
	
	public void add(Player p)
	{
		pList.add(p);
		turns.add(p);
		setTurnOrder();
	}
	
	public void addEnemy(Enemy e)
	{
		turns.add(e);
		enemyList.add(e);
		setTurnOrder();
	}
	
	public void remove(Player p)
	{
		pList.remove(p);
		turns.remove(p);
		if (inactive.indexOf(p.getName()) != -1)
			inactive.remove(p.getName());
		setTurnOrder();
	}
	
	public void removeEnemy(Enemy e)
	{
		turns.remove(e);
		enemyList.remove(e);
		setTurnOrder();
	}
	
	/**
	 * @param turnNumber The turn number that the list
	 * is to search at.
	 * @return A full Player object situated at the 
	 * turn number that is queried.
	 */
	public Player get(int turnNumber) 
	{
		if (turns.size() < turnNumber - 1)
			return null;
		return turns.get(turnNumber);
	}
	
	
	/**
	 * @param n The name of the Player to return.
	 * @return A full Player object if the given string 
	 * is a valid and participating player.
	 */
	public Player get(String n)
	{
		for (Player p : pList)
			if (n.equals(p.getName()))
				return p;
		return null;
	}
	
	/**
	 * @param p The Player to return.
	 * @return The Player object if the parameter p is valid
	 * in the list.
	 */
	public Player get(Player p)
	{
		int a = pList.indexOf(p);
		if (a!=-1)
			return pList.get(a);
		else
			return null;
	}
	
	public int getTotalTurns()
	{
		return pList.size()-inactive.size();
	}
	
	/**
	 * After a certain amount of time, participants may
	 * set a player into the inactive mode if the person
	 * is taking too long to respond (the threshold can be
	 * set by room admins of the IRC channel). 
	 * @param p The Player to be set inactive.
	 */
	public void setInactive(Player p)
	{
		inactive.add(p.getName());
	}
	
	/**
	 * Makes a previously-inactive player active again.
	 * @param p The Player to be made active again.
	 */
	public void setActive(Player p)
	{
		if (inactive.indexOf(p.getName())!=-1)
			inactive.remove(p.getName());
		else 
			return;
	}
	
	
	/**
	 * Sorts the turns list to determine who is going at what time.
	 * This makes use of the Player's compareTo() method.
	 */
	private void setTurnOrder()
	{
		Collections.sort(turns);
	}
	
	/**
	 * Checks whether the participating player is actually playing in the 
	 * game as according to the PlayerList.
	 * @param s The name of the player to be checked.
	 * @return Whether the player is valid or not as according to the 
	 * PlayerList. Ideally, it will return "true" if the player is participating
	 * and "false" if they are not.
	 */
	public boolean isValid(String s)
	{
		for (Player p : pList)
			if (p.getName().equalsIgnoreCase(s))
				return true;
		return false;
	}

	/**
	 * Exports the entire PlayerList's stats into their respective files.
	 * This function should be used before the IRC Bot thread terminates to prevent 
	 * loss of data.
	 */
	public void saveAllPlayers() 
	{
		for (Player p : pList)
			p.save();
		for (Enemy e : enemyList)
			e.save();
		for (NPC n : NPCList)
			n.save();
	}


}
