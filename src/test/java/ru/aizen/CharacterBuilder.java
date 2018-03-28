package ru.aizen;

import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.Status;
import ru.aizen.domain.character.Title;

public class CharacterBuilder {
    private String name;
    private Status status;
    private Title title;
    private CharacterClass characterClass;

    public static CharacterBuilder create(){
        return new CharacterBuilder();
    }

/*     public CharacterBuilder name(String name){
       this.name = name;
        return this;
    }

    public CharacterBuilder status(String name){
        this.name = name;
        return this;
    }

    public CharacterBuilder name(String name){
        this.name = name;
        return this;
    }

    public CharacterBuilder name(String name){
        this.name = name;
        return this;
    }*/




}
