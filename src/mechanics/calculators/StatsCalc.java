package mechanics.calculators;

import mechanics.Die;
import mechanics.values.Growth;

/**
 * Calculates the stat gains at
 * player level-ups. Found in the Player class.
 * @see Player
 * @see EXPCalc
 * @see DamageCalc
 * @author RehdBlob
 * @version 0.93a
 * @since 0.9
 * @since 2011.0701
 */
public class StatsCalc
{
	public int incHP(int HP, Growth.growths mod)
	{
		int newHP = HP;
		switch (mod)
		{
		case LOW:
			newHP += 9+Die.roll();
			break;
		case MEDIUM:
			newHP += 9+Die.roll(11);
			break;
		case HIGH:
			newHP += 9+Die.roll(16);
			break;
		case VERY_HIGH:
			newHP += 9+Die.roll(21);
			break;
		case MAX:
			newHP += 9+Die.roll(26);
			break;
		}
		return newHP;
	}
	
	public int inc(int stat, Growth.growths mod)
	{
		int newStat = stat;
		switch (mod)
		{
		case ZERO:
			return newStat;
		case LOW:
			newStat += Die.roll(2);
			break;
		case MEDIUM:
			newStat += Die.roll(3);
			break;
		case HIGH:
			newStat += Die.roll(4);
			break;
		case VERY_HIGH:
			newStat += Die.roll(5);
			break;
		case MAX:
			newStat += Die.roll();
			break;
		}
		return newStat;
	}
}
