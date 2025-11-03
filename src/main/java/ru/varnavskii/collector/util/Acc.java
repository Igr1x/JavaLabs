package ru.varnavskii.collector.util;

import lombok.Getter;
import ru.varnavskii.model.Room;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Acc {
    private double totalArea;
    private int totalRooms;
    private int roomsWithWindows;
    private int totalFurniture;
    private double minArea = Double.POSITIVE_INFINITY;
    private double maxArea = Double.NEGATIVE_INFINITY;
    private final Map<String, Integer> countByType = new HashMap<>();
    private final Map<String, Integer> roomsByBuilding = new HashMap<>();

    public void addRoom(Room room) {
        totalRooms++;
        totalArea += room.getArea();
        totalFurniture += room.getFurniture().size();
        if (room.isHasWindow()) roomsWithWindows++;
        minArea = Math.min(minArea, room.getArea());
        maxArea = Math.max(maxArea, room.getArea());

        countByType.merge(room.getType().name(), 1, Integer::sum);
        roomsByBuilding.merge(room.getBuilding().getAddress(), 1, Integer::sum);
    }

    public void merge(Acc other) {
        totalRooms += other.totalRooms;
        totalArea += other.totalArea;
        totalFurniture += other.totalFurniture;
        roomsWithWindows += other.roomsWithWindows;
        minArea = Math.min(minArea, other.minArea);
        maxArea = Math.max(maxArea, other.maxArea);

        other.countByType.forEach((k, v) -> countByType.merge(k, v, Integer::sum));
        other.roomsByBuilding.forEach((k, v) -> roomsByBuilding.merge(k, v, Integer::sum));
    }
}

