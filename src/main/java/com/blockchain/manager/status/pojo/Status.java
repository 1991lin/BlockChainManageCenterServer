package com.blockchain.manager.status.pojo;

/**
 * @author eric
 */
public enum Status {

    /**
     * ready
     */
    READY("Ready"),

    /**
     * processing
     */
    PROCESSING("Processing"),

    /**
     * connected
     */
    CONNECTED("Connected"),

    /**
     * failed
     */
    FAILED("Failed"),

    /**
     * off-line
     */
    OFFLINE("Off-line");



    private final String status;


    Status(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                '}';
    }
}
