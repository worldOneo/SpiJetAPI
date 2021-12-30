package de.worldoneo.spijetapi.tasks;

import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@UtilityClass
public class Tasks {

  /**
   * Run an I/O heavy task (a lazy task) which spends most of its time waiting
   *
   * @param supplier the task to run
   * @param <T>      the type which the task should return
   * @return the CompletableFuture running the task returning the type T
   */
  public static <T> CompletableFuture<T> newLazy(Supplier<T> supplier) {
    return CompletableFuture.supplyAsync(supplier, LazyTaskPool.commonThreadPool());
  }

  /**
   * Run an I/O heavy task (a lazy task) which spends most of its time waiting
   *
   * @param runnable the lazy task
   * @return a CompletableFuture running the lazy task
   */
  public static CompletableFuture<Void> newLazy(Runnable runnable) {
    return CompletableFuture.runAsync(runnable, LazyTaskPool.commonThreadPool());
  }

  /**
   * Run an CPU heavy task which spends most of its time computing.
   *
   * @param supplier the task to run
   * @param <T>      the type which the task should return
   * @return the CompletableFuture running the task returning the type T
   */
  public static <T> CompletableFuture<T> newHeavy(Supplier<T> supplier) {
    return CompletableFuture.supplyAsync(supplier, LazyTaskPool.commonThreadPool());
  }

  /**
   * Run an CPU heavy task which spends most of its time computing.
   *
   * @param runnable the task to run
   * @return the CompletableFuture running the task
   */
  public static CompletableFuture<Void> newHeavy(Runnable runnable) {
    return CompletableFuture.runAsync(runnable, HeavyTaskPool.commonThreadPool());
  }
}
