package synthesizer;
import java.util.Iterator;
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        this.fillCount = 0;
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last = (last + 1) % capacity;
            fillCount++;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T val = rb[first];
            first = (first + 1) % capacity;
            fillCount--;
            return val;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int num;
        public ArrayRingBufferIterator() {
            pos = first;
            num = 0;
        }
        public T next() {
            T returnItem = rb[pos];
            pos = (pos + 1) % capacity;
            num += 1;
            return returnItem;
        }

        @Override
        public boolean hasNext() {
            return num < fillCount;
        }
    }
}
