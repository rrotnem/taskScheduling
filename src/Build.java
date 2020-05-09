import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Rose
 * @version 10-28-2018
 */
public class Build {
    
    private List<Step> steps;
    private int priority;
    /**
     * constructor
     */
    public Build() {
    //empty constructor
        steps = new ArrayList<Step>();
    }
    /**
     * constructor take priority
     * @param priority priority for its build
     */
    public Build(int priority) {
        this.priority = priority;
        steps = new ArrayList<Step>();
    }
    /**
     * 
     * @return priority of the build
     */
    public int getPriority() {
        return this.priority;
    }
    /**
     * 
     * @param step a step to be added to build
     */
    public void addStep(Step step) {
        steps.add(step);
        step.setParent(this);
    }

    public List<Step> getSteps() {
        return this.steps;
    }

    /**
     * Returns the tasks that are currently ready to run.
     * @return the tasks that are currently ready to run
     */
    public List<Task> getWaitingTasks() {
        if (steps.isEmpty()) {
            return new ArrayList<Task>();
        }
        return steps.get(0).getTasks();

    }

    /**
     * Marks a Step complete, and removes it from the build.
     *
     * @param step the step
     * @return the list of new tasks that are able to run,
     *         now that this task is complete
     */
    public List<Task> stepComplete(Step step) {
//      This is correct, but will cause compile errors until you've
//      added some things to your classes.
//
        steps.remove(step);

        return getWaitingTasks();

    }
}
