package ru.varnavskii.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class Room {
    private long id;
    private String name;
    private LocalDate creationDate;
    private RoomType type;
    private Coordinate coordinate;
    private Building building;
    private List<Furniture> furniture;
    private double area;
    private boolean hasWindow;

    @SneakyThrows
    public RoomType getRoomType(long delay) {
        Thread.sleep(delay);
        return this.type;
    }
}