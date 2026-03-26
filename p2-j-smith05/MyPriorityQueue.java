/**
 * MyPriorityQueue class that extends the MaxHeap class and implements the PriorityQueueInterface for managing tasks in a priority queue.
 * This class provides methods for enqueuing tasks, dequeuing tasks with the highest priority, checking if the queue is empty, and updating the priorities of tasks based on their waiting time.
 * The enqueue method inserts a task into the max-heap, while the dequeue method extracts the task with the highest priority.
 * The update method iterates through all tasks in the heap and increments their waiting time, increasing their priority if they have been waiting for a specified amount of time.
 * The class handles exceptions that may occur during heap operations and ensures that the heap property is maintained after updates.
 * @author Jacob Smith
 */
public class MyPriorityQueue extends MaxHeap implements PriorityQueueInterface {

    @Override
    public void enqueue(Task task) {
        try {
            insert(task); // Insert the task into the max-heap
        } catch (HeapException e) {
            System.err.println("enqueue failed: " + e.getMessage());
        }
    }

    @Override
    public Task dequeue() {
        try {
            return extractMax(); // Extract the task with the highest priority
        } catch (HeapException e) {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void update(int timeToIncrementPriority, int maxPriority) {

        // Iterate through all tasks in the heap and update their waiting time and priority if necessary
        for (int i = 0; i < size(); i++) {
            Task t = heap[i];
            if (t == null) continue;

            t.incrementWaitingTime();

            if (t.getWaitingTime() >= timeToIncrementPriority) {

                int currentP = t.getPriority();

                if (currentP < maxPriority) {
                    int newP = Math.min(maxPriority, currentP + 1);

                    try {
                        increaseKey(i, newP);
                    } catch (HeapException e) {
                        System.err.println("update failed at index " + i + ": " + e.getMessage());
                    }
                }
                t.resetWaitingTime();
            }
        }
    }
}