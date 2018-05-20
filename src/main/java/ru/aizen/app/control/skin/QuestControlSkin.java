package ru.aizen.app.control.skin;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.aizen.app.control.QuestControl;

public class QuestControlSkin extends SkinBase<QuestControl> {
    /**
     * Constructor for all SkinBase instances.
     * @param control The control for which this Skin should attach to.
     */
    public QuestControlSkin(QuestControl control) {
        super(control);
        control.getStylesheets().add("/css/questControl.css");
        ImageView image = control.getImage();
        image.setFitWidth(48.0d);
        image.setFitHeight(49.0d);
        Label name = control.getName();
        name.getStyleClass().add("name");
        CheckBox value = control.getCheckBox();
        value.getStyleClass().add("value");
        VBox vBox = new VBox(name, value);
        HBox hBox = new HBox(2.0d, image, vBox);
        hBox.getStyleClass().add("wrapper");
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        getChildren().add(hBox);
    }
}
