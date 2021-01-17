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

    public SpiTask runAsync(Runnable runnable) {
        return schedule(runnable, 0);
    }

    public SpiTask schedule(Runnable runnable, long pause) {
        return schedule(runnable, pause, TimeUnit.MILLISECONDS);
    }

    public SpiTask schedule(Runnable runnable, long pause, TimeUnit timeUnit) {
        return schedule(runnable, 0L, pause, timeUnit);
    }

    public SpiTask schedule(Runnable runnable, long delay, long pause, TimeUnit timeUnit) {
        int taskId = taskCounter.getAndIncrement();
        SpiTask spiTask = new SpiTask(this, taskId, runnable, delay, pause, timeUnit);
        synchronized (lock) {
            tasks.put(taskId, spiTask);
        }
        executorService.execute(spiTask);
        return spiTask;
    }

    public void cancel(@Nullable SpiTask spiTask) {
        if (spiTask == null) return;
        spiTask.cancel();
    }

    public void cancel0(SpiTask spiTask) {
        synchronized (lock) {
            tasks.remove(spiTask.getTaskID());
        }
    }

    public void cancel(int id) {
        cancel(tasks.get(id));
    }
}


