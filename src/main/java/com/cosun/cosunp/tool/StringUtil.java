package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.entity.OutClockIn;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/22 0022 上午 11:27
 * @Modified By:
 * @Modified-date:2018/12/22 0022 上午 11:27
 */
public class StringUtil {

    public static int getLeaveTypByStr(int line, String str) throws Exception {
        if (str != null) {
            if ("正常请假".equals(str)) {
                return 0;
            } else if ("因公外出".equals(str)) {
                return 1;
            } else if ("带薪年假".equals(str)) {
                return 2;
            } else if ("丧假".equals(str)) {
                return 3;
            } else if ("婚假".equals(str)) {
                return 4;
            } else if ("产假".equals(str)) {
                return 5;
            } else if ("陪产假".equals(str)) {
                return 6;
            } else {
                throw new Exception("第" + line + "行输入请假类型有误，请检查");
            }
        }
        return 0;
    }


    public static String increateFinishiNoByOrldFinishiNo(String oldnewestProdNo, String shortEngName) throws Exception {
        if (oldnewestProdNo != null) {
            String newestOrder;
            String beforStr = oldnewestProdNo.substring(0, 9);
            String afterNum = oldnewestProdNo.substring(9, oldnewestProdNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            dateStr = dateStr.substring(2, dateStr.length());
            String orderName = "C" + dateStr + shortEngName + "01";
            return orderName;
        }
    }


    public static Integer LeaveTypeTransf(String oldType) throws Exception {
        if (oldType.equals("年假")) {
            return 2;
        } else if (oldType.equals("事假")) {
            return 0;
        } else if (oldType.equals("病假")) {
            return 7;
        } else if (oldType.equals("婚假")) {
            return 4;
        } else if (oldType.equals("产假")) {
            return 5;
        } else if (oldType.equals("陪产假")) {
            return 6;
        }
        return 8;
    }

    public static WorkSet plusPianCha(WorkSet oldSet, DaKaPianCha daKaPianCha) throws Exception {
        WorkSet newSet = new WorkSet();
        newSet.setMorningOn(new java.sql.Time(oldSet.getMorningOn().getTime() + (daKaPianCha.getPianChaMin() * 60000)));
        newSet.setMorningOff(new java.sql.Time(oldSet.getMorningOff().getTime() - (daKaPianCha.getPianChaMin() * 60000)));
        newSet.setNoonOn(new java.sql.Time(oldSet.getNoonOn().getTime() + (daKaPianCha.getPianChaMin() * 60000)));
        newSet.setNoonOff(new java.sql.Time(oldSet.getNoonOff().getTime() - (daKaPianCha.getPianChaMin() * 60000)));
        return newSet;
    }

    public static int calTimesByOutClockIn(OutClockIn oci) throws Exception {
        int times = 0;
        if (oci.getClockInDateAMOnStr() != null && oci.getAmOnUrl() != null) {
            times++;
        }
        if (oci.getClockInDatePMOnStr() != null && oci.getPmOnUrl() != null) {
            times++;
        }
        if (oci.getClockInDateNMOnStr() != null && oci.getNmOnUrl() != null) {
            times++;
        }
        return times;
    }

    public static int checkIsNeed(String timeStr, WorkSet ws, KQBean kq) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d = format.parse(timeStr);
        Time timeNew = new java.sql.Time(d.getTime());
        if ((timeNew.after(ws.getMorningOnFrom()) && timeNew.before(ws.getMorningOnEnd())) || timeNew == ws.getMorningOnFrom() || timeNew == ws.getMorningOnEnd()) {
            if (kq.getaOnTime() != null) {
                return 4;
            }
        }

        if ((timeNew.after(ws.getMorningOffFrom()) && timeNew.before(ws.getMorningOffEnd())) || timeNew == ws.getMorningOffFrom() || timeNew == ws.getMorningOffEnd()) {
            if (kq.getaOffTime() != null) {
                return 4;
            }
        }

        if ((timeNew.after(ws.getNoonOnFrom()) && timeNew.before(ws.getNoonOnEnd())) || timeNew == ws.getNoonOnFrom() || timeNew == ws.getNoonOnEnd()) {
            if (kq.getpOnTime() != null) {
                return 4;
            }
        }

        if ((timeNew.after(ws.getNoonOffFrom()) && timeNew.before(ws.getNoonOffEnd())) || timeNew == ws.getNoonOffFrom() || timeNew == ws.getNoonOffEnd()) {
            if (kq.getpOffTime() != null) {
                return 4;
            }
        }

        if ((timeNew.after(ws.getExtworkonFrom()) && timeNew.before(ws.getExtworkonEnd())) || timeNew == ws.getExtworkonFrom() || timeNew == ws.getExtworkonEnd()) {
            if (kq.getExtWorkOnTime() != null) {
                return 4;
            }
        }

        return 0;

    }

    public static int checkIsRepeatQianKa(QianKa oldQK, QianKa newQk, WorkSet ws) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d = format.parse(newQk.getTimeStr());
        Time timeNew = new java.sql.Time(d.getTime());
        int isHow = 0;
        if ((timeNew.after(ws.getMorningOnFrom()) && timeNew.before(ws.getMorningOnEnd())) || timeNew == ws.getMorningOnFrom() || timeNew == ws.getMorningOnEnd()) {
            isHow = 1;
        }

        if ((timeNew.after(ws.getMorningOffFrom()) && timeNew.before(ws.getMorningOffEnd())) || timeNew == ws.getMorningOffFrom() || timeNew == ws.getMorningOffEnd()) {
            isHow = 2;
        }

        if ((timeNew.after(ws.getNoonOnFrom()) && timeNew.before(ws.getNoonOnEnd())) || timeNew == ws.getNoonOnFrom() || timeNew == ws.getNoonOnEnd()) {
            isHow = 3;
        }

        if ((timeNew.after(ws.getNoonOffFrom()) && timeNew.before(ws.getNoonOffEnd())) || timeNew == ws.getNoonOffFrom() || timeNew == ws.getNoonOffEnd()) {
            isHow = 4;
        }

        if ((timeNew.after(ws.getExtworkonFrom()) && timeNew.before(ws.getExtworkonEnd())) || timeNew == ws.getExtworkonFrom() || timeNew == ws.getExtworkonEnd()) {
            isHow = 5;
        }

        List<String> allStr = new ArrayList<String>();
        String[] strs = oldQK.getTimeStr().split(" ");
        for (String s : strs) {
            allStr.add(s);
        }
        List<Time> times = formTime(allStr);

        if (isHow == 1) {
            for (Time ti : times) {
                if ((ti.after(ws.getMorningOnFrom()) && ti.before(ws.getMorningOnEnd())) || ti == ws.getMorningOnFrom() || ti == ws.getMorningOnEnd()) {
                    return 1;
                }
            }
        }

        if (isHow == 2) {
            for (Time ti : times) {
                if ((ti.after(ws.getMorningOffFrom()) && ti.before(ws.getMorningOffEnd())) || ti == ws.getMorningOffFrom() || ti == ws.getMorningOffEnd()) {
                    return 1;
                }
            }
        }

        if (isHow == 3) {
            for (Time ti : times) {
                if ((ti.after(ws.getNoonOnFrom()) && ti.before(ws.getNoonOnEnd())) || ti == ws.getNoonOnFrom() || ti == ws.getNoonOnEnd()) {
                    return 1;
                }
            }
        }

        if (isHow == 4) {
            for (Time ti : times) {
                if ((ti.after(ws.getNoonOffFrom()) && ti.before(ws.getNoonOffEnd())) || ti == ws.getNoonOffFrom() || ti == ws.getNoonOffEnd()) {
                    return 1;
                }
            }
        }


        if (isHow == 5) {
            for (Time ti : times) {
                if ((ti.after(ws.getExtworkonFrom()) && ti.before(ws.getExtworkonEnd())) || ti == ws.getExtworkonFrom() || ti == ws.getExtworkonEnd()) {
                    return 1;
                }
            }
        }

        return 0;
    }


    public static int checkIsIn(String time, WorkSet ws) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d = format.parse(time);
        Time timeNew = new java.sql.Time(d.getTime());
        if ((timeNew.after(ws.getMorningOnFrom()) && timeNew.before(ws.getMorningOnEnd())) || timeNew == ws.getMorningOnFrom() || timeNew == ws.getMorningOnEnd()) {
            return 1;
        }

        if ((timeNew.after(ws.getMorningOffFrom()) && timeNew.before(ws.getMorningOffEnd())) || timeNew == ws.getMorningOffFrom() || timeNew == ws.getMorningOffEnd()) {
            return 1;
        }

        if ((timeNew.after(ws.getNoonOnFrom()) && timeNew.before(ws.getNoonOnEnd())) || timeNew == ws.getNoonOnFrom() || timeNew == ws.getNoonOnEnd()) {
            return 1;
        }

        if ((timeNew.after(ws.getNoonOffFrom()) && timeNew.before(ws.getNoonOffEnd())) || timeNew == ws.getNoonOffFrom() || timeNew == ws.getNoonOffEnd()) {
            return 1;
        }

        if ((timeNew.after(ws.getExtworkonFrom()) && timeNew.before(ws.getExtworkonEnd())) || timeNew == ws.getExtworkonFrom() || timeNew == ws.getExtworkonEnd()) {
            return 1;
        }
        return 3;
    }

    public static Time returnLargeTime(Time tim, String timestr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date d = format.parse(timestr);
            Time time2 = new java.sql.Time(d.getTime());
            if (time2.after(tim))
                return time2;
            return tim;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sortTimes(String qiankaStr, String timeStr) throws Exception {
        List<String> allStr = new ArrayList<String>();
        String[] strs = timeStr.split(" ");
        for (String s : strs) {
            allStr.add(s);
        }
        allStr.add(qiankaStr);
        List<Time> times = formTime(allStr);
        for (int i = 0; i < times.size() - 1; i++) {
            for (int j = 0; j < times.size() - 1 - i; j++) {
                if (times.get(j).after(times.get(j + 1))) {
                    Time temp = times.get(j + 1);
                    times.set(j + 1, times.get(j));
                    times.set(j, temp);
                }
            }
        }

        StringBuilder returnStr = new StringBuilder();
        for (Time Ti : times) {
            returnStr.append(Ti.toString() + " ");
        }

        return returnStr.toString();
    }


    public static String sortTime(String timeStr) throws Exception {
        if (timeStr != null && timeStr.trim().length() > 0) {
            List<String> allStr = new ArrayList<String>();
            String[] strs = timeStr.split(" ");
            for (String s : strs) {
                allStr.add(s);
            }
            List<Time> times = formTime(allStr);
            for (int i = 0; i < times.size() - 1; i++) {
                for (int j = 0; j < times.size() - 1 - i; j++) {
                    if (times.get(j).after(times.get(j + 1))) {
                        Time temp = times.get(j + 1);
                        times.set(j + 1, times.get(j));
                        times.set(j, temp);
                    }
                }
            }

            StringBuilder returnStr = new StringBuilder();
            for (Time Ti : times) {
                returnStr.append(Ti.toString() + " ");
            }
            return returnStr.toString();
        }
        return null;
    }

    public static String sortTimes2(String zhTimesStr, String qkTimeStr) throws Exception {
        List<String> allStr = new ArrayList<String>();
        String[] strs = zhTimesStr.split(" ");
        String[] strs2 = qkTimeStr.split(" ");
        for (String s : strs) {
            allStr.add(s);
        }
        for (String f : strs2) {
            allStr.add(f);
        }
        List<Time> times = formTime(allStr);

        times = new ArrayList<Time>(new HashSet<Time>(times));

        for (int i = 0; i < times.size() - 1; i++) {
            for (int j = 0; j < times.size() - 1 - i; j++) {
                if (times.get(j).after(times.get(j + 1))) {
                    Time temp = times.get(j + 1);
                    times.set(j + 1, times.get(j));
                    times.set(j, temp);
                }
            }
        }

        StringBuilder returnStr = new StringBuilder();
        for (Time Ti : times) {
            returnStr.append(Ti.toString() + " ");
        }

        return returnStr.toString();
    }


    public static List<Time> formTime(List<String> times) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        List<Time> timeList = new ArrayList<Time>();
        Time time = null;
        Date d = null;
        for (String str : times) {
            d = format.parse(str);
            time = new java.sql.Time(d.getTime());
            if(!timeList.contains(time)) {
                timeList.add(time);
            }
        }
        return timeList;
    }

    public static String onlyTimeStr(OutClockIn outClockIn) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (outClockIn != null) {
            if (outClockIn.getClockInDateAMOnStr() != null) {
                sb.append(outClockIn.getClockInDateAMOnStr().split(" ")[1]).append(" ");
            }
            if (outClockIn.getClockInDatePMOnStr() != null) {
                sb.append(outClockIn.getClockInDatePMOnStr().split(" ")[1]).append(" ");
            }
            if (outClockIn.getClockInDateNMOnStr() != null) {
                sb.append(outClockIn.getClockInDateNMOnStr().split(" ")[1]);
            }
            return sb.toString();
        }
        return "";
    }


    public static String increateOrderByOlderOrderNo(String oldNewestNo, String shortEngName) throws Exception {
        if (oldNewestNo != null) {
            String newestOrder;
            String beforStr = oldNewestNo.substring(0, 15);
            String afterNum = oldNewestNo.substring(15, oldNewestNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            String orderName = "COSUN" + dateStr + shortEngName + "01";
            return orderName;
        }
    }

    public static String increateProdNoByOlderProductNo(String oldNewestNo, String shortEngName) throws Exception {
        if (oldNewestNo != null) {
            String newestOrder;
            String beforStr = oldNewestNo.substring(0, 9);
            String afterNum = oldNewestNo.substring(9, oldNewestNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            dateStr = dateStr.substring(2, dateStr.length());
            String orderName = "C" + dateStr + shortEngName + "01";
            return orderName;
        }
    }


    public static List<String> getAllSalors() {
        List<String> salors = new ArrayList<String>();
        salors.add("邹时雨");
        salors.add("胡锋");
        salors.add("王世珺");
        salors.add("赵彩霞");
        salors.add("亚晓艾");
        salors.add("钟源");
        salors.add("陈小迷");
        salors.add("熊新宇");
        salors.add("潘莉");
        salors.add("连鸿业");
        salors.add("刘彩云");
        salors.add("明松");
        salors.add("周涛");
        salors.add("梁浩");
        salors.add("肖昌升");
        salors.add("高玉昕");
        salors.add("吴亚莹");
        salors.add("何嘉琪");
        salors.add("李彩苹");
        salors.add("龚菁");
        salors.add("李晨");
        salors.add("梁国程");
        salors.add("陈双全");
        salors.add("彭亚明");
        salors.add("陈军");
        return salors;
    }

    public static String formateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(date);
    }


    public static int occurrencesNumber(String string, String str) {
        int i = 0;
        while (string.indexOf(str) != -1) {
            int a = string.indexOf(str);
            string = string.substring(a + 1);
            i++;
        }
        return i;
    }


    public static String afterString(String string, String str) {
        int i = 0;
        if (string.indexOf(str) != -1) {
            int beginstr = string.indexOf(str);
            int laststr = string.length();
            string = string.substring(beginstr + 1, laststr);
        }
        return string;
    }


    public static String subMyString(String str, String a) {
        int index = str.lastIndexOf(a);
        if (index >= 1) {
            return str.substring(0, index + 1);
        }
        return null;
    }

    public static String subMyString1(String str, String a) {
        int index = str.lastIndexOf(a);
        if (index >= 1) {
            return str.substring(0, index);
        }
        return null;
    }


    public static String subAfterString(String str, String a) {
        if (str.contains(a)) {
            int index = str.lastIndexOf(a);
            if (index >= 1) {
                str = str.substring(index + 1, str.length());
                return str;
            }
        }
        return str;

    }


    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static int searchStrNum(String str, String strRes) {
        int n = 0;
        int index = 0;
        index = str.indexOf(strRes);
        while (index != -1) {
            n++;
            index = str.indexOf(strRes, index + 1);
        }

        return n;
    }

    public static String StringToDateStr(String empNo) {
        String year = empNo.substring(2, 6);
        String month = empNo.substring(6, 8);
        String day = empNo.substring(8, 10);
        return year + "-" + month + "-" + day;
    }

    public static Double computeFullWorkHours(String dateStr) {
        String[] dateArray = dateStr.split(",");
        return dateArray.length * 8D;
    }

}
