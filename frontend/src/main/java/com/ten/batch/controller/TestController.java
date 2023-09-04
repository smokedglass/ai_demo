package com.ten.batch.controller;

import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.entity.User;
import com.ten.batch.service.UserService;
import com.ten.batch.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 名称：TestController
 * 功能描述：测试
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 11:50
 */
@CrossOrigin(origins = "*")
@RequestMapping("/sys")
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/test")
    public Result test() {
        User user = userService.findByMobile("123456");
        return new Result(ResultCode.SUCCESS, user);
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");

        User user = userService.findByMobile(mobile);
        //登录失败
        if (user == null || !user.getPassword().equals(password)) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        } else {
            //登录成功
            //api权限字符串
            StringBuilder sb = new StringBuilder();
            //获取到所有的可访问API权限
            /*for (Role role : user.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    if (perm.getType() == PermissionConstants.PERMISSION_API) {
                        sb.append(perm.getCode()).append(",");
                    }
                }
            }*/
            Map<String, Object> map = new HashMap<>();
            //可访问的api权限字符串
            map.put("apis", sb.toString());
            map.put("userName", user.getUsername());
            //map.put("companyId", user.getCompanyId());
            //map.put("companyName", user.getCompanyName());
            //map.put("departmentId", user.getDepartmentId());
            //map.put("departmentName", user.getDepartmentName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
    }
}
