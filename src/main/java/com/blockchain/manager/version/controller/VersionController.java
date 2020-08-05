package com.blockchain.manager.version.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Eric
 * @create: 2020-08-02 22:15
 **/


@RestController(value = "/version")
public class VersionController {


    @RequestMapping(value = "/get/latest", method = RequestMethod.POST)
    public String getLatestVersion() {
        return "1.0";
    }


    @RequestMapping(value = "/isSupported/{clientVersion}", method = RequestMethod.POST)
    public boolean isStillSupported(@PathVariable String clientVersion) {
        return false;
    }

}
