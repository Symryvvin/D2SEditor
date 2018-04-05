package ru.aizen.control.skin;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.aizen.control.NumericField;
import ru.aizen.control.SkillControl;


public class SkillControlSkin extends SkinBase<SkillControl> {

    /**
     * Constructor for all SkinBase instances.
     * @param control The control for which this Skin should attach to.
     */
    public SkillControlSkin(SkillControl control) {
        super(control);
        ImageView image = control.getImage();
        image.setFitWidth(48.0d);
        image.setFitHeight(48.0d);
        Label name = control.getName();
        name.setStyle("-fx-font-size: 10px; " +
                "-fx-pref-height: 24px");
        NumericField value = control.getValue();
        value.setStyle("-fx-font-size: 10px; " +
                "-fx-pref-height: 24px; " +
                "-fx-pref-width: 30px");
        HBox hBox = new HBox(2.0d, image, value);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        VBox vBox = new VBox(name, hBox);
        getChildren().add(vBox);
    }
}