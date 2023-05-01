package com.xjdl.study.consts.enumConst;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
enum EnumConstDemoTest3 {
	NULL(null, "Null"),
	BAT(Bat.class, "Bat"),
	SLIME(Slime.class, "Slime"),
	BOSS(Boss.class, "Boss");

	private final Class<?> clz;
	private final String code;
	private Monster monster;

	EnumConstDemoTest3(Class<?> clz, String code) {
		this.clz = clz;
		this.code = code;
		this.monster = getInstance();
	}

	public String getCode() {
		return this.code;
	}

	public Monster getMonster() {
		return monster;
	}

	private Monster getInstance() {
		if (clz == null) {
			return monster;
		}
		try {
			monster = (Monster) clz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("", e);
		}
		return monster;
	}
}

@Slf4j
class Monster {
	public Monster() {
	}

	public static Monster getInstance(String name) {
		// fixme 这里为什么不能用stream替换？ call()也是一样
		for (EnumConstDemoTest3 item : EnumSet.allOf(EnumConstDemoTest3.class)) {
			if (item.getCode().equalsIgnoreCase(name)) {
				return item.getMonster();
			}
		}
		return null;
	}

	public String call() {
		String simpleName = this.getClass().getSimpleName();
		for (EnumConstDemoTest3 value : EnumConstDemoTest3.values()) {
			if (simpleName.equals(value.name()))
				break;
		}
		return "This is " + simpleName;
	}

}

@Slf4j
class Bat extends Monster {
	public Bat() {
		log.info("Bat构造完成");
	}
}

@Slf4j
class Slime extends Monster {
	public Slime() {
		log.info("Slime构造完成");
	}
}

@Slf4j
class Boss extends Monster {
	public Boss() {
		log.info("Boss构造完成");
	}
}

@Slf4j
class Joker extends Monster {
	public Joker() {
		log.info("Joker构造完成");
	}
}

/**
 * 枚举类实例化对象
 */
@Slf4j
public class EnumConstDemo3 {
	public static void main(String[] args) {
		log.info("{}", Monster.getInstance("baT"));
		log.info("{}", Monster.getInstance("BaT"));
		log.info("{}", EnumConstDemoTest3.BAT.getMonster());

		log.info("{}", Monster.getInstance("sliMe"));
		log.info("{}", Monster.getInstance("sLimE"));
		log.info("{}", EnumConstDemoTest3.SLIME.getMonster());

		log.info("{}", Monster.getInstance("BoSs"));
		log.info("{}", Monster.getInstance("BOSs"));
		log.info("{}", EnumConstDemoTest3.BOSS.getMonster());

		log.info("{}", Monster.getInstance("nuLl"));
		log.info("{}", EnumConstDemoTest3.NULL.getMonster());
	}
}
