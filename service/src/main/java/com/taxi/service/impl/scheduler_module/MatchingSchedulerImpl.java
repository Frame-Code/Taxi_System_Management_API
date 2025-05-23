package com.taxi.service.impl.scheduler_module;

import com.taxi.service.interfaces.scheduler_module.IMatchingScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class MatchingSchedulerImpl implements IMatchingScheduler {
    private ScheduledExecutorService executor;

    @Override
    public ScheduledFuture<?> schedulePeriod(Runnable task, int delay, int period) {
        executor = Executors.newScheduledThreadPool(1);
        return executor.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS);
    }

    @Override
    public ScheduledFuture<?> scheduleUnique(Runnable task, int delay) {
        executor = Executors.newScheduledThreadPool(1);
        return executor.schedule(task, delay, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void shutdownNow() {
        executor.shutdownNow();
    }

    @Override
    public ScheduledExecutorService getExecutor() {
        return executor;
    }
}
