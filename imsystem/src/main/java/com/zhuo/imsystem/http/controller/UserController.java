package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.AvatarService;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.FirstLetterUtil;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    AvatarService avatarService;

    // 通过uid查询信息
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public ResponseJson getUser(@PathVariable String uid){
        User user = new User();
        User res = userService.queryUser(uid);
        user.setUid(res.getUid());
        user.setUserName(res.getUserName());
        user.setAvatarUrl(res.getAvatarUrl());
        return success().setData(user);
    }

    // 获取当前用户的信息
    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public ResponseJson aboutMe(@RequestHeader("X-Token") String token){
        Claims claims = JWTUtil.parseJWT(token, ConstVar.JWT_SECRET);
        String uid = JSONObject.parseObject(claims.getSubject()).getString("uid");
        User user = new User();
        User res = userService.queryUser(uid);
        user.setUid(res.getUid());
        user.setUserName(res.getUserName());
        user.setAvatarUrl(res.getAvatarUrl());
        return success().setData(user);
    }

    // 通过用户名查找用户信息
    @RequestMapping(value = "/get-user-info-by-username",method = RequestMethod.GET)
    public ResponseJson getUserInfoByName(@RequestParam("username") String username){
        User res = userService.queryUserByUserName(username);
        if(res==null)
            return success();
        HashMap hashMap = new HashMap();
        hashMap.put("username",res.getUserName());
        hashMap.put("uid",res.getUid());
        hashMap.put("avatarUrl",res.getAvatarUrl());
        hashMap.put("firstLetterOfName", FirstLetterUtil.getFirstLetter(res.getUserName()));
        hashMap.put("nickname",res.getNickName());
        return success().addArrayData(hashMap);
    }
    // 注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseJson register(@RequestBody JSONObject json) throws Exception{
        User user = json.toJavaObject(User.class);
        Boolean res = userService.register(user);
        if(res)
            return success();
        else
            return error();
    }

    // 上传用户头像 (仅上传 未修改用户信息)
    @RequestMapping(value = "/avatar/upload",method = RequestMethod.POST)
    public ResponseJson uploadAvatar(HttpServletRequest request, MultipartFile file)throws Exception{
        String uid = (String) request.getAttribute("uid");
        String filename = avatarService.generateAvatarName();
        String path = ConstVar.AVATAR_BASE_PATH+ "/"+uid+"/";
        byte[] data = file.getBytes();
        String imageType = "png";
        if("image/jpeg".equalsIgnoreCase(file.getContentType())) {
            imageType = "jpg";
        }
        boolean validRes = avatarService.valid(uid); // 校验环境
        String saveRes = avatarService.save(data,filename,path,imageType,80,80); // 上传头像
        if(validRes&&saveRes!=null){
            return success().setData(saveRes);
        }else{
            return error("头像上传失败", StatusCode.ERROR_USER_AVATAR_UPLOAD_FAILED);
        }
    }

    // 修改为用户信息
    @RequestMapping(value = "/info/save",method = RequestMethod.POST)
    public ResponseJson saveInfo(HttpServletRequest request,@RequestBody JSONObject json){
        String uid = (String) request.getAttribute("uid");
        String avatarUrl = json.getString("avatarUrl");
        boolean res = userService.updateUserInfo(uid,avatarUrl);
        if(res){
            return success();
        }else {
            return error("修改用户信息失败",StatusCode.ERROR_CHANGE_USER_INFO_FAILED);
        }
    }

    // 获取头像
    @RequestMapping(value = "/avatar/get/{uid}/{filename:.+\\..+}",method = RequestMethod.GET,produces = {MediaType.IMAGE_JPEG_VALUE,  MediaType.IMAGE_PNG_VALUE })
    @ResponseBody
    public byte[] getImage(@PathVariable String uid,@PathVariable String filename) {
        try {
            System.out.println(uid+" "+filename);
            return avatarService.download(uid,filename);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
