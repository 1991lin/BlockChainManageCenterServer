package com.blockchain.manager.file.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Eric
 * @create: 2020-08-02 22:28
 **/

@RestController(value = "/jar")
public class SdkJarController {


    @RequestMapping(value = "/get/latest")
    public void getLatestController() {

    }

}
