package ru.varnavskii.spliterator;

import ru.varnavskii.model.Room;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

    public class RoomSpliterator implements Spliterator<Room> {

        private final List<Room> rooms;
        private int current = 0;
        private final long delay;

        public RoomSpliterator(List<Room> rooms, long delay) {
            this.rooms = rooms;
            this.delay = delay;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Room> action) {
            if (current >= rooms.size()) return false;

            Room room = rooms.get(current++);
            action.accept(room);
            return true;
        }

        @Override
        public Spliterator<Room> trySplit() {
            int remaining = rooms.size() - current;
            if (remaining < 10) {
                return null;
            }
            int mid = current + remaining / 2;
            List<Room> subList = rooms.subList(current, mid);
            current = mid;
            return new RoomSpliterator(subList, delay);
        }

        @Override
        public long estimateSize() {
            return rooms.size() - current;
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | SUBSIZED | IMMUTABLE;
        }
    }

