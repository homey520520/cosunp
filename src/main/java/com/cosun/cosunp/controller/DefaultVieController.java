package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author:homey Wong
 * @date:2018/12/28 0028 下午 4:12
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
public class DefaultVieController {

    private static Logger logger = LogManager.getLogger(DefaultVieController.class);


    @ResponseBody
    @RequestMapping("/")
    public ModelAndView toIndexPage() throws Exception {
        ModelAndView mav = new ModelAndView("index");
        try {
            DownloadView downloadView = new DownloadView();
            downloadView.setFlag("true");
            mav.addObject("view", downloadView);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return mav;
    }


}
