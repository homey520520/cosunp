package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IOrderServ;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/9/6 0006 下午 3:58
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/pmc")
public class PMCController {

    private static Logger logger = LogManager.getLogger(PMCController.class);

    @Autowired
    IOrderServ orderServ;

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;

    @ResponseBody
    @RequestMapping(value = "/toPMCPage")
    public ModelAndView toPMCPage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("pmc");
        OrderHead orderHead = new OrderHead();
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHeadForPMC(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/toUpdateOrderHeadItemByheadId")
    public ModelAndView toUpdateOrderHeadItemByheadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
        ModelAndView mav = new ModelAndView("updateorderpmc");
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        mav.addObject("extensionList", extensionList);
        return mav;
    }
}
