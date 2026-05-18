package org.example;

public class RoundRobin {

    public static void run(QueueArray<Process> queue, int quantum, int totalProcesses) {
        System.out.println("\n========================================");
        System.out.println("   Round Robin Scheduling (Quantum = " + quantum + ")");
        System.out.println("========================================\n");

        int currentTime = 0;
        int completed = 0;

        while (completed < totalProcesses) {
            Process p = queue.dequeue();

            if (p == null) break;

            // Context switch IN — load process
            p.setState("Running");
            int timeUsed = Math.min(quantum, p.getRemainingTime());

            System.out.printf("Time %3d | P%d is RUNNING | Remaining: %d -> %d%n",
                    currentTime, p.getPid(), p.getRemainingTime(), p.getRemainingTime() - timeUsed);

            currentTime += timeUsed;
            p.setRemainingTime(p.getRemainingTime() - timeUsed);

            if (p.getRemainingTime() == 0) {
                // Process finished
                p.setState("Terminated");
                p.setTurnaroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
                completed++;
                System.out.printf("Time %3d | P%d FINISHED  | Turnaround: %d | Waiting: %d%n",
                        currentTime, p.getPid(), p.getTurnaroundTime(), p.getWaitingTime());
            } else {
                // Context switch OUT — save process, put back in queue
                p.setState("Ready");
                queue.enqueue(p);
                System.out.printf("Time %3d | P%d PREEMPTED | Remaining: %d%n",
                        currentTime, p.getPid(), p.getRemainingTime());
            }
        }

        System.out.println("\n========================================");
        System.out.println("        Scheduling Complete");
        System.out.println("Total time: " + currentTime);
        System.out.println("========================================");
    }
}
