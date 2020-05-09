import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PriorityScheduler implements Scheduler {

    private int numIdleServers;
    private List<Task> taskQue;
    private List<Build> builds;
    private List<String> log;
    
    public PriorityScheduler(int numServers) {
        
        this.numIdleServers = numServers;
        this.builds = new ArrayList<Build>();
        this.taskQue = new ArrayList<Task>();
        this.log = new ArrayList<String>();
    }
    
    /**
     * @author Rose
     * @version 1
     */
    public class CompareTask extends TaskCompare {

        public CompareTask() {
           
        }
        @Override
        public int compareTo(Task task, Task task1) {
            
            
            return task.getPriority()-task1.getPriority();
        }
       
    }

    @Override
    public void addBuild(Build build) {
        
        builds.add(build);
        taskQue.addAll(build.getWaitingTasks());
        
    }

    @Override
    public List<Build> getBuilds() {
        return this.builds;
    }
    
    /**
     * @return task next task
     */
    private Task getNextTask() {
        if (taskQue.size() > 0) {
            return taskQue.remove(0);
        }
        
        return null;
    }

    @Override
    public void run() {

        List<Task> runningTasks = new ArrayList<>();
       
        int timer = 0;
        while (true) {
        
            while (numIdleServers > 0) {
                if (!taskQue.isEmpty()) {
                    TaskPriorityQueue que = new TaskPriorityQueue(taskQue, new CompareTask());
                    taskQue = que.sort();
                }
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
                    taskQue.addAll(running.markComplete());
                   
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
    
    /**
     * @author Rose
     * @version 1
     */
    public class TaskPriorityQueue {
        private List<Task> taskQueue;
        private TaskCompare com;
        
        /**
         * @param taskQue que for sort
         * @param taskCompare compare
         */
        public TaskPriorityQueue(List<Task> taskQue, TaskCompare taskCompare) {
            
           this.taskQueue = taskQue;
           this.com = taskCompare;
            
        }
        
        /**
         * @return list a list of tasks
         */
        public List<Task> sort() {

            Task task = taskQueue.get(0);
            int i = 1;
            while (i < taskQueue.size()) {
                
                if(com.compareTo(task, taskQueue.get(i)) < 0) {
                    Task copy = taskQueue.remove(i);
                    taskQueue.add(0, copy);
                    break;
                }
                i++;
            }
            
            return taskQueue;
            
        }
        
        
    }
}
