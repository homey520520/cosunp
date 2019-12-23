package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IUserInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements IUserInfoServ {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public UserInfo findUserByUserNameandPassword(String userName, String userPwd) throws Exception {
        return userInfoMapper.findUserByUserNameandPassword(userName, userPwd);
    }

    public void setNewPasswordByuId(Integer uId, String newPassword) throws Exception {
        userInfoMapper.setNewPasswordByuId(uId, newPassword);
    }

    public String getMobileNumByUserName(String userName) throws Exception {
        return userInfoMapper.getMobileNumByUserName(userName);
    }

    public int getUserInfoCountByEmpNo(String empNo) throws Exception {
        return userInfoMapper.getUserInfoCountByEmpNo(empNo);
    }

    public Employee getUserInfoByEmpNo(String empNo) throws Exception {
        return userInfoMapper.getUserInfoByEmpNo(empNo);
    }

    public int getUserInfoCountByUserName(String userName) throws Exception {
        return userInfoMapper.getUserInfoCountByUserName(userName);
    }

    public void saveUserInfoByBean(UserInfo userInfo) throws Exception {
        userInfoMapper.saveUserInfoByBean(userInfo);

    }

    public List<UserInfo> findAllUser(UserInfo userInfo) throws Exception {
        return userInfoMapper.findAllUserA(userInfo);
    }

    public int findAllUserCount() throws Exception {
        return userInfoMapper.findAllUserCount();
    }

    public void deleteUserInfotByUId(Integer uId) throws Exception {
        userInfoMapper.deleteUserInfotByUId(uId);
    }

    public void deleteUserByBatch(List<Integer> ids) throws Exception {
        userInfoMapper.deleteUserByBatch(ids);
    }

    public List<UserInfo> queryUserByCondition(UserInfo userInfo) throws Exception {
        return userInfoMapper.queryUserByCondition(userInfo);
    }

    public int queryUserByConditionCount(UserInfo userInfo) throws Exception {
        return userInfoMapper.queryUserByConditionCount(userInfo);

    }

    public UserInfo getUserInfoByUId(Integer uId) throws Exception {
        return userInfoMapper.getUserInfoByUId(uId);
    }

    public void updateUserInfo(UserInfo userInfo) throws Exception {
        userInfoMapper.updateUserInfo(userInfo);

    }

    public void updateUserInfoLoginTime(Integer uId,String datetime) throws Exception {
        userInfoMapper.updateUserInfoLoginTime(uId,datetime);
    }




}
