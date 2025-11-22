package com.Test;


class Counter {
	    private int count = 0;

	    // synchronized method ensures only one thread can execute at a time
	    public synchronized void increment() {
	        count++;
	    }

	    public synchronized void decrement() {
	        count--;
	    }

	    public int getCount() {
	        return count;
	    }
	}

	class CounterTask implements Runnable {
	    private Counter counter;

	    public CounterTask(Counter counter) {
	        this.counter = counter;
	    }

	    @Override
	    public void run() {
	        for(int i = 0; i < 1000; i++) {
	            counter.increment();
	        }
	        for(int i = 0; i < 500; i++) {
	            counter.decrement();
	        }
	    }
	}

	public class SharedCounterSynchronized {
	    public static void main(String[] args) throws InterruptedException {
	        Counter counter = new Counter();

	        Thread t1 = new Thread(new CounterTask(counter));
	        Thread t2 = new Thread(new CounterTask(counter));

	        t1.start();
	        t2.start();

	        t1.join();
	        t2.join();

	        System.out.println("Final Count: " + counter.getCount());
	    }
	}



