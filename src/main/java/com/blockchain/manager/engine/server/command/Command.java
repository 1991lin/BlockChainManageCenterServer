package com.blockchain.manager.engine.server.command;


/**
 * @author eric
 */
public enum Command {
    /**
     * restart client
     */
    RESTART("restart"),


    /**
     * stop the client
     */
    STOP("stop");


    private String command;

    Command(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                '}';
    }
}
