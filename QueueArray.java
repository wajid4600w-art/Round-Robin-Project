package org.example;

public class QueueArray<T> {
    private T[] queue;
    private int front;
    private int rear;
    private int count;
    private int maxSize;

    @SuppressWarnings("unchecked")
    public QueueArray(int size) {
        maxSize = size;
        queue = (T[]) new Object[maxSize];
        front = 0;
        rear = 0;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == maxSize;
    }

    public void enqueue(T value) {
        if (isFull()) {
            // Resize the queue by doubling capacity
            T[] newQueue = (T[]) new Object[maxSize * 2];
            for (int i = 0; i < count; i++) {
                newQueue[i] = queue[(front + i) % maxSize];
            }
            queue = newQueue;
            front = 0;
            rear = count;
            maxSize *= 2;
        }
        queue[rear] = value;
        rear = (rear + 1) % maxSize;
        count++;
    }

    public T dequeue() {
        if (!isEmpty()) {
            T value = queue[front];
            front = (front + 1) % maxSize;
            count--;
            return value;
        }
        return null;
    }

    public T peek() {
        if (!isEmpty()) {
            return queue[front];
        }
        return null;
    }

    public int size() {
        return count;
    }
}
