package de.worldoneo.spijetapi.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiScheduler {
    private final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder()
            .setNameFormat("SpiThread #%1$d")
            .build());
    private final AtomicInteger taskCounter = new AtomicInteger();
    private final TIntObjectMap<SpiTask> tasks = TCollections.synchronizedMap(new TIntObjectHashMap<>());
    private final Object lock = new Object();


    private static final SpiScheduler instance = new SpiScheduler();

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
     * @param pause    The pause between runs (in ms)
     * @return the task which is running
     */
    public SpiTask schedule(Runnable runnable, long pause) {
        return schedule(runnable, pause, TimeUnit.MILLISECONDS);
    }

    /**
     * Runs a task in intervals.
     *
     * @param runnable The task to run
     * @param pause    The pause between runs
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
     * @param pause    The pause between runs
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

    /**
     * <b>This function is not functional on its own!</b>
     * do not use it if you aren't sure if you now what you are doing.
     *
     * @param spiTask the task to remove from the scheduler
     */
    public void cancel0(SpiTask spiTask) {
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


