package ru.aizen.app.stage;

import javafx.scene.control.Alert;

public final class Alerts {

    private Alerts() {
        throw new AssertionError();
    }

    public static Alert showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        return alert;
    }

    public static Alert showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Message");
        alert.setContentText(message);
        return alert;
    }

}
