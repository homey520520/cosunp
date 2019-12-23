package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.service.IrulesServ;
import com.cosun.cosunp.tool.PinYinUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * @author:homey Wong
     * @date:2018.12.19
     */


    @Autowired
    private IUserInfoServ userInfoServ;

    @Autowired
    IPersonServ personServ;

    @Autowired
    private IrulesServ rulesServ;

    private static final String INDEX = "index";
    private static Logger logger = LogManager.getLogger(AccountController.class);

    @ResponseBody
    @RequestMapping(value = "/tologin")
    public ModelAndView toLoginPage(HttpServletRequest request) throws Exception {
        try {
            HttpSession session = request.getSession();
            int interval = session.getMaxInactiveInterval();
            ModelAndView mav = new ModelAndView(INDEX);
            DownloadView downloadView = new DownloadView();
            downloadView.setFlag("true");
            mav.addObject("view", downloadView);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/toResetPassword")
    public ModelAndView toResetPassword(HttpSession session) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("reset-password");
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            if (userInfo == null) {
                mav = new ModelAndView(INDEX);
                DownloadView downloadView = new DownloadView();
                downloadView.setFlag("true");
                mav.addObject("view", downloadView);
                return mav;
            }
            mav.addObject("userInfo", userInfo);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toforgetpasswordpage")
    public ModelAndView toForgetPassword(HttpSession session) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("forget-password");
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            mav.addObject("userInfo", userInfo);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toregister")
    public ModelAndView toregister() throws Exception {
        try {
            ModelAndView mav = new ModelAndView("register");
            List<Employee> employeeList = personServ.findAllEmployees();
            mav.addObject("employeeList", employeeList);
            mav.addObject("userInfo", new UserInfo());
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/checkUserNameExsit", method = RequestMethod.POST)
    public void checkUserNameExsit(@RequestBody String username, HttpServletResponse response) throws Exception {
        try {
            int isExsitCount = userInfoServ.getUserInfoCountByUserName(username);
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(isExsitCount);
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
    @RequestMapping(value = "/checkUserExsitAndReturnPinyinName", method = RequestMethod.POST)
    public void checkUserExsitAndReturnPinyinName(@RequestBody String empNo, HttpServletResponse response) throws
            Exception {
        try {
            String returnMessage;
            Employee employee;
            int isExsit = userInfoServ.getUserInfoCountByEmpNo(empNo);
            if (isExsit > 0) {
                returnMessage = "1";
            } else {
                employee = userInfoServ.getUserInfoByEmpNo(empNo);
                returnMessage = PinYinUtil.toPinyin(employee.getName()).concat(",").concat(employee.getDeptName());
            }

            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(returnMessage);
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
    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute(value = "view") DownloadView view, HttpSession session) throws
            Exception {
        ModelAndView mav;
        try {
            UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(), view.getPassword());
            if (userInfo != null && userInfo.getUserName() != null && userInfo.getState()==1) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userInfoServ.updateUserInfoLoginTime(userInfo.getuId(),df.format(new Date()).toString());
                session.setAttribute("account", userInfo);
                view.setFullName(userInfo.getFullName());
                session.setAttribute("view", view);
                mav = new ModelAndView("mainindex");
                session.setAttribute("username", userInfo.getUserName());
                session.setAttribute("password", userInfo.getUserPwd());
                mav.addObject("view", view);
                Rules rules = rulesServ.getRulesByFirst();
                mav.addObject("userInfo",userInfo);
                mav.addObject("showflaga", rules == null ? 0 : 1);
                return mav;
            }else if(userInfo !=null && userInfo.getState()!=1) {
                mav = new ModelAndView(INDEX);
                view.setUserName(userInfo.getUserName());
                view.setFlag(userInfo.getState().toString());
                mav.addObject("view", view);
                mav.addObject("userInfo",userInfo);
                return mav;
            }
            mav = new ModelAndView(INDEX);
            view.setUserName(null);
            view.setPassword(null);
            view.setFlag("false");
            mav.addObject("userInfo",userInfo);
            mav.addObject("view", view);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return mav;

    }


    @ResponseBody
    @RequestMapping(value = "/register")
    public ModelAndView register(@ModelAttribute(value = "userInfo") UserInfo userInfo, HttpSession session) throws
            Exception {
        ModelAndView view = new ModelAndView("index");
        Employee employee = userInfoServ.getUserInfoByEmpNo(userInfo.getEmpNo());
        userInfo.setFullName(employee.getName());
        userInfo.setState(0);
        userInfo.setUseruploadright(1);
        userInfo.setUserActor(4);
        userInfoServ.saveUserInfoByBean(userInfo);
        DownloadView vi = new DownloadView();
        vi.setFlag("3");
        vi.setUserName(userInfo.getUserName());
        view.addObject("view",vi);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/loginnew")
    public ModelAndView loginnew(@ModelAttribute(value = "view") DownloadView view, HttpSession session) throws
            Exception {
        ModelAndView mav;
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        try {
            view.setFullName(userInfo.getFullName());
            mav = new ModelAndView("mainindex");
            mav.addObject("view", view);
            List<Rules> menuList = rulesServ.findAllRulesAll();
            mav.addObject("menuList", menuList);
            Rules rules = rulesServ.getRulesByName("总经办");
            String htmlContent = "";
            if (rules != null) {
                int index = rules.getFileDir().lastIndexOf(".");
                String htmlName = rules.getFileDir().substring(0, index) + ".html";
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(htmlName), "UTF-8"));
                String line;

                while ((line = br.readLine()) != null) {
                    htmlContent += line + "\n";
                }
            }
            mav.addObject("htmlStr", htmlContent);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView(INDEX);
        try {
            session.removeAttribute("account");
            DownloadView downloadView = new DownloadView();
            downloadView.setFlag("true");
            view.addObject("view", downloadView);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @GetMapping("/toMainPage")
    public ModelAndView toMainPage(HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("mainindex");
            List<Rules> menuList = rulesServ.findAllRulesAll();
            DownloadView v = new DownloadView();
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            v.setFullName(userInfo.getFullName());
            Rules rules = rulesServ.getRulesByFirst();
            view.addObject("showflaga", rules == null ? 0 : 1);
            if (userInfo == null) {
                view = new ModelAndView(INDEX);
            }
            view.addObject("view", v);
            view.addObject("menuList", menuList);
            view.addObject("userInfo",userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @GetMapping("/saveNewPassword")
    public ModelAndView saveNewPassword(String newPassword, HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView(INDEX);
            DownloadView v = new DownloadView();
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            if (userInfo != null) {
                session.removeAttribute("account");
                v.setFullName(userInfo.getFullName());
                userInfoServ.setNewPasswordByuId(userInfo.getuId(), newPassword);
                userInfo.setUserPwd(newPassword);
                session.setAttribute("account", userInfo);
                view.addObject("view", v);
            }
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @RequestMapping(value = "/getMobileNumByUserName", method = RequestMethod.POST)
    @ResponseBody
    public void getMobileNumByUserName(String userName, HttpServletResponse response) throws Exception {
        String str = null;
        try {
            String mobileNum = userInfoServ.getMobileNumByUserName(userName);
            ObjectMapper x = new ObjectMapper();

            str = x.writeValueAsString(mobileNum);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

}
