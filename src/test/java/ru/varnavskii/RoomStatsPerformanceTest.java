package ru.varnavskii;

import ru.varnavskii.model.Room;
import ru.varnavskii.stats.RoomStats;
import ru.varnavskii.stats.RoomStatsCalc;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static ru.varnavskii.generator.RoomGenerator.generateRooms;

public class RoomStatsPerformanceTest {
    private static final int[] SIZES = {5000, 50000, 250000};

    public static void main(String[] args) {
        for (int size : SIZES) {
            System.out.println("\n===" + size);

            List<Room> rooms = generateRooms(size);

            Instant start = Instant.now();
            RoomStats stats = RoomStatsCalc.iterative(rooms);
            Instant end = Instant.now();
            long time = Duration.between(start, end).toMillis();

            System.out.printf("Iterative: %d ms\n", time);

            start = Instant.now();
            stats = RoomStatsCalc.standardStream(rooms);
            end = Instant.now();
            time = Duration.between(start, end).toMillis();

            System.out.printf("Stream std: %d ms\n", time);

            start = Instant.now();
            stats = RoomStatsCalc.customCollector(rooms);
            end = Instant.now();
            time = Duration.between(start, end).toMillis();

            System.out.printf("Stream custom collector: %d ms\n", time);
        }
    }
}
