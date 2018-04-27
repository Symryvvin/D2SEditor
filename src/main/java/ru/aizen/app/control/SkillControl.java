package ru.aizen.app.control;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.aizen.app.control.skin.SkillControlSkin;


public class SkillControl extends Control {
    private ImageView image;
    private Label name;
    private NumericField value;
    private int skillOrder;

    public SkillControl() {
        this.image = new ImageView();
        this.name = new Label();
        this.value = new NumericField(99);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setValue(String value) {
        this.value.setText(value);
    }

    public void setImageByPath(String path) {
        this.image.setImage(new Image(path));
    }

    public void setSkillOrder(int skillOrder) {
        this.skillOrder = skillOrder;
    }

    public ImageView getImageView() {
        return image;
    }

    public Label getLabel() {
        return name;
    }

    public NumericField getNumericField() {
        return value;
    }

    public Byte getValue() {
        return value.getNumericValue().byteValue();
    }

    public int getSkillOrder() {
        return skillOrder;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SkillControlSkin(this);
    }



}