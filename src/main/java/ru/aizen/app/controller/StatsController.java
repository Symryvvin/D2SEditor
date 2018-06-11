package ru.aizen.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.app.control.NumericField;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.block.AttributesBlock;
import ru.aizen.domain.character.entity.Levels;

@Component
public class StatsController extends BaseController {
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
    @FXML private Label experience;

    private AttributesBlock attributesBlock;

    @Autowired
    public StatsController(Character character) {
        super(character);
    }

    public void initialize() {
        level.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    character.setLevel("Level " + newValue);
                    experience.setText(Levels.getExperienceAtLevel(getLevel()));
                    long maxGold = Levels.getGoldAtLevel(getLevel());
                    gold.setMaxValue(maxGold);
                    long maxGoldBank = Levels.getGoldBankAtLevel(getLevel());
                    goldInStash.setMaxValue(maxGoldBank);
                    if (gold.getNumericValue() > maxGold)
                        gold.setText(String.valueOf(maxGold));
                    if (goldInStash.getNumericValue() > maxGoldBank)
                        goldInStash.setText(String.valueOf(maxGoldBank));
                });
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
        character.getMetaBlock().setLevel(Integer.parseInt(level.getText()));
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
        gold.setText(getAttributeValue(AttributesBlock.GOLD));
        goldInStash.setText(getAttributeValue(AttributesBlock.GOLD_IN_STASH));
    }

    private int getLevel() {
        return Integer.parseInt(level.getText());
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
