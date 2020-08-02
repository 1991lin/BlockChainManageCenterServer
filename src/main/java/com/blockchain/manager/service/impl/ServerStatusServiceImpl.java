package com.blockchain.manager.service.impl;

import com.blockchain.manager.service.ServerStatusService;
import com.blockchain.manager.entity.server.ServerStatus;

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
