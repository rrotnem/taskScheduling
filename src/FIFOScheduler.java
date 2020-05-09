import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Rose
 * @version 1
 */

public class FIFOScheduler implements Scheduler {

    private List<Task> taskQueue;
    private List<Build> builds;

    private List<String> log;
    private long start;
    private int numIdleServers;
    
    /**
     * @param numServers for task
     */
    public FIFOScheduler(int numServers) {

        numIdleServers = numServers;
        builds = new ArrayList<Build>();
        taskQueue = new ArrayList<Task>();
        log = new ArrayList<String>();
        start = System.currentTimeMillis();
        
    }

    @Override
    public void addBuild(Build build) {
 
        //        to run into a queue (getWaitingTasks() will return them)
        
        builds.add(build);
        taskQueue.addAll(build.getWaitingTasks());
    }

    @Override
    public List<Build> getBuilds() {
        return this.builds;
    }
    
    /**
     * @return task next task
     */
    private Task getNextTask() {
        if (taskQueue.size() > 0) {
            return taskQueue.remove(0);
        }
        
        return null;
    }
    
    @Override
    public void run() {
        //  While there are tasks that haven't finished...

        //  Execute one tick of each running task

        //  When you call markComplete() because a Task has finished,
        //  it will return tasks that are now ready to run.  Add them
        //  to your queue.

        //  If there are fewer running tasks than taserver, start more tasks

        List<Task> runningTasks = new ArrayList<>();
        int timer = 0;
        while (true) {
        
            while (numIdleServers > 0) {
                Task task = getNextTask();
                if (task == null) {
                    break;
                }         
                
                runningTasks.add(task);
                // log task start time is timer
                log.add(timer + ":" + task.getId() + ":" 
                           + "started:" + task.getDesc());
                numIdleServers--;
            }
           
            if (runningTasks.isEmpty()) {
                break;
            }
            
            for (Task task :runningTasks) {
                task.tick();
            }
            timer++;
            
            Iterator<Task> it = runningTasks.iterator();
            while (it.hasNext()) {
                Task running = it.next();
                if (running.isCompleted()) {
                    // log running task complete time is timer
                    log.add(timer + ":" + running.getId() + ":" 
                            + "completed:" + running.getDesc());
                    taskQueue.addAll(running.markComplete());
                    numIdleServers++;
                    it.remove();
                }
            }

        }
    }

    @Override
    public List<String> getLogs() {

        return log;
    }
    

}

