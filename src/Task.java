import java.util.List;
/**
 * 
 * @author Rose Rotnem01
 * @version 10-23-2018
 */
public class Task {
//Initialize variables
    private Step parent;
    private int id;
    private String desc;
    private int duration;
    
    private int tick;
    
  
    /**
     * 
     * @param id id for task
     * @param desc description for task
     * @param duration time to take
     */
    public Task(int id, String desc, int duration) {
 
        //assign variables
        this.id = id;
        this.desc = desc;
        this.duration = duration;
    }
    /**
     * 
     * @return id the id of task
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * 
     * @param parent the parent of this
     */
    public void setParent(Step parent) {
        
        this.parent = parent;
    }
    /**
     * 
     * @return desc the description of task
     */
    public String getDesc() {
        return this.desc;
    }
    
    /**
     * 
     * @return duration the time frame of task
     */
    public int getDuration() {
        return this.duration;
    }
    /**
     * 
     * @return priority the priority of the task
     */
    public int getPriority() {
        return parent.getPriority();
    }

    /**
     * Simulates a clock tick by updating the duration.
     */
    public void tick() {
        tick++;
        
        
    }
    
    /**
     * 
     * @return true or false if task completes
     */
    public boolean isCompleted() {
        return tick == duration;
    }

    /**
     * Marks this task as complete, and removes it from the parent.
     *
     * @return the list of new tasks that are able to run,
     *         now that this task is complete
     */
    public List<Task> markComplete() {
//      This is correct, but will cause compile errors until you've
//      added some things to your classes.
//
        return this.parent.taskComplete(this);
      
    }
}
