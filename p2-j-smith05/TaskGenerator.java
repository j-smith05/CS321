import java.util.Random;
/**
 * TaskGenerator class that implements the TaskGeneratorInterface for generating tasks and managing energy storage.
 * This class provides methods for creating new tasks, decrementing energy storage based on task types, resetting energy storage, and determining the outcome of tasks based on an unlucky probability.
 * The generateTask method uses a random number generator to determine whether a new task should be generated based on a specified probability.
 * The getUnlucky method calculates the outcome of a task (survived, passed out, or died) based on the task type's probabilities and the provided unlucky probability.
 * The toString method provides a string representation of a task based on its type and current energy storage.
 * @author Jacob Smith
 */
public class TaskGenerator implements TaskGeneratorInterface {

    private int currentEnergyStorage;
    private final Random rng;
    private double taskProbability = 0.5;

    public TaskGenerator(long seed) {
        this.currentEnergyStorage = DEFAULT_ENERGY;
        this.rng = new Random(seed);
    }

    public TaskGenerator(double probability, long seed) {
        this.currentEnergyStorage = DEFAULT_ENERGY;
        this.taskProbability = probability;
        this.rng = new Random(seed);
    }

    public TaskGenerator(double probability) {
        this(probability, System.nanoTime());
    }

    @Override
    public Task getNewTask(int hourCreated, TaskInterface.TaskType taskType, String taskDescription) {
        return new Task(0, taskType, taskDescription, hourCreated);
    }

    @Override
    public void decrementEnergyStorage(Task.TaskType taskType) {
        if (taskType == null) return;
        currentEnergyStorage -= taskType.getEnergyPerHour();
    }

    @Override
    public void resetCurrentEnergyStorage() {
        currentEnergyStorage = DEFAULT_ENERGY;
    }

    @Override
    public int getCurrentEnergyStorage() {
        return currentEnergyStorage;
    }

    @Override
    public void setCurrentEnergyStorage(int newEnergyNum) {
        currentEnergyStorage = Math.max(0, newEnergyNum);
    }

    @Override
    public boolean generateTask() {
        return rng.nextDouble() < taskProbability;
    }

    @Override
    public int getUnlucky(Task task, double unluckyProb) {

        // Determine the outcome of the task based on the unlucky probability and the task type's probabilities
        Task.TaskType type = task.getTaskType();

        double passoutProb = type.getPassingOutProbability();
        double deathProb   = type.getDyingProbability();

        if (unluckyProb <= passoutProb) {

            if (unluckyProb <= deathProb && type == Task.TaskType.MINING) {
                currentEnergyStorage = (int)(currentEnergyStorage * 0.25);
                task.setPriority(0);

                return DEATH; // Priority drops to 0, energy storage is reduced to 25%
            }
            currentEnergyStorage /= 2;
            return PASSED_OUT; // No change to priority, but energy storage is halved
        }
        return SURVIVED; // No change to energy storage or priority
    }

    @Override
    public String toString(Task task, Task.TaskType taskType) {
        if(taskType == Task.TaskType.MINING) {
            return "     Mining " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FISHING) {
            return "     Fishing " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")" ;
        }
        if(taskType == Task.TaskType.FARM_MAINTENANCE) {
            return "     Farm Maintenance " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FORAGING) {
            return "     Foraging " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")" ;
        }
        if(taskType == Task.TaskType.FEEDING) {
            return "     Feeding " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.SOCIALIZING) {
            return "     Socializing " + task.getTaskDescription() + " at " + currentEnergyStorage + " energy points (Priority:" + task.getPriority() +")";
        }
        else { return "nothing to see here..."; }
    }
}
