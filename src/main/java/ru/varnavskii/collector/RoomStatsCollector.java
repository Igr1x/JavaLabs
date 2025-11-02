package ru.varnavskii.collector;

import ru.varnavskii.collector.util.Acc;
import ru.varnavskii.model.Room;
import ru.varnavskii.stats.RoomStats;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoomStatsCollector implements Collector<Room, Acc, RoomStats> {

    @Override
    public Supplier<Acc> supplier() {
        return Acc::new;
    }

    @Override
    public BiConsumer<Acc, Room> accumulator() {
        return Acc::addRoom;
    }

    @Override
    public BinaryOperator<Acc> combiner() {
        return (a, b) -> {
            a.merge(b);
            return a;
        };
    }

    @Override
    public Function<Acc, RoomStats> finisher() {
        return acc -> {
            double avgArea = acc.getTotalRooms() == 0
                    ? 0
                    : acc.getTotalArea() / acc.getTotalRooms();
            int avgFurniture = acc.getTotalRooms() == 0
                    ? 0
                    : (int) Math.round((double) acc.getTotalFurniture() / acc.getTotalRooms());
            double percentWithWindows = acc.getTotalRooms() == 0
                    ? 0
                    : ((double) acc.getRoomsWithWindows() / acc.getTotalRooms()) * 100;

            return new RoomStats(
                    avgArea,
                    acc.getRoomsWithWindows(),
                    avgFurniture,
                    acc.getCountByType(),
                    percentWithWindows,
                    acc.getTotalFurniture(),
                    acc.getMinArea() == Double.POSITIVE_INFINITY ? 0 : acc.getMinArea(),
                    acc.getMaxArea() == Double.NEGATIVE_INFINITY ? 0 : acc.getMaxArea(),
                    acc.getRoomsByBuilding()
            );
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}