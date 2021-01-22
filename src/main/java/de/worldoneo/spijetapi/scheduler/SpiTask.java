package de.worldoneo.spijetapi.scheduler;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class SpiTask implements Runnable {
    private final int taskID;
    private final Runnable runnable;
    private final long delay;
    private final long pause;
    private final SpiScheduler spiScheduler;
    private Throwable lastException;

    @Getter(AccessLevel.NONE)
    private final AtomicBoolean running = new AtomicBoolean(true);

    public SpiTask(SpiScheduler spiScheduler, int taskID, Runnable runnable, long delay, long pause, TimeUnit timeUnit) {
        this.taskID = taskID;
        this.runnable = runnable;
        this.delay = timeUnit.toMillis(delay);
        this.pause = timeUnit.toMillis(pause);
        this.spiScheduler = spiScheduler;
    }

    @Override
    public void run() {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                lastException = e;
                Thread.currentThread().interrupt();
            }
        }

        while (running.get()) {
            try {
                runnable.run();
            } catch (Throwable t) {
                lastException = t;
            }
            if (pause <= 0) break;
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                lastException = e;
                Thread.currentThread().interrupt();
            }
        }
        this.cancel();
    }

    /**
     * Cancels this task and removes it from the scheduler.
     */
    public void cancel() {
        boolean wasRunning = running.getAndSet(false);
        if (wasRunning) {
            spiScheduler.cancel0(this);
        }
    }
}
