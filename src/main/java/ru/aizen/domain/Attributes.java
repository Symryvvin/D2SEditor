package ru.aizen.domain;

import java.util.HashMap;

public class Attributes extends HashMap<String, Integer> {
    public static final String STRENGTH = "Strength";
    public static final String ENERGY = "Energy";
    public static final String DEXTERITY = "Dexterity";
    public static final String VITALITY = "Vitality";
    public static final String STAT_POINTS = "Stat points";
    public static final String SKILL_POINTS = "Skill points";
    public static final String HP = "Hit point";
    public static final String MAX_HP = "Max hit points";
    public static final String MP = "Mana points";
    public static final String MAX_MP = "Max mana points";
    public static final String SP = "Stamina points";
    public static final String MAX_SP = "Max stamina points";
    public static final String LEVEL = "Level";
    public static final String EXPERIENCE = "Experience";
    public static final String GOLD = "Gold";
    public static final String GOLD_IN_STASH = "Gold in stash";

    public Attributes() {
    }

    @Override
    public Integer put(String key, Integer value) {
        return super.put(key, value);
    }
}
