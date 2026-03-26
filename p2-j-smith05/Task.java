/**
 * Task class that implements the TaskInterface and Comparable interface for managing tasks in a priority queue.
 * Each task has a priority, type, description, waiting time, and the hour it was created.
 * The class provides methods for getting and setting the priority, getting the task type and description,
 * incrementing and resetting the waiting time, and comparing tasks based on their priority and creation time.
 * The compareTo method is implemented to allow tasks to be compared first by priority and then by hour created,
 * with earlier created tasks being considered higher priority in case of a tie in priority.
 * @author Jacob Smith
 */
public class Task implements TaskInterface, Comparable<Task> {

    private int priority;
    private TaskType taskType;
    private String taskDescription;
    private int waitingTime;
    private int hourCreated;

    /**
     * Constructor for Task class that initializes the task with a given priority, type, description, 
     * and hour created. The waiting time is initialized to 0.
     * @param priority an integer representing the priority of the task
     * @param taskType an enum representing the type of the task
     * @param taskDescription a string describing the task
     * @param hourCreated an integer representing the hour the task was created
     */
    public Task(int priority, TaskType taskType, String taskDescription, int hourCreated) {
        this.priority = priority;
        this.taskType = taskType;
        this.taskDescription = taskTaskDescriptionSafe(taskDescription);
        this.waitingTime = 0;
        this.hourCreated = hourCreated;
    }

    /**
     * Overloaded constructor for Task class that initializes the task with a given priority, type, and description.
     * The hour created is set to 0 by default, and the waiting time is initialized
     * @param priority an integer representing the priority of the task
     * @param taskType an enum representing the type of the task
     * @param taskDescription a string describing the task
     */
    public Task(int priority, TaskType taskType, String taskDescription) {
        this(priority, taskType, taskDescription, 0);
    }

    /**
     * Helper method to safely handle null task descriptions by returning an empty string if the input is null.
     * @param s a string representing the task description
     * @return a non-null string for the task description
     */
    private String taskTaskDescriptionSafe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Getter for the hour the task was created.
     * @return the hour the task was created
     */
    public int getHourCreated() {
        return hourCreated;
    }

    @Override
    public int compareTo(Task o) { // Compare tasks first by priority, then by hour created (earlier is higher priority)
        if (o == null) return 1;

        if (this.priority != o.priority) {
            return Integer.compare(this.priority, o.priority);
        }
        return Integer.compare(o.hourCreated, this.hourCreated);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public void incrementWaitingTime() {
        waitingTime++;
    }

    @Override
    public void resetWaitingTime() {
        waitingTime = 0;
    }

    @Override
    public int getWaitingTime() {
        return waitingTime;
    }
    @Override
    public String toString() {
        return taskType + " " + taskDescription + " at Hour: " + hourCreated + ":00";
    }
}