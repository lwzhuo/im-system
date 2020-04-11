package com.zhuo.imsystem.websocket.util;

import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.websocket.protocal.RegisterProtocal;

public class Signature {
    public static boolean checkRegisterSig(RegisterProtocal msg){
        String token = msg.getToken();
        String res = JWTUtil.checkJWT(token);
        if(res==null)
            return false;
        else
            return true;
    }
}
