package com.zhuo.imsystem.http.interceptor;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 身份认证拦截器
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        System.out.println("[http]"+request.getRequestURL()+"[获取token]"+token);
        String res = JWTUtil.checkJWT(token);
        if(res==null){
            // 解析jwt失败 或者 token过期
            String resJson = new ResponseJson().error("token 不合法",StatusCode.ERROR_JSON_WEB_TOKEN_INVALID).toString();
            response.setHeader("Content-Type","application/json; charset=utf-8");
            response.getWriter().write(resJson);
            return false;
        }else {
            String uid = JSONObject.parseObject(res).getString("uid");
            request.setAttribute("uid",uid);
            return true;
        }
    }
}
