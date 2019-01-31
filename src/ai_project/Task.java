
package ai_project;

public class Task {
    
    private int id;
    private int startTime;
    private int finishTime;
    private int execTime;
    
    private int hight;
    private int predecessorsNum;
    private int successorsNum;
    
    private int [] dependences;
    private int [] successors;
    
    
    // constructors
    
    public Task() {
        
    }

    public Task(int id, int execTime, int hight, int predecessorNum, int successorsNum, int[] dependences, int[] successors) {
        this.id = id;
        this.execTime = execTime;
        this.hight = hight;
        this.predecessorsNum = predecessorNum;
        this.successorsNum = successorsNum;
        this.dependences = dependences;
        this.successors = successors;
    }
    
    
    // Setters
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public void setPredecessorsNum(int predecessorsNum) {
        this.predecessorsNum = predecessorsNum;
    }

    public void setSuccessorsNum(int successorsNum) {
        this.successorsNum = successorsNum;
    }

    public void setDependences(int[] dependences) {
        this.dependences = dependences;
    }

    public void setSuccessors(int[] successors) {
        this.successors = successors;
    }

    public void setDependences2(int index, int value) {
        this.dependences[index] = value;
    }

    
    
    

    // Getters
    
    public int getId() {    
        return id;
    }
   
    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getExecTime() {
        return execTime;
    }

    public int getHight() {
        return hight;
    }

    public int getPredecessorsNum() {
        return predecessorsNum;
    }

    public int getSuccessorsNum() {
        return successorsNum;
    }
    
    public int[] getDependences() {
        return dependences;
    }
    
    public int getDependences(int index){
        return dependences[index];
    }
    
    public int[] getSuccessors() {
        return successors;
    }

    public int getSuccessors(int index){
        return successors[index];
    }
    
}
