import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * @author Rose
 * @version 10-23-2018
 */
public class Step {

    private Build parent;
   
    private List<Task> tasks;
    /**
     * constructor
     */
    public Step() {
        tasks = new ArrayList<Task>();
    }
    /**
     * 
     * @param id new id
     * @param desc description
     * @param duration time frame
     */
    public Step(int id, String desc, int duration) {
        this();
        tasks = new ArrayList<Task>();
        addTask(new Task(id, desc, duration));
    }
    /**
     * 
     * @param parent a build parent
     */
    public void setParent(Build parent) {
        this.parent = parent;
        
    }
    /**
     * 
     * @return priority of the build
     */
    public int getPriority() {
        return parent.getPriority();
    }
    /**
     * 
     * @param task the task to be added to step
     */
    public void addTask(Task task) {
        tasks.add(task);
        task.setParent(this);
    }

    /**
     * 
     * @return a list of tasks
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Marks a task as complete, and removes it from the parent.
     *
     * @param t the task
     * @return the list of new tasks that are able to run,
     *         now that this task is complete
     */
    public List<Task> taskComplete(Task t) {
//        This is correct, but will cause compile errors until you've
//        added some things to your classes.
//
        tasks.remove(t);
        if (!tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return this.parent.stepComplete(this);

    }
}
