package ru.aizen.controller;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import ru.aizen.control.NumericField;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.block.AttributesBlock;

@Component
public class StatsController extends AbstractController {
    @FXML private NumericField hp;
    @FXML private NumericField mp;
    @FXML private NumericField sp;
    @FXML private NumericField statPoints;
    @FXML private NumericField skillPoints;
    @FXML private NumericField gold;
    @FXML private NumericField goldInStash;
    @FXML private NumericField strength;
    @FXML private NumericField dexterity;
    @FXML private NumericField vitality;
    @FXML private NumericField energy;
    @FXML private NumericField level;
    @FXML private NumericField experience;

    private AttributesBlock attributesBlock;


    public StatsController(Character character) {
        super(character);
    }

    public void initialize() {
        level.textProperty()
                .addListener((observable, oldValue, newValue) ->
                        character.setLevel("Level " + newValue));
    }

    @Override
    protected void loadCharacter() {
        loadCharacterStats();
    }

    @Override
    public void saveCharacter() {
        attributesBlock.put(AttributesBlock.STRENGTH, Long.parseLong(strength.getText()));
        attributesBlock.put(AttributesBlock.DEXTERITY, Long.parseLong(dexterity.getText()));
        attributesBlock.put(AttributesBlock.VITALITY, Long.parseLong(vitality.getText()));
        attributesBlock.put(AttributesBlock.ENERGY, Long.parseLong(energy.getText()));
        attributesBlock.put(AttributesBlock.LEVEL, Long.parseLong(level.getText()));
        attributesBlock.put(AttributesBlock.EXPERIENCE, Long.parseLong(experience.getText()));
        attributesBlock.put(AttributesBlock.MAX_HP, Long.parseLong(hp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MAX_MP, Long.parseLong(mp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MAX_SP, Long.parseLong(sp.getText()) * 256);
        attributesBlock.put(AttributesBlock.HP, Long.parseLong(hp.getText()) * 256);
        attributesBlock.put(AttributesBlock.MP, Long.parseLong(mp.getText()) * 256);
        attributesBlock.put(AttributesBlock.SP, Long.parseLong(sp.getText()) * 256);
        attributesBlock.put(AttributesBlock.LEVEL, Long.parseLong(level.getText()));
        attributesBlock.put(AttributesBlock.SKILL_POINTS, Long.parseLong(skillPoints.getText()));
        attributesBlock.put(AttributesBlock.STAT_POINTS, Long.parseLong(statPoints.getText()));
        attributesBlock.put(AttributesBlock.GOLD, Long.parseLong(gold.getText()));
        attributesBlock.put(AttributesBlock.GOLD_IN_STASH, Long.parseLong(goldInStash.getText()));
    }

    private void loadCharacterStats() {
        attributesBlock = character.getAttributesBlock();
        strength.setText(getAttributeValue(AttributesBlock.STRENGTH));
        dexterity.setText(getAttributeValue(AttributesBlock.DEXTERITY));
        vitality.setText(getAttributeValue(AttributesBlock.VITALITY));
        energy.setText(getAttributeValue(AttributesBlock.ENERGY));
        level.setText(getAttributeValue(AttributesBlock.LEVEL));
        experience.setText(getAttributeValue(AttributesBlock.EXPERIENCE));
        hp.setText(getAttributeValue(AttributesBlock.MAX_HP, true));
        mp.setText(getAttributeValue(AttributesBlock.MAX_MP, true));
        sp.setText(getAttributeValue(AttributesBlock.MAX_SP, true));
        statPoints.setText(getAttributeValue(AttributesBlock.STAT_POINTS));
        skillPoints.setText(getAttributeValue(AttributesBlock.SKILL_POINTS));
        //max value of gold depends of level temporary for 99 lvl
        gold.setMaxValue(990000);
        gold.setText(getAttributeValue(AttributesBlock.GOLD));
        //max value of gold in stash depends of level temporary for 99 lvl
        goldInStash.setMaxValue(2500000);
        goldInStash.setText(getAttributeValue(AttributesBlock.GOLD_IN_STASH));
        character.setAttributesBlock(attributesBlock);

    }

    private String getAttributeValue(long id) {
        return getAttributeValue(id, false);
    }

    private String getAttributeValue(long id, boolean divide) {
        if (attributesBlock.containsKey(id)) {
            if (divide)
                return String.valueOf(attributesBlock.get(id) / 256);
            else
                return String.valueOf(attributesBlock.get(id));
        }
        return "0";
    }
}
