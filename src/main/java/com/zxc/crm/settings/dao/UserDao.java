package com.zxc.crm.settings.dao;

import com.zxc.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {

     User login(Map<String, String> map);
}
