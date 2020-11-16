package com.zxc.crm.settings.service.impl;

import com.zxc.crm.exception.LoginException;
import com.zxc.crm.settings.dao.UserDao;
import com.zxc.crm.settings.domain.User;
import com.zxc.crm.settings.service.UserService;
import com.zxc.crm.utils.DateTimeUtil;
import com.zxc.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<String,String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user =  userDao.login(map);
        if (user == null){
            throw new LoginException("账号密码错误");
        }
        //执行到这说明账号密码正确
        //继续验证其他三项

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }

        //判断失效锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

        //判断IP地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }
        return null;
    }
}
