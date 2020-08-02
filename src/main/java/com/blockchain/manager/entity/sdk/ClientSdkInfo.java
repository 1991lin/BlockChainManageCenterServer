package com.blockchain.manager.entity.sdk;

import lombok.Data;

import java.util.Date;

/**
 * @author: Eric
 * @create: 2020-08-02 22:08
 **/

@Data
public class ClientSdkInfo {

    private String sdkVersion;
    private String md5;
    private Date lastModifyTime;

}
