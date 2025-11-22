package com.Test;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounterTask implements Runnable {
    private AtomicInteger count;

    public AtomicCounterTask(AtomicInteger count) {
        this.count = count;
    }

    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            count.incrementAndGet(); // atomic increment
        }
        for(int i = 0; i < 500; i++) {
            count.decrementAndGet(); // atomic decrement
        }
    }
}

public class SharedCounterAtomic {
	  public static void main(String[] args) throws InterruptedException {
	        AtomicInteger count = new AtomicInteger(0);

	        Thread t1 = new Thread(new AtomicCounterTask(count));
	        Thread t2 = new Thread(new AtomicCounterTask(count));

	        t1.start();
	        t2.start();

	        t1.join();
	        t2.join();

	        System.out.println("Final Count: " + count.get());
	    }
}
