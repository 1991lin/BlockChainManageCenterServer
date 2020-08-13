package com.blockchain.manager.engine.constant;


/**
 * @author eric
 */
public interface Limitation {

    int READ_TIME = 5;
    int WRITE_TIME = 5;
    int ALL_IDLE_TIME = 0;


    int MAX_LOST_CONNECTION_LIMITATION = 2;

}
