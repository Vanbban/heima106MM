package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author: Vanbban
 * @create 2020-12-16 16:36
 */
@Controller
public class UserConstroller {
    private UserService userService = new UserService();

    @RequestMapping("/user/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User paramentUser = JsonUtils.parseJSON2Object(request, User.class);
            User loginUser = userService.findUser(paramentUser);
            request.getSession().setAttribute(Constants.MM_USER_LOGIN,loginUser);
            JsonUtils.printResult(response, new Result(true, "登录成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, e.getMessage()));
        }
    }

    @RequestMapping("/user/logout")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            request.getSession().invalidate();

            JsonUtils.printResult(response, new Result(true, "退出成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "退出失败"));
        }
    }
}
