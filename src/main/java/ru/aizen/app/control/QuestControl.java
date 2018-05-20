package ru.aizen.app.control;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.aizen.app.control.skin.QuestControlSkin;

public class QuestControl extends Control {
    private ImageView image;
    private Label name;
    private CheckBox isCompleted;
    private int order;

    public QuestControl() {
        this.image = new ImageView();
        this.name = new Label();
        this.isCompleted = new CheckBox();
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(String path) {
        image.setImage(new Image(path));
    }

    public Label getName() {
        return name;
    }

    public void setName(String value) {
        name.setText(value);
    }

    public CheckBox getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean value) {
        isCompleted.setSelected(value);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new QuestControlSkin(this);
    }
}
