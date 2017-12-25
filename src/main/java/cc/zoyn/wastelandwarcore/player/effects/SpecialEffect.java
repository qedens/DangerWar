package cc.zoyn.wastelandwarcore.player.effects;

public class SpecialEffect {
	private final SpecialEffectType type;
	private final long duration;
	private final int level;
	public SpecialEffect(SpecialEffectType type,long duration,int level) {
		this.type = type;
		this.duration = duration;
		this.level = level;
	}
	public SpecialEffectType getType() {
		return type;
	}
	public long getDuration() {
		return duration;
	}
	public int getLevel() {
		return level;
	}
	/**
	 * 特殊属性效果,中毒,腐蚀,减速,虚弱
	 * @author DFKK
	 *
	 */
	public enum SpecialEffectType {
		POISON,CORROSION,SLOW,WEAKNESS;
	}
}
