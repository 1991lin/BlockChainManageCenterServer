package com.blockchain.manager.status.service;

import com.blockchain.manager.status.pojo.ServerStatus;

import java.util.List;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/
public interface ServerStatusService {

    /**
     * get client server status by server name
     * @param serverName
     * @return String
     */
    String getClientServerStatus(String serverName);

    /**
     * get all client server status
     * @return
     */
    List<ServerStatus> getAllClientServerStatus();

}
