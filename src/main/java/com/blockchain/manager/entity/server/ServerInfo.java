package com.blockchain.manager.entity.server;

import lombok.Data;

import java.util.Objects;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Data
public class ServerInfo {
    /**
     * get from client sdk
     */
    private String company;
    private String serverName;

    /**
     * get from network protocol
     */
    private String macAddress;
    private String ip;
}
