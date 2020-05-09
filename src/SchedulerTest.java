import java.util.List;

import junit.framework.TestCase;

/**
 * @author Rose
 * @version 01
 */
public class SchedulerTest extends TestCase {

    /**
     * test
     */
    public void testTask() {
        Build build = new Build(2);
        build.addStep(new Step(111, "Compile code", 5));
        build.addStep(new Step(121, "Run tests", 2));
        build.addStep(new Step(131, "Deploy changes", 1));
      
        Task task = build.getSteps().get(0).getTasks().get(0);
        task.markComplete();
        assertEquals(build.getSteps().size(), 2); 

        Step newStep = new Step(141, "add tests", 2);
        build.addStep(newStep);
    
       
        assertEquals(task.getDesc(),"Compile code");
        assertEquals(task.getId(), 111);  
        assertEquals(task.getDuration(),5);
        assertEquals(build.getSteps().size(), 3);  
        assertEquals(build.getPriority(), 2);

        
        
    }


    
    /**
     * test
     */
    public void testFIFOScheduler() {
        
        FIFOScheduler s = new FIFOScheduler(1);
        Build build = new Build();
        build.addStep(new Step(111, "Compile code", 5));
        build.addStep(new Step(121, "Run tests", 2));
        build.addStep(new Step(131, "Deploy changes", 1));
        Build newBuild = new Build();
        newBuild.addStep(new Step(211, "second build code", 3));
        newBuild.addStep(new Step(221, "second build", 2));
        
        s.addBuild(build);
        List<Build> builds = s.getBuilds();
        assertEquals(builds.size(), 1);
        
        //s.addBuild(newBuild);
        
        s.run();
        
        for (String str: s.getLogs()) {
            System.out.print(str);
        }
    }
        

    public void testPriorityScheduler() {
            
        PriorityScheduler s = new PriorityScheduler(2);
        Build build = new Build(1);
        build.addStep(new Step(111, "Compile code", 2));
        Task task1 = new Task(121, "Run UI tests", 2);
        Task task2 = new Task(122, "Run backend tests", 3);
        Task task3 = new Task(123, "Run integration tests", 1);
        Step step = new Step();
        step.addTask(task1);
        step.addTask(task2);
        step.addTask(task3);
        build.addStep(step);
        build.addStep(new Step(131, "Deploy changes", 2));       

        s.addBuild(build);
        
        
        Build build2 = new Build(2);
        build2.addStep(new Step(211, "Second Compile code", 2));
        Task task4 = new Task(221, "Second Run UI tests", 3);
        Task task5 = new Task(222, "Second Run backend tests", 2);
        
        Step step1 = new Step();
        step1.addTask(task4);
        step1.addTask(task5);
      
        build2.addStep(step1);
        build2.addStep(new Step(231, "Second Deploy changes", 3));
        s.addBuild(build2);

        s.run();
        
        assertEquals(s.getBuilds().size(), 2);

        for (String str : s.getLogs()) {
            System.out.print(str + "\n");
        }
    }
    public void testPreemptiveScheduler() {
        
      PreemptiveScheduler s = new PreemptiveScheduler(2);
      Build build = new Build(1);
      build.addStep(new Step(111, "Compile code", 2));
      Task task1 = new Task(121, "Run UI tests", 2);
      Task task2 = new Task(122, "Run backend tests", 3);
      Task task3 = new Task(123, "Run integration tests", 1);
      Step step = new Step();
      step.addTask(task1);
      step.addTask(task2);
      step.addTask(task3);
      build.addStep(step);
      build.addStep(new Step(131, "Deploy changes", 2));       

      s.addBuild(build);
      
      
      Build build2 = new Build(2);
      build2.addStep(new Step(211, "Second Compile code", 2));
      Task task4 = new Task(221, "Second Run UI tests", 3);
      Task task5 = new Task(222, "Second Run backend tests", 2);
      
      Step step1 = new Step();
      step1.addTask(task4);
      step1.addTask(task5);
    
      build2.addStep(step1);
      build2.addStep(new Step(231, "Second Deploy changes", 3));
      s.addBuild(build2);

      s.run();
      
      assertEquals(s.getBuilds().size(), 2);

      for (String str : s.getLogs()) {
          System.out.print(str + "\n");
      }
        
  }


}
