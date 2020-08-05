package com.blockchain.manager.status.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/
@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @RequestMapping(value = "/all/servers", method = {RequestMethod.GET, RequestMethod.POST})
    public String getServerStatus(){
        return "Server : ".concat(" TestServer, Status : ");
    }

}
