package org.example;

import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;

    private Semaphore[] forks;

    public DiningPhilosophers() {
        forks = new Semaphore[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    private void think(int philosopherId) throws InterruptedException {
        System.out.println("Философ " + philosopherId + " думает...");
        Thread.sleep((long)(Math.random() * 1000));
    }

    private void eat(int philosopherId) throws InterruptedException {
        System.out.println("Философ " + philosopherId + " ест...");
        Thread.sleep((long)(Math.random() * 1000));
    }

    private class Philosopher extends Thread {
        private int id;
        private int mealsEaten = 0;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while(mealsEaten < 3) {

                    think(id);

                    forks[id].acquire();
                    forks[(id + 1) % NUM_PHILOSOPHERS].acquire();

                    eat(id);
                    mealsEaten++;

                    forks[id].release();
                    forks[(id + 1) % NUM_PHILOSOPHERS].release();
                }

                System.out.println("Философ " + id + " закончил обед.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            diningPhilosophers.new Philosopher(i).start();
        }
    }
}