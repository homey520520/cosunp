package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.ZhongKongBean;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @author:homey Wong
 * @Date: 2019/10/7  上午 11:20
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ZkemSDKUtils {

    private static ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");


    public static boolean connect(String address, int port) {
        boolean result = zkem.invoke("Connect_NET", address, port).getBoolean();
        return result;
    }


    public static boolean readGeneralLogData() {
        boolean result = zkem.invoke("ReadGeneralLogData", 1).getBoolean();
        return result;
    }


    public static boolean readLastestLogData(Date lastest) {
        boolean result = zkem.invoke("ReadLastestLogData", 2018 - 07 - 24).getBoolean();
        return result;
    }

    public static List<ZhongKongBean> getGeneralLogData(String fromDate, String endDate) {
        Variant dwMachineNumber = new Variant(1, true);

        Variant dwEnrollNumber = new Variant("", true);
        Variant dwVerifyMode = new Variant(0, true);
        Variant dwInOutMode = new Variant(0, true);
        Variant dwYear = new Variant(0, true);
        Variant dwMonth = new Variant(0, true);
        Variant dwDay = new Variant(0, true);
        Variant dwHour = new Variant(0, true);
        Variant dwMinute = new Variant(0, true);
        Variant dwSecond = new Variant(0, true);
        Variant dwWorkCode = new Variant(0, true);
        List<ZhongKongBean> strList = new ArrayList<ZhongKongBean>();
        boolean newresult = false;
        try {
            do {
                Variant vResult = Dispatch.call(zkem, "SSR_GetGeneralLogData", dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond, dwWorkCode);
                newresult = vResult.getBoolean();
                if (newresult) {
                    String enrollNumber = dwEnrollNumber.getStringRef();

                    if (enrollNumber == null || enrollNumber.trim().length() == 0)
                        continue;
                    String month = dwMonth.getIntRef() + "";
                    String day = dwDay.getIntRef() + "";
                    if (dwMonth.getIntRef() < 10) {
                        month = "0" + dwMonth.getIntRef();
                    }
                    if (dwDay.getIntRef() < 10) {
                        day = "0" + dwDay.getIntRef();
                    }
                    String validDate = dwYear.getIntRef() + "-" + month + "-" + day;
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDatedateTime = sm.parse(fromDate);
                    Date endDatedateTime = sm.parse(endDate);
                    Date valiDateDate = sm.parse(validDate);
                    if ((endDatedateTime.after(valiDateDate) && fromDatedateTime.before(valiDateDate))) {
                        ZhongKongBean m = new ZhongKongBean();
                        m.setEnrollNumber(enrollNumber);
                        m.setTime(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
                        m.setDateStr(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef());
                        m.setTimeStr(dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
                        m.setVerifyMode(dwVerifyMode.getIntRef());
                        m.setInOutMode(dwInOutMode.getIntRef());
                        m.setYear(dwYear.getIntRef());
                        m.setMonth(dwMonth.getIntRef() + "");
                        m.setDay(dwDay.getIntRef());
                        m.setHour(dwHour.getIntRef());
                        m.setMinute(dwMinute.getIntRef());
                        m.setSecond(dwSecond.getIntRef());
                        strList.add(m);
                    }
                }
            } while (newresult == true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static List<ZhongKongBean> getGeneralLogData(String beforDay, Integer num) {
        Variant dwMachineNumber = new Variant(1, true);
        Variant dwEnrollNumber = new Variant("", true);
        Variant dwVerifyMode = new Variant(0, true);
        Variant dwInOutMode = new Variant(0, true);
        Variant dwYear = new Variant(0, true);
        Variant dwMonth = new Variant(0, true);
        Variant dwDay = new Variant(0, true);
        Variant dwHour = new Variant(0, true);
        Variant dwMinute = new Variant(0, true);
        Variant dwSecond = new Variant(0, true);
        Variant dwWorkCode = new Variant(0, true);
        List<ZhongKongBean> strList = new ArrayList<ZhongKongBean>();
        boolean newresult = false;
        try {
            do {
                Variant vResult = Dispatch.call(zkem, "SSR_GetGeneralLogData", dwMachineNumber, dwEnrollNumber, dwVerifyMode, dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond, dwWorkCode);
                newresult = vResult.getBoolean();
                if (newresult) {
                    String enrollNumber = dwEnrollNumber.getStringRef();

                    if (enrollNumber == null || enrollNumber.trim().length() == 0 || isNumeric(enrollNumber))
                        continue;
                    String month = dwMonth.getIntRef() + "";
                    String day = dwDay.getIntRef() + "";
                    if (dwMonth.getIntRef() < 10) {
                        month = "0" + dwMonth.getIntRef();
                    }
                    if (dwDay.getIntRef() < 10) {
                        day = "0" + dwDay.getIntRef();
                    }
                    String validDate = dwYear.getIntRef() + "-" + month + "-" + day;
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat ti = new SimpleDateFormat("HH:mm:ss");
                    Date endDatedateTime = sm.parse(beforDay);
                    Date valiDateDate = sm.parse(validDate);
                    if (valiDateDate.equals(endDatedateTime)) {
                        ZhongKongBean m = new ZhongKongBean();
                        m.setEnrollNumber(enrollNumber);
                        m.setTime(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
                        m.setDateStr(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef());
                        m.setTimeStr(dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef());
                        m.setTimeTime(ti.parse(dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef()));
                        m.setYearMonth(dwYear.getIntRef() + "-" + dwMonth.getIntRef());
                        m.setVerifyMode(dwVerifyMode.getIntRef());
                        m.setInOutMode(dwInOutMode.getIntRef());
                        m.setYear(dwYear.getIntRef());
                        m.setMonth(dwMonth.getIntRef() + "");
                        m.setDay(dwDay.getIntRef());
                        m.setHour(dwHour.getIntRef());
                        m.setMinute(dwMinute.getIntRef());
                        m.setSecond(dwSecond.getIntRef());
                        m.setMachineNum(num);
                        strList.add(m);
                    }
                }
            } while (newresult == true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static List<Map<String, Object>> getUserInfo() {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        boolean result = zkem.invoke("ReadAllUserID", 1).getBoolean();

        Variant v0 = new Variant(1);
        Variant sdwEnrollNumber = new Variant("", true);
        Variant sName = new Variant("", true);
        Variant sPassword = new Variant("", true);
        Variant iPrivilege = new Variant(0, true);
        Variant bEnabled = new Variant(false, true);

        while (result) {
            result = zkem.invoke("SSR_GetAllUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled).getBoolean();

            String enrollNumber = sdwEnrollNumber.getStringRef();
            if (enrollNumber == null || enrollNumber.trim().length() == 0)
                continue;

            String name = sName.getStringRef();
            if (sName.getStringRef().length() > 4) {
                name = sName.getStringRef().substring(0, 4);
            }
            if (name.trim().length() == 0)
                continue;
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("EnrollNumber", enrollNumber);
            m.put("Name", name);
            m.put("Password", sPassword.getStringRef());
            m.put("Privilege", iPrivilege.getIntRef());
            m.put("Enabled", bEnabled.getBooleanRef());

            resultList.add(m);
        }
        return resultList;
    }

    public static boolean setUserInfo(String number, String name, String password, int isPrivilege, boolean enabled) {
        Variant v0 = new Variant(1);
        Variant sdwEnrollNumber = new Variant(number, true);
        Variant sName = new Variant(name, true);
        Variant sPassword = new Variant(password, true);
        Variant iPrivilege = new Variant(isPrivilege, true);
        Variant bEnabled = new Variant(enabled, true);

        boolean result = zkem.invoke("SSR_SetUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled).getBoolean();
        return result;
    }

    public static Map<String, Object> getUserInfoByNumber(String number) {
        Variant v0 = new Variant(1);
        Variant sdwEnrollNumber = new Variant(number, true);
        Variant sName = new Variant("", true);
        Variant sPassword = new Variant("", true);
        Variant iPrivilege = new Variant(0, true);
        Variant bEnabled = new Variant(false, true);
        boolean result = zkem.invoke("SSR_GetUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled).getBoolean();
        if (result) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("EnrollNumber", number);
            m.put("Name", sName.getStringRef());
            m.put("Password", sPassword.getStringRef());
            m.put("Privilege", iPrivilege.getIntRef());
            m.put("Enabled", bEnabled.getBooleanRef());
            return m;
        }
        return null;
    }


    public static void main(String[] args) {
//        ZkemSDKUtils sdk = new ZkemSDKUtils();
//        Map<String, Object> map = new HashMap<String, Object>();
//        boolean connFlag = sdk.connect("192.168.2.12", 4370);
//        List<ZhongKongBean> strList = new ArrayList<ZhongKongBean>();
//        if (connFlag) {
//            boolean flag = sdk.readGeneralLogData();
//            strList.addAll(sdk.getGeneralLogData("2019-10-07"));
//        }
//
//        boolean connFlag1 = sdk.connect("192.168.2.10", 4370);
//        if (connFlag1) {
//            boolean flag = sdk.readGeneralLogData();
//            strList.addAll(sdk.getGeneralLogData("2019-10-07"));
//        }
//
//        boolean connFlag2 = sdk.connect("192.168.2.11", 4370);
//        if (connFlag2) {
//            boolean flag = sdk.readGeneralLogData();
//            strList.addAll(sdk.getGeneralLogData("2019-10-07"));
//        }
//
//        boolean connFlag3 = sdk.connect("192.168.0.202", 4370);
//        if (connFlag3) {
//            boolean flag = sdk.readGeneralLogData();
//            strList.addAll(sdk.getGeneralLogData("2019-10-07"));
//        }
//
//
//        //拼装之前排序
//        List<ZhongKongBean> newZkbList = new ArrayList<ZhongKongBean>();
//        List<ZhongKongBean> secZkbList = new ArrayList<ZhongKongBean>();
//        Integer encroNum1 = null;
//        Integer encroNum2 = null;
//        List<Integer> alreadyHaveNum = new ArrayList<Integer>();
//        for (int a = 0; a < strList.size(); a++) {
//            encroNum1 = strList.get(a).getEnrollNumber();
//            if (!alreadyHaveNum.contains(encroNum1)) {
//                for (int b = 0; b < strList.size(); b++) {
//                    encroNum2 = strList.get(b).getEnrollNumber();
//                    if (encroNum1.equals(encroNum2)) {
//                        secZkbList.add(strList.get(b));
//                    }
//                }
//
//                for (int i = 0; i < secZkbList.size() - 1; i++) {
//                    for (int j = 0; j < secZkbList.size() - 1 - i; j++) {
//                        if (secZkbList.get(j).getTimeTime().after(secZkbList.get(j + 1).getTimeTime())) {
//                            ZhongKongBean temp = secZkbList.get(j + 1);
//                            secZkbList.set(j + 1, secZkbList.get(j));
//                            secZkbList.set(j, temp);
//                        }
//                    }
//                }
//                alreadyHaveNum.add(encroNum1);
//                newZkbList.addAll(secZkbList);
//                secZkbList.clear();
//            }
//            secZkbList.clear();
//        }
//
//        ZhongKongBean zkb01 = null;
//        ZhongKongBean zkb02 = null;
//        ZhongKongBean zkb = null;
//        List<ZhongKongBean> toDataBaseList = new ArrayList<ZhongKongBean>();
//        boolean isHave;
//        for (int a = 0; a < newZkbList.size(); a++) {
//            zkb01 = newZkbList.get(a);
//            newZkbList.remove(a);
//            a--;
//            isHave = false;
//            for (int b = 0; b < toDataBaseList.size(); b++) {
//                zkb02 = toDataBaseList.get(b);
//                if (zkb01.getEnrollNumber().equals(zkb02.getEnrollNumber()) && zkb01.getDateStr().equals(zkb02.getDateStr())) {
//                    isHave = true;
//                    zkb02.setTimeStr(zkb02.getTimeStr() + " " + zkb01.getTimeStr());
//                }
//            }
//            if (!isHave) {
//                zkb = new ZhongKongBean();
//                zkb.setEnrollNumber(zkb01.getEnrollNumber());
//                zkb.setDateStr(zkb01.getDateStr());
//                zkb.setTimeStr(zkb01.getTimeStr());
//                toDataBaseList.add(zkb);
//            }
//        }
//
//        System.out.println(toDataBaseList.size() + "***");
//        for (int a = 0; a < toDataBaseList.size(); a++) {
//            System.out.println(toDataBaseList.get(a).getEnrollNumber() + " " + toDataBaseList.get(a).getDateStr() + " "
//                    + toDataBaseList.get(a).getTimeStr());
//        }
    }

}
