package com.ten.batch.interceptor;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.ResultCode;
import io.jsonwebtoken.Claims;
import com.ten.batch.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.util.StringUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：JwtInterceptor
 * 功能描述：jwt拦截器
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 11:15
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * 简化获取token数据的代码编写（判断是否登录）
     * 1.通过request获取请求token信息
     * 2.从token中解析获取claims
     * 3.将claims绑定到request域中
     */

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.通过request获取请求token信息
        String authorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否已Bearer开头
//        System.out.println("Authorization " + authorization);
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            //获取token数据
            String token = authorization.replace("Bearer ", "");
            //System.out.println("token " + token);

            //解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null) {
               /* //通过claims获取到当前用户的可访问API权限字符串(api-user-delete,api-user-update)
                String apis = (String) claims.get("apis");
                //通过handler（强转）
                HandlerMethod h = (HandlerMethod) handler;
                //获取接口上的requestMapping注解
                RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
                //获取当前请求接口中的name属性
                String name = annotation.name();*/
                //判断当前用户是否具有响应的请求权限
                //if (apis.contains(name)) {
                    request.setAttribute("user_claims", claims);
                    return true;
                //} else {
                //    throw new CommonException(ResultCode.UNAUTHORISE);
                //}
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }
}
