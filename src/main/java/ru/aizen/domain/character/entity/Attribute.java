package ru.aizen.domain.character.entity;

import org.apache.commons.csv.CSVRecord;

public class Attribute {
    public static final int ID_OFFSET = 9;

    private long id;
    private String name;
    private int length;

    public Attribute(int id, String name, int length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    public Attribute(CSVRecord record) {
        this.id = Long.parseLong(record.get("id"));
        this.name = record.get("name");
        this.length = Integer.parseInt(record.get("length"));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
}
