package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Extension;
import com.cosun.cosunp.entity.Rules;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IrulesServ;
import com.cosun.cosunp.tool.ReadTextUtil;
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
 * @date:2019/6/4 0004 下午 3:28
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/setup")
public class SetUpController {

    private static Logger logger = LogManager.getLogger(SetUpController.class);


    @Autowired
    IrulesServ rulesServ;

    @ResponseBody
    @RequestMapping("/toSetUp")
    public ModelAndView toSetUp(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("setup");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Rules rules = new Rules();
            rules.setDeptId(1);
            List<Rules> rulesList = rulesServ.findAllRulesByDeptId(rules);
            view.addObject("userInfo", userInfo);
            view.addObject("rulesList", rulesList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/saveSetUp", method = RequestMethod.POST)
    public void saveSetUp(Rules rules, HttpServletResponse response) throws Exception {
        try {
            rulesServ.saveFirstShowById(rules);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(1);
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
    @RequestMapping(value = "/saveExtension", method = RequestMethod.POST)
    public void saveExtension(HttpServletResponse response) throws Exception {
        try {
            List<Extension> exs = new ReadTextUtil().readTxtUtil("C:\\Users\\Administrator\\Desktop\\aaaa.txt");
            rulesServ.saveExtension(exs);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

}
