package com.taxi.service.interfaces.scheduler_module;

import java.util.concurrent.ScheduledFuture;

public interface IMatchingScheduler {
    ScheduledFuture<?> schedulePeriod(Runnable task, int delay, int period);
    ScheduledFuture<?> scheduleUnique(Runnable task, int delay);
    void shutdown();
    void shutdownNow();
}
