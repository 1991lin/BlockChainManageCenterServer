package com.blockchain.manager.status.service.impl;

import com.blockchain.manager.status.pojo.ServerStatus;
import com.blockchain.manager.status.service.ServerStatusService;

import java.util.List;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

public class ServerStatusServiceImpl implements ServerStatusService {
    @Override
    public String getClientServerStatus(String serverName) {
        return "";
    }

    @Override
    public List<ServerStatus> getAllClientServerStatus() {
        return null;
    }
}
