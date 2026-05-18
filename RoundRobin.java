package org.example;

public class RoundRobin {

    // -------------------------------------------------------
    // PREEMPTIVE Round Robin
    // Each process gets at most 'quantum' units, then rotates.
    // -------------------------------------------------------
    public static void runPreemptive(QueueArray<Process> queue, int quantum, int totalProcesses) {
        System.out.println("\n========================================");
        System.out.println(" Preemptive Round Robin (Quantum = " + quantum + ")");
        System.out.println("========================================\n");

        int currentTime = 0;
        int completed = 0;

        while (completed < totalProcesses) {
            Process p = queue.dequeue();
            if (p == null) break;

            // Context switch IN
            p.setState("Running");
            int timeUsed = Math.min(quantum, p.getRemainingTime());

            System.out.printf("Time %3d | P%d RUNNING   | Remaining: %d -> %d%n",
                    currentTime, p.getPid(), p.getRemainingTime(), p.getRemainingTime() - timeUsed);

            currentTime += timeUsed;
            p.setRemainingTime(p.getRemainingTime() - timeUsed);

            if (p.getRemainingTime() == 0) {
                // Process done
                p.setState("Terminated");
                p.setTurnaroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
                completed++;
                System.out.printf("Time %3d | P%d FINISHED  | Turnaround: %d | Waiting: %d%n",
                        currentTime, p.getPid(), p.getTurnaroundTime(), p.getWaitingTime());
            } else {
                // Context switch OUT — put back at end of queue
                p.setState("Ready");
                queue.enqueue(p);
                System.out.printf("Time %3d | P%d PREEMPTED | Remaining: %d%n",
                        currentTime, p.getPid(), p.getRemainingTime());
            }
        }

        printSummary(currentTime);
    }

    // -------------------------------------------------------
    // NON-PREEMPTIVE Round Robin
    // Each process runs to completion before the next starts.
    // The quantum is ignored — each process gets full burst time.
    // -------------------------------------------------------
    public static void runNonPreemptive(QueueArray<Process> queue, int totalProcesses) {
        System.out.println("\n========================================");
        System.out.println(" Non-Preemptive Round Robin");
        System.out.println(" (Each process runs until completion)");
        System.out.println("========================================\n");

        int currentTime = 0;
        int completed = 0;

        while (completed < totalProcesses) {
            Process p = queue.dequeue();
            if (p == null) break;

            // Context switch IN — runs fully without interruption
            p.setState("Running");
            System.out.printf("Time %3d | P%d RUNNING   | Burst: %d (runs to completion)%n",
                    currentTime, p.getPid(), p.getRemainingTime());

            currentTime += p.getRemainingTime();
            p.setRemainingTime(0);

            // Process done
            p.setState("Terminated");
            p.setTurnaroundTime(currentTime - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
            completed++;

            System.out.printf("Time %3d | P%d FINISHED  | Turnaround: %d | Waiting: %d%n",
                    currentTime, p.getPid(), p.getTurnaroundTime(), p.getWaitingTime());
        }

        printSummary(currentTime);
    }

    // -------------------------------------------------------
    // Shared summary footer
    // -------------------------------------------------------
    private static void printSummary(int totalTime) {
        System.out.println("\n========================================");
        System.out.println("         Scheduling Complete");
        System.out.println("         Total CPU Time: " + totalTime);
        System.out.println("========================================");
    }
}
