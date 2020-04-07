package com.zhuo.imsystem.http.controller;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        String name = res.getName();

        JSONObject jwtJson = new JSONObject();
        json.put("uid", uid);
        Const constConfig = new Const();
//        System.out.println(constConfig.JWT_SECRET+" "+constConfig.JWT_TTL);
        String jwt = JWTUtil.createJWT(jwtJson.toJSONString(), constConfig.JWT_SECRET, constConfig.JWT_TTL);

        return success()
                .setData("name",name)
                .setData("uid",uid)
                .setData("token",jwt);
    }
}
