package mechanics.values;

/**
 * A class that describes what a Tempo Skill will do.
 * @author RehdBlob
 * @version 0.93a
 * @since 0.93a
 * @since 2011.1223
 */
public class TempoSkillMod extends Value {

	protected static enum tempoSkill {
		MYSTIC, ATKMODIFY, DUALCAST, MULTIATK, SUMMON
	}
	
	public TempoSkillMod(String n) {
		name = n;
	}
	
}
