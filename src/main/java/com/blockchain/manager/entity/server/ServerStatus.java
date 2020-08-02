package com.blockchain.manager.entity.server;


import com.blockchain.manager.entity.Status;
import lombok.Data;

import java.util.Date;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Data
public class ServerStatus {

    private Status status;
    private Date lastAccessTime;

}
