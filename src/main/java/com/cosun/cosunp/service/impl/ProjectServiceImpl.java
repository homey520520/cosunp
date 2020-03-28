package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.China;
import com.cosun.cosunp.entity.ProjectHead;
import com.cosun.cosunp.entity.ProjectHeadOrder;
import com.cosun.cosunp.entity.ProjectHeadOrderItem;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.mapper.ProjectMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IProjectServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/3/23 0023 下午 5:33
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements IProjectServ {

    private static Logger logger = LogManager.getLogger(ProjectServiceImpl.class);

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<ProjectHead> totalProjectNumByEmpNo(String empNo) throws Exception {
        return projectMapper.totalProjectNumByEmpNo(empNo);
    }

    @Override
    public List<ProjectHeadOrder> getTotalProjectOrderByName(String userid, String projectName) throws Exception {
        return projectMapper.getTotalProjectOrderByName(userid,projectName);
    }

    @Override
    public List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String userid, String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderITEMByOrderS(userid,orderNo);
    }

    @Override
    public ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String userid,String productName,String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderITEMMoreByOrderS(userid,productName,orderNo);
    }

    @Override
    public int findNameRepeatOrNot(String userid, String projectName) throws Exception {
        return projectMapper.findNameRepeatOrNot(userid,projectName);
    }

    @Override
    public void saveProjectByNameAndRemark(String userid, String projectName,String remark) throws Exception {
         projectMapper.saveProjectByNameAndRemark(userid,projectName,remark);
    }

    @Override
    public List<China> getAllMainProvince() throws Exception {
        return projectMapper.getAllMainProvince();
    }

    @Override
    public ProjectHeadOrder checkOrderNoRepeat(String userId,String orderNo) throws Exception {
        return projectMapper.checkOrderNoRepeat(userId,orderNo);
    }

    @Override
    public void saveProjectHeadByBean(Integer provinceId,String userId,String orderNo,String customerName,String projectName,Integer id) throws Exception {
        projectMapper.saveProjectHeadByBean(provinceId,userId,orderNo,customerName,projectName,id);
    }

    @Override
    public ProjectHead findPHbyName(String projectName,String userId) throws Exception {
        return projectMapper.findPHbyName(projectName,userId);
    }

    @Override
    public ProjectHeadOrderItem checkProductRepeatForOneOrder(String orderNo,String productName) throws Exception{
        return projectMapper.checkProductRepeatForOneOrder(orderNo,productName);
    }

    @Override
    public ProjectHeadOrder findPHObyOrderNo(String orderNo) throws Exception {
        return projectMapper.findPHObyOrderNo(orderNo);
    }

    @Override
    public void saveProjectHeadItemByBean(Integer id,String productName,String totalBao,String date) throws Exception {
        projectMapper.saveProjectHeadItemByBean(id,productName,totalBao,date);
    }

    @Override
    public void saveOrderItemMor(Integer id,ProjectHeadOrderItem phoi) throws Exception {
        projectMapper.saveOrderItemMor(phoi);
    }

    @Override
    public ProjectHeadOrder getProjectOrderByOrderNo(String orderNo) throws Exception {
        return projectMapper.getProjectOrderByOrderNo(orderNo);
    }

    @Override
    public void updateOrderNoRepeat(ProjectHeadOrder pho) throws Exception {
         projectMapper.updateOrderNoRepeat(pho);
    }
}
