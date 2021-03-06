package ru.aizen.app.control.skin;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.aizen.app.control.NumericField;
import ru.aizen.app.control.SkillControl;


public class SkillControlSkin extends SkinBase<SkillControl> {

    /**
     * Constructor for all SkinBase instances.
     * @param control The control for which this Skin should attach to.
     */
    public SkillControlSkin(SkillControl control) {
        super(control);
        control.getStylesheets().add("/css/skillControl.css");
        ImageView image = control.getImageView();
        image.setFitWidth(48.0d);
        image.setFitHeight(48.0d);
        Label name = control.getLabel();
        name.getStyleClass().add("name");
        NumericField value = control.getNumericField();
        value.getStyleClass().add("value");
        HBox hBox = new HBox(2.0d, image, value);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        VBox vBox = new VBox(name, hBox);
        getChildren().add(vBox);
    }
}