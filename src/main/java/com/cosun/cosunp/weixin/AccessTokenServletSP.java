package com.cosun.cosunp.weixin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author:homey Wong
 * @Date: 2020/02/26  上午 11:26
 * @Description:
 * @Modified By:
 * @Modified-date:
 **/
@WebServlet(name = "AccessTokenServletSP")
public class AccessTokenServletSP extends HttpServlet {


    public void init() throws ServletException {
        TokenThread.appId = WeiXinConfig.appid;
        TokenThread.appSecretsp = WeiXinConfig.secretsp;
        new Thread(new TokenThread()).start();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
