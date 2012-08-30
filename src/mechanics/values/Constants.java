package mechanics.values;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import mechanics.Status;


/**
 * A constants file for checking status effects,
 * growths, etc. This class initializes itself with a 
 * configuration file that contains all of the possible 
 * growths, effects, Tempo Skill modifiers, and elements.
 *<p>
 * @author RehdBlob
 * @since 0.9b
 * @version 0.93a
 * @since 2011.0704
 */
public class Constants
{
	private Hashtable<String, Value> constants = 
			new Hashtable<String, Value>();
	private Hashtable<String, valueLink> link = 
			new Hashtable<String, valueLink>();
	
	private enum valueLink {
		statInc, statDec, growth, element, tempoSkillMod
	}
	
	
	/**
	 * Initializes a sort of a database of constants that the PaintasyBot 
	 * will be accessing throughout its operation.
	 * @param f The configuration file with the constants table to be read.
	 * @since 0.93a
	 * @since 2011.1223
	 */
	public Constants(File f)
	{
		linkInit();
		try {
			Scanner file = new Scanner(f);
			while(file.hasNextLine()) {
				String temp = file.nextLine();
				String key = temp.substring(0,temp.indexOf(' '));
				String value = temp.substring(temp.indexOf(' ') + 1);
				switch(link.get(value.toLowerCase())) {
					case statInc:
						constants.put(key, new StatInc(value)); 
						break;
					case statDec:
						constants.put(key, new StatDec(value)); 
						break;
					case growth:
						constants.put(key, new Growth(value)); 
						break;
					case element:
						constants.put(key, new Element(value)); 
						break;
					case tempoSkillMod:
						constants.put(key, new TempoSkillMod(value)); 
						break;
					default:
						break;
					}
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void linkInit() {
		link.put("statinc", valueLink.statInc);
		link.put("statdec", valueLink.statDec);
		link.put("growth", valueLink.growth);
		link.put("element", valueLink.element);
		link.put("buff", valueLink.statInc);
		link.put("debuff", valueLink.statDec);
		link.put("temposkillmod", valueLink.tempoSkillMod);
	}

	/**
	 * This constant signifies the end of a list in
	 * a text file.
	 */
	public static final int ENDOFLIST = -2;
	
	/**
	 * When there is no effect, element, time constant,
	 * etc, this variable should be used.
	 */
	public static final int NONE = -1;
	

	/* ****************************
	 *   Durations of Effects     *
	 ******************************/
	public static final int PERMANENT = 0;
	/*
	If "1" then the possible values are 1
	If "2" then the possible values are 1-2
	If "3" then the possible values are 1-3
	If "4" then the possible values are 1-4
	If "5" then the possible values are 2-5
	If "6" then the possible values are 2-6
	*/
	
	/**
	 * A quick way to determine stat growths through
	 * integers.
	 * @param i The integer in question when checking
	 * character stat growths
	 * @return Either ZERO, LOW, MEDIUM, HIGH, VERY HIGH
	 * or MAX.
	 */
	public static String switchGrowths(int i) 
	{
		/*switch (i)
		{
			case ZERO:
				return "ZERO";
			case LOW:
				return "LOW";
			case MEDIUM:
				return "MEDIUM";
			case HIGH:
				return "HIGH";
			case VERY_HIGH:
				return "VERY HIGH";
			case MAX:
				return "MAX";
		}*/
		return "";
	}
	
	/**
	 * @param i The status effect identifier that is to
	 * be compared.
	 * @return The appropriate Status effect that is given
	 * by the int i.
	 * @since 0.92b
	 * @since 2011.0729
	 */
	public static Status switchStatus(int i)
	{
		/*switch(i)
		{
		case KO:
			return new Status(0,"KO");
		case POISON:
			return new Status(1,"POISON");
		case SLOW:
			return new Status(2,"SLOW");
		case STUNNED:
			return new Status(3,"STUNNED");
		case BLIND:
			return new Status(4,"BLIND");
		case STOP:
			return new Status(5,"STOP");
		case SLEEP:
			return new Status(6,"SLEEP");
		case MUTE:
			return new Status(7,"MUTE");
		case UNMOTIVATED:
			return new Status(8,"UNMOTIVATED");
		case BLOCKADE:
			return new Status(9,"BLOCKADE");
		case RAGE:
			return new Status(10,"RAGE");
		}*/
		return new Status(-1,"NONE");
	}
	
}
