package ru.varnavskii.generator;

import ru.varnavskii.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomGenerator {

    private static final Random RANDOM = new Random();
    private static final String[] ROOM_NAMES = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon"};
    private static final String[] FURNITURE_NAMES = {"Table", "Chair", "Sofa", "Desk", "Bed", "Wardrobe"};
    private static final String[] BUILDING_NAMES = {"Main Office", "Annex", "Lab", "Workshop"};
    private static final String[] ADDRESSES = {"1 Main St", "22 Green Ave", "45 Industrial Rd"};

    public static List<Room> generateRooms(int count) {
        List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            long id = i + 1;
            String name = ROOM_NAMES[RANDOM.nextInt(ROOM_NAMES.length)] + "-" + id;
            LocalDate creationDate = LocalDate.now().minusDays(RANDOM.nextInt(2000));

            RoomType type = RoomType.values()[RANDOM.nextInt(RoomType.values().length)];
            Coordinate coordinate = getRandomCoordinate();
            Building building = getRandomBuilding();

            List<Furniture> furniture = new ArrayList<>();
            int furnitureCount = 1 + (int) Math.round(Math.pow(RANDOM.nextDouble(), 1.5) * 10);
            for (int j = 0; j < furnitureCount; j++) {
                furniture.add(getRandomFurniture());
            }

            double area = 5 + RANDOM.nextDouble() * 95;

            boolean hasWindow = RANDOM.nextDouble() < 0.4;;

            rooms.add(new Room(id, name, creationDate, type, coordinate, building, furniture, area, hasWindow));
        }

        return rooms;
    }

    private static Building getRandomBuilding() {
        return new Building(
                BUILDING_NAMES[RANDOM.nextInt(BUILDING_NAMES.length)],
                1 + RANDOM.nextInt(10),
                ADDRESSES[RANDOM.nextInt(ADDRESSES.length)]
        );
    }

    private static Furniture getRandomFurniture() {
        return new Furniture(
                FURNITURE_NAMES[RANDOM.nextInt(FURNITURE_NAMES.length)],
                0.5 + RANDOM.nextDouble() * 2,
                0.5 + RANDOM.nextDouble() * 2,
                0.5 + RANDOM.nextDouble() * 2
        );
    }

    private static Coordinate getRandomCoordinate() {
        return new Coordinate(
                RANDOM.nextDouble() * 100,
                RANDOM.nextDouble() * 100
        );
    }
}
