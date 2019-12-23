package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Position;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/12 0012 上午 10:15
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/power")
public class PowerController {

    private static Logger logger = LogManager.getLogger(PowerController.class);

    @Autowired
    IPersonServ personServ;

    @Autowired
    private IUserInfoServ userInfoServ;

    @ResponseBody
    @RequestMapping("/touserpower")
    public ModelAndView touserpower(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("userpower");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<UserInfo> userInfoList = userInfoServ.findAllUser(userInfo);
            int recordCount = userInfoServ.findAllUserCount();
            int maxPage = recordCount % userInfo.getPageSize() == 0 ? recordCount / userInfo.getPageSize() : recordCount / userInfo.getPageSize() + 1;
            userInfo.setMaxPage(maxPage);
            userInfo.setRecordCount(recordCount);
            view.addObject("userInfoList", userInfoList);
            view.addObject("empList", empList);
            view.addObject("userInfo", userInfo);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
    @ResponseBody
    @RequestMapping(value = "/updateUserPower", method = RequestMethod.GET)
    public ModelAndView updateUserPower(UserInfo userInfo,HttpSession session) throws Exception {
        try {
            Integer cu = userInfo.getCurrentPage();
            ModelAndView view = new ModelAndView("userpower");
            userInfoServ.updateUserInfo(userInfo);
            List<Employee> empList = personServ.findAllEmployees();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<UserInfo> userInfoList = userInfoServ.queryUserByCondition(userInfo);
            int recordCount = userInfoServ.queryUserByConditionCount(userInfo);
            int maxPage = recordCount % userInfo.getPageSize() == 0 ? recordCount / userInfo.getPageSize() : recordCount / userInfo.getPageSize() + 1;
            userInfo = (UserInfo) session.getAttribute("account");
            userInfo.setMaxPage(maxPage);
            userInfo.setRecordCount(recordCount);
            userInfo.setCurrentPage(cu);
            view.addObject("userInfoList", userInfoList);
            view.addObject("empList", empList);
            view.addObject("userInfo", userInfo);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 3);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/toupdateUserInfoById", method = RequestMethod.GET)
    public ModelAndView toupdateUserInfoById(UserInfo userInfo) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updateuserinfopage");
            userInfo =userInfoServ.getUserInfoByUId(userInfo.getuId());
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/queryUserByCondition", method = RequestMethod.POST)
    public void queryUserByCondition(UserInfo userInfo, HttpServletResponse response,HttpSession session) throws Exception {
        try {
            Integer cuPage = userInfo.getCurrentPage();
            List<UserInfo> userInfoList = userInfoServ.queryUserByCondition(userInfo);
            int recordCount = userInfoServ.queryUserByConditionCount(userInfo);
            int maxPage = recordCount % userInfo.getPageSize() == 0 ? recordCount / userInfo.getPageSize() : recordCount / userInfo.getPageSize() + 1;
            userInfo = (UserInfo) session.getAttribute("account");
            if(userInfoList.size() > 0) {
                userInfoList.get(0).setMaxPage(maxPage);
                userInfoList.get(0).setRecordCount(recordCount);
                userInfoList.get(0).setCurrentPage(cuPage);
                userInfoList.get(0).setUserActor(userInfo.getUserActor());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(userInfoList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleteUserByBatch", method = RequestMethod.GET)
    public ModelAndView deleteUserByBatch(UserInfo userInfo,HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("userpower");
            userInfoServ.deleteUserByBatch(userInfo.getIds());
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<UserInfo> userInfoList = userInfoServ.findAllUser(userInfo);
            int recordCount = userInfoServ.findAllUserCount();
            int maxPage = recordCount % userInfo.getPageSize() == 0 ? recordCount / userInfo.getPageSize() : recordCount / userInfo.getPageSize() + 1;
            userInfo = (UserInfo) session.getAttribute("account");
            userInfo.setMaxPage(maxPage);
            userInfo.setRecordCount(recordCount);
            view.addObject("userInfoList", userInfoList);
            view.addObject("empList", empList);
            view.addObject("userInfo", userInfo);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleteUserInfoById", method = RequestMethod.GET)
    public ModelAndView deleteUserInfoById(Integer id,HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("userpower");
            userInfoServ.deleteUserInfotByUId(id);
            UserInfo userInfo = new UserInfo();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<UserInfo> userInfoList = userInfoServ.findAllUser(userInfo);
            int recordCount = userInfoServ.findAllUserCount();
            int maxPage = recordCount % userInfo.getPageSize() == 0 ? recordCount / userInfo.getPageSize() : recordCount / userInfo.getPageSize() + 1;
            userInfo = (UserInfo) session.getAttribute("account");
            userInfo.setMaxPage(maxPage);
            userInfo.setRecordCount(recordCount);
            view.addObject("userInfoList", userInfoList);
            view.addObject("empList", empList);
            view.addObject("userInfo", userInfo);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


}
