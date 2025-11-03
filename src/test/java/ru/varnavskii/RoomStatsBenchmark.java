package ru.varnavskii;

import org.openjdk.jmh.annotations.*;
import ru.varnavskii.generator.RoomGenerator;
import ru.varnavskii.model.Room;
import ru.varnavskii.stats.RoomStats;
import ru.varnavskii.stats.RoomStatsCalc;

import java.util.concurrent.TimeUnit;
import java.util.List;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class RoomStatsBenchmark {

    @Param({"100", "200", "500", "1000"})
    int size;

    @Param({"0", "10"})
    long delay;

    List<Room> rooms;

    @Setup(Level.Trial)
    public void setup() {
        rooms = RoomGenerator.generateRooms(size);
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    public RoomStats sequential() {
        return RoomStatsCalc.parallelStream(rooms, 0);
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @Threads(10)
    public RoomStats parallel() {
        return RoomStatsCalc.standardStream(rooms, delay);
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @Threads(10)
    public RoomStats optParallel() {
        return RoomStatsCalc.optimizedParallel(rooms, delay);
    }
}

