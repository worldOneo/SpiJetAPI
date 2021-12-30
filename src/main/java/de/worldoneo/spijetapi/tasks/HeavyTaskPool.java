package de.worldoneo.spijetapi.tasks;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeavyTaskPool {
  public static final int FIXED_POOL_SIZE = 1;
  public static final HeavyTaskPool commonPool = new HeavyTaskPool();
  @Getter
  private final ExecutorService threadPool = Executors.newFixedThreadPool(FIXED_POOL_SIZE);

  private HeavyTaskPool() {
  }

  public static ExecutorService commonThreadPool() {
    return commonPool.threadPool;
  }

  public static HeavyTaskPool commonPool() {
    return commonPool;
  }
}
