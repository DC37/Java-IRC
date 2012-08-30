package mechanics;
import static java.lang.Math.*;

/**
 * A simple die roll generator.
 * <p>
 * @author RehdBlob
 * @since 0.9a
 * @version 0.93a
 * @since 2011.0702
 */
public class Die 
{
	/** Gives a standard 6-sided die roll.
	*/
	public static long roll()
	{
		return (long)(random()*6) + 1;
	}
	
	/**
	Gives an integer die roll with the specified amount of sides.
	<p>
	If the sides specified is <= 0, this returns 1.
	@param i The number of sides the die to be rolled is to have.
	*/
	public static long roll(long i)
	{

		if (i <= 0)
			return 1;
		return (long)(random()*i) + 1;
	}
	
	/**
	Gives a decimal die roll with the specified amount of sides.
	<p> 
	Using this method will give extremely interesting results as one 
	cannot normally roll dice with fractions of sides. Useful for random
	number generation.
	If the sides specified is <= 0, this returns 1.
	@param i The number of sides the die to be rolled is to have.
	*/
	public static double roll(double i)
	{
		if (i<=0)
			return 1.0;
		return (random()*i) + 1;
	}
}
