package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.DateSplit;
import com.cosun.cosunp.entity.FaDing;
import com.cosun.cosunp.entity.WorkDate;
import com.cosun.cosunp.entity.WorkSet;
import com.cosun.cosunp.service.IPersonServ;
import org.apache.el.parser.ParseException;

import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author:homey Wong
 * @date:2019/6/29 0029 上午 11:27
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DateUtil {

    IPersonServ personServ = null;


    public static long startToEnd(Date startDate, Date endDate) {
        String[] startStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate).split("-");
        String[] endStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate).split("-");
        Integer startYear = Integer.parseInt(startStr[0]);
        Integer startMonth = Integer.parseInt(startStr[1]);
        Integer startDay = Integer.parseInt(startStr[2]);
        Integer endYear = Integer.parseInt(endStr[0]);
        Integer endMonth = Integer.parseInt(endStr[1]);
        Integer endDay = Integer.parseInt(endStr[2]);
        LocalDate endLocalDate = LocalDate.of(endYear, endMonth, endDay);
        LocalDate startLocalDate = LocalDate.of(startYear, startMonth, startDay);
        return startLocalDate.until(endLocalDate, ChronoUnit.DAYS);
    }

    public static String getBeforeDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        return df.format(time);
    }

    public static String getAfterDay(String dateStr) throws Exception {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String nextDay = null;
        try {
            Date temp = dft.parse(dateStr);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, 1);
            temp = cld.getTime();
            nextDay = dft.format(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextDay;
    }

    public Map<String, String> getAfterDay2(String dateStr, int jiday, List<String> faDingList
            , String[] empNoList) throws Exception {
        personServ = SpringUtil.getBean(IPersonServ.class);
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<String, String>();
        int sundayge = 0;
        int qingjiaday = 0;
        int fadingday = 0;
        StringBuilder qingJiaName = new StringBuilder("");
        Date temp = null;
        Calendar cal = null;
        boolean isWeekend = false;
        String nextDay = null;
        try {
            temp = dft.parse(dateStr);
            cal = Calendar.getInstance();
            cal.setTime(temp);
            for (int i = 1; i <= jiday; i++) {
                cal.add(Calendar.DATE, 1);
                isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
                if (isWeekend) {
                    ++jiday;
                    ++sundayge;
                    continue;
                } else {
                    temp = cal.getTime();
                    nextDay = dft.format(temp);
                    if (faDingList.contains(nextDay)) {
                        ++fadingday;
                        ++jiday;
                        continue;
                    }

                    a:
                    if (empNoList != null) {
                        for (int aa = 0; aa < empNoList.length; aa++) {
                            if (personServ.getLeaveByEmpNOAndDateStr(nextDay, empNoList[aa]) > 0) {
                                ++jiday;
                                ++qingjiaday;
                                qingJiaName.append(empNoList[aa]);
                                break a;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("nextDay", nextDay + "");
        map.put("sundayge", sundayge + "");
        map.put("qingjiaday", qingjiaday + "");
        map.put("fadingday", fadingday + "");
        map.put("qingJiaName", qingJiaName.toString());
        return map;
    }



    public Map<String, String> getAfterDay3(String dateStr, int jiday, List<String> faDingList
            , String empNo) throws Exception {
        personServ = SpringUtil.getBean(IPersonServ.class);
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<String, String>();
        int sundayge = 0;
        int qingjiaday = 0;
        int fadingday = 0;
        StringBuilder qingJiaName = new StringBuilder("");
        Date temp = null;
        Calendar cal = null;
        boolean isWeekend = false;
        String nextDay = null;
        try {
            temp = dft.parse(dateStr);
            cal = Calendar.getInstance();
            cal.setTime(temp);
            for (int i = 1; i <= jiday; i++) {
                cal.add(Calendar.DATE, 1);
                isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
                if (isWeekend) {
                    ++jiday;
                    ++sundayge;
                    continue;
                } else {
                    temp = cal.getTime();
                    nextDay = dft.format(temp);
                    if (faDingList.contains(nextDay)) {
                        ++fadingday;
                        ++jiday;
                        continue;
                    }

                    a:
                    if (empNo != null) {
                            if (personServ.getLeaveByEmpNOAndDateStr(nextDay, empNo) > 0) {
                                ++jiday;
                                ++qingjiaday;
                                qingJiaName.append(empNo);
                                break a;

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("nextDay", nextDay + "");
        map.put("sundayge", sundayge + "");
        map.put("qingjiaday", qingjiaday + "");
        map.put("fadingday", fadingday + "");
        map.put("qingJiaName", qingJiaName.toString());
        return map;
    }


    public static boolean isWeekend(String dateStr) {
        boolean isWeekend = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWeekend;
    }

    public static List<String> toDatePriodTranstoDays(String fromDate, String endDate) {
        List<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(fromDate);
            Date end_date = sdf.parse(endDate);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start_date);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end_date);
            while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(result);
        for (int a = 0; a < result.size(); a++) {
            System.out.println(result.get(a) + "***");
        }
        return result;
    }


    public static Map<String, String> spareDates(String oldDateStr, String newDateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(newDateStr==null){
            newDateStr = "1900-10-10";
        }
        Date newDate = sdf.parse(newDateStr);
        Date oldDate = sdf.parse(oldDateStr);
        Map<String, String> map = new HashMap<String, String>();
        int days = (int) ((newDate.getTime() - oldDate.getTime()) / (1000 * 3600 * 24));
        if (days < 0) {
            map.put("isDelay", "延迟");
            map.put("howdays", BigInteger.valueOf(days).abs() + "天,系统已自动重新排单.");
        } else if(days > 0){
            map.put("isDelay", "提前");
            map.put("howdays", BigInteger.valueOf(days).abs() + "天,恭喜，系统已自动重新排单.");
        }else {
            map.put("isDelay", "准时");
            map.put("howdays","完成,值得嘉奖.");
        }
        return map;
    }

    public static void main(String[] arg) {
        try {
            System.out.println(getWeek("2019-10-01"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static List<Time> getTimeList(String timeStr) {
        String[] times = timeStr.split(" ");
        if (times != null) {
            List<Time> timeList = new ArrayList<Time>();
            for (int i = 0; i < times.length; i++) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date d = format.parse(times[i] + ":00");
                    timeList.add(new java.sql.Time(d.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return timeList;
        }
        return null;
    }

    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        double abc = (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
        return abc;
    }


    public static double getDistanceOfTwoDate2(String before1, String after1) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date before = sdf.parse(before1);
            Date after = sdf.parse(after1);
            long beforeTime = before.getTime();
            long afterTime = after.getTime();
            return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String getJiaBanHoursByDateSplitAndTimeStr(DateSplit ds, String timeStr, WorkSet ws, Integer workType) {
        Double jiaBanH = 0.0;
        Time fromTime;
        Time endTime;
        List<Time> timeList = getTimeList(timeStr);
        String sb = null;
        if (timeList.get(0).before(ds.getDateFrom()) || timeList.get(0).equals(ds.getDateFrom())) {
            sb = ("25,25,");
        } else {
            sb = ("26,26,");
        }

        if ((timeList.get(timeList.size() - 1).after(ds.getDateEnd()) || timeList.get(timeList.size() - 1).equals(ds.getDateEnd())) &&
                (timeList.get(0).before(ds.getDateFrom()) || timeList.get(0).equals(ds.getDateFrom()))) {
            if (workType.intValue() == 1) {
                if (timeList.get(0).before(ws.getMorningOff()) && timeList.get(timeList.size() - 1).before(ws.getNoonOn())) {
                    jiaBanH = (calcuHours(ds.getDateFrom(), ds.getDateEnd())) + 0.5;
                } else {
                    jiaBanH = calcuHours(ds.getDateFrom(), ds.getDateEnd());
                }
            } else {
                jiaBanH = calcuHours(ds.getDateFrom(), ds.getDateEnd()) - 0.5;
            }
            return sb + jiaBanH;
        } else if (timeList.get(timeList.size() - 1).after(ds.getDateEnd()) || timeList.get(timeList.size() - 1).equals(ds.getDateEnd())) {
            if (workType.intValue() == 1) {
                if (timeList.get(0).before(ws.getMorningOff()) && timeList.get(timeList.size() - 1).before(ws.getNoonOn())) {
                    jiaBanH = (calcuHours(timeList.get(0), ds.getDateEnd())) + 0.5;
                } else {
                    jiaBanH = calcuHours(timeList.get(0), ds.getDateEnd());
                }
            } else {
                jiaBanH = calcuHours(timeList.get(0), ds.getDateEnd());
            }

            return sb + jiaBanH;
        } else if (timeList.get(0).before(ds.getDateFrom()) || timeList.get(0).equals(ds.getDateFrom())) {
            if (workType.intValue() == 1) {
                if (timeList.get(0).before(ws.getMorningOff()) && timeList.get(timeList.size() - 1).before(ws.getNoonOn())) {
                    jiaBanH = (calcuHours(ds.getDateFrom(), timeList.get(timeList.size() - 1))) + 0.5;
                } else {
                    jiaBanH = (calcuHours(ds.getDateFrom(), timeList.get(timeList.size() - 1)));
                }
            } else {
                jiaBanH = calcuHours(ds.getDateFrom(), timeList.get(timeList.size() - 1));
            }

            return sb + jiaBanH;
        } else {
            if (workType.intValue() == 1) {
                if (timeList.get(0).before(ws.getMorningOff()) && timeList.get(timeList.size() - 1).before(ws.getNoonOn())) {
                    jiaBanH = (calcuHours(timeList.get(0), timeList.get(timeList.size() - 1))) + 0.5;
                } else {
                    jiaBanH = calcuHours(timeList.get(0), timeList.get(timeList.size() - 1));
                }
            } else {
                jiaBanH = calcuHours(timeList.get(0), timeList.get(timeList.size() - 1));
            }
            return sb + jiaBanH;
        }

    }

    public static int calcuLateMinutes(Time fromTi, Time morningOn) {
        Long abc = fromTi.getTime() - morningOn.getTime();
        int extHours = ((abc.intValue()) / (1000 * 60));
        return (extHours + 1);
    }


    public static Double calcuHours(Time fromTi, Time endTi) {
        Long abc = endTi.getTime() - fromTi.getTime();
        Double extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
        String abcd = String.format("%.1f", extHours);
        String[] allNum = null;
        String intNum = "";
        String deciNum = "";
        if (abcd != null && !extHours.equals("0.0")) {
            String ccc = String.valueOf(abcd);
            allNum = String.valueOf(abcd).split("\\.");
            intNum = allNum[0];
            deciNum = allNum[1];
            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                deciNum = "0";
            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                deciNum = "5";
            }
            extHours = Double.valueOf(intNum + "." + deciNum);
        }
        if (extHours >= 5)
            return extHours - 1;

        return extHours;
    }

    public static List<DateSplit> splitDateToDateArray(Date fromDateTime, Date endDateTime) throws Exception {
        DateSplit ds = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> dateStr = toDatePriodTranstoDays(sdf.format(fromDateTime), sdf.format(endDateTime));
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        List<DateSplit> dateSplitList = new ArrayList<DateSplit>();
        String fromTime = sdf2.format(fromDateTime);
        fromTime = fromTime.split(" ")[1];
        String endTime = sdf2.format(endDateTime);
        endTime = endTime.split(" ")[1];
        if (dateStr.size() == 1) {
            ds = new DateSplit();
            ds.setDate(dateStr.get(0));
            ds.setDateFrom(new java.sql.Time(sdf1.parse(fromTime).getTime()));
            ds.setDateEnd(new java.sql.Time(sdf1.parse(endTime).getTime()));
            dateSplitList.add(ds);
            return dateSplitList;
        } else if (dateStr.size() == 2) {
            ds = new DateSplit();
            ds.setDate(dateStr.get(0));
            ds.setDateFrom(new java.sql.Time(sdf1.parse(fromTime).getTime()));
            ds.setDateEnd(new java.sql.Time(sdf1.parse("17:30:00").getTime()));
            dateSplitList.add(ds);
            ds = new DateSplit();
            ds.setDate(dateStr.get(1));
            ds.setDateFrom(new java.sql.Time(sdf1.parse("08:30:00").getTime()));
            ds.setDateEnd(new java.sql.Time(sdf1.parse(endTime).getTime()));
            dateSplitList.add(ds);
            return dateSplitList;
        } else {
            for (int a = 0; a < dateStr.size(); a++) {
                if (a == 0) {
                    ds = new DateSplit();
                    ds.setDate(dateStr.get(a));
                    ds.setDateFrom(new java.sql.Time(sdf1.parse(fromTime).getTime()));
                    ds.setDateEnd(new java.sql.Time(sdf1.parse("17:30:00").getTime()));
                    dateSplitList.add(ds);
                } else if (a == dateStr.size() - 1) {
                    ds = new DateSplit();
                    ds.setDate(dateStr.get(a));
                    ds.setDateFrom(new java.sql.Time(sdf1.parse("08:30:00").getTime()));
                    ds.setDateEnd(new java.sql.Time(sdf1.parse(endTime).getTime()));
                    dateSplitList.add(ds);
                } else {
                    ds = new DateSplit();
                    ds.setDate(dateStr.get(a));
                    ds.setDateFrom(new java.sql.Time(sdf1.parse("08:30:00").getTime()));
                    ds.setDateEnd(new java.sql.Time(sdf1.parse("17:30:00").getTime()));
                    dateSplitList.add(ds);
                }
            }

            return dateSplitList;
        }

    }

    public static int getWeek(String pTime) throws Exception {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayForWeek = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dayForWeek;
    }

    public static String getWeekStr(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(datetime);
            cal.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }

    public static int getDaysByYearMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static boolean checkIsWeekEnd(String dateStrs, String day) {
        if (dateStrs != null) {
            String[] dates = dateStrs.split(",");
            for (int a = 0; a < dates.length; a++) {
                if (dates[a].equals(day)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }


    public static boolean checkIsFaDing(String dateStrs, String day) {
        if (dateStrs != null) {
            String[] dates = dateStrs.split(",");
            for (int a = 0; a < dates.length; a++) {
                if (dates[a].equals(day)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }


}
