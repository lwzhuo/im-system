package com.zhuo.imsystem.websocket.util;

import com.zhuo.imsystem.websocket.protocal.RegisterProtocal;
import org.apache.commons.codec.digest.DigestUtils;

public class Signature {
    public static boolean checkRegisterSig(RegisterProtocal msg){
        String token = msg.getToken();
        int ts = msg.getTs();
        String str_ts = String.valueOf(ts);
        String validToken = "register";
        if(token.equalsIgnoreCase(validToken))
            return true;
        else
            return false;
    }
}
