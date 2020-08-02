package com.blockchain.manager.controller;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: BlockChainManageCenterServer
 * @author: Eric
 **/

@RestController
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    @RequestMapping(value = "/get/{type}/{target}",  method = {RequestMethod.GET,RequestMethod.POST})
    public String getConfiguration(@PathVariable String type, @PathVariable String target){
        return "Get the " + target + " of the " + type;
    }

}
