package com.zxc.crm.settings.service.impl;

import com.zxc.crm.settings.dao.UserDao;
import com.zxc.crm.settings.service.UserService;
import com.zxc.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
