
package ai_project;

import static ai_project.Main.*;
import java.util.ArrayList;
import java.util.Random;

public class Population {

    
  private int id;
  private float aveTime;  
  private int numOfSchedules;
  private int numOfCurrentSchedules;
  private Schedule [] schedules;
  
  
    // Constructors

    public Population(int numOfSchedules) {
        this.numOfSchedules = numOfSchedules;
        schedules = new Schedule[numOfSchedules];
    }

    public Population(int id, float aveTime,int numOfSchedules, Schedule[] schedules) {
          this.id = id;
          this.aveTime = aveTime;
          this.schedules = schedules;
          this.numOfSchedules = numOfSchedules;
          
          schedules = new Schedule[numOfSchedules];
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setAveTime(float aveTime) {
        this.aveTime = aveTime;
    }

    public void setNumOfSchedules(int numOfSchedules) {
        this.numOfSchedules = numOfSchedules;
    }
    
    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }
    
    public void setSchedules(Schedule s, int index) {
        this.schedules[index] = s;
    }

    public void setNumOfCurrentSchedules(int numOfCurrentSchedules) {
        this.numOfCurrentSchedules = numOfCurrentSchedules;
    }
    
    
    // Getters

    public int getId() {
        return id;
    }

    public float getAveTime() {
        return aveTime;
    }

    public int getNumOfSchedules() {
        return numOfSchedules;
    }
    
    public Schedule[] getSchedules() {
        return schedules;
    }

    public int getNumOfCurrentSchedules() {
        return numOfCurrentSchedules;
    }
    
    
    
    
    // others
    
    // sort schedules by their finish time from worst to best (used in selection operation)
    public void sortSchedules()
    {
       for(int i=0; i<this.schedules.length-1; i++)      
       {
         for(int j=i+1; j<this.schedules.length; j++)       
         {
           if(this.schedules[i].getFinishTime() < this.schedules[j].getFinishTime())
           {
             swap(this.schedules[i], this.schedules[j]);
           }
         }
       }
    }
    
    public void swap(Schedule s1, Schedule s2)
    {
      Schedule temp = new Schedule();
      
      temp.setId(s1.getId());
      temp.setFinishTime(s1.getFinishTime());
      temp.setP1_tasks(s1.getP1_tasks());
      temp.setP2_tasks(s1.getP2_tasks());
      temp.setP1_size(s1.getP1_size());
      temp.setP2_size(s1.getP2_size());
      temp.setFitnessRatio(s1.getFitnessRatio());
      temp.setUpperBound(s1.getUpperBound());
      temp.setLowerBound(s1.getLowerBound());
      
      s1.setId(s2.getId());
      s1.setFinishTime(s2.getFinishTime());
      s1.setP1_tasks(s2.getP1_tasks());
      s1.setP2_tasks(s2.getP2_tasks());
      s1.setP1_size(s2.getP1_size());
      s1.setP2_size(s2.getP2_size());
      s1.setFitnessRatio(s2.getFitnessRatio());
      s1.setUpperBound(s2.getUpperBound());
      s1.setLowerBound(s2.getLowerBound());
      
      s2.setId(temp.getId());
      s2.setFinishTime(temp.getFinishTime());
      s2.setP1_tasks(temp.getP1_tasks());
      s2.setP2_tasks(temp.getP2_tasks());
      s2.setP1_size(temp.getP1_size());
      s2.setP2_size(temp.getP2_size());
      s2.setFitnessRatio(temp.getFitnessRatio());
      s2.setUpperBound(temp.getUpperBound());
      s2.setLowerBound(temp.getLowerBound());
      
    }
    
    public void calc_schedules_Ratio()
    {
        // sort schedules by their finish time from worst to best
        this.sortSchedules();
        
        float t = 0;
        int sumOfAllRanks = 0;
        
        for(int i=1; i<=this.schedules.length; i++)
            sumOfAllRanks += i;
        
        // calc fitness ration for each schedule in the population
        for (int i = 0; i < this.schedules.length; i++) {
            t = (float) (i + 1) / (float) sumOfAllRanks;
            this.schedules[i].setFitnessRatio(t);
        }
    }
    
    public void calc_Lower_Upper_bound()
    {
      this.schedules[0].setLowerBound(0.0f);
      this.schedules[0].setUpperBound(this.schedules[0].getFitnessRatio());
        
      for(int i=1; i<this.schedules.length; i++)
      {
        this.schedules[i].setLowerBound(this.schedules[i-1].getUpperBound() + 0.00000001f);
        this.schedules[i].setUpperBound(this.schedules[i].getLowerBound() + this.schedules[i].getFitnessRatio());
      }
    }

    public void fill_First_Population()
    {
        
        int r;
        Random rand;
        
        int len;
        int previousIndex;
        
        for(int i=0; i<this.getNumOfSchedules(); i++) {
                
          // create tmep schedule  
          Schedule tempSch = new Schedule();
          tempSch.setId(i+1);
          
          // visit all tasks and form random schedules to fill the populatoin
          for(int j=0; j<allTasks.length; j++) {
           
            // create a temp task
            Task tempTask = new Task();
            tempTask.setId(allTasks[j].getId());
            tempTask.setExecTime(allTasks[j].getExecTime());
            tempTask.setHight(allTasks[j].getHight());
            tempTask.setPredecessorsNum(allTasks[j].getPredecessorsNum());
            tempTask.setSuccessorsNum(allTasks[j].getPredecessorsNum());
            tempTask.setDependences(allTasks[j].getDependences());
            tempTask.setSuccessors(allTasks[j].getSuccessors());
            
            
            // generate a rondom number to put this task in either CPU-1 or CPU-2
            rand = new Random();
            r = rand.nextInt(2)+1;
            
 
            if(r==1)
            { 
              // when we fill the first task in the 1st processor   
              if(tempSch.getP1_size() == 0) 
              {
                tempTask.setStartTime(0);
                tempTask.setFinishTime(tempTask.getExecTime());
              }
              else
              {
                 // to know the finish time for the previous task to fill the start time of the current task
                 previousIndex = (tempSch.getP1_size() - 1);
                 tempTask.setStartTime(tempSch.getP1_tasks()[previousIndex].getFinishTime());
                 tempTask.setFinishTime(tempTask.getStartTime() + tempTask.getExecTime());
              }
              
              tempSch.getP1_tasks()[tempSch.getP1_size()] = tempTask;
              tempSch.setP1_size(tempSch.getP1_size()+1);
            }
            else if(r==2)
            {
              // when we fill the first task in the 2nd processor   
              if(tempSch.getP2_size() == 0) 
              {
                tempTask.setStartTime(0);
                tempTask.setFinishTime(tempTask.getExecTime());
              }
              else
              {
                 // to know the finish time for the previous task to fill the start time of the current task
                 previousIndex = (tempSch.getP2_size() - 1);
                 tempTask.setStartTime(tempSch.getP2_tasks()[previousIndex].getFinishTime());
                 tempTask.setFinishTime(tempTask.getStartTime() + tempTask.getExecTime());
              }  
                
              tempSch.getP2_tasks()[tempSch.getP2_size()] = tempTask; 
              tempSch.setP2_size(tempSch.getP2_size()+1);
            }
            
          }
          
          
          
          
          
          // ----------------------------------------------------------- check duplicate schedules before adding to population
          boolean flag = true;
          for(int z=0; z<i; z++)
          {
            if(checkDuplicateSchedules(this.getSchedules()[z], tempSch));
            else
            {
               flag=false;
               break;
            }
          }
         
          // if the temp schedule is not repeated so .. add it to the population
          if(flag == true){
          
             // calc finish time for the schedule
             // first update the start time and finish time for each task in the schedule by calling (calc_final_finishTime())
             // then determine the longest finish time between the two processors and assign it to the member data (finish time) of the schedule
             tempSch.calc_fitness_function();
         
             this.setSchedules(tempSch, i);
             
             this.setNumOfCurrentSchedules( this.getNumOfCurrentSchedules() + 1);
          }
          else
          {
             i--;
          }
         // -------------------------------------------------------------
         
          
        }
 
    } 
  
    public static boolean checkDuplicateSchedules(Schedule s1, Schedule s2)
    {
       if( (s1.getP1_size() != s2.getP1_size()) || (s1.getP2_size() != s2.getP2_size()) )
           return true;
       
       for(int i=0; i<s1.getP1_size(); i++)
       {
         if(s1.getP1_tasks()[i].getId() != s2.getP1_tasks()[i].getId())
             return true;
       }
       
       for(int i=0; i<s1.getP2_size(); i++)
       {
         if(s1.getP2_tasks()[i].getId() != s2.getP2_tasks()[i].getId())
             return true;
       }
       
       return false;
    }
    
    public void calcPopAveTime()
    {
      float sumFinishesTime = 0;  
      for(int i=0; i<this.getNumOfSchedules(); i++)
      {
        sumFinishesTime += this.schedules[i].getFinishTime();
      }
      
      this.setAveTime(sumFinishesTime/this.numOfSchedules);
    }
    
    public Population crossOver()
    {
        
      Population nextPop = null;
      
      // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> adding the best five schedules at the beggining
      if(this.numOfSchedules == 100){
        nextPop = new Population(this.numOfSchedules+5);   // 5 is the best five schedules from the previos population
      }
      else if(this.numOfSchedules == 105){
        nextPop = new Population(this.numOfSchedules);  
      }
      
      addFiveBestSchedule(nextPop);
     // System.out.println("-------------------------------------------------------------> best five schedule added sccessfully !!");
      
      // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> adding the best five schedules at the beggining
     
     
     
     
      // temp schedules list that keeps track of 50% of the schedules in the population to do cross over operation on them 
      ArrayList<Schedule> tempList = new ArrayList();
      
      Random r = new Random();
      float min, max, result; // variables to deal with the random number
      
      float cross_over_ratio = 0.7f;
      
      // count to indicate the number of selected schedules from current population (must be 50%)
      // whenever you select a schedule and put it in tempList increase the counter by 1
      // at the end I should have (number of schedules in the populiatn / 2) in tempList
      int count = 0;
      
      // select 50% from schedules in the population
      
      int x=0;
      if(this.getNumOfSchedules() == 100)
        x = 100;
      else if(this.getNumOfSchedules() == 105)
        x = 105;      
      
      while(count < 50)
      {
        // ----------------------------- generate random number
        min = 0.0f;
        max = 0.99f;
        result = (float)Math.random() * (max - min) + min; 
        // ----------------------------------------------------
        
        // get the schedule that random number points to
        for(int j=0; j<this.numOfSchedules; j++)
        {
          if((result >= this.schedules[j].getLowerBound()) && (result < this.schedules[j].getUpperBound()) && (this.schedules[j].isSelected()==false))
          {
              tempList.add(this.schedules[j]);
              this.schedules[j].setSelected(true);
              count++;
              break;
          }
        } 
      }
      
      // after we fill the tempList with 50% of the schedules ..
      // loop on them and generate random number .. if it's under cross_over_ratio then move this schedule into Cross Over Box
      
      // cross over box to do cross over operation 
      ArrayList<Schedule> CO_Box = new ArrayList();
      
      // variable to determine number of crossOver operatoin that have done
      int CO_counter = 1;
     
      
      while(CO_counter <= (100/2))   // ----------------------------------------------------------------------------------------------------------------------> this.numOfSchedules is wrong beacase we must do (100/2) cross over operation only not(105/2)
      {
        for(int i=0; i<tempList.size(); i++)
        {
          // ----------------------------- generate random number
          min = 0.0f;
          max = 0.99f;
          result = (float)Math.random() * (max - min) + min; 
          // ----------------------------------------------------

          if(result <= cross_over_ratio)
          {
             // ------------------------------------------------ added code 
             if(CO_Box.size() == 1)
             {
                if(this.schedules[i].getId() == CO_Box.get(0).getId()){
                  
                }
                else {
                  CO_Box.add(this.schedules[i]);
                } 
             }
             else
             {
               CO_Box.add(this.schedules[i]);
             }
             // ------------------------------------------------ 
                 
          }
          if(CO_Box.size() == 2)
          {
            doCrossOverOp(CO_Box.get(0), CO_Box.get(1), nextPop); 
            CO_counter++;
            CO_Box.clear();
          }
          if(CO_counter > (100/2))         // ----------------------------------------------------------------------------------------------------------------------> this.numOfSchedules is wrong beacase we must do (100/2) cross over operation only not(105/2)
              break;
        }
      }
       
      
      return nextPop;
    }
    
    
    public void addFiveBestSchedule(Population nextPop)
    {
       // add the best five scheduls from the prevois population
      Schedule tempFiveSch [] = new Schedule[5];
      
       for(int j=0; j<tempFiveSch.length; j++)   // create them by new
       { 
         tempFiveSch[j] = new Schedule();
       }
   
      // -----------------------------------------------------------------------------------------------fill processor1 and processor2 in each ( tempFiveSch )
      
  
      for(int j=0; j<tempFiveSch.length; j++)
      {    
       
         tempFiveSch[j].setFinishTime(this.getFiveBestSchedules()[j].getFinishTime());

         tempFiveSch[j].setP1_tasks(this.getFiveBestSchedules()[j].getP1_tasks());
         tempFiveSch[j].setP2_tasks(this.getFiveBestSchedules()[j].getP2_tasks());
         tempFiveSch[j].setP1_size(this.getFiveBestSchedules()[j].getP1_size());
         tempFiveSch[j].setP2_size(this.getFiveBestSchedules()[j].getP2_size());

         tempFiveSch[j].setFitnessRatio(this.getFiveBestSchedules()[j].getFitnessRatio());
         tempFiveSch[j].setUpperBound(this.getFiveBestSchedules()[j].getUpperBound());
         tempFiveSch[j].setLowerBound(this.getFiveBestSchedules()[j].getLowerBound());
         
         // change the id 
         tempFiveSch[j].setId(nextPop.getNumOfCurrentSchedules()+1);
         
         tempFiveSch[j].calc_start_finish_time();
         tempFiveSch[j].calc_fitness_function();
         
         // add the schedule
         nextPop.getSchedules()[nextPop.getNumOfCurrentSchedules()] = tempFiveSch[j];
         nextPop.setNumOfCurrentSchedules(nextPop.getNumOfCurrentSchedules()+1);
      }
  
    }
    
    public void doCrossOverOp(Schedule s1, Schedule s2, Population next_pop)
    {
       Schedule ch1 = new Schedule();
       Schedule ch2 = new Schedule();
       
       ch1.setId(next_pop.getNumOfCurrentSchedules()+1);
       ch2.setId(next_pop.getNumOfCurrentSchedules()+1);
       
       // make random cross over point
       Random r = new Random();
       int crossOverPoint = r.nextInt(Main.allTasks[Main.allTasks.length-1].getHight() - 1);
       
       // loop on s1.p1 left part
       for(int i=0; i<s1.getP1_size(); i++)
       {  
          if(s1.getP1_tasks()[i].getHight() <= crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s1.getP1_tasks()[i].getId());
            tempTask.setExecTime(s1.getP1_tasks()[i].getExecTime());
            tempTask.setHight(s1.getP1_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s1.getP1_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s1.getP1_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s1.getP1_tasks()[i].getDependences());
            tempTask.setSuccessors(s1.getP1_tasks()[i].getSuccessors());

            ch1.getP1_tasks()[ch1.getP1_size()] = tempTask;
            ch1.setP1_size(ch1.getP1_size()+1);
          }
       }
       
       // loop on s2.p1 left part
       for(int i=0; i<s2.getP1_size(); i++)
       {
          if(s2.getP1_tasks()[i].getHight() <= crossOverPoint)
          { 
            Task tempTask = new Task(); 
            tempTask.setId(s2.getP1_tasks()[i].getId());
            tempTask.setExecTime(s2.getP1_tasks()[i].getExecTime());
            tempTask.setHight(s2.getP1_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s2.getP1_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s2.getP1_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s2.getP1_tasks()[i].getDependences());
            tempTask.setSuccessors(s2.getP1_tasks()[i].getSuccessors());

            ch2.getP1_tasks()[ch2.getP1_size()] = tempTask;
            ch2.setP1_size(ch2.getP1_size()+1);
          }
       }
      
      
       // loop on s2.p1 right part
       for(int i=0; i<s2.getP1_size(); i++)
       {
          if(s2.getP1_tasks()[i].getHight() > crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s2.getP1_tasks()[i].getId());
            tempTask.setExecTime(s2.getP1_tasks()[i].getExecTime());
            tempTask.setHight(s2.getP1_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s2.getP1_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s2.getP1_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s2.getP1_tasks()[i].getDependences());
            tempTask.setSuccessors(s2.getP1_tasks()[i].getSuccessors());  
              
            ch1.getP1_tasks()[ch1.getP1_size()] = tempTask;
            ch1.setP1_size(ch1.getP1_size()+1);
          }
       }
       
       // loop on s1.p1 right part
       for(int i=0; i<s1.getP1_size(); i++)
       {
          if(s1.getP1_tasks()[i].getHight() > crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s1.getP1_tasks()[i].getId());
            tempTask.setExecTime(s1.getP1_tasks()[i].getExecTime());
            tempTask.setHight(s1.getP1_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s1.getP1_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s1.getP1_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s1.getP1_tasks()[i].getDependences());
            tempTask.setSuccessors(s1.getP1_tasks()[i].getSuccessors());
            
            ch2.getP1_tasks()[ch2.getP1_size()] = tempTask;
            ch2.setP1_size(ch2.getP1_size()+1);
          }
          
       }
       
       ////////////////////////////////////////////////////////////////// 
       
       // loop on s1.p2 left part
       for(int i=0; i<s1.getP2_size(); i++)
       {  
          if(s1.getP2_tasks()[i].getHight() <= crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s1.getP2_tasks()[i].getId());
            tempTask.setExecTime(s1.getP2_tasks()[i].getExecTime());
            tempTask.setHight(s1.getP2_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s1.getP2_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s1.getP2_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s1.getP2_tasks()[i].getDependences());
            tempTask.setSuccessors(s1.getP2_tasks()[i].getSuccessors());

            ch1.getP2_tasks()[ch1.getP2_size()] = tempTask;
            ch1.setP2_size(ch1.getP2_size()+1);
          }
       }
       
       // loop on s2.p2 left part
       for(int i=0; i<s2.getP2_size(); i++)
       {
          if(s2.getP2_tasks()[i].getHight() <= crossOverPoint)
          { 
            Task tempTask = new Task(); 
            tempTask.setId(s2.getP2_tasks()[i].getId());
            tempTask.setExecTime(s2.getP2_tasks()[i].getExecTime());
            tempTask.setHight(s2.getP2_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s2.getP2_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s2.getP2_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s2.getP2_tasks()[i].getDependences());
            tempTask.setSuccessors(s2.getP2_tasks()[i].getSuccessors());

            ch2.getP2_tasks()[ch2.getP2_size()] = tempTask;
            ch2.setP2_size(ch2.getP2_size()+1);
          }
       }
      
      
       // loop on s2.p2 right part
       for(int i=0; i<s2.getP2_size(); i++)
       {
          if(s2.getP2_tasks()[i].getHight() > crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s2.getP2_tasks()[i].getId());
            tempTask.setExecTime(s2.getP2_tasks()[i].getExecTime());
            tempTask.setHight(s2.getP2_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s2.getP2_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s2.getP2_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s2.getP2_tasks()[i].getDependences());
            tempTask.setSuccessors(s2.getP2_tasks()[i].getSuccessors());  
              
            ch1.getP2_tasks()[ch1.getP2_size()] = tempTask;
            ch1.setP2_size(ch1.getP2_size()+1);
          }
       }
       
       // loop on s1.p2 right part
       for(int i=0; i<s1.getP2_size(); i++)
       {
          if(s1.getP2_tasks()[i].getHight() > crossOverPoint)
          {
            Task tempTask = new Task(); 
            tempTask.setId(s1.getP2_tasks()[i].getId());
            tempTask.setExecTime(s1.getP2_tasks()[i].getExecTime());
            tempTask.setHight(s1.getP2_tasks()[i].getHight());
            tempTask.setPredecessorsNum(s1.getP2_tasks()[i].getPredecessorsNum());
            tempTask.setSuccessorsNum(s1.getP2_tasks()[i].getSuccessorsNum());
            tempTask.setDependences(s1.getP2_tasks()[i].getDependences());
            tempTask.setSuccessors(s1.getP2_tasks()[i].getSuccessors());
            
            ch2.getP2_tasks()[ch2.getP2_size()] = tempTask;
            ch2.setP2_size(ch2.getP2_size()+1);
          }
          
       }
       
       ch1.calc_start_finish_time();
       ch2.calc_start_finish_time();
       
       ch1.calc_fitness_function();
       ch2.calc_fitness_function();
       
       
       next_pop.getSchedules()[next_pop.getNumOfCurrentSchedules()] = ch1;
       next_pop.setNumOfCurrentSchedules(next_pop.getNumOfCurrentSchedules()+1);
    //   System.out.println("---------> next pop Num of current shc + 1   = " + (next_pop.getNumOfCurrentSchedules()));
       
       
       next_pop.getSchedules()[next_pop.getNumOfCurrentSchedules()] = ch2;
       next_pop.setNumOfCurrentSchedules(next_pop.getNumOfCurrentSchedules()+1);
     //  System.out.println("--------> next pop Num of current shc + 1   = " + (next_pop.getNumOfCurrentSchedules()));
    }
    
    public Schedule getBestSchedule()
    {
      int index_of_best_schedule = 0;
      int minFT=99999;
      
      for(int i=0; i<this.numOfSchedules; i++)
      {
        if(minFT > this.schedules[i].getFinishTime())
        {
          minFT = this.schedules[i].getFinishTime();
          index_of_best_schedule = i;
        }
      }
      
      return this.schedules[index_of_best_schedule];
    }
    
    public Schedule [] getFiveBestSchedules()
    {
        Schedule [] bestFive = new Schedule[5];
        
        // we already sort the schedules 
        // sortSchedules();   so no need to call this function
        
        for(int i=this.schedules.length-1, j=0; i>=(this.schedules.length-5); i--, j++)
        {
          bestFive[j] = new Schedule();
          bestFive[j] = this.schedules[i];
        }
        
        return bestFive;
    }
    
    public void mutation()
    {
        float min, max, result; // variables to deal with the random number
        
        // Mutation Ratio
        float mutationRatio = 0.001f;
        
        for(int i=0; i<this.numOfSchedules; i++)
        {
          // ----------------------------- generate random number
          min = 0.0f;
          max = 0.99f;
          result = (float)Math.random() * (max - min) + min; 
          // ----------------------------------------------------
          
          if(result <= mutationRatio)
          {
            doMutation(this.schedules[i]);
          }
        }
        
    }
    
    public void doMutation(Schedule s)                     
    {
      for(int i=0; i<(s.getP1_size()-1); i++)
      {
        if(s.getP1_tasks()[i].getHight() == s.getP1_tasks()[i+1].getHight())
        {
          swap(s.getP1_tasks()[i], s.getP1_tasks()[i+1]);
          s.calc_start_finish_time();
          s.calc_fitness_function();
          return;
        }
      }
      
      for(int i=0; i<(s.getP2_size()-1); i++)
      {
        if(s.getP2_tasks()[i].getHight() == s.getP2_tasks()[i+1].getHight())
        {
          swap(s.getP2_tasks()[i], s.getP2_tasks()[i+1]);
          s.calc_start_finish_time();
          s.calc_fitness_function();
          return;
        }
      }
      
    }
    
    public void swap(Task t1, Task t2)
    {
      Task tempTask = new Task();
      
      tempTask.setId(t1.getId());
      tempTask.setStartTime(t1.getStartTime());
      tempTask.setFinishTime(t1.getFinishTime());
      tempTask.setExecTime(t1.getExecTime());
      tempTask.setHight(t1.getHight());
      tempTask.setPredecessorsNum(t1.getPredecessorsNum());
      tempTask.setSuccessorsNum(t1.getSuccessorsNum());
      tempTask.setDependences(t1.getDependences());
      tempTask.setSuccessors(t1.getSuccessors());
      
      t1.setId(t2.getId());
      t1.setStartTime(t2.getStartTime());
      t1.setFinishTime(t2.getFinishTime());
      t1.setExecTime(t2.getExecTime());
      t1.setHight(t2.getHight());
      t1.setPredecessorsNum(t2.getPredecessorsNum());
      t1.setSuccessorsNum(t2.getSuccessorsNum());
      t1.setDependences(t2.getDependences());
      t1.setSuccessors(t2.getSuccessors());
      
      t2.setId(tempTask.getId());
      t2.setStartTime(tempTask.getStartTime());
      t2.setFinishTime(tempTask.getFinishTime());
      t2.setExecTime(tempTask.getExecTime());
      t2.setHight(tempTask.getHight());
      t2.setPredecessorsNum(tempTask.getPredecessorsNum());
      t2.setSuccessorsNum(tempTask.getSuccessorsNum());
      t2.setDependences(tempTask.getDependences());
      t2.setSuccessors(tempTask.getSuccessors());
      
    }
    
}
