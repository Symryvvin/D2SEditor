package ru.aizen.domain.character.attribute;

import java.util.ArrayList;
import java.util.List;

public class SkillTree {
    private String name;
    private List<Skill> skills;
    private int order;

    public SkillTree(int order, byte[] skillsValue) {
        this.order = order;
        skills = new ArrayList<>(10);
        for(int i = 0; i < skillsValue.length; i++){
            skills.add(new Skill(skillsValue[i], i + 1));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "SkillTree{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                ", order=" + order +
                '}';
    }
}
