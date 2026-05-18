package org.example;

public class RoundRobin {

    // -------------------------------------------------------
    //============================== PREEMPTIVE Round Robin========================================
    // -------------------------------------------------------
    private static double total_waiting_time;
    private static double total_turnaround_time;
    private static int procCounter;
    public static void runPreemptive(QueueArray<Process> queue, int quantum, int totalProcesses) {
        System.out.println("\n==========================================");
        System.out.println(" Preemptive Round Robin (  Quantum = " + quantum + " ) ");
        System.out.println("========================================\n");

        int currentTime = 0;
        int completed = 0;
        double turnaround [] = new double[ totalProcesses ];
        double waiting [] = new double[totalProcesses ];

        while (completed <totalProcesses) {
            Process p = queue.dequeue();
            if (p == null) break;

            // =========================Context switch =============================
            p.setState("Running");
            int timeUsed = Math.min(quantum, p.getRemainingTime( ));


            System.out.printf("Time %3d |  P%d RUNNING    | Remaining: %d ---> %d%n",  currentTime, p.getPid(), p.getRemainingTime(), p.getRemainingTime() - timeUsed);
            currentTime += timeUsed;
            p.setRemainingTime(p.getRemainingTime() - timeUsed);

            if (p.getRemainingTime() == 0) {
                //============== Process done-======
                p.setState("Terminated");
                p.setTurnaroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
                completed++;
                turnaround[p.getPid()] = p.getTurnaroundTime();
                waiting[p.getPid()] = p.getWaitingTime();
                System.out.printf("Time %3d | P%d FINISHED  | Turnaround: %d | Waiting: %d%n",
                        currentTime, p.getPid(), p.getTurnaroundTime(), p.getWaitingTime());
            } else {
                //-============================================== Context switch OUT===================
                p.setState("Ready");
                queue.enqueue(p);
                System.out.printf("Time %3d | P%d PREEMPTED | Remaining: %d%n",
                        currentTime, p.getPid(), p.getRemainingTime());
            }
        }
        for (int i = 0; i < totalProcesses; i++) {
            total_turnaround_time += turnaround[i];
            total_waiting_time += waiting[i];
        }
        procCounter = totalProcesses;


        double average_turnaround_time = total_turnaround_time / procCounter;
        double average_waiting_time = total_waiting_time / procCounter;

        System.out.println("\n========================================");
        System.out.println("        Scheduling Complete   ");
        System.out.println("Total time :-> " + currentTime);
        System.out.println("Average turnaround time :-> " + average_turnaround_time);
        System.out.println("Average waiting time:-> " + average_waiting_time);
        System.out.println("========================================");
    }



    // ===================================================-================
    //---------------------- NON  PREEMPTIVE ROund Robin---------------=------
    // ==================================================================
    public static void runNonPreemptive( QueueArray<  Process > queue, int totalProcesses) {
        System.out.println("\n========================================");
        System.out.println(" Non Preemptive Round Robin  ");
        System.out.println(" (Each process runs until completions ) ");
        System.out.println("========================================\n");

        int currentTime = 0;
        int completed = 0;
        double turnaround[] = new double[totalProcesses ];
        double waiting[] = new double[totalProcesses ];

        while (completed < totalProcesses) {
            Process p = queue.dequeue();
            if (p == null) break;

            p.setState("Running");

            System.out.printf("Time %3d | P%d RUNNING   | Burst: %d (runs to completion)%n",
                    currentTime, p.getPid(), p.getRemainingTime());

            currentTime += p.getRemainingTime();
            p.setRemainingTime(0);

            // ======================================Process done==================
            p.setState("Terminated");
            p.setTurnaroundTime(currentTime  - p.getArrivalTime ( ));
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
            completed++;
            turnaround[p.getPid()] = p.getTurnaroundTime();
            waiting[p.getPid()] = p.getWaitingTime();

            System.out.printf("Time %3d | P%d FINISHED  | Turnaround: %d | Waiting: %d%n",   currentTime, p.getPid(), p.getTurnaroundTime(), p.getWaitingTime());
        }

        for (int i = 0; i < totalProcesses; i++) {
            total_turnaround_time += turnaround[i];
            total_waiting_time += waiting[i];
        }
        procCounter = totalProcesses;


        double average_turnaround_time = total_turnaround_time / procCounter;
        double average_waiting_time = total_waiting_time / procCounter;

        System.out.println("\n========================================");
        System.out.println("        Scheduling Complete  ");
        System.out.println("");
        System.out.println(" Total time: " + currentTime);
        System.out.println(" Average turn around time: " + average_turnaround_time);
        System.out.println(" Average waiting time: " + average_waiting_time);
        
        System.out.println("========================================");
    }
}
