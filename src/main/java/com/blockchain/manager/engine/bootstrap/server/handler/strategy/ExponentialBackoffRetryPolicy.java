package com.blockchain.manager.engine.bootstrap.server.handler.strategy;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author: Eric
 * @create: 2020-08-06 21:15
 **/

@Data
@Slf4j
public class ExponentialBackoffRetryPolicy implements RetryPolicy {


    private static final int MAX_RETRIES = 5;
    private static final long DEFAULT_MAX_SLEEP_TIME = Integer.MAX_VALUE;
    private static final long BASE_SLEEP_TIME = 5;

    private int maxRetries;
    private long maxSleepMs;


    private Random random = new Random();

    @Override
    public boolean allowRetry(int retryCount) {

        if (retryCount > MAX_RETRIES) {
            return false;
        }
        return true;

    }

    @Override
    public long getSleepTime(int retryCount) {
        if (retryCount < 0) {
            throw new IllegalArgumentException("The count of the retry should not be less than 0");
        }

        if (retryCount > MAX_RETRIES) {
            throw new IllegalStateException("The retry count should not be more than " + MAX_RETRIES);
        }

        long sleepTime = BASE_SLEEP_TIME + Math.max(1, random.nextInt(1 << retryCount));

        if (sleepTime > DEFAULT_MAX_SLEEP_TIME) {
            sleepTime = DEFAULT_MAX_SLEEP_TIME;
        }

        return sleepTime;
    }
}
