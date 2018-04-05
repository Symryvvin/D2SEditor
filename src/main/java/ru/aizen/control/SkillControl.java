package ru.aizen.control;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.aizen.control.skin.SkillControlSkin;


public class SkillControl extends Control {
    private ImageView image;
    private Label name;
    private NumericField value;

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

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SkillControlSkin(this);
    }

    public ImageView getImage() {
        return image;
    }

    public Label getName() {
        return name;
    }

    public NumericField getValue() {
        return value;
    }
}