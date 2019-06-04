package com.sharePhoto.common;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


/**
 * @author Joher
 * @data 2019/5/31
 **/
public class GeneratorPassword {

    public static String generateHash(String username, String password) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = password;//密码原值
        ByteSource salt = ByteSource.Util.bytes(username);//以账号作为盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        return result.toString();

    }
}
