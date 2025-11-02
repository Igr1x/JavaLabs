package ru.varnavskii.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Furniture {
    private String name;
    private double width;
    private double height;
    private double depth;
}
