package ru.aizen.controller;

import javafx.fxml.FXML;

public class EditorController {
    @FXML private StatsController statsController;

    public StatsController getStatsController() {
        return statsController;
    }
}
