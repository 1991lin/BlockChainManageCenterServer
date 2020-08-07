package com.blockchain.manager.engine.bootstrap.server.handler.strategy;


/**
 * @author eric
 */
public interface RetryPolicy {


    /**
     * allow the retry
     *
     * @param retryCount
     * @return
     */
    boolean allowRetry(int retryCount);


    /**
     * get the sleep time of current retry count
     *
     * @param retryCount
     * @return
     */
    long getSleepTime(int retryCount);


}
