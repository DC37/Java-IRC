package mechanics;

/***********************************************
 A "controller" class - this dictates the turn actions
 and battle actions of the game. 
 <p>
 One could say it is a like a management firm that 
 orders things from a factory.
 It also dictates the turn order and mechanics
 of the battle scene.
 @param list The PlayerList that holds Players and that has methods
 that edit said list.
 @param msg A message String that will activate the run() method
 of the MPBot if any of the Controller's methods happens to generate
 a message.
 @param inBattle A boolean that keeps track of whether Players
 and Enemies are in battle.
 @param turnNumber Gives the turn number of the battle. This tells the 
 Controller which Player to select next for their turn.
 
 @author RehdBlob
 @since 0.9a
 @since 2011.0702
 @version 0.93a
************************************************/

public class Controller //implements Runnable
{
	private boolean menu = true;
	private boolean inBattle;
	
	private int turnNumber;
	private int select = 0;
	
	private PlayerList list;

	private String msg = "";


	/**
	 * Initializes the Controller class and creates
	 * a new PlayerList instance.
	 */
	public Controller()
	{
		list = new PlayerList();
		inBattle = false;
		turnNumber = 0;
	}
	
	/**
	 * Begins a typical RPG-style battle.
	 */
	public void startBattle()
	{
		inBattle = true;
		addMessage("*Battle Swirl*");
		addMessage(list.get(turnNumber).getName() + "'s turn!");
		battleTurn();
	}
	
	/**
	 * The general structure for every battle turn in the RPG.
	 * The order of a battle:
	 *<p>
	 *1. Check statuses and apply status effects
	 *<p>
	 *2. Main Menu
	 *<p>
	 *3. Main Menu Action / modifiers
	 *<p>
	 *4. Main Menu Action application + calculators
	 *<p>
	 *5. Damage calculation / healing / status effect application
	 *<p>
	 *6. Check turn order
	 *<p>
	 *7. Next turn
	 *<p>
	 */
	private void battleTurn() 
	{
		if(list.get(turnNumber).hasStatus())
			list.get(turnNumber).applyStatus();
		mainMenu();
		while (menu);
		applyAction(select);
	}

	private void applyAction(int s) 
	{
		switch(s)
		{
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		default: 
			break;
		}
	}

	/**
	 * Outputs the main menu for each Player character
	 * at the start of every turn. It displays the current
	 * HP, TP, and the options that each player character
	 * may use.
	 */
	private void mainMenu() 
	{
		addMessage("*Main Menu*");
		addMessage("HP: "+list.get(turnNumber).getHP()
				+ "/" + list.get(turnNumber).getFullHP());
		addMessage("TP: "+list.get(turnNumber).getTP()
				+ "/100");
		addMessage("*Attack");
		addMessage("*Defend");
		addMessage("*Skills");
		addMessage("*Tempo Skills");
		addMessage("*Items");
		addMessage("*Check");
		addMessage("*Run");
	}

	/**
	 * Checks whether a Player is active and 
	 * participating in the RPG.
	 * @param s Name of the Player in question.
	 * @return Whether the player is participating.
	 */
	public boolean isPlaying(String s)
	{
		return list.isValid(s);
	}
	
	/**
	 * Adds a new Player character to the list of Players.
	 * @param s The name of the player to be added to the game.
	 */
	public void addPlayer(String s)
	{
		list.add(s);
	}
	
	/**
	 * Increases the turn count by 1 while in battle.
	 * <p>
	 * Does nothing if not in battle.
	 */
	public void nextTurn()
	{
		if (inBattle)
		{
			if (turnNumber >= list.getSize())
				turnNumber = 0;
			else
				turnNumber++;
			msg = list.get(turnNumber).getName();
			msg += "\'s turn!";
		}
		else
			return;
	}
	
	/**
	 * A generic attack by an attacker unto a defender.
	 * @param attacker The attacker.
	 * @param defender The defender.
	 * @param effect The effect of the attack.
	 */
	public void attack(String attacker, String defender, int effect)
	{
		
	}
	
	/**
	 * Causes a character to defend.
	 * @param sender The character to defend.
	 */
	public void defend(String sender) 
	{
		
	}
	
	/**
	 * When a person is inactive for too long, they are
	 * put into the inactive defend position and therefore
	 * have a lower chance of being hit, along with an auto-
	 * defend mechanism.
	 * @param inactive The person going inactive.
	 */
	public void inactiveDefend(String inactive)
	{
		list.setInactive(list.get(inactive));
	}
	
	/**
	 * @return Whether the Controller is in the battle
	 * state.
	 */
	public boolean isInBattle()
	{
		return inBattle;
	}
	
	/**
	 * Ends the battle.
	 */
	public void endBattle()
	{
		inBattle = false;
	}
	
	/**
	 * @return Whether the Controller has a message
	 * for the IRC bot to display.
	 */
	public boolean hasMessage() 
	{
		return !(msg == null) & !msg.isEmpty();
	}
	
	/**
	 * @return Whether there is more than one message in the
	 * queue for the IRC Bot to display.
	 * @since 0.92a
	 * @since 2011.0726
	 */
	public boolean hasLotsOfMessages() 
	{
		return msg.indexOf('|') != msg.lastIndexOf('|');
	}
	
	/**
	 * @return The next string in the pipeline that 
	 * is to be displayed by the IRC bot.
	 */
	public String nextMessage() 
	{
			String temp = msg.substring(0,msg.indexOf('|'));
			String temp2 = msg.substring(msg.indexOf('|')+1);
			if(hasLotsOfMessages())
				msg = temp2;
			else
				msg = "";
			return temp;
	}
	
	/**
	 * Adds a message for the IRC Bot to respond with.
	 * <p>
	 * If the String msg is not currently empty, the 
	 * String queue will be used instead.
	 * @param s The message to be added.
	 */
	public void addMessage(String s) 
	{
			msg += s + '|';
	}
	
	/**
	 * @return When a player decides to check the 
	 * statistics of an Enemy character, this 
	 * function will give the HP, atk, def, sp. atk,
	 * sp. def, and spd of that Enemy if the stats are
	 * set to visible.
	 */
	public String checkEnemyStats() 
	{
		return null;
	}
	
	public String result()
	{
		return null;
	}

	/*@Override
	public void run() 
	{
		while (true)
		{
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
	}
*/
	/**
	 * For use right before the IRC bot closes, this
	 * method saves the data in the PlayerList.
	 */
	public void saveSettings() 
	{
		list.saveAllPlayers();
	}
	
	/**
	 * Uses an item that a Player holds.
	 * @param s The Player using the item.
	 * @param i The item to be used.
	 * @param target The target that the item is to be used on.
	 */
	public void useItem(String s, String i, String target)
	{
		int effect = list.get(s).use(i);
		applyEffect(target, effect);
	}

	/**
	 * Applies a status effect.
	 * @param target The target of the effect.
	 * @param effect The effect to be applied.
	 */
	private void applyEffect(String target, int effect) 
	{
		if (effect == -1)
			list.get(target).promote();
	}

	/**
	 * @param s The sender querying whether they are next.
	 * @return Whether the sender is next in line in battle.
	 */
	public boolean isNext(String s) 
	{
		return list.get(turnNumber).equals(s);
	}

	/**
	 * Opens the skills menu for the Player that has
	 * requested this action. Using a skill will end
	 * the player's turn.
	 * @param sender
	 * @since 0.92a
	 * @since 2011.0725
	 */
	public void openSkillsMenu(String sender) 
	{
		
	}

	/**
	 * Opens the Tempo Skills menu for the Player that has
	 * requested this action. Using a tempo skill will open the
	 * associated modified menu.
	 * @param sender
	 * @since 0.92a
	 * @since 2011.0725
	 */
	public void openTempoSkillsMenu(String sender) 
	{
		
	}

	/**
	 * Attempts to run from the battle - this applies to the
	 * entire group. Each character will have a different chance
	 * of running depending on their speed, HP, and status
	 * effects. However, if any one person succeeds in running, 
	 * the entire group will flee from battle. If a person selects
	 * this option, it will end their turn.
	 * @param sender The Player requesting to run from battle.
	 * @since 0.92a
	 * @since 2011.0725
	 */
	public void escape(String sender) 
	{
		
	}

}
