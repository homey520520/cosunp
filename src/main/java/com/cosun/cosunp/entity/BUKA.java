package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/2/26  上午 11:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class BUKA implements Serializable {

    private static final long serialVersionUID = -7729623372590499965L;

    private String id;
    private String title;  //异常状态  //补卡时间  //补卡事由  //说明附件
    private String type;
    private String[] value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }


//    {
//        "spname":"请假",
//            "apply_name":"请假测试",
//            "apply_org":"请假测试企业",
//            "approval_name":[
//        "审批人测试"
//            ],
//        "notify_name":[
//        "抄送人测试"
//            ],
//        "sp_status":1,
//            "sp_num":201704200004,
//            "apply_time":1499153693,
//            "apply_user_id":"testuser",
//            "":{
//                "timeunit":0,
//                "leave_type":4,
//                "start_time":1492099200,
//                "end_time":1492790400,
//                "duration":144,
//                "reason":""
//    }，
//        "comm":{
//        "apply_data":"{\"item-1492610773696\":{\"title\":\"abc\",\"type\":\"text\",\"value\":\"\"}}"
//    }
//    },


    // "{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},"+
    // "{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1581586200000},"+
    // "{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"忘记打卡\"},"+
    // "{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"


//    {
//            "errcode":0,
//            "errmsg":"ok",
//            "count":2,
//            "total":2,
//            "next_spnum":202002140002,
//            "data":[
//                    {
//                            "spname":"补卡申请",
//                            "apply_name":"吴世乐",
//                            "apply_org":"项目中心/项目管理",
//                            "approval_name":["刘志勇","凡钟俊","苏天凤"],
//                            "notify_name":["陈婷","邱加萍+312"],
//                            "sp_status":2,
//                            "sp_num":202002140001,
//                            "comm":{
//                                "apply_data":"[" +
//                                "{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"}," +
//                                "{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1581586200000}," +
//                                "{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"忘记打卡\"}," +
//                                "{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"},
//                                "mediaids":[],
//                                "apply_time":1581635509,
//                                "apply_user_id":"WuShiLe"},
//
//
//
//                   {"spname":"补卡申请","apply_name":"彭思思","apply_org":"财务部","approval_name":["董朝霞","苏天凤"],"notify_name":["陈婷","邱加萍+312"],"sp_status":2,"sp_num":202002140002,"comm":{"apply_data":"[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1581640200000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"忘记打卡，有测体温\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"},"mediaids":[],"apply_time":1581654480,"apply_user_id":"PengSiSi"}
//        ]
//    }


//    {
//            "errcode":0,
//            "errmsg":"ok",
//            "count":10,
//            "total":10,
//            "next_spnum":202002170010,
//            "data":[
//        {
//                "spname":"外出申请（项目中心）",
//                "apply_name":"凡钟俊",
//                "apply_org":"项目中心",
//                "approval_name":["凡钟俊", "苏天凤"],
//                "notify_name":["陈婷", "邱加萍+312"],
//                "sp_status":2,
//                "sp_num":202002170001,
//                "comm":{
//                    "apply_data":
//                   "[{" +
//                           "\"id\":\"item-1497581558567\"," +
//                           "\"title\":\"外出事由\"," +
//                           "\"type\":\"textarea\"," +
//                           "\"value\":\"科技园现场勘察沟通/前海控股\"}," +
//                           "{" +
//                           "\"id\":\"item-1497581575653\"," +
//                           "\"title\":\"外出地点\"," +
//                           "\"type\":\"text\"," +
//                           "\"value\":\"科技园\"}," +
//                           "{" +
//                           "\"id\":\"smart-time\"," +
//                           "\"title\":\"外出\"," +
//                           "\"type\":\"smart\"," +
//                           "\"value\":" +
//                           "[{" +
//                           "\"id\":\"begin_time\"," +
//                           "\"title\":\"开始时间\"," +
//                           "\"type\":\"datehour\"," +
//                           "\"value\":1581899400000}," +
//                           "{" +
//                           "\"id\":\"end_time\"," +
//                           "\"title\":\"结束时间\"," +
//                           "\"type\":\"datehour\"," +
//                           "\"value\":1581924600000}," +
//                           "{" +
//                           "\"id\":\"duration\"," +
//                           "\"title\":\"外出时长\"," +
//                           "\"type\":\"durationdatehour\"," +
//                           "\"value\":25200}]}," +
//                           "{" +
//                           "\"id\":\"item-1497581590290\"," +
//                           "\"title\":\"附件\"," +
//                           "\"type\":\"file\"," +
//                           "\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581898823, "apply_user_id":"FanZhongJun" },
//
//
//        {
//                "spname":"外出申请（项目中心）",
//                "apply_name":"刘红波",
//                "apply_org":"项目中心/项目业务",
//                "approval_name":["凡钟俊", "苏天凤"],
//                "notify_name":["陈婷", "邱加萍+312"],
//                "sp_status":2,
//                "sp_num":202002170002,
//                "comm":{
//                     "apply_data":
//            "[{" +
//                     "\"id\":\"item-1497581558567\"," +
//                    "\"title\":\"外出事由\"," +
//                    "\"type\":\"textarea\"," +
//                    "\"value\":\"福田办理深圳CA\"}," +
//                    "{" +
//                    "\"id\":\"item-1497581575653\"," +
//                    "\"title\":\"外出地点\"," +
//                    "\"type\":\"text\"," +
//                    "\"value\":\"福田设计大厦\"}," +
//                    "{" +
//                    "\"id\":\"smart-time\"," +
//                    "\"title\":\"外出\"," +
//                    "\"type\":\"smart\"," +
//                    "\"value\":" +
//                    "[{" +
//                    "\"id\":\"begin_time\"," +
//                    "\"title\":\"开始时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1581901200000}," +
//                    "{" +
//                    "\"id\":\"end_time\"," +
//                    "\"title\":\"结束时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1581926400000}," +
//                    "{" +
//                    "\"id\":\"duration\"," +
//                    "\"title\":\"外出时长\"," +
//                    "\"type\":\"durationdatehour\"," +
//                    "\"value\":25200}]}," +
//                    "{" +
//                    "\"id\":\"item-1497581590290\"," +
//                    "\"title\":\"附件\"," +
//                    "\"type\":\"file\"," +
//                    "\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581899032, "apply_user_id":"LiuHongBo" },
//        {
//                "spname":"出差",
//                "apply_name":"樊启丙",
//                "apply_org":"项目中心/项目业务",
//                "approval_name":["凡钟俊"],
//                "notify_name":[],
//                "sp_status":2,
//                "sp_num":202002170003,
//                "comm":{
//            "apply_data":
//            "[{" +
//                    "\"id\":\"item-1497581558567\"," +
//                    "\"title\":\"出差事由\"," +
//                    "\"type\":\"textarea\"," +
//                    "\"value\":\"爱蒂宫朱经理约现场讨论蓝景店户外照片报批及室内门牌留电交底事宜。\"}," +
//                    "{" +
//                    "\"id\":\"item-1497581575653\"," +
//                    "\"title\":\"出差地点\"," +
//                    "\"type\":\"text\"," +
//                    "\"value\":\"福田，南山\"}," +
//                    "{" +
//                    "\"id\":\"smart-time\"," +
//                    "\"title\":\"出差\"," +
//                    "\"type\":\"smart\"," +
//                    "\"value\":[{" +
//                    "\"id\":\"begin_time\"," +
//                    "\"title\":\"开始时间\"," +
//                    "\"type\":\"datehalfday\"," +
//                    "\"value\":1581868800000}," +
//                    "{\"id\":" +
//                    "\"end_time\"," +
//                    "\"title\":\"结束时间\"," +
//                    "\"type\":\"datehalfday\"," +
//                    "\"value\":1581912000000}," +
//                    "{\"id\":\"duration\"," +
//                    "\"title\":\"出差时长\"," +
//                    "\"type\":\"durationdatehalfday\"," +
//                    "\"value\":86400}]}," +
//                    "{" +
//                    "\"id\":\"item-1497581590290\"," +
//                    "\"title\":\"附件\"," +
//                    "\"type\":\"file\"," +
//                    "\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581901739, "apply_user_id":"FanQiBing" },
//        {
//                "spname":"外出申请（项目中心） ",
//                "apply_name":"曹英华",
//                "apply_org":"项目中心/项目管理",
//                "approval_name":["凡钟俊", "苏天凤"],
//                "notify_name":["陈婷", "邱加萍+312"],
//                "sp_status":2, "sp_num":202002170004,
//                "comm":{
//            "apply_data":
//            "[{\"id\":\"item-1497581558567\",\"title\":\"外出事由\",\"type\":\"textarea\",\"value\":\"坪地社区（美顺佳购物广场）配合社区人员出入检查\"},{\"id\":\"item-1497581575653\",\"title\":\"外出地点\",\"type\":\"text\",\"value\":\"坪地\"},{\"id\":\"smart-time\",\"title\":\"外出\",\"type\":\"smart\",\"value\":[{\"id\":\"begin_time\",\"title\":\"开始时间\",\"type\":\"datehour\",\"value\":1581912000000},{\"id\":\"end_time\",\"title\":\"结束时间\",\"type\":\"datehour\",\"value\":1581926400000},{\"id\":\"duration\",\"title\":\"外出时长\",\"type\":\"durationdatehour\",\"value\":14400}]},{\"id\":\"item-1497581590290\",\"title\":\"附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581912596, "apply_user_id":"CaoYingHua" },
//        {
//            "spname":"补卡申请", "apply_name":"王键", "apply_org":"项目中心/项目深化", "approval_name":["黄杰生", "凡钟俊", "苏天凤"],
//            "notify_name":["陈婷", "邱加萍+312"],"sp_status":3, "sp_num":202002170005, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1577926800000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"试用企业微信\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581922994, "apply_user_id":"WangJian" },
//        {
//            "spname":"补卡申请", "apply_name":"刘志勇", "apply_org":"项目中心/项目管理", "approval_name":["刘志勇", "凡钟俊", "苏天凤"],
//            "notify_name":["陈婷", "邱加萍+312"],"sp_status":1, "sp_num":202002170006, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1578011400000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"外出珠海中安项目办结算资料，早上6点至晚上23点，在外出登记有备注\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581925489, "apply_user_id":"LiuZhiYong" },
//        {
//            "spname":"补卡申请", "apply_name":"刘志勇", "apply_org":"项目中心/项目管理", "approval_name":["刘志勇", "凡钟俊", "苏天凤"],
//            "notify_name":["陈婷", "邱加萍+312"],"sp_status":1, "sp_num":202002170007, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1578130200000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"外出壹城中心办结算资料，在外出登记有备注\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581925665, "apply_user_id":"LiuZhiYong" },
//        {
//            "spname":"补卡申请", "apply_name":"刘志勇", "apply_org":"项目中心/项目管理", "approval_name":["刘志勇", "凡钟俊", "苏天凤"],
//            "notify_name":["陈婷", "邱加萍+312"],"sp_status":1, "sp_num":202002170008, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1578303000000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"外出壹城送发票及跟进结算资料，在外出登记有备注\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581925753, "apply_user_id":"LiuZhiYong" },
//        {
//            "spname":"补卡申请", "apply_name":"樊启丙", "apply_org":"项目中心/项目业务", "approval_name":["樊启丙", "凡钟俊", "苏天凤"],
//            "notify_name":["陈婷", "邱加萍+312"],"sp_status":2, "sp_num":202002170009, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1578994200000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"华策集团裴总宴请\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581928849, "apply_user_id":"FanQiBing" },
//        {
//            "spname":"补卡申请", "apply_name":"王卫文", "apply_org":"研发信息中心", "approval_name":["王卫文", "苏天凤"],"notify_name":[
//            "陈婷", "邱加萍+312"],"sp_status":2, "sp_num":202002170010, "comm":{
//            "apply_data":
//            "[{\"id\":\"checkin-status\",\"title\":\"异常状态\",\"type\":\"text\",\"un_print\":false,\"value\":\"未打卡\"},{\"id\":\"checkin-time\",\"title\":\"补卡时间\",\"type\":\"datehour\",\"un_print\":false,\"value\":1579253400000},{\"id\":\"item-1497417335175\",\"title\":\"补卡事由\",\"type\":\"textarea\",\"value\":\"忘记打卡\"},{\"id\":\"item-1497513542396\",\"title\":\"说明附件\",\"type\":\"file\",\"value\":[]}]"
//        },"mediaids":[],"apply_time":1581928891, "apply_user_id":"WangWeiWen"
//        }
//        ]
   // }


//    {
//            "errcode":0,
//            "errmsg":"ok",
//            "count":13,
//            "total":13,
//            "next_spnum":202002250014,
//            "data":[{
//                "spname":"请假管理（项目中心）",
//                "apply_name":"张孝林",
//                "apply_org":"项目中心/项目管理",
//                "approval_name":["凡钟俊","苏天凤"],
//                "notify_name":["邱加萍+312","陈婷","苏天凤","凡钟俊"],
//                "sp_status":2,
//                "sp_num":202002250002,
//                "comm":{
//                    "apply_data":"[{" +
//                    "\"id\":\"smart-select\"," +
//                    "\"title\":\"请假类型\"," +
//                    "\"type\":\"select\"," +
//                    "\"value\":\"事假\"}," +
//                    "{" +
//                    "\"id\":\"smart-time\"," +
//                    "\"title\":\"请假\"," +
//                    "\"type\":\"smart\"," +
//                    "\"value\":[{" +
//                    "\"id\":\"begin_time\"," +
//                    "\"title\":\"开始时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1582589040000}," +
//                    "{" +
//                    "\"id\":\"end_time\"," +
//                    "\"title\":\"结束时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1582623000000}," +
//                    "{" +
//                    "\"id\":\"duration\"," +
//                    "\"title\":\"请假时长\"," +
//                    "\"type\":\"durationdatehour\"," +
//                    "\"value\":28800}]}," +
//                    "{" +
//                    "\"id\":\"item-1497581399901\"," +
//                    "\"title\":\"请假事由\"," +
//                    "\"type\":\"textarea\"," +
//                    "\"value\":\"小孩子不舒服\"}," +
//                    "{" +
//                    "\"id\":\"item-1497581426169\"," +
//                    "\"title\":\"说明附件\"," +
//                    "\"type\":\"file\"," +
//                    "\"value\":[]}]"},
//                    "mediaids":[],
//                    "apply_time":1582589111,
//                    "apply_user_id":"ZhangXiaoLin"},
//
//
//
//        {
//                "spname":"请假管理（亚非区）",
//                "apply_name":"梁国程",
//                "apply_org":"销售中心/亚非区",
//                "approval_name":["邹时雨(Young Zou)","陈军","苏天凤"],
//                "notify_name":["陈婷","邱加萍+312","邹时雨(Young Zou)"],
//                "sp_status":1,
//                "sp_num":202002250013,
//                "comm":{
//                    "apply_data":"[{" +
//                    "\"id\":\"smart-select\"," +
//                    "\"title\":\"请假类型\"," +
//                    "\"type\":\"select\"," +
//                    "\"value\":\"其他\"},{" +
//                    "\"id\":\"smart-time\"," +
//                    "\"title\":\"请假\"," +
//                    "\"type\":\"smart\"," +
//                    "\"value\":[{" +
//                    "\"id\":\"begin_time\"," +
//                    "\"title\":\"开始时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1581082440000}," +
//                    "{" +
//                    "\"id\":\"end_time\"," +
//                    "\"title\":\"结束时间\"," +
//                    "\"type\":\"datehour\"," +
//                    "\"value\":1581168840000}," +
//                    "{" +
//                    "\"id\":\"duration\"," +
//                    "\"title\":\"请假时长\"," +
//                    "\"type\":\"durationdatehour\"," +
//                    "\"value\":28800}]}," +
//                    "{" +
//                    "\"id\":\"item-1497581399901\"," +
//                    "\"title\":\"请假事由\"," +
//                    "\"type\":\"textarea\"," +
//                    "\"value\":\"家中有事，不能按时在线办公。\"}," +
//                    "{" +
//                    "\"id\":\"item-1497581426169\"," +
//                    "\"title\":\"说明附件\"," +
//                    "\"type\":\"file\"," +
//                    "\"value\":[]}]"
//        },
//                    "mediaids":[],
//                    "apply_time":1582637717,
//                    "apply_user_id":"LiangGuoCheng"
//        },
//        ]}
}
