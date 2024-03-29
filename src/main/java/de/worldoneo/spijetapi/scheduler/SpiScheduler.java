package de.worldoneo.spijetapi.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiScheduler {
    private static final SpiScheduler instance = new SpiScheduler();
    private final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder()
            .setNameFormat("SpiThread #%1$d")
            .build());
    private final AtomicInteger taskCounter = new AtomicInteger();
    private final Map<Integer, SpiTask> tasks = Collections.synchronizedMap(new HashMap<>());
    private final Object lock = new Object();

    private SpiScheduler() {
    }

    public static SpiScheduler getInstance() {
        return instance;
    }

    /**
     * Runs a task asynchronous.
     * This is recommended to use as it uses a CachedThreadPool to reduce the overhead of creating a thread.
     *
     * @param runnable The runnable to run async
     * @return the task which is running
     */
    public SpiTask runAsync(Runnable runnable) {
        return schedule(runnable, 0);
    }

    /**
     * Runs a task in intervals.
     * The delay of the task is 0 (it starts with no pause) and pauses then.
     *
     * @param runnable The task to
     * @param pause    The pause between runs (in ms). A pause of 0 means only run once
     * @return the task which is running
     */
    public SpiTask schedule(Runnable runnable, long pause) {
        return schedule(runnable, pause, TimeUnit.MILLISECONDS);
    }

    /**
     * Runs a task in intervals.
     *
     * @param runnable The task to run
     * @param pause    The pause between runs. A pause of 0 means only run once
     * @param timeUnit the timeunit of the pause
     * @return the task which is running
     */
    public SpiTask schedule(Runnable runnable, long pause, TimeUnit timeUnit) {
        return schedule(runnable, 0L, pause, timeUnit);
    }

    /**
     * Runs a task in intervals with a delay first
     *
     * @param runnable The task to run
     * @param delay    The delay to wait before the run
     * @param pause    The pause between runs. A pause of 0 means only run once
     * @param timeUnit The timeunit of the pause and the delay
     * @return the task which is running
     */
    public SpiTask schedule(Runnable runnable, long delay, long pause, TimeUnit timeUnit) {
        int taskId = taskCounter.getAndIncrement();
        SpiTask spiTask = new SpiTask(this, taskId, runnable, delay, pause, timeUnit);
        synchronized (lock) {
            tasks.put(taskId, spiTask);
        }
        executorService.execute(spiTask);
        return spiTask;
    }

    /**
     * Cancels a task
     *
     * @param spiTask the task to cancel
     */
    public void cancel(@Nullable SpiTask spiTask) {
        if (spiTask == null) return;
        spiTask.cancel();
    }

   void cancel0(SpiTask spiTask) {
        synchronized (lock) {
            tasks.remove(spiTask.getTaskID());
        }
    }

    /**
     * Cancels a task based on it id
     *
     * @param id the id of the task to cancel
     */
    public void cancel(int id) {
        cancel(tasks.get(id));
    }
}


