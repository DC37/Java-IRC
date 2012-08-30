package mechanics.calculators;
//import static java.lang.Math.*;

/**
 * Calculates the EXP gained
 * by Players at the end of battle. Found in the Player class.
 * @see Player
 * @see DamageCalc
 * @see StatsCalc
 * @author RehdBlob
 * @version 0.93a
 * @since 0.9
 * @since 2011.0701
 */
public class EXPCalc
{
	public int compute(int level, int otherLevel, int baseEXP) 
	{
		int result = baseEXP;
		result += (otherLevel-level)*5;
		return result;
	}
	
	/*public int round(double i)
	{
		if (i - floor(i) >= 0.5)
			return (int)ceil(i);
		else
			return (int)floor(i);
	}
	*/
}
