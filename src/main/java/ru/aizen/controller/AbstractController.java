package ru.aizen.controller;

import ru.aizen.domain.Character;

public abstract class AbstractController {
    protected Character character;

    public void setCharacter(Character character){
        this.character = character;
    }

    protected abstract void loadCharacter();
}
