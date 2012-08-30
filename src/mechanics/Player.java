package mechanics;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import mechanics.calculators.*;
import mechanics.exceptions.NoStatsException;
import mechanics.items.Item;
import mechanics.items.ItemList;
import mechanics.skills.Skill;
import mechanics.skills.TempoSkill;
import mechanics.values.*;


/**
 * A multipurpose class that tracks users' progress
 * in Mario Paintasy. 
 * <p>
 * Originally, this would have been called "Character.exe"
 * or maybe even "Characterv3.00.exe" - However since the move
 * onto the JAVA platform has been a large one, and because of the
 * revamping of all of the mechanics of the game, the version has
 * been reset.
 * <p>
 * @author RehdBlob
 * @since 0.9
 * @version 0.93a
 * @since 2011.0701
 *
 */
public class Player implements Comparable<Player> {
	//Stats, attributes and skills
	protected String name   = "";
	protected String nClass = "";
	protected String pClass = "";
	
	protected int [] stats = new int [6];
	// {HP, atk, def, spAtk, spDef, spd}
	protected int [] mod   = new int[6];
	// {mod1, mod2, mod3, mod4, mod5, mod6}
	protected int TP, level, exp;
	
	protected ArrayList<Item> items     = new ArrayList<Item>();
	protected ArrayList<Skill> skills   = new ArrayList<Skill>();
	protected TempoSkill [] tempoSkills = new TempoSkill[4];
	
	protected boolean isNPC, isPromoted, isActive, isEnemy;
	protected boolean hasData = false;
	
	//Calculators and modifiers
	protected StatsCalc lvl   = new StatsCalc();
	protected DamageCalc dmg  = new DamageCalc();
	protected EXPCalc EXP     = new EXPCalc();
	
	protected int [] promotionGains = new int[6];
	
	// {HP, atk, def, spAtk, spDef, spd}
	
	//Battle-only
	protected ArrayList<Status> status = new ArrayList<Status>();
	protected int damage = 0, row;
	protected int [] battleStats;
	
	
	/**
	 * Default constructor; does not do very much as
	 * is painfully obvious when one tries to System.out.print
	 * a Player initialized by this.
	 * <p>
	 * Use of this method is discouraged; it should be used for
	 * testing purposes only.
	 * @deprecated
	 */
	public Player()
	{
		level = 1;
		exp = 0;
		name = "";
		isNPC = false;
		isPromoted = false;
		isActive = true;
		isEnemy = false;
	}
	
	/**
	 * A default constructor that automatically loads the 
	 * character specified by the string parameter given it.
	 * This will generate a Player with complete stats, skills,
	 * Tempo Skills, and modifiers. Use of this method is highly
	 * encouraged over the default Player() constructor.
	 * @param s The name of the player character to be loaded.
	 */
	public Player(String s)
	{
		loadPlayer(s);
	}
	
	/**
	 * Compares a player to another player in terms
	 * of speed, and then by level. 
	 * <p>
	 * @return The faster player will be ordered to a higher position than
	 * a slower player. However, if two players' speed stats
	 * are equal, then their levels are compared with each other, with
	 * the one with the higher level winning out. If the players 
	 * are of equal level and of equal speed, then the method
	 * returns 0.
	 * @param other The other Player to be compared to.
	 */
	@Override
	public int compareTo(Player other) 
	{
		if (stats[5] > other.stats[5])
			return 1;
		if (stats[5] < other.stats[5])
			return -1;
		if (level > other.level)
			return 1;
		if (level < other.level)
			return -1;
		return 0;
	}
	
	/**
	 * Attacks an enemy.
	 * @return An integer value indicating the
	 * raw damage that the player has done with their regular
	 * physical attack.
	 */
	public int attack(Enemy other)
	{
		damage = (int)dmg.compute(stats[1], other.stats[2]);
		return damage;
	}
	
	/**
	 * Adds a status effect to the player character.
	 * @param i The status identifier to be added to the character.
	 */
	public void addStatus(int i)
	{
		status.add(Constants.switchStatus(i));
	}
	
	/**
	 * @return Whether the Player character has status effects that
	 * need to be applied at the start of their turn.
	 */
	public boolean hasStatus() 
	{
		return !(status.size()==0);
	}	
	
	/**
	 * Applies the status effects onto the Player character.
	 */
	public void applyStatus() 
	{
		for (Status s : status)
		{
			s.effect();
		}
	}
	
	/**
	 * Increases the level of the player by 1, as long as
	 * the player is able to level up; at level 25, players
	 * may not level up until they are promoted.
	 * <p>
	 * At level 50, the level cap is reached and the player cannot
	 * level up any further.
	 */
	private void levelUp()
	{
		if (level < 25 && !isPromoted)
			{
				stats[0] = lvl.incHP(stats[0], mod[0]);
				for (int i = 1; i<stats.length; i++)
					stats[i] = lvl.inc(stats[i], mod[i]);
				level++;
			}
		else if (level >= 25 && isPromoted && level < 50)
			{
				stats[0] = lvl.incHP(stats[0], mod[0]);
				for (int i = 1; i<stats.length; i++)
					stats[i] = lvl.inc(stats[i], mod[i]);
				level++;
			}
	}
	
	/**
	 * Improves the Player character's status and makes it possible
	 * for the player to access levels beyond Level 25. It also 
	 * improves a Player's stat gains, along with opening up the 
	 * possibility of learning new skills learned after Level 25.
	 */
	public void promote()
	{
		isPromoted = true;
		levelUp();
		promotionGains();
		level--;
	}
	
	/**
	 * Increases the player's experience point amount
	 * by the specified integer value. This 
	 * supports double- or even triple- or (etc) level-ups as 
	 * the method calls itself at the end of the declaration.
	 * @param i The amount of EXP to be gained by the player.
	 */
	public void gainEXP(int i)
	{
		exp += i;
		if (exp >= 100 && level < 25)
			{
				levelUp();
				exp -= 100;
			}
		else if (exp >= 100 && level == 25 && !isPromoted)
			{
				exp = 100;
			}
		else if (exp >= 100 && level >= 25 && isPromoted)
			{
				levelUp();
				exp -= 100;
			}
		if (exp > 100)
		{
			gainEXP(0);
		}
	}
	
	/**
	 * Changes the stat modifier gains that the Player has
	 * as a result of promotion.
	 */
	private void promotionGains() 
	{
		for (int i=0; i<6; i++)
		{
			stats[i]+=promotionGains[i];
		}
	}

	/**
	 * Loads a player's text file. The text file must exist
	 * for this method to work.
	 * <p>
	 * There is a specific format to the text file that the
	 * method is designed to load. More on that later when
	 * this is actually finished.
	 * @param n The name of the player that the method
	 * is to load.
	 */
	public void loadPlayer(String n)
	{
		//Load Strings first, then ints
		hasData = true;
		String next = "";
		try
		{
			Scanner file = new Scanner(new File("Players", (n + ".txt")));
			name = file.nextLine();
			nClass = file.nextLine();
			pClass = file.nextLine();
			//Items
			while (true)
				{
					next = file.nextLine();
					if (next.equalsIgnoreCase("End"))
						break;
					else
					{
						for (int i = 0; i< ItemList.list.length; i++)
							if (ItemList.list[i].equals(next))
								items.add(ItemList.list[i]);
					}
				}
			
			//Skills
			while (true)
			{
				next = file.nextLine();
				if (next.equalsIgnoreCase("End"))
					break;
				else
					try 
						{
							skills.add(new Skill(next));
						} 
				catch (NoStatsException e) 
					{
						e.printStackTrace();
					}
			}
			
			//Tempo Skills
			for (int i = 0; i < 4; i++)
				try 
					{
						tempoSkills[i] = new TempoSkill(file.nextLine());
					} 
				catch (NoStatsException e) 
					{
						e.printStackTrace();
					}
			
			//HP, atk, def, spAtk, spDef, spd
			for (int i = 0; i < stats.length; i++)
				stats[i] = file.nextInt();
			battleStats = stats;
			level = file.nextInt();
			
			for (int i = 0; i < mod.length; i++)
				mod[i] = file.nextInt();
			
			for (int i = 0; i < promotionGains.length; i++)
				promotionGains[i] = file.nextInt();
			
			exp = file.nextInt();
			TP = file.nextInt();
			row = file.nextInt();
			isNPC = file.nextBoolean();
			isPromoted = file.nextBoolean();
			isActive = true;
			isEnemy = false;
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

	
	}
	
	
	/**
	 * Saves a player's data into a text file named after the player. 
	 * <p>
	 * Assuming that the player was valid to begin with, this method
	 * creates a text file that can be read by the loadPlayer method
	 * The intent of this method is to make it possible to close the 
	 * program without losing player data.
	 * @since 0.92a
	 * @since 2011.0725
	 */
	public void save()
	{
		try 
		{
			PrintStream writer = new PrintStream("Players\\" + name + ".txt");
			writer.println(name);
			writer.println(nClass);
			writer.println(pClass);
			for (Item i : items)
				writer.println(i.toString());
			writer.println("End");
			for (Skill s : skills)
				writer.println(s.toString());
			writer.println("End");
			for (int i = 0; i < 4; i++)
				writer.println(tempoSkills[i].toString());
			for (int i : stats)
				writer.println(i);
			writer.println(level);
			for (int i : mod)
				writer.println(i);
			for (int i : promotionGains)
				writer.println(i);
			writer.println(exp);
			writer.println(TP);
			writer.println(row);
			writer.println(isNPC);
			writer.println(isPromoted);
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("File not found!");
		}
		
	}
	
	/**
	 * Gets stats of a player character.
	 * @return Statistics of a player
	 * character, beginning with name, level, experience points
	 * whether they are promoted, and then proceeding to give the 
	 * numerical stats of that character: HP, Attack, Defense, 
	 * Special Attack, Special Defense, Speed, and then the
	 * growths per levelup for those respective stats.
	 * @throws NoStatsException 
	 */
	public String getStats() throws NoStatsException
	{
		if (!hasData)
			throw new NoStatsException();
		String out = name + "\n";
		out += "Level: " + level + "\n";
		out += "\nEXP: " + exp + "/100\n";
		if (isPromoted)
			out += "Class: " + pClass + "\n";
		else
			out += "Class: " + nClass + "\n";
		out += "\nStats: \n";
		out += "HP: " + stats[0] + "\n";
		out += "Atk: " + stats[1] + "\n";
		out += "Def: " + stats[2] + "\n";
		out += "Sp. Atk: " + stats[3] + "\n";
		out += "Sp. Def: " + stats[4] + "\n";
		out += "Spd: " + stats[5] + "\n";
		
		out += "\nGrowths: \n";
		out += "HP:" + Constants.switchGrowths(mod[0]) + "\n";
		out += "Atk: " + Constants.switchGrowths(mod[1]) + "\n";
		out += "Def: " + Constants.switchGrowths(mod[2]) + "\n";
		out += "Sp. Atk: " + Constants.switchGrowths(mod[3]) + "\n";
		out += "Sp. Def: " + Constants.switchGrowths(mod[4]) + "\n";
		out += "Spd: " + Constants.switchGrowths(mod[5]) + "\n";
		return out;
	}
	
	/**
	 * @param s The name to be compared to.
	 * @return Whether the String is equal to the 
	 * Player's name, ignoring case.
	 */
	public boolean equals(String s)
	{
		return name.equalsIgnoreCase(s);
	}
	
	/**
	 * @param other The Player to be compared to.
	 * @return Whether the other player's name is equal to
	 * this Player's name, ignoring case.
	 */
	public boolean equals(Player other)
	{
		return this.name.equalsIgnoreCase(other.name);
	}
	
	/**
	 * A comprehensive list of player character
	 * attributes.
	 * @return A full printout of a player character's
	 * variables. It should print out what the 
	 * getStats() method originally was to print out, and then
	 * also append the Items, Skills, and Tempo Skills that that
	 * player character has.
	 */
	@Override
	public String toString()
	{
		String out;
		try 
			{
				out = getStats();
			} 
		catch (NoStatsException e) 
		{
			e.printStackTrace();
			return "";
		}
		out += "\nItems: \n";
		if (items.size() == 0)
			out += "None\n";
		else
			for (Item i : items)
				out += i.toString() + "\n";
		out += "\nSkills: \n";
		for (Skill s : skills)
			out += s.toString() + "\n";
		out += "\nTempo Skills: \n";
		for (TempoSkill t : tempoSkills)
			out += t.toString() + "\n";
		return out;
	}
	
	/**
	 *Uses an item.
	 *@param i The name of the item to be used.
	 *@return The integer effect of the item to be used
	 *@see Constants
	 */
	public int use(String i) 
	{
		Item usedItem = items.get(Integer.parseInt(i));
		items.remove(items.get(Integer.parseInt(i)));
		return usedItem.use();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHP() {
		return battleStats[0];
	}
	public void setHP(int HP) {
		battleStats[0] = HP;
	}
	public int getTP() {
		return TP;
	}
	public void setTP(int tP) {
		TP = tP;
	}
	public int getAtk() {
		return stats[1];
	}
	public void setAtk(int atk) {
		stats[1] = atk;
	}
	public int getDef() {
		return stats[2];
	}
	public void setDef(int def) {
		stats[2] = def;
	}
	public int getSpAtk() {
		return stats[3];
	}
	public void setSpAtk(int spAtk) {
		stats[3] = spAtk;
	}
	public int getSpDef() {
		return stats[4];
	}
	public void setSpDef(int spDef) {
		stats[4] = spDef;
	}
	public int getSpd() {
		return stats[5];
	}
	public void setSpd(int spd) {
		this.stats[5] = spd;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public boolean isNPC() {
		return isNPC;
	}
	public void setNPC(boolean isNPC) {
		this.isNPC = isNPC;
	}
	public boolean isPromoted() {
		return isPromoted;
	}
	public void setPromoted(boolean isPromoted) {
		this.isPromoted = isPromoted;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getMod1() {
		return mod[0];
	}
	public void setMod1(int mod1) {
		mod[0] = mod1;
	}
	public int getMod2() {
		return mod[1];
	}
	public void setMod2(int mod2) {
		mod[1] = mod2;
	}
	public int getMod3() {
		return mod[2];
	}
	public void setMod3(int mod3) {
		mod[2] = mod3;
	}
	public int getMod4() {
		return mod[3];
	}
	public void setMod4(int mod4) {
		mod[3] = mod4;
	}
	public int getMod5() {
		return mod[4];
	}
	public void setMod5(int mod5) {
		mod[4] = mod5;
	}
	public int getMod6() {
		return mod[5];
	}
	public void setMod6(int mod6) {
		mod[5] = mod6;
	}
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}
	public TempoSkill[] getTempoSkills() {
		return tempoSkills;
	}
	public void setTempoSkills(TempoSkill[] tempoSkills) {
		this.tempoSkills = tempoSkills;
	}
	public int[] getPromotionGains() {
		return promotionGains;
	}
	public void setPromotionGains(int[] promotionGains) {
		this.promotionGains = promotionGains;
	}
	public ArrayList<Status> getStatus() {
		return status;
	}
	public void setStatus(ArrayList<Status> status) {
		this.status = status;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public String getnClass() {
		return nClass;
	}
	public void setnClass(String nClass) {
		this.nClass = nClass;
	}
	public String getpClass() {
		return pClass;
	}
	public void setpClass(String pClass) {
		this.pClass = pClass;
	}
	public int getFullHP() 
	{
		return stats[0];
	}
	public void setFullHP(int HP)
	{
		stats[0] = HP;
	}
}
