package com.blockchain.manager.engine.bootstrap.protocol;

public enum ConnectionEstablishHandShakeEnum {


    /**
     * client send the sync
     */
    SEND_SYNC("send_ack"),

    /**
     * server response the ack
     */
    SEND_ACK("send_ack"),

    /**
     * the client return established
     */
    ESTABLISH("established");

    private String status;

    ConnectionEstablishHandShakeEnum(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ConnectionEstablishHandShakeEnum{" +
                "status='" + status + '\'' +
                '}';
    }
}
