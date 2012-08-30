package mechanics.values;


/**
 * A general class for effects
 * @author RehdBlob
 * @version 0.93a
 * @since 0.93a
 * @since 2011.1223
 */
public abstract class Effect extends Value {
	
	protected String effect = "";
	protected int stageValue = 0;
	
	/**
	 * Indicates whether an effect affects one self, one
	 * other, a group, either a single person or a group, or
	 * everyone on a side.
	 * @since 0.93a
	 * @since 2011.1222
	 */
	protected enum modifier {
		SELF, OTHER, GROUP, SNGLGRP, ALL
	}
	
	/**
	 * An effect list; the effects are as follows:
	 * <p>
	 * KO = Knocked out; obvious effect.
	 * <p>
	 * POISON = Will lose a certain amount of HP proportional
	 * to total HP whenever the afflicted attempts to attack. 
	 * Lasts ~3-5 turns.
	 * <p>
	 * SLOW = The afflicted loses a stage of speed.
	 * <p>
	 * STUNNED = The afflicted cannot use physical attacks but
	 * can still use special attacks.
	 * <p>
	 * BLIND = The afflicted loses a stage of accuracy.
	 * <p>
	 * STOP = The character cannot move at all. Their 
	 * turn is skipped - this lasts ~2-5 turns.
	 * <p>
	 * SLEEP = The character that is asleep is automatically put in the 
	 * back row and begins to regain health as long as they are in
	 * this state. They also are put on "inactiveDefend" mode and 
	 * are not as easily hit. However, they cannot do anything,
	 * like with STOP.
	 * <p>
	 * MUTE = Cannot use special attacks.
	 * <p>
	 * UNMOTIVATED = The Tempo Power bar will not rise in this condition.
	 * <p>
	 * BLOCKADE = The afflicted user cannot use values in this state.
	 * <p>
	 * RAGE = The afflicted user has been hit by a fit of RAGE and
	 * automatically attacks a random enemy at the start
	 * of their turns. Usually lasts ~2-5 turns.
	 * <p>
	 * FREEZE = Same effect as STOP. Lasts ~2-5 turns.
	 * <p>
	 * BURN = Same effect as POISON. Lasts ~2-5 turns.
	 * <p>
	 * HEAL = Heals a character.
	 * <p>
	 * ATKUP = Increases attack by one stage.
	 * <p>
	 * DEFUP = Increases defense by one stage.
	 * <p>
	 * SPATKUP = Increases special attack by one stage.
	 * <p>
	 * SPDEFUP = Increases special attack by one stage.
	 * <p>
	 * HASTE = Increases speed by one stage.
	 * <p>
	 * SHIELD = Protects from attack for one turn.
	 * <p>
	 * @since 0.93a
	 * @since 2011.1222
	 */
	protected enum effect {
		KO, POISON, SLOW, STUNNED, BLIND, STOP, SLEEP,
		MUTE, UNMOTIVATED, BLOCKADE, RAGE, FREEZE, BURN,
		HEAL, ATKUP, DEFUP, SPATKUP, SPDEFUP, HASTE, SHIELD
	}
	
	public abstract int affect();
	public abstract int duration();
	
	public abstract void wearOff();
	public abstract void renew();
}
