package ru.varnavskii.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Building {
    private String name;
    private int floors;
    private String address;
}
