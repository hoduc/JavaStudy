package basicjava.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

public class Java21Test {

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
    
}
