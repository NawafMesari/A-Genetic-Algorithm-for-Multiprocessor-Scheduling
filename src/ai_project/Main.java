package ai_project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main{
    
    // to store all tasks given in input file
    public static Task allTasks [];
    
    // to store all generated populations
    public static ArrayList<Population> populations = new ArrayList();
    
    
    
    public static void main(String[] args)
    {

        runMainFunc("C:\\Users\\Abdul\\Documents\\NetBeansProjects\\AI_Project\\instance3.txt");
        
    }
    
    public static void runMainFunc(String inputFilePath)
    {
        allTasks = readFromFile(inputFilePath);   
        
        // sort all Tasks that built from input graph in ascending order of Hight
        sortAllTasks();
        // give all Tasks IDs alse in ascending order
        giveTasksID();
        
        // assume we have fixed number of schedule in each population
        int Num_OF_Schedules = 100;
        
        // create first population
        Population p1 = new Population(Num_OF_Schedules);
        
        p1.fill_First_Population();
        p1.calc_schedules_Ratio();
        p1.calc_Lower_Upper_bound();
        p1.mutation();
        p1.calcPopAveTime();
        
        // add the first populatoin to the populations list
        populations.add(p1);
        
        // now create the next populations
        
        int max_num_of_generations = 1000;
        int no_useful_generations = 0;  // if on improvments during next 100 iteration, stop       
        
        for(int i=0; i<max_num_of_generations; i++)
        {
            // create another population
            populations.add(populations.get(i).crossOver());
            populations.get(i+1).calc_schedules_Ratio();
            populations.get(i+1).calc_Lower_Upper_bound();
            populations.get(i+1).mutation();
            populations.get(i+1).calcPopAveTime();
            
            if((populations.get(i).getBestSchedule().getFinishTime() <= populations.get(i+1).getBestSchedule().getFinishTime())) {
              no_useful_generations++;
            }
            else {
              no_useful_generations = 0;  
            }
            
            if(no_useful_generations >= 100)   // if no improvment after next 100 generations .. stop and remove the last 100 generation
            {  
              int pop_size_without_the_100_duplicate_generation = populations.size()-100;
              
              for(int j=(populations.size()-1); j>=pop_size_without_the_100_duplicate_generation; j--) {
                populations.remove(j);
              }
   
              break;
            } 
        }
        

        // output in the console : 
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("We want to schedule : " + allTasks.length + " Tasks.");
        System.out.println("Generated Populations : " + (populations.size()) + " Populations." );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        
        for(int i=0; i<populations.size(); i++)
        {
          System.out.println("\nBest Schedule In Population Number " + (i+1) + ", Is : ");
          populations.get(i).getBestSchedule().print();
        }
    }
    
    public static Task[] readFromFile(String inputFile) 
    {
        
        java.io.File file = new java.io.File(inputFile);
        
        // to read line by line from the file .. every line divided into parts and stored in array called (Parts)
        String Parts[];
        String subParts[];
        
        // temp array of all tasks in the file
        Task allTasks [] = null;
        
         try
         {
            Scanner input = new Scanner(file);
            
            // temp variables
            int tasksNum;
            int execTime;
            int hight;        
            int taskCounter = 0;
            boolean flag = true;
            
            
            // loop on the file line by line
            while(input.hasNext()) 
            {   
              // get data in that line 
              String line = input.nextLine();
              
              // to read the number of tasks in first line of the input file
              if(flag)
              {  
                tasksNum = Integer.parseInt(line);
                allTasks = new Task[tasksNum];               
                flag = false;
                continue;
              }
              
              
              
              allTasks[taskCounter] = new Task();
             
              
              
              Parts = line.split(" ");
              
              // for predecessors
              if(Parts[0].equals("-"))
              {
                allTasks[taskCounter].setPredecessorsNum(0);
              }
              else{
                  subParts = Parts[0].split(",");
                  
                  if(subParts.length == 1){
                    allTasks[taskCounter].setPredecessorsNum(1);
                    int tempArr [] = new int[1];  // create temp array to store predecessor number
                    tempArr[0] = Integer.parseInt(Parts[0]);
                    allTasks[taskCounter].setDependences(tempArr);
                  }
                  else{
                    allTasks[taskCounter].setPredecessorsNum(subParts.length); 
                      
                    int tempArr [] = new int[subParts.length];  // create temp array to store predecessors numbers
                    for(int i=0; i<tempArr.length; i++)
                    {
                      tempArr[i] = Integer.parseInt(subParts[i]);
                    }
                   
                    
                    allTasks[taskCounter].setDependences(tempArr);
                  }
              }
              
              // for successors
              if(Parts[1].equals("-"))
              {
                 allTasks[taskCounter].setSuccessorsNum(0);
              }
              else{
                  subParts = Parts[1].split(",");
                  
                  if(subParts.length == 1){
                    allTasks[taskCounter].setSuccessorsNum(1);
                    int tempArr [] = new int[1];  // creat temp array to store successor number
                    tempArr[0] = Integer.parseInt(Parts[1]);
                    allTasks[taskCounter].setSuccessors(tempArr);
                  }
                  else{
                    allTasks[taskCounter].setSuccessorsNum(subParts.length);
                    
                    int tempArr [] = new int[subParts.length];  // creat temp array to store successors numbers
                    for(int i=0; i<tempArr.length; i++)
                    {
                      tempArr[i] = Integer.parseInt(subParts[i]);
                    }
                  
                    allTasks[taskCounter].setSuccessors(tempArr);
                  }
                 
              }
              
              execTime = Integer.parseInt(Parts[2]);
              allTasks[taskCounter].setExecTime(execTime);
              
              hight = Integer.parseInt(Parts[3]);
              allTasks[taskCounter].setHight(hight);
             
              taskCounter++;
             }
    
            
         }catch(Exception e){
             System.out.println("Error while reading the input File !!");
             System.out.println(e.getStackTrace());
         }
             
        return allTasks;    
    }
    
    // sort all Tasks that built from input graph in ascending order of Hight
    public static void sortAllTasks()
    {
      for(int i=0; i<allTasks.length-1; i++)
      {
        for(int j=i+1; j<allTasks.length; j++)
        {
          if(allTasks[i].getHight() > allTasks[j].getHight())
          {
            swap(allTasks[i], allTasks[j]);
          }
        }
      }
    }
    
    public static void swap(Task t1, Task t2)
    {
      Task temp = new Task();
      
      temp.setExecTime(t1.getExecTime());
      temp.setHight(t1.getHight());
      temp.setSuccessorsNum(t1.getSuccessorsNum());
      temp.setPredecessorsNum(t1.getPredecessorsNum());
      temp.setDependences(t1.getDependences());
      temp.setSuccessors(t1.getSuccessors());
      
      t1.setExecTime(t2.getExecTime());
      t1.setHight(t2.getHight());
      t1.setSuccessorsNum(t2.getSuccessorsNum());
      t1.setPredecessorsNum(t2.getPredecessorsNum());
      t1.setDependences(t2.getDependences());
      t1.setSuccessors(t2.getSuccessors());
      
      t2.setExecTime(temp.getExecTime());
      t2.setHight(temp.getHight());
      t2.setSuccessorsNum(temp.getSuccessorsNum());
      t2.setPredecessorsNum(temp.getPredecessorsNum());
      t2.setDependences(temp.getDependences());
      t2.setSuccessors(temp.getSuccessors());
      
    }
    
    // to store all generated populations
    public static void giveTasksID()
    {
      for(int i=0; i<allTasks.length; i++)
      {
        allTasks[i].setId(i+1);
      }
    }

}
