package com.blockchain.manager.server.pojo;

import com.blockchain.manager.status.pojo.ServerStatus;
import lombok.Data;

/**
 * @author eric
 */
@Data
public class Server {

    private ServerInfo serverInformation;
    private ServerStatus serverStatus;

}
