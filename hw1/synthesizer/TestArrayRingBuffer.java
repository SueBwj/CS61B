package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(1);
        arb.enqueue(0);
        int output1 = arb.peek();
        int excepted1 = 2;
        assertEquals(output1, excepted1); // inspect whether peek() is right
        int output2 = arb.dequeue();
        assertEquals(output2, excepted1); // inspect whether dequeue() is right
        int output3 = arb.peek();
        int excepted2 = 3;
        assertEquals(output3, excepted2);
        int output4 = arb.dequeue();
        int excepted3 = 3;
        assertEquals(output4, excepted3);
        int output5 = arb.dequeue();
        int excepted4 = 1;
        assertEquals(output5, excepted4);
        assertFalse(arb.isEmpty());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
