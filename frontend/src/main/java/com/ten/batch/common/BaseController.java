package com.ten.batch.common;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：BaseController
 * 描述：公共控制器类
 *
 * @version 1.0
 * @author: Zhang BoRU
 * @datetime: 2022-04-25 22:37
 */

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Claims claims;
    protected String userId;

    /**
     * 在进入控制器之前执行的内容
     */
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        Object obj = request.getAttribute("user_claims");

        if (obj != null) {
            this.claims = (Claims) obj;
            this.userId = claims.getId();
        }
    }
}
