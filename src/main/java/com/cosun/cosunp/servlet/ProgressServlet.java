package com.cosun.cosunp.servlet;

import com.cosun.cosunp.singleton.ProgressSingleton;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author:homey Wong
 * @date:2019/3/4 0004 上午 10:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@WebServlet("/fileupdown/progressservlet")
public class ProgressServlet extends HttpServlet {


    public ProgressServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getSession().getId();
        String filename = request.getParameter("filename");
        id = id + filename;
        Object size = ProgressSingleton.get(id + "Size");
        size = size == null ? 100 : size;
        Object progress = ProgressSingleton.get(id + "Progress");
        progress = progress == null ? 0 : progress;
        Object totalSize = ProgressSingleton.get(id + "TotalSize");
        totalSize = totalSize == null ? 100 : totalSize;
        JSONObject json = new JSONObject();
        json.put("size", size);
        json.put("progress", progress);
        json.put("totalSize",totalSize);
        response.getWriter().print(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
