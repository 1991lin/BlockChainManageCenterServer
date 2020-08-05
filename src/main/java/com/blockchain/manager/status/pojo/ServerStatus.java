package com.blockchain.manager.status.pojo;


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
