package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("     Round Robin CPU Scheduler");
        System.out.println("========================================\n");

        // --- Get number of processes ---
        int numProcesses = 0;
        while (numProcesses <= 0) {
            System.out.print("Enter number of processes: ");
            if (input.hasNextInt()) {
                numProcesses = input.nextInt();
                if (numProcesses <= 0)
                    System.out.println("  Error: Must be greater than 0. Try again.");
            } else {
                System.out.println("  Error: Please enter a valid number.");
                input.next();
            }
        }

        // --- Get time quantum ---
        int quantum = 0;
        while (quantum <= 0) {
            System.out.print("Enter time quantum: ");
            if (input.hasNextInt()) {
                quantum = input.nextInt();
                if (quantum <= 0)
                    System.out.println("  Error: Quantum must be greater than 0. Try again.");
            } else {
                System.out.println("  Error: Please enter a valid number.");
                input.next();
            }
        }

        // --- Enter process details ---
        System.out.println();
        QueueArray<Process> queue = new QueueArray<>(numProcesses);

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("--- Process " + i + " ---");

            // Burst time
            int burst = 0;
            while (burst <= 0) {
                System.out.print("  Burst time:   ");
                if (input.hasNextInt()) {
                    burst = input.nextInt();
                    if (burst <= 0)
                        System.out.println("  Error: Burst time must be greater than 0.");
                } else {
                    System.out.println("  Error: Please enter a valid number.");
                    input.next();
                }
            }

            // Arrival time
            int arrival = -1;
            while (arrival < 0) {
                System.out.print("  Arrival time: ");
                if (input.hasNextInt()) {
                    arrival = input.nextInt();
                    if (arrival < 0)
                        System.out.println("  Error: Arrival time cannot be negative.");
                } else {
                    System.out.println("  Error: Please enter a valid number.");
                    input.next();
                }
            }

            queue.enqueue(new Process(burst, arrival));
            System.out.println("  P" + i + " added.\n");
        }

        // --- Run the scheduler ---
        RoundRobin.run(queue, quantum, numProcesses);

        input.close();
    }
}
