package com.cosun.cosunp.weixin;

import com.cosun.cosunp.weixin.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2019/8/7  上午 8:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class aaaa {
//    {
//        "button": [
//        {
//            "name": "外出打卡",
//                "sub_button": [
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/getMobileLocate&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "地址打卡"
//            },
//            {
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/getCamera&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "照相打卡"
//            }
//            ]
//        },
//        {
//            "name": "考勤查询",
//                "sub_button": [
//            {
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/queryOutClockIn&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "外出考勤记录"
//            },
//            {
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/queryLeaveSheet&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "因公外出单"
//            },
//            {
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/outClockInNote&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "打卡条件须知"
//            }
//            ]
//        }
//    ]
//    }

//     "sub_button": [
//    {
//        "type": "view",
//            "url": "https://www.cnblogs.com/i3yuan/",
//            "name": "陵南高中"
//    },
//    {
//        "type": "view",
//            "url": "https://www.cnblogs.com/i3yuan/",
//            "name": "海南高中"
//    },
//    {
//        "type": "view",
//            "url": "https://www.cnblogs.com/i3yuan/",
//            "name": "湘北高中"
//    }
//            ]

    //22.77200698852539,114.3155288696289
    //ZzGk5eR49FPNwxhXzbVWXjDsGBBXTA6V

//    @ResponseBody
//    @RequestMapping(value = "/punchClock")
//    public void punchClock(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //22.77200698852539
//        //114.3155288696289
//        Double latitude = Double.valueOf(request.getParameter("latitude"));
//        Double longitude = Double.valueOf(request.getParameter("longitude"));
//        String openId = request.getParameter("openId");
//        ModelAndView mav = new ModelAndView("success");
//        String code = request.getParameter("code");
//        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
//        jedis = pool.getResource();
//        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
//        // Map<String, String> map = WeiXinUtil.xmlToMap(request);
//        Map<String, String> address = MapUtil.getCityByLonLat(latitude, longitude);
//        String returnMes = "";
//        if (address != null) {
//            OutClockIn outClockIn = new OutClockIn();
//            Date date = new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//            String dateStr = format.format(date);
//            String dateStr1 = format1.format(date);
//            String hourStr = dateStr.split(" ")[1];
//            String addr = address.get("province") + address.get("city") + address.get("district") +
//                    address.get("street");
//            Integer hour = Integer.valueOf(hourStr.split(":")[0]);
//            outClockIn.setClockInDateStr(dateStr1);
//            outClockIn.setWeixinNo(openId);
//            if (hour < 12 && hour >= 6) {
//                //视为上午打卡
//                outClockIn.setClockInDateAMOnStr(dateStr);
//                outClockIn.setClockInAddrAMOn(addr);
//                int isClockInAlready = personServ.isClockInAlready(dateStr1, "clockInDateAMOn");
//                if (isClockInAlready == 0) {
//                    personServ.saveOrUpdateOutClockInData(outClockIn);
//                } else {
//                    returnMes = "您已经打过卡,不能重复打卡!";
//                }
//            } else if (hour >= 12 && hour <= 18) {
//                //视为下午打卡
//                outClockIn.setClockInDatePMOnStr(dateStr);
//                outClockIn.setClockInAddrPMOn(addr);
//                int isClockInAlready = personServ.isClockInAlready(dateStr1, "clockInDatePMOn");
//                if (isClockInAlready == 0) {
//                    personServ.saveOrUpdateOutClockInData(outClockIn);
//                } else {
//                    returnMes = "您已经打过卡,不能重复打卡!";
//                }
//            } else if (hour > 18 && hour <= 24) {
//                //视为晚上打卡
//                outClockIn.setClockInDateNMOnStr(dateStr);
//                outClockIn.setClockInAddNMOn(addr);
//                int isClockInAlready = personServ.isClockInAlready(dateStr1, "clockInDateNMOn");
//                if (isClockInAlready == 0) {
//                    personServ.saveOrUpdateOutClockInData(outClockIn);
//                } else {
//                    returnMes = "您已经打过卡,不能重复打卡!";
//                }
//            } else {
//                returnMes = "您打卡的时间不在规定时间段内!";
//            }
//            returnMes = "您打卡成功,打卡地址为:" + address.get("province") + address.get("city") + address.get("district") +
//                    address.get("street");
//        } else {
//            returnMes = "打卡失败，请稍后重试!";
//        }
//
//        response.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String message = null;
//        try {
//            PrintWriter out = response.getWriter();
//            InMsgEntity text = new InMsgEntity();
//            text.setFromUserName(WeiXinConfig.fromId); //原来的信息发送者，将变成信息接受者
//            text.setToUserName(openId); //原理的接受者，变成发送者
//            text.setMsgType("text"); //表示消息的类型是text类型
//            text.setCreateTime(new Date().getTime());
//            text.setContent(returnMes);
//            message = WeiXinUtil.textMessageToXml(text); //装换成 xml 格式发送给微信解析
//            out.print(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/getCamera&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect"
    //http://api.map.baidu.com/reverse_geocoding/v3/?ak=ZzGk5eR49FPNwxhXzbVWXjDsGBBXTA6V&output=json&coordtype=wgs84ll&location=22.77200698852539,114.3155288696289

    //{
//        "button": [
//        {
//            "name": "外出打卡",
//                "sub_button": [
//            {
//                "type": "view",
//                    "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5109277d8902606&redirect_uri=http://homey.nat100.top/weixin/getMobileLocate&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
//                    "name": "地址打卡"
//            },
//            {
//                "type": "view",
//                    "url": "https://www.cnblogs.com/i3yuan/",
//                    "name": "照相打卡"
//            }
//            ]
//        },
//        {
//            "type": "click",
//                "name": "考勤查询",
//                "key": "clickquery"
//        }
//    ]
//    }

//    {
//        "button": [
//        {
//            "type": "view",
//                "name": "打卡",
//                "url": "http://homey.nat100.top/weixin/getMobileLocate"
//        },
//        {
//            "type": "click",
//                "name": "考勤查询",
//                "key": "clickquery"
//        }
//    ]
//    }
//    {
//        "button": [
//        {
//            "type": "click",
//                "name": "打卡",
//                "key": "clockin"
//        },
//        {
//            "type": "click",
//                "name": "考勤查询",
//                "key": "clickquery"
//        }
//    ]
//    }
//
//    整理后:
        //=============================业务模块=======================================
//      1.业务模块,订单的增（业务图纸+订单信息）    完成
//      ，改，                                      完成
//        查                                        完成
//        功能
//        下发，终止，暂停，完成 状态               完成
//        订单编号根据登录名自动生成                完成
//        PDF打印功能                               完成
//        订单图纸上传功能                          完成
//        订单下发后系统推送给PMC                   等待
//      2.项目类的订单
//           项目类的单如果是分批下单需要进行更细的项目名称编写；
        //   8-14 下午开始
//        不分批？
//       （？？一样可以指定时间段才能下订单）
//        命名是什么命名，订单？项目？
//        注意 命名规则： 文件类型（？？）+日期+项目名称+订单号+业务员名字，


        //================================PMC模块============================================
        //    而后PMC根据订单需求表和图纸文件确认人员安排与完成时间，
        //    确认是否需要提前买材料，需要外发（五种文件里面需要哪几种）；
        //    a)线上进行时：
        //    iii.PMC完成后系统推送消息给对应设计师；
        //    iv.PMC：指定人员与完成时间、确认修改流程、更改完成时间
        // =============================分界线===================================
        //


//    iv.设计师设计制作图纸，后续产出有制作图纸、BOM表、激光文件、雕刻文件、外发文件，
//    完成制定图纸的上传确认；
//    v.消息推送设计图纸等给业务，把BOM表推送给PMC，提示图纸完成，业务自己确认的4个小时之内完成
//    ，需要客户确认的两天之内反馈，消息推送给PMC部门，PMC部门根据是否有修改以及修改工作量做下一步安排；
//    b)作为对象的操作：
//    i.管理员：设定管理员、业务员、设计师、PMC的角色与权限；
//    ii.业务员：下发订单、修改订单、暂停订单、终止订单、确认完成、动作描述。
//    iii.设计师：深化、制作图纸、修改图纸；
//    iv.PMC：指定人员与完成时间、确认修改流程、更改完成时间
//    v.系统：消息推送
//    c)作为数据的操作
//    i.订单信息【订单需求表（产品数量、交期、类别、尺寸大小、大致工艺描述等）和图纸文件】
//             【制作组别、设计师、设计完成时间】：新增、修改、暂停、终止；
//    ii.制作图纸【制作图纸、BOM表、激光文件、雕刻文件、外发文件】：新增、修改、暂停、终止；

}
