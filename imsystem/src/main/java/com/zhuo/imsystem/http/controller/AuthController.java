package com.zhuo.imsystem.http.controller;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.model.JwtSubject;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.FirstLetterUtil;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseJson login(@RequestBody JSONObject json) throws Exception{
        User user = json.toJavaObject(User.class);
        User res = userService.login(user);
        String uid = res.getUid();
        String username = res.getUserName();
        String avatarUrl = res.getAvatarUrl();

        String jwtSubject = new JwtSubject(uid).toString();
        String jwt = JWTUtil.createJWT(jwtSubject, ConstVar.JWT_SECRET, ConstVar.JWT_TTL);

        HashMap hashMap = new HashMap();
        hashMap.put("username",username);
        hashMap.put("uid",res.getUid());
        hashMap.put("avatarUrl",avatarUrl);
        hashMap.put("firstLetterOfName", FirstLetterUtil.getFirstLetter(res.getUserName()));
        hashMap.put("nickname","");
        hashMap.put("token",jwt);
        return success().setData(hashMap);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ResponseJson logout(HttpServletRequest request){
        String uid = (String) request.getAttribute("uid");
        userService.logout(uid);
        System.out.println("用户["+uid+"]退出登录");
        return success();
    }
}
