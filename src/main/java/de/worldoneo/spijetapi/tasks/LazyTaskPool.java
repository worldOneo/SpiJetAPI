package de.worldoneo.spijetapi.tasks;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Thread pool for lazy tasks which do mainly I/O
 */
public class LazyTaskPool {
  public static final int MAX_POOL_SIZE = 64;
  private static final LazyTaskPool commonPool = new LazyTaskPool();
  @Getter
  private final ExecutorService threadPool = Executors.newCachedThreadPool();

  private LazyTaskPool() {
    ((ThreadPoolExecutor) threadPool).setMaximumPoolSize(MAX_POOL_SIZE);
  }

  public static ExecutorService commonThreadPool() {
    return commonPool.threadPool;
  }

  public static LazyTaskPool commonPool() {
    return commonPool;
  }
}
