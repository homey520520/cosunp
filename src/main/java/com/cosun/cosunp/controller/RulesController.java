package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.Rules;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IrulesServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/20 0020 下午 1:39
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/rules")
public class RulesController {

    private static Logger logger = LogManager.getLogger(RulesController.class);

    @Autowired
    IrulesServ rulesServ;

    @Autowired
    IPersonServ personServ;

    @ResponseBody
    @RequestMapping("/getHtmlById")
    public void getHtmlById(Rules ruless, HttpServletResponse response) throws Exception {
        try {
            Rules rules = rulesServ.getRulesById(ruless.getId());
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
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(htmlContent);
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
    @RequestMapping("/torulespageAll")
    public ModelAndView torulespageAll(int deptId, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("rulesZong");
        try {
            Rules rules = new Rules();
            rules.setDeptId(deptId);
            List<Rules> rulesList = rulesServ.findAllRulesByDeptId(rules);
            int recordCount = rulesServ.findAllRulesByDeptIdCount(rules);
            int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                    recordCount / rules.getPageSize() + 1;
            rules.setMaxPage(maxPage);
            rules.setRecordCount(recordCount);
            view.addObject("rulesList", rulesList);
            view.addObject("rules", rules);
            view.addObject("userInfo", userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }


    @ResponseBody
    @RequestMapping("/torulespage")
    public ModelAndView torulespage(Integer showflag, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("rules");
        try {
            Rules rules = new Rules();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Rules> rulesList = rulesServ.findAllRules(rules);
            int recordCount = rulesServ.findAllRulesCount();
            int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                    recordCount / rules.getPageSize() + 1;
            rules.setMaxPage(maxPage);
            rules.setRecordCount(recordCount);
            view.addObject("deptList", deptList);
            view.addObject("rules", rules);
            view.addObject("rulesList", rulesList);
            view.addObject("userInfo", userInfo);
            view.addObject("flag", showflag);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddrulespage")
    public ModelAndView toaddrulespage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("addrules");
        try {
            Rules rules = new Rules();
            rules.setLoginName(userInfo.getFullName());
            List<Dept> deptList = personServ.findAllDeptAll();
            view.addObject("deptList", deptList);
            view.addObject("rules", rules);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/toupdateRulesById")
    public ModelAndView toupdateRulesById(Integer id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("updaterules");
        try {
            List<Dept> deptList = personServ.findAllDeptAll();
            Rules rules = rulesServ.getRulesById(id);
            modelAndView.addObject("rules", rules);
            modelAndView.addObject("deptList", deptList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/downLoadByRulesId")
    public void downLoadByRulesId(HttpServletResponse resp, Integer id)
            throws Exception {
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            Rules rule = rulesServ.getRulesById(id);
            resp.setHeader("content-type", "application/octet-stream");
            resp.setContentType("application/octet-stream");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(rule.getFileName().getBytes(), "iso-8859-1"));
            byte[] buff = new byte[1024];
            outputStream = resp.getOutputStream();
            File file = new File(rule.getFileDir());
            FileInputStream fis = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fis);
            int num = bufferedInputStream.read(buff);
            while (num != -1) {
                outputStream.write(buff, 0, num);
                outputStream.flush();
                num = bufferedInputStream.read(buff);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleteRulesById", method = RequestMethod.POST)
    public ModelAndView deleteRulesById(Integer id, HttpSession session) throws Exception {
        Rules rules = new Rules();
        ModelAndView view = new ModelAndView("rules");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            rulesServ.deleteRulesById(id);
            DownloadView views = new DownloadView();
            session.setAttribute("account", userInfo);
            views.setFullName(userInfo.getFullName());
            session.setAttribute("view", views);
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            view.addObject("view", views);
            List<Rules> menuList = rulesServ.findAllRulesAll();
            List<Rules> rulesList = rulesServ.findAllRules(new Rules());
            int recordCount = rulesServ.findAllRulesCount();
            int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                    recordCount / rules.getPageSize() + 1;
            rules.setMaxPage(maxPage);
            rules.setRecordCount(recordCount);
            view.addObject("rules", rules);
            view.addObject("menuList", menuList);
            view.addObject("flag", 2);
            view.addObject("showflag", 2);
            view.addObject("userInfo", userInfo);
            view.addObject("rulesList", rulesList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/deleteRulesByBatch")
    public ModelAndView deleteRulesByBatch(Rules rules, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("rules");
        try {
            rulesServ.deleteRulesByBatch(rules.getIds());
            DownloadView views = new DownloadView();
            session.setAttribute("account", userInfo);
            views.setFullName(userInfo.getFullName());
            session.setAttribute("view", views);
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            view.addObject("view", views);
            List<Rules> menuList = rulesServ.findAllRulesAll();
            List<Rules> rulesList = rulesServ.findAllRules(new Rules());
            int recordCount = rulesServ.findAllRulesCount();
            int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                    recordCount / rules.getPageSize() + 1;
            rules.setMaxPage(maxPage);
            rules.setRecordCount(recordCount);
            view.addObject("rules", rules);
            view.addObject("menuList", menuList);
            view.addObject("flag", 2);
            view.addObject("showflag", 2);
            view.addObject("userInfo", userInfo);
            view.addObject("rulesList", rulesList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;

    }

    @ResponseBody
    @RequestMapping(value = "/showRulesById", method = RequestMethod.POST)
    public void showRulesById(Integer id, HttpServletResponse response) throws Exception {
        try {
            Rules rules = rulesServ.getRulesById(id);
            int index = rules.getFileDir().lastIndexOf(".");
            String htmlName = rules.getFileDir().substring(0, index) + ".html";
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(htmlName), "UTF-8"));
            String line;
            String htmlContent = "";
            while ((line = br.readLine()) != null) {
                htmlContent += line + "\n";
            }
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(htmlContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkRulesIsExsit", method = RequestMethod.POST)
    public void checkRulesIsExsit(@RequestBody Integer deptId, HttpServletResponse response) throws Exception {
        try {
            int isExist = rulesServ.getRulesByDeptId(deptId);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(isExist);
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
    @RequestMapping(value = "/queryRulesByCondition", method = RequestMethod.POST)
    public void queryRulesByCondition(Rules rules, HttpServletResponse response, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        try {
            List<Rules> rulesList = rulesServ.queryRulesByCondition(rules);
            int recordCount = rulesServ.queryRulesByConditionCount(rules);
            int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() : recordCount / rules.getPageSize() + 1;
            if (rulesList.size() > 0) {
                rulesList.get(0).setMaxPage(maxPage);
                rulesList.get(0).setRecordCount(recordCount);
                rulesList.get(0).setCurrentPage(rules.getCurrentPage());
                rulesList.get(0).setUserActor(userInfo.getUserActor());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(rulesList);
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
    @RequestMapping(value = "/updateRules", method = RequestMethod.POST)
    public ModelAndView updateRules(@RequestParam("file") MultipartFile file, HttpSession session, Rules rules) throws Exception {
        try {
            int index = file.getOriginalFilename().indexOf(".");
            int filenamesame = rulesServ.getRulesByNameAndId(file.getOriginalFilename().substring(0, index), rules.getDeptId());
            ModelAndView view = new ModelAndView("rules");
            if (filenamesame <= 0) {
                UserInfo userInfo = (UserInfo) session.getAttribute("account");
                rules.setUploaderId(userInfo.getuId());
                rules.setUploadDate(new Date());
                boolean isUploadFileRight = rulesServ.updateRulesById(file, rules);

                DownloadView views = new DownloadView();
                session.setAttribute("account", userInfo);
                views.setFullName(userInfo.getFullName());
                session.setAttribute("view", views);
                session.setAttribute("username", userInfo.getUserName());
                session.setAttribute("password", userInfo.getUserPwd());
                view.addObject("userInfo", userInfo);
                List<Rules> rulesList = rulesServ.findAllRules(rules);
                view.addObject("view", views);
                List<Rules> menuList = rulesServ.findAllRulesAll();
                view.addObject("menuList", menuList);
                view.addObject("rulesList", rulesList);
                if (isUploadFileRight) {
                    view.addObject("flag", 3);
                    view.addObject("showflag", 3);
                } else {
                    view.addObject("flag", 5);
                    view.addObject("showflag", 5);
                }
            } else {

                UserInfo userInfo = (UserInfo) session.getAttribute("account");
                DownloadView views = new DownloadView();
                session.setAttribute("account", userInfo);
                views.setFullName(userInfo.getFullName());
                session.setAttribute("view", views);
                session.setAttribute("username", userInfo.getUserName());
                session.setAttribute("password", userInfo.getUserPwd());
                view.addObject("userInfo", userInfo);
                List<Rules> rulesList = rulesServ.findAllRules(rules);
                view.addObject("view", views);
                List<Rules> menuList = rulesServ.findAllRulesAll();
                view.addObject("menuList", menuList);
                view.addObject("rulesList", rulesList);
                view.addObject("flag", 6);

            }
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/addRules")
    public ModelAndView addRules(@RequestParam("file") MultipartFile file, HttpSession session, Rules rules, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("rules");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            int index = file.getOriginalFilename().indexOf(".");
            int fileNameCount = rulesServ.getRulesByNameAndId(file.getOriginalFilename().substring(0, index), rules.getDeptId());
            if (fileNameCount <= 0) {
                List<Dept> deptList = personServ.findAllDeptAll();
                rules.setLoginName(userInfo.getFullName());
                rules.setUploadDate(new Date());
                rules.setUploaderId(userInfo.getuId());
                boolean isDocorDocx = rulesServ.saveRuleByRuleBean(file, rules);

                DownloadView views = new DownloadView();
                session.setAttribute("account", userInfo);
                views.setFullName(userInfo.getFullName());
                session.setAttribute("view", views);
                session.setAttribute("username", userInfo.getUserName());
                session.setAttribute("password", userInfo.getUserPwd());
                view.addObject("view", views);
                view.addObject("userInfo", userInfo);
                List<Rules> menuList = rulesServ.findAllRulesAll();
                List<Rules> rulesList = rulesServ.findAllRules(rules);
                view.addObject("menuList", menuList);
                view.addObject("rulesList", rulesList);
                if (isDocorDocx) {
                    view.addObject("flag", 1);
                    view.addObject("showflag", 1);
                } else {
                    view.addObject("flag", 5);
                    view.addObject("showflag", 5);
                }
            } else {
                DownloadView views = new DownloadView();
                session.setAttribute("account", userInfo);
                views.setFullName(userInfo.getFullName());
                session.setAttribute("view", views);
                session.setAttribute("username", userInfo.getUserName());
                session.setAttribute("password", userInfo.getUserPwd());
                view.addObject("view", views);
                view.addObject("userInfo", userInfo);
                List<Rules> menuList = rulesServ.findAllRulesAll();
                List<Rules> rulesList = rulesServ.findAllRules(rules);
                view.addObject("menuList", menuList);
                view.addObject("rulesList", rulesList);
                view.addObject("flag", 6);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;


    }


    @ResponseBody
    @RequestMapping(value = "/getFileOutStreamById")
    public void getFileOutStreamById(Integer rulesId, HttpSession session, HttpServletResponse response) throws Exception {
        Rules rules = rulesServ.getRulesById(rulesId);
        int index = rules.getFileDir().lastIndexOf(".");
        String pdfPath = rules.getFileDir().substring(0, index).concat(".pdf");
        File file = new File(pdfPath);
        if (file.exists()) {
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
            }
        } else {
            return;
        }


    }

    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(@RequestParam("rulesId") Integer rulesId, HttpServletRequest request, HttpServletResponse response) {
        FileInputStream input = null;
        try {
            Rules rules = rulesServ.getRulesById(rulesId);
            int index = rules.getFileDir().lastIndexOf(".");
            String pdfPath = rules.getFileDir().substring(0, index).concat(".pdf");
            File file = new File(pdfPath);
            if (file.exists()) {
                byte[] data = null;
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/previewFirst", method = RequestMethod.GET)
    public void previewFirst(HttpServletResponse response) {
        FileInputStream input = null;
        try {
            Rules rules = rulesServ.getRulesByFirst();
            int index = rules.getFileDir().lastIndexOf(".");
            String pdfPath = rules.getFileDir().substring(0, index).concat(".pdf");
            File file = new File(pdfPath);
            if (file.exists()) {
                byte[] data = null;
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
