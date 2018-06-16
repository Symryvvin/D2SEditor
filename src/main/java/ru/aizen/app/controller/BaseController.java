package ru.aizen.app.controller;

import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

public abstract class BaseController {
    protected Character character;

    protected abstract void loadCharacter() throws ValidatorException;

    public abstract void saveCharacter() throws ValidatorException;

    public void setCharacter(Character character) {
        this.character = character;
    }
}
