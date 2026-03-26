/**
 * MaxHeap class that implements a max-heap data structure for managing tasks based on their priority.
 * The heap is implemented using an array and supports operations such as insertion, extraction of the maximum element, and increasing the priority of a task.
 * The class also includes methods for building the max-heap and maintaining the heap property after modifications.
 * The heap is designed to store Task objects, which are compared based on their priority and creation time.
 * The MaxHeap class is used as a base for the MyPriorityQueue class, which implements a priority queue interface for managing tasks in a more specific way.
 * @author Jacob Smith
 */

public class MaxHeap {

    static final int DEFAULT_CAPACITY = 10;

    protected Task[] heap;
    private int size;

    /**
     * Default constructor that initializes an empty max-heap with a default capacity. The 
     * internal array is created with the default capacity, and the size is set to 0.
     */
    public MaxHeap() {
        heap = new Task[DEFAULT_CAPACITY];
        size = 0;
    }
    
    /**
     * Constructor that builds a max-heap from an array of tasks. The input array is copied 
     * into the heap's internal array, and then the buildMaxHeap method is called to ensure 
     * that the max-heap property is maintained.
     * @param tasks
     */
    public MaxHeap(Task[] tasks) {
        heap = new Task[Math.max(DEFAULT_CAPACITY, tasks.length)];
        size = tasks.length;

        for (int i = 0; i < tasks.length; i++) {
            heap[i] = tasks[i];
        }

        buildMaxHeap();
    }

    /**
     * Checks if the heap is empty.
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of tasks in the heap.
     * @return the size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Returns the index of the parent of the node at index i.
     * @param i the index of the node whose parent index is to be calculated
     * @return the index of the parent node, or -1 if i is 0 (root node)
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Returns the index of the left child of the node at index i.
     * @param i the index of the node whose left child index is to be calculated
     * @return the index of the left child node
     */
    private int left(int i) {
        return 2 * i + 1; 
    }

    /**
     * Returns the index of the right child of the node at index i.
     * @param i the index of the node whose right child index is to be calculated
     * @return the index of the right child node
     */
    private int right(int i) {
        return 2 * i + 2;
    }

    /**
     * Swaps the tasks at indices i and j in the heap array.
     * @param i the index of the first task to be swapped
     * @param j the index of the second task to be swapped
     */
    private void swap(int i, int j) {
        Task temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Doubles the capacity of the heap array when the current 
     * size reaches the length of the array. This method creates 
     * a new array with double the capacity, copies the existing 
     * tasks to the new array, and then replaces the old array 
     * with the new one.
     */
    private void doubleCapacity() {
        if (size < heap.length) return;

        Task[] newHeap = new Task[heap.length * 2];
        for (int k = 0; k < heap.length; k++) {
            newHeap[k] = heap[k];
        }
        heap = newHeap;
    }

    /**
     * Compares two tasks based on their priority. Returns a positive number 
     * if a has higher priority than b, a negative number if a has lower 
     * priority than b, and 0 if they have
     * @param a the first task to compare
     * @param b the second task to compare
     * @return a positive number if a has higher priority than b, a 
     * negative number if a has lower priority than b, and 0 if they have the same priority
     */
    private int compare(Task a, Task b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    /**
     * Maintains the max-heap property for the subtree rooted at index i.
     * @param i the index of the subtree root to heapify
     */
    private void maxHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;

        // Compare with left child
        if (l < size && compare(heap[l], heap[largest]) > 0) {
            largest = l;
        }

        // Compare with right child
        if (r < size && compare(heap[r], heap[largest]) > 0) {
            largest = r;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    /**
     * Builds a max-heap from the current array of tasks. 
     * This method should be called after inserting all tasks 
     * into the heap to ensure that the max-heap property is maintained.
     */
    public void buildMaxHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    /**
     * Inserts a new task into the heap. 
     * @param task the task to be inserted
     * @throws HeapException if the task is null or if the heap is full
     */
    public void insert(Task task) throws HeapException {
        if (task == null) {
            throw new HeapException("Task cannot be null");
        }

        doubleCapacity(); // Ensure there is enough space for the new task
        heap[size] = task;
        int i = size;
        size++;
        
        while (i > 0 && compare(heap[i], heap[parent(i)]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Returns the task with the highest priority without removing it from the heap.
     * @return the task with the highest priority
     * @throws HeapException if the heap is empty
     */
    public Task max() throws HeapException {
        if (isEmpty()) {
            throw new HeapException("Heap is empty");
        }
        return heap[0];
    }

    /**
     * Removes and returns the task with the highest priority from the heap.
     * @return the task with the highest priority
     * @throws HeapException if the heap is empty
     */
    public Task extractMax() throws HeapException {
        if (isEmpty()) {
            throw new HeapException("Heap is empty");
        }

        // Store the maximum element, replace it with the last element in the heap, and then heapify to maintain the max-heap property
        Task max = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        maxHeapify(0);
        return max;
    }

    /**
     * Increases the priority of the task at index i to newPriority.
     * @param i the index of the task to be updated
     * @param newPriority the new priority value for the task
     * @throws HeapException if the index is out of bounds or if the 
     * new priority is less than the current priority
     */
    public void increaseKey(int i, int newPriority) throws HeapException {
        if (i < 0 || i >= size) {
            throw new HeapException("Index out of bounds");
        }
        if (newPriority < heap[i].getPriority()) {
            throw new HeapException("New priority must be greater than or equal to current priority");
        }
        
        // Update the priority of the task and then bubble up to maintain the max-heap
        heap[i].setPriority(newPriority);
        while (i > 0 && compare(heap[i], heap[parent(i)]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Returns a copy of the current array of tasks in the heap.
     * @return a copy of the current array of tasks in the heap
     */
    public Task[] getHeap() {
    Task[] copy = new Task[size];
    for (int i = 0; i < size; i++) { // Only copy the valid portion of the heap
        copy[i] = heap[i];
    }
    return copy;
    }
}