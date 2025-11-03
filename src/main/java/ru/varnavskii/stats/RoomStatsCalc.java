package ru.varnavskii.stats;

import ru.varnavskii.collector.RoomStatsCollector;
import ru.varnavskii.model.Room;
import ru.varnavskii.spliterator.RoomSpliterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RoomStatsCalc {

    public static RoomStats iterative(List<Room> rooms) {
        double totalArea = 0;
        int totalFurniture = 0;
        int roomsWithWindows = 0;
        Map<String, Integer> countByType = new HashMap<>();
        double minArea = Double.MAX_VALUE;
        double maxArea = Double.MIN_VALUE;
        Map<String, Integer> roomsByBuilding = new HashMap<>();

        for (Room room : rooms) {
            totalArea += room.getArea();
            if (room.isHasWindow()) roomsWithWindows++;
            totalFurniture += room.getFurniture().size();
            countByType.merge(room.getType().name(),
                    1,
                    Integer::sum);
            minArea = Math.min(minArea, room.getArea());
            maxArea = Math.max(maxArea, room.getArea());
            roomsByBuilding.merge(room.getBuilding().getAddress(),
                    1,
                    Integer::sum);
        }

        int n = rooms.size();
        return new RoomStats(totalArea / n,
                roomsWithWindows,
                (int) Math.round((double) totalFurniture / n),
                countByType,
                ((double) roomsWithWindows / n) * 100,
                totalFurniture,
                minArea,
                maxArea,
                roomsByBuilding);
    }

    public static RoomStats standardStream(List<Room> rooms, long delay) {
        double avgArea = rooms.stream()
                .collect(Collectors.averagingDouble(Room::getArea));
        int withWindows = Math.toIntExact(rooms.stream()
                .filter(Room::isHasWindow)
                .count());
        int avgFurniture = (int) Math.round(rooms.stream()
                .collect(Collectors.averagingInt(r -> r.getFurniture().size())));
        Map<String, Integer> countByType = rooms.parallelStream()
                .collect(Collectors.toMap(it -> it.getRoomType(delay).name(),
                        it -> 1,
                        Integer::sum));
        long totalFurniture = rooms.stream()
                .mapToLong(it -> it.getFurniture().size())
                .sum();
        OptionalDouble minArea = rooms.stream()
                .mapToDouble(Room::getArea)
                .min();
        OptionalDouble maxArea = rooms.stream()
                .mapToDouble(Room::getArea)
                .max();
        Map<String, Integer> roomsByBuilding = rooms.stream()
                .collect(Collectors.toMap(it -> it.getBuilding().getAddress(),
                        it -> 1,
                        Integer::sum
                ));
        return new RoomStats(avgArea,
                withWindows,
                avgFurniture,
                countByType,
                ((double) withWindows / rooms.size()) * 100,
                totalFurniture,
                minArea.orElse(0.0),
                maxArea.orElse(0.0),
                roomsByBuilding
        );
    }

    public static RoomStats customCollector(List<Room> rooms) {
        return rooms.stream().collect(new RoomStatsCollector());
    }

    public static RoomStats optimizedParallel(List<Room> rooms, long delay) {
        RoomSpliterator spliterator = new RoomSpliterator(rooms, delay);

        return StreamSupport.stream(spliterator, true)
                .collect(new RoomStatsCollector());
    }

    public static RoomStats parallelStream(List<Room> rooms, long delay) {
        double avgArea = rooms.stream()
                .collect(Collectors.averagingDouble(Room::getArea));
        int withWindows = Math.toIntExact(rooms.stream()
                .filter(Room::isHasWindow)
                .count());
        int avgFurniture = (int) Math.round(rooms.stream()
                .collect(Collectors.averagingInt(r -> r.getFurniture().size())));
        Map<String, Integer> countByType = rooms.parallelStream()
                .collect(Collectors.toConcurrentMap(it -> it.getRoomType(delay).name(),
                        it -> 1,
                        Integer::sum));
        long totalFurniture = rooms.stream()
                .mapToLong(it -> it.getFurniture().size())
                .sum();
        OptionalDouble minArea = rooms.stream()
                .mapToDouble(Room::getArea)
                .min();
        OptionalDouble maxArea = rooms.stream()
                .mapToDouble(Room::getArea)
                .max();
        Map<String, Integer> roomsByBuilding = rooms.stream()
                .collect(Collectors.toMap(it -> it.getBuilding().getAddress(),
                        it -> 1,
                        Integer::sum
                ));
        return new RoomStats(avgArea,
                withWindows,
                avgFurniture,
                countByType,
                ((double) withWindows / rooms.size()) * 100,
                totalFurniture,
                minArea.orElse(0.0),
                maxArea.orElse(0.0),
                roomsByBuilding
        );
    }
}
