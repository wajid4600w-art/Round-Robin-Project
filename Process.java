package org.example;

public class Process {
    private static int procCounter = 0;

    private int pid;
    private String state;
    private int burstTime;
    private int remainingTime;
    private int arrivalTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(int burstTime, int arrivalTime) {
        this.pid = procCounter++;
        this.state = "New";
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    public static void resetCounter() {
        procCounter = 0;
    }

    // Getters and Setters
    public int getPid()                      { return pid; }
    public String getState()                 { return state; }
    public void setState(String state)       { this.state = state; }
    public int getBurstTime()                { return burstTime; }
    public int getRemainingTime()            { return remainingTime; }
    public void setRemainingTime(int t)      { this.remainingTime = t; }
    public int getArrivalTime()              { return arrivalTime; }
    public int getWaitingTime()              { return waitingTime; }
    public void setWaitingTime(int t)        { this.waitingTime = t; }
    public int getTurnaroundTime()           { return turnaroundTime; }
    public void setTurnaroundTime(int t)     { this.turnaroundTime = t; }

    @Override
    public String toString() {
        return String.format("P%d [Burst: %d, Arrival: %d, State: %s]",
                pid, burstTime, arrivalTime, state);
    }
}
