package ru.aizen.domain.character.block;

import ru.aizen.domain.character.attribute.Skill;
import ru.aizen.domain.character.attribute.SkillTree;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkillsBlock extends DataBlock{
    private List<SkillTree> skillTrees;

    public SkillsBlock(int order) {
        super(order);
    }

    @Override
    public DataBlock parse(ByteBuffer buffer) {
        size = buffer.capacity();
        buffer.getShort();
        skillTrees = new ArrayList<>(3);
        byte[] firstTree = new byte[10];
        byte[] secondTree = new byte[10];
        byte[] thirdTree = new byte[10];
        buffer.get(firstTree, 0, 10);
        buffer.get(secondTree, 0, 10);
        buffer.get(thirdTree, 0, 10);
        skillTrees.add(new SkillTree(1, firstTree));
        skillTrees.add(new SkillTree(2, secondTree));
        skillTrees.add(new SkillTree(3, thirdTree));
        return this;
    }

    @Override
    public ByteBuffer collect() {
        List<Integer> values = skillTrees.stream()
                .flatMap(skillTree -> skillTree.getSkills().stream())
                .map(Skill::getValue)
                .collect(Collectors.toList());
        byte[] result = new byte[30];
        for(int i = 0; i < values.size(); i++){
            result[i] = values.get(i).byteValue();
        }
        ByteBuffer buffer = ByteBuffer.allocate(2 + result.length)
                .put((ByteBuffer) ByteBuffer.allocate(2).put((byte) 105).put((byte) 102).flip())
                .put(result);
        buffer.flip();
        return buffer;
    }

    public List<SkillTree> getSkillTrees() {
        return skillTrees;
    }

    public void setSkillTrees(List<SkillTree> skillTrees) {
        this.skillTrees = skillTrees;
    }
}
