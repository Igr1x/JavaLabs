package ru.varnavskii.collector.util;

import lombok.Getter;
import ru.varnavskii.model.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

@Getter
public class Acc {
    private final DoubleAdder totalArea = new DoubleAdder();
    private final LongAdder totalRooms = new LongAdder();
    private final LongAdder roomsWithWindows = new LongAdder();
    private final LongAdder totalFurniture = new LongAdder();
    private final DoubleAccumulator minArea = new DoubleAccumulator(Double::min, Double.POSITIVE_INFINITY);
    private final DoubleAccumulator maxArea = new DoubleAccumulator(Double::max, Double.NEGATIVE_INFINITY);
    private final Map<String, Integer> countByType = new ConcurrentHashMap<>();
    private final Map<String, Integer> roomsByBuilding = new ConcurrentHashMap<>();

    public void addRoom(Room room) {
        totalRooms.add(1);
        totalArea.add(room.getArea());
        totalFurniture.add(room.getFurniture().size());
        if (room.isHasWindow()) roomsWithWindows.add(1);
        minArea.accumulate(Math.min(minArea.doubleValue(), room.getArea()));
        maxArea.accumulate(Math.max(maxArea.doubleValue(), room.getArea()));

        countByType.merge(room.getType().name(), 1, Integer::sum);
        roomsByBuilding.merge(room.getBuilding().getAddress(), 1, Integer::sum);
    }

    public void merge(Acc other) {
        totalRooms.add(other.totalRooms.intValue());
        totalArea.add(other.totalArea.doubleValue());
        totalFurniture.add(other.totalFurniture.intValue());
        roomsWithWindows.add(other.roomsWithWindows.intValue());
        minArea.accumulate(Math.min(minArea.doubleValue(), other.minArea.doubleValue()));
        maxArea.accumulate(Math.max(maxArea.doubleValue(), other.maxArea.doubleValue()));

        other.countByType.forEach((k, v) -> countByType.merge(k, v, Integer::sum));
        other.roomsByBuilding.forEach((k, v) -> roomsByBuilding.merge(k, v, Integer::sum));
    }
}

