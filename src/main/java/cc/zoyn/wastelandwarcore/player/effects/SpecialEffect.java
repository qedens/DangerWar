package cc.zoyn.wastelandwarcore.player.effects;

public class SpecialEffect {
	private SpecialEffectType type;
	private long duration;
	private int level;
	private long nowDuration;
	
	public SpecialEffect(SpecialEffectType type,long duration,int level) {
		this.type = type;
		this.duration = duration;
		this.level = level;
	}
	public void setNowDuration(long nowDuration) {
		this.nowDuration = nowDuration;
	}
	public long getNowDuration() {
		return nowDuration;
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
	 * 特殊属性效果集: 中毒,腐蚀,减速,虚弱,破甲(玩家自身的护甲亏损)
	 * @author DFKK
	 *
	 */
	public enum SpecialEffectType {
		POISON,CORROSION,SLOW,WEAKNESS,ARMORBREAK;
	}
}