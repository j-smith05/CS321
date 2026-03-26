import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for MaxHeap implementation.
 * Covers basic operations, edge cases, and heap property validation.
 * Assumes Task and TaskInterface are defined elsewhere with appropriate methods.
 * Tests include:
 * - isEmpty() on empty and non-empty heaps
 * - insert() and checking internal array structure
 * - extractMax() and max() for correct ordering
 * - increaseKey() for proper bubbling up
 * - Helper methods to validate max-heap property and reverse-sorted order of extracted priorities
 * - Tests with ascending, descending, and random priority insertions to ensure various scenarios are covered.
 * @author Jacob Smith
 */
public class MaxHeapTest {

    private MaxHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MaxHeap();
    }

    private Task task(int priority, int hourCreated) {
        return new Task(priority, TaskInterface.TaskType.FARM_MAINTENANCE, "p=" + priority, hourCreated);
    }

    private boolean checkIfMaxHeap(Task[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < arr.length && arr[i].compareTo(arr[left]) < 0) return false;
            if (right < arr.length && arr[i].compareTo(arr[right]) < 0) return false;
        }
        return true;
    }

    // Helper: verify extracted priorities are reverse-sorted (descending)
    private boolean checkIfReverseSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] < a[i]) return false;
        }
        return true;
    }

    @Test
    void emptyHeap_isEmptyTrue() {
        assertTrue(heap.isEmpty());
    }

    @Test
    void oneElementHeap_isEmptyFalse() throws HeapException {
        heap.insert(task(10, 0));
        assertFalse(heap.isEmpty());
    }

    @Test
    void insert_twoElements_checkHeapArrayPositions() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(20, 1));

        Task[] arr = heap.getHeap();
        assertEquals(20, arr[0].getPriority());
        assertEquals(5, arr[1].getPriority());
    }

    @Test
    void extractMax_twoElements_sortedOrder() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(20, 1));

        assertEquals(20, heap.max().getPriority());          // also tests max()
        assertEquals(20, heap.extractMax().getPriority());
        assertEquals(5, heap.extractMax().getPriority());
    }

    @Test
    void extractMax_threeElements_sortedOrder() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(20, 1));
        heap.insert(task(10, 2));

        assertEquals(20, heap.extractMax().getPriority());
        assertEquals(10, heap.extractMax().getPriority());
        assertEquals(5, heap.extractMax().getPriority());
    }

    @Test
    void increaseKey_twoElements_movesUp_checkArray() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(10, 1));
        heap.increaseKey(1, 15);

        Task[] arr = heap.getHeap();
        assertEquals(15, arr[0].getPriority());
    }

    @Test
    void increaseKey_threeElements_movesUp_checkArray() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(10, 1));
        heap.insert(task(7, 2));
        heap.increaseKey(2, 20);

        Task[] arr = heap.getHeap();
        assertEquals(20, arr[0].getPriority());
    }

    @Test
    void helper_checkIfMaxHeap_trueOnValidHeapArray() throws HeapException {
        heap.insert(task(5, 0));
        heap.insert(task(20, 1));
        heap.insert(task(10, 2));

        assertTrue(checkIfMaxHeap(heap.getHeap()));
    }

    @Test
    void helper_checkIfReverseSorted_detectsDescending() {
        assertTrue(checkIfReverseSorted(new int[]{9, 7, 7, 3, 1}));
        assertFalse(checkIfReverseSorted(new int[]{9, 10, 3}));
    }

    @Test
    void insertAscending_checkHeapProperty_and_extractReverseSorted() throws HeapException {
        int n = 25;
        for (int i = 1; i <= n; i++) {
            heap.insert(task(i, i));
        }

        assertTrue(checkIfMaxHeap(heap.getHeap()));

        int[] extracted = new int[n];
        for (int i = 0; i < n; i++) {
            extracted[i] = heap.extractMax().getPriority();
        }
        assertTrue(checkIfReverseSorted(extracted));
    }

    @Test
    void insertDescending_checkHeapProperty_and_extractReverseSorted() throws HeapException {
        int n = 25;
        for (int i = n; i >= 1; i--) {
            heap.insert(task(i, i));
        }

        assertTrue(checkIfMaxHeap(heap.getHeap()));

        int[] extracted = new int[n];
        for (int i = 0; i < n; i++) {
            extracted[i] = heap.extractMax().getPriority();
        }
        assertTrue(checkIfReverseSorted(extracted));
    }

    @Test
    void insertRandom_checkHeapProperty_and_extractReverseSorted() throws HeapException {
        int n = 25;
        Random r = new Random(12345);

        for (int i = 0; i < n; i++) {
            int p = 1 + r.nextInt(n);
            heap.insert(task(p, i));
        }

        assertTrue(checkIfMaxHeap(heap.getHeap()));

        int[] extracted = new int[n];
        for (int i = 0; i < n; i++) {
            extracted[i] = heap.extractMax().getPriority();
        }
        assertTrue(checkIfReverseSorted(extracted));
    }
}