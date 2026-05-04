package basicjava.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class Java21Test {
    sealed interface Shape
        permits Rectangle {
            double area();
        }
    
    record Point(int x, int y) {};
    record Rectangle(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) implements Shape {

        @Override
        public double area() {
            // w * h
            return Math.abs(topRight.x - topLeft.x) * Math.abs(bottomLeft.y - topLeft.y);
        }

    }

    // it kinda misnomer that our switch just functioning as the logging
    // for now since the shape.area() actually bounded correctly
    double area(Shape shape) {
        return switch(shape) {
            case Rectangle(var topLeft, var topRight, var bottomLeft, var bottomRight) -> {
                System.out.println(String.format("(%s, %s, %s, %s)", topLeft, topRight, bottomLeft, bottomRight));
                yield shape.area();
            }
            default -> 0;
        };
    }


    void assertSequencedCollection(SequencedCollection<Integer> oneTwoThree) {
        var threeTwoOne = List.of(3, 2, 1).iterator();
        var oneTwoThreeReversed = oneTwoThree.reversed().iterator();
        
        while (threeTwoOne.hasNext() && oneTwoThreeReversed.hasNext()) {
            assertEquals(threeTwoOne.next(), oneTwoThreeReversed.next());
        }

        assertEquals(1, oneTwoThree.getFirst());
        assertEquals(3, oneTwoThree.getLast());
    }

    @Test
    void sequencedCollection() {
        var oneTwoThree = List.of(1, 2, 3);
        /*
            Maybe one of the cleaner approach of constructing
            these collections is that we do reflection enumerating 
            the concrete classes implementing SequencedCollection
            and reflection construct them and pass to assertSequencedCollection but It can be future work (maybe)
        */
        assertSequencedCollection(oneTwoThree);
        assertSequencedCollection(new LinkedList<>(oneTwoThree));
        assertSequencedCollection(new ArrayDeque<>(oneTwoThree));
        assertSequencedCollection(new TreeSet<>(List.of(1, 3, 2))); // will be [1,2,3] bc of natural order
    }

    @Test
    void recordPatternMatching() {
        assertEquals(12.0, 
            area(
                new Rectangle(
                    new Point(0, 3),
                    new Point(4, 3),
                    new Point(0, 0),
                    new Point(4, 0)
                )
            )
        );
    }

    private static String runtimeType(Object obj) {
        String t = "unknown";
        // switch does not do pattern matching yet until java 21
        if (obj instanceof String s) {
            t = "[" + s + "]" + ":string";
        } else if (obj instanceof Integer i) {
            t = "[" + i + "]" + ":int";
        } else if (obj instanceof Point p) {
            t = "[" + p + "]" + ":Point";
        } else if (obj instanceof Rectangle r) {
            t = "[" + r + "]" + ":Rectangle";
        }
        return  t;
    }

    @Test
    void patternMatchingInstanceof() {
        assertEquals("[hello]:string", runtimeType("hello"));
        assertEquals("[42]:int", runtimeType(42));
        assertEquals("unknown", runtimeType(42.0));
        assertEquals("[Point[x=0, y=0]]:Point", runtimeType(new Point(0, 0)));
        assertEquals("[Rectangle[topLeft=Point[x=0, y=3], topRight=Point[x=4, y=3], bottomLeft=Point[x=0, y=0], bottomRight=Point[x=4, y=0]]]:Rectangle", 
            runtimeType(
                new Rectangle(
                        new Point(0, 3),
                        new Point(4, 3),
                        new Point(0, 0),
                        new Point(4, 0)
                )
            )
        );
    }

    @Test
    void virtualThreads() throws InterruptedException {
        // simple thread
        var vt = Thread.startVirtualThread(() -> {
            System.out.println("Virtual Thread Simple:" + Thread.currentThread());
        });
        vt.join();
        
        // thread builder
        var vtb = Thread.ofVirtual()
                    .name("vtb-", 1)
                    .start(() -> {
                        System.out.println("Virtual Thread Simple:" + Thread.currentThread());
                    });
        vtb.join();

        // structured concurrency style
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    try{
                        Thread.sleep(1000);
                        System.out.println("Finished-" + i);
                    } catch(InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return i;
                });
            });
        }
    }
    
}
