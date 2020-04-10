package com.zhuo.imsystem.http.interceptor;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.config.StatusCode;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import io.jsonwebtoken.Claims;
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
        try {
            Claims claims = JWTUtil.parseJWT(token, Const.JWT_SECRET);
            long expirTime = claims.getExpiration().getTime();
            long nowMillis = System.currentTimeMillis();
            if(expirTime<=nowMillis){// 校验签名是否过期
                String resJson = new ResponseJson().error("token 已经过期", StatusCode.ERROR_JSON_WEB_TOKEN_EXPIRE).toString();
                response.setHeader("Content-Type","application/json; charset=utf-8");
                response.getWriter().write(resJson);
                return false;
            }
        }catch (Exception e) {
            // 解析jwt失败
            String resJson = new ResponseJson().error("token 不合法",StatusCode.ERROR_JSON_WEB_TOKEN_INVALID).toString();
            response.setHeader("Content-Type","application/json; charset=utf-8");
            response.getWriter().write(resJson);
            return false;
        }
        return true;
    }
}
