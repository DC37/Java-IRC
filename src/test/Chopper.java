package test;
import mechanics.skills.Skill;

import java.util.ArrayList;

public class Chopper 
{
	public static void main(String [] args)
	{
		Skill a = new Skill();
		String s = "90 90 90 12.12 13 14.";
		ArrayList<Integer> one = new ArrayList<Integer>();
		one = a.chop(s);
		System.out.println(one.toString());
	}
}
