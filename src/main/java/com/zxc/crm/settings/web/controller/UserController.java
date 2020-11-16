package com.zxc.crm.settings.web.controller;

import com.zxc.crm.settings.domain.User;
import com.zxc.crm.settings.service.UserService;
import com.zxc.crm.settings.service.impl.UserServiceImpl;
import com.zxc.crm.utils.MD5Util;
import com.zxc.crm.utils.PrintJson;
import com.zxc.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入用户控制器");
        String path = request.getServletPath();
        System.out.println(path);
        if ("/settings/user/login.do".equals(path)){
                    login(request,response);
            System.out.println("执行这里的代码");
        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }
    public void login(HttpServletRequest request,HttpServletResponse response){
        System.out.println("进入到验证登录");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码的明文形式转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip---"+ip);

        //业务层开发统一使用代理类形态的接口对象
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user =  userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //执行到这没有抛出异常，表示登录成功
            /*
            {"success":true}
              String str = "{\"success\":true}";
              response.getWriter().print(str);
             */
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            //执行到catch，说明登录失败
             /*
            {"success":false,"msg":"账号或密码错了"}
             */
             String msg = e.getMessage();
             /*
             现在作为controller，需要为ajax提供多项信息
             （1）将多项信息打包成map，或者将map解析为json串
             （2）创建一个VO
              private boolean success;
              private String msg;
              如果经常用那么就创建一个VO
              */
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
