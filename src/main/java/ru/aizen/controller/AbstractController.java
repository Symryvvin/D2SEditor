package ru.aizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.exception.ValidatorException;

@Component
public abstract class AbstractController {
    protected final Character character;

    @Autowired
    public AbstractController(Character character) {
        this.character = character;
    }

    protected abstract void loadCharacter() throws ValidatorException;

    public abstract void saveCharacter() throws ValidatorException;
}
