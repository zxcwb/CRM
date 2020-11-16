package com.zxc.crm.settings.service;


import com.zxc.crm.exception.LoginException;
import com.zxc.crm.settings.domain.User;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
