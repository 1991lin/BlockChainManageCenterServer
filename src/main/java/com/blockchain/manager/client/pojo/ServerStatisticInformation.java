package com.blockchain.manager.client.pojo;

import lombok.Data;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@Data
public class ServerStatisticInformation {

    private UserCompany userCompanyInformation;
    private SdkPhysicalInformation sdkPhysicalInformation;
    private ClientNetwork clientNetwork;

}
