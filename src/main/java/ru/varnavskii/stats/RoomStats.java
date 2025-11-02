package ru.varnavskii.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RoomStats {
    double avgArea;
    int withWindows;
    int avgCountFurniture;
    Map<String, Integer> countByType;
    double percentWithWindows;
    long totalFurniture;
    double minArea;
    double maxArea;
    Map<String, Integer> roomsByBuilding;

    @Override
    public String toString() {
        return "RoomStats {\n" +
               "  avgArea = " + String.format("%.2f", avgArea) + ",\n" +
               "  withWindows = " + withWindows + ",\n" +
               "  avgCountFurniture = " + avgCountFurniture + ",\n" +
               "  countByType = " + countByType + ",\n" +
               "  percentWithWindows = " + String.format("%.2f", percentWithWindows) + "%,\n" +
               "  totalFurniture = " + totalFurniture + ",\n" +
               "  minArea = " + String.format("%.2f", minArea) + ",\n" +
               "  maxArea = " + String.format("%.2f", maxArea) + ",\n" +
               "  roomsByBuilding = " + roomsByBuilding + "\n" +
               '}';
    }
}
