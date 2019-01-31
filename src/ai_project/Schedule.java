
package ai_project;


public class Schedule {
    
    private int id;
    private int finishTime;
    
    private Task [] p1_tasks;
    private Task [] p2_tasks;
    
    private int p1_size;
    private int p2_size;
    
    private float fitnessRatio;
    private float lowerBound;
    private float upperBound;
    
    // boolean var to determine wether this schedule has been selected to do cross over operation or not
    private boolean selected;
    
    // Constructors

    public Schedule() {
        
        this.p1_size = 0;
        this.p2_size = 0;
        
        this.finishTime = 0;
        
        p1_tasks = new Task[Main.allTasks.length];
        p2_tasks = new Task[Main.allTasks.length];
        
        this.upperBound = 0.0f;
        this.lowerBound = 0.0f;
        
        this.selected = false;
    }

    public Schedule(int id, int finishTime, Task[] p1_tasks, Task[] p2_tasks, int p1_size, int p2_size, float fitnessRatio, float lowerBound, float upperBound) {
        this.id = id;
        this.finishTime = finishTime;
        this.p1_tasks = p1_tasks;
        this.p2_tasks = p2_tasks;
        this.p1_size = p1_size;
        this.p2_size = p2_size;
        this.fitnessRatio = fitnessRatio;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }


    
    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setP1_tasks(Task[] p1_tasks) {
        this.p1_tasks = p1_tasks;
    }

    public void setP2_tasks(Task[] p2_tasks) {
        this.p2_tasks = p2_tasks;
    }

    public void setFitnessRatio(float fitnessRatio) {
        this.fitnessRatio = fitnessRatio;
    }

    public void setLowerBound(float lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(float upperBound) {
        this.upperBound = upperBound;
    }

    public void setP1_size(int p1_size) {
        this.p1_size = p1_size;
    }

    public void setP2_size(int p2_size) {
        this.p2_size = p2_size;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
    
    
    // Getters

    public int getId() {
        return id;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public Task[] getP1_tasks() {
        return p1_tasks;
    }

    public Task[] getP2_tasks() {
        return p2_tasks;
    }

    public float getFitnessRatio() {
        return fitnessRatio;
    }

    public float getLowerBound() {
        return lowerBound;
    }

    public float getUpperBound() {
        return upperBound;
    }

    public int getP1_size() {
        return p1_size;
    }

    public int getP2_size() {
        return p2_size;
    }

    public boolean isSelected() {
        return selected;
    }
    
    
    
    // Others
    
    public void calc_fitness_function()
    {
      this.calc_final_finishTime();
        
      int max = 0;
      for(int i=0; i<this.getP1_size(); i++)
      {
        if(max < this.p1_tasks[i].getFinishTime())
            max = this.p1_tasks[i].getFinishTime();
      }
      
      for(int i=0; i<this.getP2_size(); i++)
      {
        if(max < this.p2_tasks[i].getFinishTime())
            max = this.p2_tasks[i].getFinishTime();
      }
      
      this.finishTime = max;
    }
    
    public void calc_final_finishTime() 
    {
        int tempDep[];
        int tempMax = 0;
        int max1 = 0;
        int extra1 = 0;
        int max2 = 0;
        int extra2 = 0;
        int high = Main.allTasks[Main.allTasks.length-1].getHight();
        
        for (int i = 1; i <= high; i++) 
        {
            for (int j = 0; j < this.p1_size; j++) 
            {
                if (this.p1_tasks[j].getHight() == i) 
                {
                    for (int k = 0; k < this.p1_tasks[j].getPredecessorsNum(); k++) 
                    {
                        for (int z = 0; z < this.p2_size; z++) 
                        {
                           if ( (this.p1_tasks[j].getDependences()[k]) == (this.p2_tasks[z].getId()) ) 
                           {
                                if (max1 <= this.p2_tasks[z].getFinishTime()) {
                                    max1 = this.p2_tasks[z].getFinishTime();
                                }
                                 
                            }

                        }
                    }

                    if (max1 >= this.p1_tasks[j].getStartTime()) {
                        extra1 = max1 - this.p1_tasks[j].getStartTime();
                        for (int n = j; n < this.p1_size; n++) {
                            this.p1_tasks[n].setStartTime(this.p1_tasks[n].getStartTime() + extra1);
                            this.p1_tasks[n].setFinishTime(this.p1_tasks[n].getFinishTime() + extra1);
                        }
                    }
                }
                max1 = 0;
                extra1 = 0;
            }

            // now i check the task in p2
            for (int w = 0; w < this.p2_size; w++) {
                if (this.p2_tasks[w].getHight() == i) {
                    for (int k = 0; k < this.p2_tasks[w].getPredecessorsNum(); k++) {
                        for (int z = 0; z < this.p1_size; z++) {
                            if (this.p2_tasks[w].getDependences()[k] == this.p1_tasks[z].getId()) {
                                if (max2 <= this.p1_tasks[z].getFinishTime()) {
                                    max2 = this.p1_tasks[z].getFinishTime();
                                }
                            }

                        }
                    }

                    if (max2 >= this.p2_tasks[w].getStartTime()) {
                        extra2 = max2 - this.p2_tasks[w].getStartTime();
                        for (int n = w; n < this.p2_size; n++) {
                            this.p2_tasks[n].setStartTime(this.p2_tasks[n].getStartTime() + extra2);
                            this.p2_tasks[n].setFinishTime(this.p2_tasks[n].getFinishTime() + extra2);
                        }
                    }
                }

                max2 = 0;
                extra2 = 0;
            }

        }

    }
    
    public void calc_start_finish_time()
    {
      // fill Processor 1 (start time & finish time) for each task 
      
      if(this.getP1_size() == 1)
      {
        this.getP1_tasks()[0].setStartTime(0);
        this.getP1_tasks()[0].setFinishTime(this.getP1_tasks()[0].getStartTime() + this.getP1_tasks()[0].getExecTime());
      }
      
      if(this.getP1_size() > 1)
      {
       
        this.getP1_tasks()[0].setStartTime(0);
        this.getP1_tasks()[0].setFinishTime(this.getP1_tasks()[0].getStartTime() + this.getP1_tasks()[0].getExecTime());  
       
        for(int i=1; i<this.getP1_size(); i++)
        {
          this.getP1_tasks()[i].setStartTime(this.getP1_tasks()[i-1].getFinishTime());
          this.getP1_tasks()[i].setFinishTime(this.getP1_tasks()[i].getStartTime() + this.getP1_tasks()[i].getExecTime());
        }
      }
      
      // fill Processor 2 (start time & finish time) for each task 
      
      if(this.getP2_size() == 1)
      {
        this.getP2_tasks()[0].setStartTime(0);
        this.getP2_tasks()[0].setFinishTime(this.getP2_tasks()[0].getStartTime() + this.getP2_tasks()[0].getExecTime());
      }
      
      if(this.getP2_size() > 1)
      {
        
        this.getP2_tasks()[0].setStartTime(0);
        this.getP2_tasks()[0].setFinishTime(this.getP2_tasks()[0].getStartTime() + this.getP2_tasks()[0].getExecTime());  
          
        for(int i=1; i<this.getP2_size(); i++)
        {
          this.getP2_tasks()[i].setStartTime(this.getP2_tasks()[i-1].getFinishTime());
          this.getP2_tasks()[i].setFinishTime(this.getP2_tasks()[i].getStartTime() + this.getP2_tasks()[i].getExecTime());
        }
      }
      
    }
    
    public void print()
    {
       System.out.println(ConsoleColors.RED + "***********************************************************************************************************************************************************************************************");
       System.out.print(ConsoleColors.BLACK + "1st Processor : " + ConsoleColors.RESET);
       for(int i=0; i<this.p1_size; i++)
       {
         System.out.print("T" + this.getP1_tasks()[i].getId());
         if(i != this.p1_size-1)
         System.out.print(", ");
       }
       System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
       System.out.print(ConsoleColors.BLACK + "2nd Processor : " + ConsoleColors.RESET);
       for(int i=0; i<this.p2_size; i++)
       {
         System.out.print("T" + this.getP2_tasks()[i].getId());
         if(i != this.p2_size-1)
         System.out.print(", ");
       }
       System.out.println("\n" + ConsoleColors.RED + "***********************************************************************************************************************************************************************************************");
       System.out.println(ConsoleColors.BLUE + "Schedule Finish Time = " +  this.getFinishTime());
    }
       
}









