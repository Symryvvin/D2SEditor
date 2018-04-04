package ru.aizen.domain.character.block;

import ru.aizen.domain.character.attribute.Skill;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkillsBlock extends DataBlock {
    private List<Skill> skills;

    public SkillsBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        buffer.getShort();
        skills = new ArrayList<>();
        byte[] values = new byte[30];
        buffer.get(values, 0, 30);
        for (int i = 0; i < values.length; i++) {
            skills.add(new Skill(values[i], i));
        }
        return this;
    }

    @Override
    public ByteBuffer collect() {
        List<Integer> values = skills.stream()
                .map(Skill::getValue)
                .collect(Collectors.toList());
        byte[] result = new byte[30];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i).byteValue();
        }
        ByteBuffer buffer = ByteBuffer.allocate(2 + result.length)
                .put((ByteBuffer) ByteBuffer.allocate(2).put((byte) 105).put((byte) 102).flip())
                .put(result);
        buffer.flip();
        return buffer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
