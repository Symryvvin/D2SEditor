package ru.aizen.app.control;

import javafx.beans.NamedArg;
import javafx.scene.control.TextField;

public class NumericField extends TextField {
    private long maxValue;
    private long minValue = 0;


    public NumericField(@NamedArg("maxValue") long maxValue) {
        super();
        this.maxValue = maxValue;
        setListener();
    }

    public Long getNumericValue() {
        return Long.parseLong(this.getText());
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    private void setListener() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                setText("0");
            } else {
                try {
                    newValue = newValue.replace(",", "");
                    long value = Long.parseLong(newValue);
                    if (value > maxValue) {
                        setText(String.valueOf(maxValue));
                    } else if (value < minValue) {
                        setText(String.valueOf(minValue));
                    } else {
                        if (!oldValue.equals(newValue))
                            setText(String.valueOf(value));
                    }
                } catch (NumberFormatException e) {
                    setText(oldValue);
                }
            }
        });
    }
}
