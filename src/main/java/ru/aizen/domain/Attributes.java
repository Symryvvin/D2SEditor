package ru.aizen.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static Map<Integer, Attribute> attributeMap;
    private static final String ATTRIBUTES_DATA = "/attributes.csv";

    static {
        try {
            attributeMap = Files.readAllLines(Paths.get(Attributes.class.getResource(ATTRIBUTES_DATA).toURI()))
                    .stream()
                    .skip(1).map(i -> i.split(";"))
                    .map(i -> new Attribute(i[0], i[1], i[2], i[3]))
                    .collect(Collectors.toMap(Attribute::getId, a -> a));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Attributes() {
    }



    @Override
    public Integer put(String key, Integer value) {
        return super.put(key, value);
    }

    public Attribute getBy(int id) {
        return attributeMap.get(id);
    }
}
