package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.weixin.OutClockIn;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/9 0009 下午 5:04
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Repository
@Mapper
public interface PersonMapper {


    @Delete({
            "<script>",
            "delete",
            "from position",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deletePositionByIdBatch(@Param("ids") List<Integer> ids);

    @Select({
            "<script>",
            "select e.id AS id, e.empno as empNo ",
            "FROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId",
            " where e.id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Employee> getEmployeeByIds(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from dept",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteDeptByBatch(@Param("ids") List<Integer> ids);


    @Delete({
            "<script>",
            "delete",
            "from employee",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteEmpByBatch(@Param("ids") List<Integer> ids);


    @Delete({
            "<script>",
            "delete",
            "from leavedata",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteLeaveByBatch(@Param("ids") List<Integer> ids);

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%') ")
    int findSaveOrNot(Position position);

    @Select("SELECT\n" +
            "\te.empno\n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN dept t ON e.deptId = t.id\n" +
            "WHERE\n" +
            "\te. NAME = #{name} " +
            "AND t.deptname = #{deptName} limit 1 ")
    String getEmpNoByDeptNameAndName(String name, String deptName);

    @Select("SELECT\n" +
            "\te.empno\n" +
            "FROM\n" +
            "\temployee e\n" +
            "WHERE\n" +
            "\te. NAME = #{name}  ")
    List<String> getEmpNoByName(String name);

    @Select("select count(*) from dept where deptName like  CONCAT('%',#{deptName},'%') ")
    int findSaveOrNot2(String deptName);

    @Select("select userid from qyweixinbd")
    List<String> getAllUserName();

    @Insert("insert into dakapiancha (pianChaMin) values (#{pianChaMin})")
    void saveDAPCSetUp(DaKaPianCha daKaPianCha);

    @Update("update dakapiancha set pianChaMin = #{pianChaMin}")
    void updateDAPCSetUp(DaKaPianCha daKaPianCha);

    @Select("select * from dakapiancha")
    DaKaPianCha getDAPC();

    @Insert("insert into position (positionName,positionLevel) values (#{positionName},#{positionLevel})")
    void savePosition(Position position);

    @Insert("insert into dept (deptName) values (#{deptName})")
    void saveDept(String deptName);

    @Insert("insert into zhongkongemployee (EnrollNumber,empNo) values (#{EnrollNumber},#{empNo})")
    void saveZKBandDing(Employee ee);

    @Select("select count(*) from position")
    int findAllPositionConditionCount();

    @Select("select count(*) from dept")
    int findAllDeptConditionCount();

    @Select("select count(*) from clockinsetup where outDays = #{outDays}")
    int findIfExsit(ClockInSetUp clockInSetUp);

    @Select("select count(*) from qianka where empNo = #{empNo} and date = #{dateStr} and timeStr like CONCAT('%',#{timeStr},'%') ")
    int findIfExsitQianKa(QianKa qianKa);


    @Insert("insert into clockinsetup (outDays,dayClockInTimes,remark) values (#{outDays},#{dayClockInTimes},#{remark})")
    void saveClockInSetUp(ClockInSetUp clockInSetUp);

    @Insert("insert into qianka (empNo,date,timeStr,type,remark) values (#{empNo},#{dateStr},#{timeStr},#{type},#{remark}) ")
    void saveQianKa(QianKa qianKa);

    @Select("select * from qianka where date = #{dateStr} and empNo = #{empNo} ")
    QianKa getQianKaByDateAndEmpno(QianKa qianKa);

    @Select(" select * from qianka where date = #{dateStr} and empNo = #{empNo}  ")
    QianKa getQianKaByDateAndEmpnoA(String empNo, String dateStr);

    @Select("SELECT * FROM outdan where empNo = #{empNo} and  outtime <= #{dateStrStart} and realcomtime >= #{dateStrEnd} ")
    Out getOutDanByEmpNoandDate(String empNo, String dateStrStart, String dateStrEnd);


    @Select("SELECT * FROM outdan where empNo = #{empNo} and  DATE_FORMAT(outtime,'%Y-%m-%d') <= #{dateStr} and " +
            " DATE_FORMAT(realcomtime,'%Y-%m-%d') >= #{dateStr} ")
    Out getOutDanByEmpNoandDateOnly(String empNo, String dateStr);


    @Select("SELECT\n" +
            "\toc.id,\n" +
            "\toc.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNmon AS clockInDateNmonStr,\n" +
            "\toc.clockInAddNMOn,\n" +
            "\toc.nmOnUrl,\n" +
            "\toc.clockInDate AS clockInDateStr\n" +
            " FROM " +
            "\toutclockin oc\n" +
            "LEFT JOIN qyweixinbd gg ON gg.userid = oc.userid\n" +
            "LEFT JOIN employee ee ON gg.empNo = ee.empNo\n" +
            "WHERE\n" +
            "\toc.clockInDate = #{dateStr} \n" +
            "AND ee.empNo = #{empNo} ")
    OutClockIn getOutClockInByEmpNoAndDateA(String empNo, String dateStr);


    @Select("SELECT\n" +
            "\toc.id,\n" +
            "\toc.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNmon AS clockInDateNmonStr,\n" +
            "\toc.clockInAddNMOn,\n" +
            "\toc.nmOnUrl,\n" +
            "\toc.clockInDate AS clockInDateStr\n" +
            " FROM " +
            "\toutclockin oc\n" +
            "LEFT JOIN qyweixinbd gg ON gg.userid = oc.userid\n" +
            "LEFT JOIN employee ee ON gg.empNo = ee.empNo\n" +
            "WHERE\n" +
            "\toc.clockInDate = #{dateStr} \n" +
            "AND ee.empNo = #{empNo} and oc.amOnUrl is not null ")
    OutClockIn getOutClockInByEmpNoAndDateAM(String empNo, String dateStr);

    @Select("SELECT\n" +
            "\toc.id,\n" +
            "\toc.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNmon AS clockInDateNmonStr,\n" +
            "\toc.clockInAddNMOn,\n" +
            "\toc.nmOnUrl,\n" +
            "\toc.clockInDate AS clockInDateStr\n" +
            " FROM " +
            "\toutclockin oc\n" +
            "LEFT JOIN qyweixinbd gg ON gg.userid = oc.userid\n" +
            "LEFT JOIN employee ee ON gg.empNo = ee.empNo\n" +
            "WHERE\n" +
            "\toc.clockInDate = #{dateStr} \n" +
            "AND ee.empNo = #{empNo} and (oc.pmOnUrl is not null or oc.nmOnUrl is not null) ")
    OutClockIn getOutClockInByEmpNoAndDatePM(String empNo, String dateStr);

    @Select("select * from clockinsetup where outDays > #{days} order by outDays asc limit 1 ")
    ClockInSetUp getClockSetUpByDays(Double days);

    @Insert("update qianka  set timeStr = #{timeStr},type = #{type},remark = #{remark} where id = #{id}")
    void updateQianKa(QianKa qianKa);

    @Update("update clockinsetup set dayClockInTimes = #{dayClockInTimes},remark = #{remark} where outDays = #{outDays}")
    void updateClockInSetUp(ClockInSetUp clockInSetUp);

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%')")
    int checkIfExsit(String positionName);

    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%')")
    int checkIfExsit2(String deptname);

    @Select("select yearMonth from zhongkongbean group by yearMonth order by yearMonth desc")
    List<String> getAllKQMonthListKQBean();


    @SelectProvider(type = PseronDaoProvider.class, method = "isClockInAlready")
    int isClockInAlready(String openId, String dateStr, String titleName);

    @Select("select " +
            "weixinNo,\n" +
            "\tifnull(date_format(clockInDateAMOn, '%h:%i:%s'),'')  as clockInDateAMOnStr,\n" +
            "\tifnull(clockInAddrAMOn,'') as clockInAddrAMOn,\n" +
            "\t \tCASE\n" +
            "WHEN amOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN amOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as amOnUrl ,\n" +
            "\tifnull(date_format(clockInDatePMOn, '%h:%i:%s'),'') as clockInDatePMOnStr,\n" +
            "\tifnull(clockInAddrPMOn,'') as clockInAddrPMOn,\n" +
            "\t\tCASE\n" +
            "WHEN pmOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN pmOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as pmOnUrl,\n" +
            "\tifnull(date_format(clockInDateNMOn, '%h:%i:%s'),'') as clockInDateNMOnStr,\n" +
            "\tifnull(clockInAddNMOn,'') as clockInAddNMOn,\n" +
            "\t\tCASE\n" +
            "WHEN nmOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN nmOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as nmOnUrl,\n" +
            "\tclockInDate as clockInDateStr" +
            " from outclockin where weixinNo = #{openId} order by clockInDate asc")
    List<OutClockIn> findAllOutClockInByOpenId(String openId);

    @Select("select * from outclockin where id = #{id}")
    OutClockIn getOutClockInById(Integer id);

    @Select("select empNo from employee where name = #{name} limit 1 ")
    String getEmpNoByNameA(String name);

    @Select("select ot.* from outdan ot left join employee ee on ot.empNo = ee.empNo  where ee.name = #{name} and ot.date = #{dateStr}")
    Out getOutDanByNameAndDateStr(Out out);

    @Delete("delete from outdan where id = #{id}")
    void deleteOutDateToMysql(Integer id);

    @Select("select gongzhonghaoId from gongzhonghao")
    List<String> findAllOpenId();

    @Select("select * from workset where month = #{yearMonth} ")
    List<WorkSet> getAllWorkSetListByYearMonth(String yearMonth);

    @Select("select * from workdate where month = #{yearMonth }")
    List<WorkDate> getAllWorkDateListByYearMonth(String yearMonth);

    @Select("select * from clockinsetup order by outDays desc")
    List<ClockInSetUp> findAllClockInSetUp();

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllLeaveA")
    List<Leave> findAllLeaveA(String monday, String tuesday, String wednesday, String thurday, String today);

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "  ee.empno,\n" +
            "  ee.id,\n" +
            "\tgg.gongzhonghaoId,\n" +
            "\tld.beginleave,\n" +
            "\tld.endleave,\n" +
            "\toc.clockInDate,\n" +
            "\toc.clockInDateAMOn,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNMOn,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl\n" +
            "FROM\n" +
            "\tleavedata ld\n" +
            "LEFT JOIN employee ee ON ld.employeeid = ee.id\n" +
            "LEFT JOIN gongzhonghao gg ON gg.empNo = ee.empno\n" +
            "LEFT JOIN outclockin oc ON oc.weixinNo = gg.gongzhonghaoId\n" +
            "AND oc.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
            "AND oc.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
            " where oc.clockInDate in(#{monday},#{tuesday},#{wednesday},#{thurday},#{today})" +
            "ORDER BY\n" +
            "\tee.empno  ASC")
    List<Employee> findLeaveDataUionOutClockData(String monday, String tuesday, String wednesday, String thurday, String today);

    @Select("select * from outclockin where userid = #{userid} and clockInDate = #{clockInDateStr} ")
    OutClockIn getOutClockInByDateAndWeiXinId(OutClockIn outClockIn);

    @Insert("INSERT into outclockin (userid,\n" +
            "\tclockInDateAMOn,\n" +
            "\tclockInAddrAMOn,\n" +
            "\tamOnUrl,\n" +
            "\tclockInDatePMOn,\n" +
            "\tclockInAddrPMOn,\n" +
            "\tpmOnUrl,\n" +
            "\tclockInDateNMOn,\n" +
            "\tclockInAddNMOn,\n" +
            "\tnmOnUrl,\n" +
            "\tclockInDate)\n" +
            " values(" +
            "#{userid}," +
            "#{clockInDateAMOnStr}," +
            "#{clockInAddrAMOn}," +
            "#{amOnUrl}," +
            "#{clockInDatePMOnStr}," +
            "#{clockInAddrPMOn}," +
            "#{pmOnUrl}," +
            "#{clockInDateNMOnStr}," +
            "#{clockInAddNMOn}," +
            "#{nmOnUrl}," +
            "#{clockInDateStr})\n")
    void addOutClockInDateByBean(OutClockIn outClockIn);

    @Insert("INSERT into outclockin (weixinNo,\n" +
            "\tclockInDateAMOn,\n" +
            "\tclockInAddrAMOn,\n" +
            "\tamOnUrl,\n" +
            "\tclockInDatePMOn,\n" +
            "\tclockInAddrPMOn,\n" +
            "\tpmOnUrl,\n" +
            "\tclockInDateNMOn,\n" +
            "\tclockInAddNMOn,\n" +
            "\tnmOnUrl,\n" +
            "\tclockInDate)\n" +
            " values(" +
            "#{weixinNo}," +
            "#{clockInDateAMOnStr}," +
            "#{clockInAddrAMOn}," +
            "#{amOnUrl}," +
            "#{clockInDatePMOnStr}," +
            "#{clockInAddrPMOn}," +
            "#{pmOnUrl}," +
            "#{clockInDateNMOnStr}," +
            "#{clockInAddNMOn}," +
            "#{nmOnUrl}," +
            "#{clockInDateStr})\n")
    void addPhotoOutClockInDateByBean(OutClockIn outClockIn);

    @Update("update outclockin set amOnUrl = #{amOnUrl} " +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByAM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDateAMOn = #{clockInDateAMOnStr},clockInAddrAMOn = #{clockInAddrAMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByAM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDatePMOn = #{clockInDatePMOnStr},clockInAddrPMOn = #{clockInAddrPMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByPM(OutClockIn outClockIn);

    @Update("update outclockin set pmOnUrl = #{pmOnUrl}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByPM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDateNMOn = #{clockInDateNMOnStr},clockInAddNMOn = #{clockInAddNMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByNM(OutClockIn outClockIn);

    @Update("update outclockin set nmOnUrl = #{nmOnUrl}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByNM(OutClockIn outClockIn);

    @Select("select * from clockinsetup order by outDays asc")
    List<ClockInSetUp> findAllCLockInSetUp();

    @Select("SELECT\n" +
            "\te.empno as empNo,\n" +
            "\te.`name` as name,\n" +
            "\tdate_format(ld.beginleave, '%Y-%m-%d %h:%i:%s')  as beginleaveStr,\n" +
            "\tdate_format(ld.endleave, '%Y-%m-%d %h:%i:%s') as endleaveStr,\n" +
            "\tld.leavelong,\n" +
            "\tld.leaveDescrip\n" +
            "FROM\n" +
            "\tleavedata ld\n" +
            "LEFT JOIN employee e ON ld.employeeid = e.id\n" +
            "LEFT JOIN gongzhonghao g ON g.empNo = e.empno" +
            " where g.gongzhonghaoId = #{openId} " +
            "order by ld.id desc")
    List<Leave> findAllLeaveByWeiXinId(String openId);

    @Select("select e.`name` from gongzhonghao g LEFT JOIN employee e on e.empno = g.empNo\n" +
            "where gongzhonghaoId = #{openId} limit 1 ")
    String getNameByWeiXinId(String openId);

    @Delete("delete from clockinsetup where outDays = #{outDays}")
    void deleteClockSetInByOutDays(Double outDays);


    @Select("SELECT\n" +
            "\tid,\n" +
            "\tworkLevel,\n" +
            "\t`month`,\n" +
            "\tupdatedate as updateDate,\n" +
            "\tmorningon as morningOn,\n" +
            "\tmorningonfrom as morningOnFrom,\n" +
            "\tmorningonend as morningOnEnd,\n" +
            "\tmorningoff as morningOff,\n" +
            "\tmorningofffrom as morningOffFrom,\n" +
            "\tmorningoffend as morningOffEnd,\n" +
            "\tnoonon as noonOn,\n" +
            "\tnoononfrom as noonOnFrom,\n" +
            "\tnoononend as noonOnEnd,\n" +
            "\tnoonoff as noonOff,\n" +
            "\tnoonofffrom as noonOffFrom,\n" +
            "\tnoonoffend as noonOffEnd,\n" +
            "\textworkon as extworkon,\n" +
            "\textworkonfrom as extworkonFrom,\n" +
            "\textworkonend as extworkonEnd,\n" +
            "\textworkoff as extworkoff,\n" +
            "\tremark as remark\n" +
            "FROM\n" +
            "\tworkset\n" +
            "WHERE\n" +
            "\t workLevel = #{positionLevel} and month = #{yearMonth}")
    WorkSet getWorkSetByYearMonthAndPositionLevel(String yearMonth, String positionLevel);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tworkLevel,\n" +
            "\t`month`,\n" +
            "\tupdatedate as updateDateStr,\n" +
            "\tmorningon as morningOnStr,\n" +
            "\tmorningonfrom as morningOnFromStr,\n" +
            "\tmorningonend as morningOnEndStr,\n" +
            "\tmorningoff as morningOffStr,\n" +
            "\tmorningofffrom as morningOffFromStr,\n" +
            "\tmorningoffend as morningOffEndStr,\n" +
            "\tnoonon as noonOnStr,\n" +
            "\tnoononfrom as noonOnFromStr,\n" +
            "\tnoononend as noonOnEndStr,\n" +
            "\tnoonoff as noonOffStr,\n" +
            "\tnoonofffrom as noonOffFromStr,\n" +
            "\tnoonoffend as noonOffEndStr,\n" +
            "\textworkon as extworkonStr,\n" +
            "\textworkonfrom as extworkonFromStr,\n" +
            "\textworkonend as extworkonEndStr,\n" +
            "\textworkoff as extworkoffStr,\n" +
            "\tremark as remark\n" +
            "FROM\n" +
            "\tworkset\n" +
            "WHERE\n" +
            "\tid = #{id}")
    WorkSet getWorkSetById(Integer id);

    @Select("select * from dept where deptname = #{name}")
    Dept getDeptByName(String name);

    @Select("select count(*) from employee where name = #{name}")
    int checkEmployIsExsit(String name);

    @Select("select * from linshiemp where name = #{name}")
    LinShiEmp getLinShiEmpByName(String name);

    @Select("select * from linshiemp where id = #{name}")
    LinShiEmp getLinShiEmpById(Integer name);

    @Select("select" +
            " id,\n" +
            " NAME,\n" +
            "yearMonth,\n" +
            "date AS dateStr,\n" +
            "hours,\n" +
            "remark from linshihours where name = #{name} and date = #{dateStr} ")
    LinShiHours getLSHByNameAndDateStr(String name, String dateStr);

    @Update("update linshiemp set isQuit = 0 where name = #{name} ")
    void updateLinShiIsQuitExsit(String name);

    @Insert("insert into linshihours (NAME,\n" +
            "\tyearMonth,\n" +
            "\tdate,\n" +
            "\thours,\n" +
            "\tremark) values (#{name},#{yearMonth},#{dateStr},#{hours},#{remark})")
    void saveLinShiDateToMysql(LinShiHours lsh);


    @Update("update linshihours set hours = #{hours} where name = #{name} and date = #{dateStr} ")
    void updateLinShiDateToMysql(LinShiHours lsh);

    @Insert("insert into linshiemp (name," +
            "sex," +
            "deptId," +
            "positionId," +
            "incompdate," +
            "birthDay," +
            "ID_NO,\n" +
            "nativePla," +
            "homeAddr," +
            "nation," +
            "worktype," +
            "isQuit)" +
            "values (#{name},#{sex},#{deptId},#{positionId},#{incompdateStr},#{birthDayStr},#{ID_NO}," +
            "#{nativePla},#{homeAddr},#{nation},#{workType},#{isQuit})")
    void saveLinShiEmpByBean(LinShiEmp lse);


    @Select("select yearMonth from zhongkongbean group by yearMonth order by yearMonth desc")
    List<String> findAllZKYearMonthList();

    @Delete("delete from pinshijiabanbgs where id = #{id} ")
    void deletePinShiDateToMysql(Integer id);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLinShiByCondition")
    List<Employee> queryLinShiByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLinShiByConditionCount")
    int queryLinShiByConditionCount(Employee employee);

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tNAME AS NAME,\n" +
            "\tsex AS sex,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tn.positionLevel AS positionLevel,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo,\n" +
            "\tworktype AS workType,\n" +
            "\te.isQuit\n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id\n" +
            "\n" +
            "UNION \n" +
            "\n" +
            "SELECT\n" +
            "\te.id AS id,\n" +
            "\tNAME AS NAME,\n" +
            "\tsex AS sex,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tn.positionLevel AS positionLevel,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\t'' AS empNo,\n" +
            "\tworktype AS workType,\n" +
            "\te.isQuit\n" +
            "FROM\n" +
            "\tlinshiemp e\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id ")
    List<Employee> findAllEmployeeLinShiAll();


    @Select("select count(*) from employee where empno = #{empoyeeNo}")
    int checkEmployNoIsExsit(String empoyeeNo);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByCondition")
    List<Employee> queryEmployeeByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutClockInByCondition")
    List<Employee> queryOutClockInByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutClockInByConditionCount")
    int queryOutClockInByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryGongZhongHaoByCondition")
    List<Employee> queryGongZhongHaoByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryGongZhongHaoByConditionCount")
    int queryGongZhongHaoByConditionCount(Employee employee);


    @SelectProvider(type = PseronDaoProvider.class, method = "queryZhongKongByCondition")
    List<Employee> queryZhongKongByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryZhongKongByConditionCount")
    int queryZhongKongByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllZKAndOutDataCondition")
    List<Employee> findAllZKAndOutDataCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllZKAndOutDataConditionCount")
    int findAllZKAndOutDataConditionCount(Employee employee);


    @SelectProvider(type = PseronDaoProvider.class, method = "findAllLSZKAndOutDataCondition")
    List<Employee> findAllLSZKAndOutDataCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllLSZKAndOutDataConditionCount")
    int findAllLSZKAndOutDataConditionCount(Employee employee);


    @SelectProvider(type = PseronDaoProvider.class, method = "findAllKQBDataCondition")
    List<KQBean> findAllKQBDataCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllKQBDataConditionCount")
    int findAllKQBDataConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryZKOUTDataByCondition")
    List<Employee> queryZKOUTDataByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryZKOUTDataByConditionCount")
    int queryZKOUTDataByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryPSByCondition")
    List<PinShiJiaBanBGS> queryPSByCondition(PinShiJiaBanBGS pinShiJiaBanBGS);


    @SelectProvider(type = PseronDaoProvider.class, method = "queryPSByConditionCount")
    int queryPSByConditionCount(PinShiJiaBanBGS pinShiJiaBanBGS);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryKQBeanDataByCondition")
    List<KQBean> queryKQBeanDataByCondition(KQBean kqBean);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryKQBeanDataByConditionCount")
    int queryKQBeanDataByConditionCount(KQBean kqBean);

    @Select("select yearMonth from monthkqinfo group by yearMonth  order by yearMonth desc")
    List<String> getAllMKMonthList();

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeSalaryByCondition")
    List<Employee> queryEmployeeSalaryByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryWorkSetByCondition")
    List<WorkSet> queryWorkSetByCondition(WorkSet workSet);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryWorkSetByConditionCount")
    int queryWorkSetByConditionCount(WorkSet workSet);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLeaveByCondition")
    List<Leave> queryLeaveByCondition(Leave leave);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutByCondition")
    List<Out> queryOutByCondition(Out out);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutByConditionCount")
    int queryOutByConditionCount(Out out);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryQKByConditionCount")
    int queryQKByConditionCount(QianKa qianKa);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryQKByCondition")
    List<QianKa> queryQKByCondition(QianKa qianKa);


    @SelectProvider(type = PseronDaoProvider.class, method = "queryYBByConditionCount")
    int queryYBByConditionCount(YeBan yeBan);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryYBByCondition")
    List<YeBan> queryYBByCondition(YeBan yeBan);

    @Select("select * from dakapiancha")
    DaKaPianCha getDaKaPianCha();

    @Select("select * from qyweixinbd where userid = #{userId} limit 1 ")
    WeiXinUsrId getUserIdByUSerId(String userId);

    @Insert("INSERT into qyweixinbd (userid,name,empNo) values (#{userid},#{name},#{empNo})")
    void saveWeiXinUserIdByBean(WeiXinUsrId wx);


    @SelectProvider(type = PseronDaoProvider.class, method = "queryLBByConditionCount")
    int queryLBByConditionCount(LianBan lianBan);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLBByCondition")
    List<LianBan> queryLBByCondition(LianBan qianKa);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLSHByConditionCount")
    int queryLSHByConditionCount(LinShiHours linShiHours);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLSHByCondition")
    List<LinShiHours> queryLSHByCondition(LinShiHours linShiHours);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryJBByConditionCount")
    int queryJBByConditionCount(JiaBan jiaBan);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryJBByCondition")
    List<JiaBan> queryJBByCondition(JiaBan jiaBan);

    @SelectProvider(type = PseronDaoProvider.class, method = "checkBeginLeaveRight")
    int checkBeginLeaveRight(Leave leave);

    @SelectProvider(type = PseronDaoProvider.class, method = "getJiaBanDanByEmpIdAndFromDateAndEndDate")
    int getJiaBanDanByEmpIdAndFromDateAndEndDate(Integer empId, String extDateFromStr, String extDateEndStr);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLeaveByConditionCount")
    int queryLeaveByConditionCount(Leave leave);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByConditionCount")
    int queryEmployeeByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeSalaryByConditionCount")
    int queryEmployeeSalaryByConditionCount(Employee employee);


    @Select("select * from position order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> findAllPosition(Position position);

    @Select("select * from clockinsetup order by outDays asc ")
    List<ClockInSetUp> findAllOutClockInSetUp();

    @Select("select count(*) from gongzhonghao where empNo = #{empNo}")
    int getGongZhongHaoByEmpNo(String empNo);

    @Select("select count(*) from qyweixinbd where empNo = #{empNo}")
    int getZhongKongByEmpNo(String empNo);

    @Select("select count(*) from qyweixinbd where userid = #{EnrollNumber}")
    int getBeanByEnrollNumber(String EnrollNumber);


    @Insert("INSERT into gongzhonghao (empNo,gongzhonghaoId)\n" +
            " values(#{empNo},#{gongzhonghaoId})\n")
    void saveGongZhongHaoByBean(GongZhongHao gongZhongHao);


    @Insert("INSERT into qyweixinbd (name,userid,empNo)\n" +
            " values(#{name},#{userid},#{empNo})\n")
    void saveZhongKongByBean(WeiXinUsrId zhongKongEmployee);


    @Delete("delete from gongzhonghao where empNo = #{empNo} ")
    void deleteGongZhongHaoByEmpNo(String empNo);

    @Delete("delete from qyweixinbd where  empNo = #{name} ")
    void deleteZhongKongByEmpNo(String name);

    @Update(" update gongzhonghao set EnrollNumber = #{EnrollNumber} where empNo = #{empNo} ")
    void updateGongZhongHaoByEmpNo(GongZhongHao gongZhongHao);


    @Update(" update qyweixinbd set userid = #{userid} where empNo = #{empNo} ")
    void updateZhongKongByEmpNo(WeiXinUsrId zhongKongEmployee);

    @Select("select * from dept order by deptname desc limit #{currentPageTotalNum},#{pageSize} ")
    List<Dept> findAllDept(Dept dept);

    @Select("select deptName from dept order by deptname desc ")
    List<String> findAllDeptA();

    @Select("select * from position order by positionName desc")
    List<Position> findAllPositionAll();

    @Select("select positionName from position order by positionName desc")
    List<String> findAllPositionAllA();

    @Select("select * from dept order by deptname desc")
    List<Dept> findAllDeptAll();

    @Insert("insert into employee (name,sex,deptId,empno,positionId,incompdate,conExpDate,birthDay,ID_NO,\n" +
            "nativePla,homeAddr,valiPeriodOfID,nation,marriaged,contactPhone,educationLe,educationLeUrl,\n" +
            "screAgreement,healthCerti,sateListAndLeaCerti,sateListAndLeaCertiUrl,otherCerti,otherCertiUrl,positionAttrId,isQuit)" +
            "values (#{name},#{sex},#{deptId},#{empNo},#{positionId},#{incomdateStr},#{conExpDateStr},#{birthDayStr},#{ID_NO}," +
            "#{nativePla},#{homeAddr},#{valiPeriodOfIDStr},#{nation},#{marriaged},#{contactPhone},#{educationLe},#{educationLeUrl}," +
            "#{screAgreement},#{healthCerti},#{sateListAndLeaCerti},#{sateListAndLeaCertiUrl},#{otherCerti},#{otherCertiUrl},#{positionAttrId},#{isQuit})")
    void addEmployeeData(Employee employee);


    @Insert("\n" +
            "INSERT into salary (empno,compresalary,possalary,jobsalary,meritsalary)\n" +
            " values(#{empNo},#{compSalary},#{posiSalary},#{jobSalary},#{meritSalary})\n")
    void addSalaryData(String empNo, Double compSalary, Double posiSalary, Double jobSalary, Double meritSalary);

    @Update(" update salary set compresalary = #{compSalary},possalary=#{posiSalary},jobsalary=#{jobSalary},meritsalary = #{meritSalary}\n" +
            " where empno = #{empNo}\n")
    void updateSalaryData(String empNo, Double compSalary, Double posiSalary, Double jobSalary, Double meritSalary);

    @Select("SELECT\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\te.otherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo," +
            "e.isQuit," +
            "h.gongzhonghaoId " +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id " +
            " left join gongzhonghao h on e.empno = h.empNo " +
            "\t\tORDER BY\n" +
            "\t\t\te.empno asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployee(Employee employee);


    @Select("SELECT\t" +
            "e. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.nation,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "e.isQuit" +
            "\t\tFROM\n" +
            "\t\t\tlinshiemp e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id " +
            "\t\tORDER BY\n" +
            "\t\t\te.name asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllLinShiEmp(Employee employee);


    @Select("select count(*) from linshiemp ")
    int findAllLinShiEmpCount();


    @Select("SELECT\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\te.otherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo," +
            "e.isQuit," +
            "h.userid as EnrollNumber " +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id " +
            " left join qyweixinbd h on e.empNo = h.empNo where e.isQuit = 0 " +
            "\t\tORDER BY\n" +
            "\t\t\te.empno asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployeeZhongKong(Employee employee);


    @Select("SELECT\n" +
            "\to.id,\n" +
            "\tod.date,\n" +
            "\te. NAME,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\to.clockInDate AS clockInDateStr,\n" +
            "\to.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\to.clockInAddrAMOn,\n" +
            "  od.id as outDanId,\n" +
            "\tCASE\n" +
            "WHEN o.amOnUrl IS NULL THEN\n" +
            "\t0\n" +
            "WHEN o.amOnUrl IS NOT NULL THEN\n" +
            "\t1\n" +
            "END AS amOnUrlInt,\n" +
            " o.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            " o.clockInAddrPMOn,\n" +
            " CASE\n" +
            "WHEN o.pmOnUrl IS NULL THEN\n" +
            "\t0\n" +
            "WHEN o.pmOnUrl IS NOT NULL THEN\n" +
            "\t1\n" +
            "END AS pmOnUrlInt,\n" +
            " o.clockInDateNMOn AS clockInDateNMOnStr,\n" +
            " o.clockInAddNMOn,\n" +
            " CASE\n" +
            "WHEN o.nmOnUrl IS NULL THEN\n" +
            "\t0\n" +
            "WHEN o.nmOnUrl IS NOT NULL THEN\n" +
            "\t1\n" +
            "END AS nmOnUrlInt\n" +
            "FROM\n" +
            "\toutclockin o\n" +
            "LEFT JOIN qyweixinbd g ON g.userid = o.userid\n" +
            "LEFT JOIN employee e ON e.empNo = g.empNo\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id\n" +
            "LEFT JOIN outdan od ON od.empNo = e.empno  and od.date = o.clockInDate\n" +
            "ORDER BY\n" +
            "\te.empno ASC " +
            " limit #{currentPageTotalNum},#{pageSize} ")
    List<Employee> findAllEmployeeOutClockIn(Employee employee);


    @Select("SELECT count(*)  " +
            "FROM\n" +
            "\toutclockin o\n" +
            "LEFT JOIN qyweixinbd g ON g.userid = o.userid\n" +
            "LEFT JOIN employee e ON e.empNo = g.empNo\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id\n" +
            "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
            "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
            "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d') ")
    int findAllEmployeeOutClockInCount(Employee employee);

    @Select("SELECT\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\te.otherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo," +
            "\tIFNULL(s.compreSalary,0) AS compreSalary," +
            "\tIFNULL(s.posSalary,0) AS posSalary," +
            "\tIFNULL(s.jobSalary,0) AS jobSalary," +
            "\tIFNULL(s.meritSalary,0) AS meritSalary," +
            "\ts.id AS salaryId, " +
            "\ts.state AS state,e.isQuit " +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id  left join salary s on e.empno = s.empno  \n" +
            "\t\tORDER BY\n" +
            "\t\t\te.empno asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployeeFinance(Employee employee);

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  e.empno as empNo,worktype as workType,e.isQuit \n" +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id\n" +
            "\t\t    ORDER BY\n" +
            "\t\t\tNAME ASC ")
    List<Employee> findAllEmployeeAll();


    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  '' as empNo," +
            "e.worktype as workType," +
            "e.isQuit \n" +
            " FROM " +
            " linshiemp e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id \n" +
            "    ORDER BY\n" +
            " NAME ASC ")
    List<Employee> findAllEmployeeAllLinShi();

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  '' as empNo," +
            "e.worktype as workType," +
            "e.isQuit \n" +
            " FROM " +
            " linshiemp e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id where e.workType = 3 \n" +
            "    ORDER BY\n" +
            " NAME ASC ")
    List<Employee> findAllEmployeeAllZhongDian();


    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "e.worktype as workType," +
            "e.isQuit \n" +
            "\t\tFROM\n" +
            " linshiemp e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id\n" +
            "\t\t    ORDER BY\n" +
            "\t\t\tNAME ASC ")
    List<Employee> findAllLinShiEmpAll();


    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  e.empno as empNo,worktype as workType,e.isQuit \n" +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id\n" +
            "\t\t  where e.worktype = 1   ORDER BY\n" +
            "\t\t\tNAME ASC ")
    List<Employee> findAllEmployeeAllOnlyBanGong();

    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel}")
    WorkDate getWorkDateByMonthAnPositionLevel(String month, String positionLevel);

    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel} or (month = #{month} and type = 2)")
    List<WorkDate> getWorkDateByMonthAnPositionLevelList(String month, String positionLevel);


    @Select("select" +
            " id,\n" +
            " NAME,\n" +
            "yearMonth,\n" +
            "date AS dateStr,\n" +
            "hours,\n" +
            "remark from linshihours where name = #{name} and date = #{dateStr} ")
    LinShiHours getLinShiHoursByNameAndDateStr(String name, String dateStr);


    @Select("SELECT\n" +
            "e.empNo,e.name \n" +
            "FROM\n" +
            "\temployee e\n" +
            "JOIN position n ON e.positionId = n.id\n" +
            "AND n.positionLevel = #{positionLevel} ")
    List<SmallEmployee> findAllEmployeeByPositionLevel(String positionLevel);


    @Select("select group_concat(workDate,',') as workDate from workdate where month = #{month} and positionLevel = #{positionLevel} and type in(0,1) ")
    WorkDate getWorkDateByMonthAnPositionLevelandType(String month, String positionLevel);

    @Select("SELECT\n" +
            "\tifnull(ee.`name`,ls.name) as name,\n" +
            "\tifnull(ee.empno,'') as empno,\n" +
            "\tt.deptname,\n" +
            "\tn.positionName,\n" +
            "\tzk.EnrollNumber AS enrollNumber,\n" +
            "\tzk.Date AS dateStr,\n" +
            "\tzk.timeStr,\n" +
            "\tzk.machineNum,\n" +
            "\tzk.yearMonth\n" +
            "FROM\n" +
            "\tzhongkongbean zk\n" +
            "LEFT JOIN  qyweixinbd zke ON zk.EnrollNumber = zke.userid\n" +
            "LEFT JOIN employee ee ON ee.empno = zke.empNo\n" +
            "LEFT JOIN linshiemp ls ON ls.name = zke.name\n" +
            "LEFT JOIN dept t ON t.id = ee.deptId\n" +
            "LEFT JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tzk.date = #{date}\n" +
            "ORDER BY\n" +
            "\tzk.EnrollNumber ASC")
    List<KQBean> getAllKQDataByYearMonthDay(String date);


    @Select("SELECT\n" +
            "\tzk.EnrollNumber AS enrollNumber,\n" +
            "\tzk.Date AS dateStr,\n" +
            "\tzk.timeStr,\n" +
            "\tzk.machineNum,\n" +
            "\tzk.yearMonth\n" +
            "FROM\n" +
            "\tzhongkongbean zk " +
            "WHERE\n" +
            "\tzk.date = #{date}\n" +
            "ORDER BY\n" +
            "\tzk.EnrollNumber ASC")
    List<ZhongKongBean> getAllKQDataByYearMonthDayA(String date);


    @SelectProvider(type = PseronDaoProvider.class, method = "getAllKQDataByYearMonthDays")
    List<KQBean> getAllKQDataByYearMonthDays(@Param("dateStr") List<OutClockIn> dateStr);

    @Select("select * from workdate where month = #{month}")
    List<WorkDate> findAllWorkDateListByMonth(String month);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryPositionByNameA")
    List<Position> queryPositionByNameA(Position position);

    @Select("select * from workset order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}  ")
    List<WorkSet> findAllWorkSet(WorkSet workSet);

    @Select("select count(*) from workset ")
    int findAllWorkSetCount(WorkSet workSet);

    @Select("select count(*) from employee ")
    int findAllEmployeeCount();

    @Select("select count(*) from employee ")
    int findAllEmployeeZhongKongCount();

    @Select("select * from userinfo  where empno = #{empNo} limit 1 ")
    UserInfo getUserInfoByEmpno(String empNo);


    @Select("select *,#{currentPage} as currentPage from dept where deptname like  CONCAT('%',#{deptname},'%') order by deptname desc limit #{currentPageTotalNum},#{pageSize}")
    List<Dept> queryDeptByNameA(Dept dept);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllPositionByConditionCount")
    int findAllPositionByConditionCount(Position position);

    @Insert("insert into zhongkongbean (EnrollNumber,Date,timeStr,yearMonth,machineNum) values(#{EnrollNumber},#{dateStr},#{timeStr},#{yearMonth},#{machineNum})")
    void saveBeforeDayZhongKongData(ZhongKongBean zkb);

    @Select("SELECT\n" +
            "\tzk.yearMonth,\n" +
            "\tzk.EnrollNumber AS enrollNumber1,\n" +
            "\tzk.dateStr as dateStr,\n" +
            "\tzk.timeStr,\n" +
            "\tee.`name`,\n" +
            "\tt.deptname\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tzk.yearMonth,\n" +
            "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
            "\t\t\tzk.Date AS dateStr,\n" +
            "\t\t\tzk.timeStr\n" +
            "\t\tFROM\n" +
            "\t\t\tzhongkongbean zk where (EnrollNumber REGEXP '[^0-9.]') <> 0 " +
            "\t) zk \n" +
            "  JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
            " JOIN employee ee ON zke.empNo = ee.empNo\n" +
            "  JOIN dept t ON ee.deptid = t.id\n" +
            "ORDER BY\n" +
            "\tzk.yearMonth desc,\n" +
            "\tzk.EnrollNumber asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllZKAndOutData(Employee employee);


    @Select("SELECT\n" +
            "\tzk.yearMonth,\n" +
            "\tzk.EnrollNumber AS enrollNumber1,\n" +
            "\tzk.dateStr as dateStr,\n" +
            "\tzk.timeStr,\n" +
            "\tee.`name`,\n" +
            "\tt.deptname\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tzk.yearMonth,\n" +
            "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
            "\t\t\tzk.Date AS dateStr,\n" +
            "\t\t\tzk.timeStr\n" +
            "\t\tFROM\n" +
            "\t\t\tzhongkongbean zk where (EnrollNumber REGEXP '[^0-9.]') <> 0\n" +
            "\t\tORDER BY\n" +
            "\t\t\tzk.yearMonth DESC,\n" +
            "\t\t\tzk.EnrollNumber ASC \n" +
            "\t) zk \n" +
            "  JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
            " JOIN linshiemp ee ON zke.name = ee.name\n" +
            "  JOIN dept t ON ee.deptid = t.id\n" +
            "ORDER BY\n" +
            "\tzk.yearMonth desc,\n" +
            "\tzk.EnrollNumber asc  limit #{currentPageTotalNum},#{pageSize} ")
    List<Employee> findAllLSZKAndOutData(Employee employee);


    @Select("SELECT count(*) " +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tzk.yearMonth,\n" +
            "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
            "\t\t\tzk.Date AS dateStr,\n" +
            "\t\t\tzk.timeStr\n" +
            "\t\tFROM\n" +
            "\t\t\tzhongkongbean zk where (EnrollNumber REGEXP '[^0-9.]') <> 0 " +
            "\t) zk \n" +
            "  JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
            " JOIN employee ee ON zke.empNo = ee.empNo ")
    int findAllZKAndOutDataCount();


    @Select("SELECT\n" +
            "\t count(*) \n" +
            "FROM\n" +
            "\tzhongkongbean zk\n" +
            "JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
            "JOIN linshiemp ee ON zke.NAME = ee.NAME\n" +
            "WHERE\n" +
            "\t(\n" +
            "\t\tzk.EnrollNumber REGEXP '[^0-9.]'\n" +
            "\t) <> 0 ")
    int findAllLSZKAndOutDataCount();


    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%') ")
    int findAllDeptByConditionCount(Dept dept);

    @Delete("delete from position where id = #{id}")
    void deletePositionById(Integer id);

    @Delete("delete from leavedata where id = #{id}")
    void deleteLeaveById(Integer id);

    @Delete("delete from workset where id = #{id}")
    void deleteWorkSetById(WorkSet workSet);

    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\te.`name` as name,\n" +
            "\te.empno as empNo,\n" +
            "\te.sex as sex,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') as incomdateStr,\n" +
            "\ta.beginleave as beginLeaveStr,\n" +
            "\ta.endleave as endLeaveStr,\n" +
            "\ta.leavelong as leaveLong,\n" +
            "\ta.leaveDescrip as leaveDescrip,\n" +
            "\ta.remark as remark,\n" +
            "\ta.type as type\n" +
            "FROM\n" +
            "\tleavedata a\n" +
            "LEFT JOIN employee e ON a.employeeid = e.id\n" +
            "LEFT JOIN position n ON n.id = e.positionId\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            " where a.id = #{id} " +
            "ORDER BY\n" +
            "\te.name DESC")
    Leave getLeaveById(Integer id);

    @Select("select  GROUP_CONCAT(positionName) from position where positionLevel = #{positionLevel} ")
    String getPositionNamesByPositionLevel(String positionLevel);

    @Delete("delete from dept where id = #{id}")
    void deleteDeptById(Integer id);


    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel} " +
            " and type = #{type} ")
    WorkDate getWorkDateByMonth(WorkDate workDate);

    @Select("select * from workdate where month = #{yearMonth} and positionLevel = #{levelPosition} " +
            " and type = #{type} ")
    WorkDate addWorkDateByMonth3(String yearMonth, String levelPosition, Integer type);


    @Select("select *,GROUP_CONCAT(workdate) from workdate where month = #{month} and positionLevel = #{positionLevel} " +
            " limit 1 ")
    WorkDate getWorkDateByMonth2(WorkDate workDate);

    @Insert("insert into dept (deptname) values(#{deptName})")
    void addDeptByDeptName(String deptName);

    @Insert("insert into position (positionName) values(#{positionName})")
    void addPositionByName(String positionName);

    @Update("update employee set isQuit = 1 ")
    void updateAllEmployeeNotExist();

    @Update("update employee set isQuit = 0 where empNo = #{empNo} ")
    void updateEmployeeIsQuitExsit(String empNo);

    @Insert("insert into position (positionName,positionLevel) values(#{positionName},#{positionLevel})")
    void addPositionByNameandPositionLevel(String positionName, String positionLevel);

    @Update("TRUNCATE table dept")
    void clearDeptData();

    @Update("TRUNCATE table position")
    void clearPositionData();

    @Update("TRUNCATE table employee")
    void clearEmployeeData();

    @Select("select * from position where positionName = #{name} ")
    Position getPositionByName(String name);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevelA(String month, String positionLevel);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevel2(String month, String positionLevel);

    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s') as beginLeaveStr ,date_format(endleave, '%Y-%m-%d %h:%i:%s') as endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type  " +
            "from leavedata where employeeid = #{employeeId} and  beginleave<= #{dataStrStart} and endleave>= #{dataEnd} limit 1 ")
    Leave getLeaveByEmIdAndMonth(Integer employeeId, String dataStrStart, String dataEnd);

    @Select("SELECT\n" +
            "\tmk.*, ee.`name` AS nameReal\n" +
            "FROM\n" +
            "\tmonthkqinfo mk\n" +
            "LEFT JOIN employee ee ON ee.empno = mk.empNo" +
            " where mk.empNo = #{empNo}  and mk.yearMonth = #{yearMonth} limit 1 ")
    MonthKQInfo getMonthKqInfoByEmpNoandYearMonth(String empNo, String yearMonth);


    @Select("SELECT\n" +
            "\tmk.*, ee.`name` AS nameReal\n" +
            "FROM\n" +
            "\tmonthkqinfo mk\n" +
            "LEFT JOIN employee ee ON ee.empno = mk.empNo" +
            " where  mk.yearMonth = #{yearMonth} ")
    List<MonthKQInfo> getMonthKqInfoListByEmpNoandYearMonth(String yearMonth);


    @Select("SELECT\n" +
            "\tjb.empNo,\n" +
            "\tjb.remark,\n" +
            "\tjb.type,\n" +
            "\tee.`name`,\n" +
            "\tdate_format(\n" +
            "\t\tjb.extDateFrom,\n" +
            "\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t) as extDateFrom,\n" +
            "\tdate_format(\n" +
            "\t\tjb.extDateEnd,\n" +
            "\t\t'%Y-%m-%d %H:%i:%s'\n" +
            "\t) as extDateEnd \n" +
            "FROM\n" +
            "\tjiaban jb\n" +
            "LEFT JOIN employee ee ON jb.empNo = ee.empno\n" +
            "WHERE\n" +
            "\tdate_format(jb.extDateFrom, '%Y-%m-%d') <= #{dateStr}\n" +
            "AND date_format(jb.extDateEnd, '%Y-%m-%d') >= #{dateStr}")
    List<JiaBan> getJiaBanDanByDateStr(String dateStr);

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "\tee.empno,\n" +
            "\tzk.Date AS dateStr,\n" +
            "\tzk.yearMonth,\n" +
            "\tnn.positionLevel,\n" +
            "\tee.workType,\n" +
            "\tzk.timeStr\n" +
            "FROM\n" +
            "\tzhongkongbean zk\n" +
            "LEFT JOIN qyweixinbd zke ON zke.userid = zk.EnrollNumber\n" +
            "LEFT JOIN employee ee ON zke.empNo = ee.empno\n" +
            "LEFT JOIN position nn ON nn.id = ee.positionid\n" +
            "WHERE\n" +
            "\tee.empno = #{empNo} and Date = #{dateStr} ")
    ZhongKongBean getZKBeanByEmpNoAndDateStr(String empNo, String dateStr);


    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "\t'' as ee.empno,\n" +
            "\tzk.Date AS dateStr,\n" +
            "\tzk.yearMonth,\n" +
            "\tnn.positionLevel,\n" +
            "\tee.workType,\n" +
            "\tzk.timeStr\n" +
            "FROM\n" +
            "\tzhongkongbean zk\n" +
            "LEFT JOIN qyweixinbd zke ON zke.userid = zk.EnrollNumber\n" +
            "LEFT JOIN linshiemp ee ON zke.name = ee.name\n" +
            "LEFT JOIN position nn ON nn.id = ee.positionid\n" +
            "WHERE\n" +
            "\tee.name = #{name} and Date = #{dateStr} ")
    ZhongKongBean getZKBeanByEmpNoAndDateStrLinShi(String empNo, String dateStr);

    @Select("select * from zhongkongbean where enrollNumber = #{num} and date = #{date}")
    ZhongKongBean getZhongKongBeanByNumAndDate(String num, String date);

    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s" +
            "') as beginLeaveStr,date_format(endleave, '%Y-%m-%d %h:%i:%s') endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type " +
            "  from leavedata where employeeid = #{employeeId} and beginleave <= #{dataStr} and endleave >= #{dataStr} limit 1 ")
    Leave getLeaveByEmIdAndMonthA(Integer employeeId, String dataStr);

    @Select("select a.employeeid as employeeId," +
            "date_format(a.beginleave, '%Y-%m-%d %h:%i:%s" +
            "') as beginLeaveStr," +
            "date_format(a.endleave, '%Y-%m-%d %h:%i:%s') endLeaveStr," +
            "a.leavelong as leaveLong," +
            "a.leaveDescrip," +
            "a.remark," +
            "a.type " +
            "  from leavedata a" +
            "  join employee e on e.id = a.employeeid " +
            " where " +
            "e.empNo = #{empNo} and a.beginleave <= #{dataStr} and a.endleave >= #{dataStr} limit 1 ")
    Leave getLeaveByEmIdAndMonthAB(String empNo, String dataStr);

    @Select("select date_format(a.incompdate, '%Y-%m-%d" +
            "') as incompdate " +
            "  from linshiemp a" +
            " where " +
            "a.name = #{name} limit 1 ")
    String getLinShiInComDateByName(String name);

    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s" +
            "') as beginLeaveStr,date_format(endleave, '%Y-%m-%d %h:%i:%s') endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type " +
            "  from leavedata where employeeid = #{empId} and beginleave <= #{leaveFrom} and endleave >= #{leaveEnd} limit 1 ")
    Leave getLeaveByEmpIdAndDateStr(String leaveFrom, String leaveEnd, Integer empId);


    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s" +
            "') as beginLeaveStr,date_format(endleave, '%Y-%m-%d %h:%i:%s') endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type " +
            "  from leavedata where employeeid = #{empId} and beginleave <= #{leaveFrom} and endleave >= #{leaveFrom} limit 1 ")
    Leave getLeaveByEmpIdAndDateStrABC(String leaveFrom, Integer empId);


    @Select("SELECT\n" +
            "\toc.id,\n" +
            "\toc.weixinNo,\n" +
            "\toc.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNmon AS clockInDateNmonStr,\n" +
            "\toc.clockInAddNMOn,\n" +
            "\toc.nmOnUrl,\n" +
            "\tclockInDate AS clockInDateStr\n" +
            "\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "LEFT JOIN gongzhonghao gg ON gg.empNo = ee.empno\n" +
            "LEFT JOIN outclockin oc ON oc.weixinNo = gg.gongzhonghaoId\n" +
            "WHERE\n" +
            "\toc.clockInDate = #{yearMonthDay} \n" +
            "AND ee.id = #{employeeId} ")
    OutClockIn getOutClockInByEmpNoandDate(Integer employeeId, String yearMonthDay);

    @Insert("insert into monthkqinfo (" +
            "  empNo,\n" +
            "\t`name`,\n" +
            "\tyearMonth,\n" +
            "\tremark," +
            "${daytitleSql}," +
            "${daytitleSqlRemark}" +
            ") values (" +
            " #{empNo}," +
            " #{nameReal}," +
            " #{yearMonth}," +
            " #{remark}," +
            " #{dayNum}, " +
            " #{dayNumRemark} " +
            ")")
    void saveMonthKQInfoByCheckKQBean(MonthKQInfo mk);


    @Update("update monthkqinfo   " +
            " set remark = #{remark}, " +
            "  ${daytitleSql} = #{dayNum}, " +
            "  ${daytitleSqlRemark} = #{dayNumRemark} " +
            "where yearMonth = #{yearMonth} and empNo = #{empNo}")
    void updateMonthKQInfoByCheckKQBean(MonthKQInfo mk);


    @Insert("insert into kqbean (enrollNumber,\n" +
            "\tyearMonth,\n" +
            "\tdateStr,\n" +
            "\t`week`,\n" +
            "\ttimeStr,\n" +
            "\tclockResult,\n" +
            "\textWorkHours,\n" +
            "\tremark,\n" +
            "\taOnTime,\n" +
            "\taOffTime,\n" +
            "\tpOnTime,\n" +
            "\tpOffTime,\n" +
            "\textWorkOnTime,\n" +
            "\textWorkOffTime,\n" +
            "\trensheCheck) values (" +
            "#{enrollNumber},\n" +
            "\t#{yearMonth},\n" +
            "\t#{dateStr},\n" +
            "\t#{week},\n" +
            "\t#{timeStr},\n" +
            "\t#{clockResult},\n" +
            "\t#{extWorkHours},\n" +
            "\t#{remark},\n" +
            "\t#{aOnTime},\n" +
            "\t#{aOffTime},\n" +
            "\t#{pOnTime},\n" +
            "\t#{pOffTime},\n" +
            "\t#{extWorkOnTime},\n" +
            "\t#{extWorkOffTime},\n" +
            "\t#{rensheCheck})")
    void saveAllNewKQBeansToMysql(KQBean kqBean);


    @Select("SELECT\n" +
            "\tkq.id,\n" +
            "\tifnull(ee.`name`,ls.name) AS NAME,\n" +
            "\tdeptname AS deptName,\n" +
            "\tkq.enrollNumber,\n" +
            "\tkq.yearMonth,\n" +
            "\tkq.dateStr,\n" +
            "\tkq.`week`,\n" +
            "\tkq.timeStr,\n" +
            "\tIFNULL(\n" +
            "\t\tkq.clockResultByRenShi,\n" +
            "\t\tkq.clockResult\n" +
            "\t) AS clockResult,\n" +
            "\tkq.extWorkHours,\n" +
            "\tkq.remark,\n" +
            "\tkq.aOnTime,\n" +
            "\tkq.aOffTime,\n" +
            "\tkq.pOnTime,\n" +
            "\tkq.pOffTime,\n" +
            "\tkq.extWorkOnTime,\n" +
            "\tkq.extWorkOffTime,\n" +
            "\tkq.rensheCheck\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\t*\n" +
            "\t\tFROM\n" +
            "\t\t\tkqbean\n" +
            "\t\tORDER BY\n" +
            "\t\t\tyearMonth DESC,\n" +
            "\t\t\tenrollNumber ASC\n" +
            "\t\t  limit #{currentPageTotalNum},#{pageSize} " +
            "\t) AS kq\n" +
            "LEFT JOIN qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
            "LEFT JOIN employee ee ON ee.empno = zke.empNo\n" +
            "LEFT JOIN linshiemp ls ON ls.name = zke.name\n" +
            "LEFT JOIN dept t ON t.id = ee.deptId")
    List<KQBean> findAllKQBData(Employee employee);

    @Select("SELECT\n" +
            "\ta.date \n" +
            "FROM\n" +
            "\tzhongkongbean a\n" +
            "where\n" +
            "\ta.yearMonth  = (\n" +
            "\t\tSELECT\n" +
            "\t\t\tyearMonth\n" +
            "\t\tFROM\n" +
            "\t\t\tzhongkongbean\n" +
            "\t\tORDER BY\n" +
            "\t\t\tdate DESC\n" +
            "\t\tLIMIT 1\n" +
            "\t)\n" +
            "group by Date asc")
    List<String> getAllKQDateList();

    @Select("SELECT count(*) " +
            "FROM\n" +
            "\tkqbean kq  ")
    int findAllKQBDataCount();

    @Update("update kqbean set extWorkHours = #{extHours},clockResultByRenShi = #{state} where id = #{id}")
    void updateKQBeanDataByRenShi(Integer id, Double extHours, Integer state);

    @Select("SELECT\n" +
            "\tmk.*, t.deptname\n" +
            "FROM\n" +
            "\tmonthkqinfo mk\n" +
            "LEFT JOIN employee ee ON mk.empNo = ee.empno\n" +
            "LEFT JOIN dept t  ON t.id = ee.deptId where yearMonth = #{yearMonth} order by ee.empNo  limit #{currentPageTotalNum},#{pageSize}  ")
    List<MonthKQInfo> findAllMonthKQData(String yearMonth, int currentPageTotalNum, int pageSize);

    @Select("SELECT count(*)" +
            "FROM\n" +
            "\tmonthkqinfo \n" +
            " where yearMonth = #{yearMonth} ")
    int findAllMonthKQDataCount(String yearMonth);

    @Select("select yearMonth from monthkqinfo group by yearMonth")
    List<String> getAllKQMonthList();

    @Select("select name from linshiemp where workType = 3")
    List<String> getAllZhongDianName();


    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByCondition")
    List<MonthKQInfo> findAllMonthKQDataByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByConditionABC")
    List<MonthKQInfo> findAllMonthKQDataByConditionABC(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByConditionBDF")
    List<MonthKQInfo> findAllMonthKQDataByConditionBDF(Employee employee);

    @UpdateProvider(type = PseronDaoProvider.class, method = "updateRenShiByDates")
    void updateRenShiByDates(@Param("dateStrs") List<String> dateStrs);

    @Select("SELECT\n" +
            "\taf.*\n" +
            "FROM\n" +
            "\tlinshiemp em\n" +
            "LEFT JOIN accufund af ON em.`name` = af.`name`\n" +
            "where af.name = #{name}  limit 1 ")
    AccuFund getAFByName(String name);

    @Select("select name from employee where empNo = #{empNo} ")
    String getNameByEmpNo(String empNo);


    @Select("delete from tiaoxiu where id = #{id} ")
    void deleteTiaoXiuById(Integer id);

    @Insert("insert into tiaoxiu (\tempNo,\n" +
            "\t`name`,\n" +
            "\tfromDate,\n" +
            "\thours,\n" +
            "\ttoDate,\n" +
            "\ttype,\n" +
            "\tusaged,\n" +
            "\tremark,\n" +
            "\ttotalHours) values " +
            "(" +
            "#{empNo}," +
            "#{name}," +
            "#{fromDateStr}," +
            "#{hours}," +
            "#{toDateStr}," +
            "#{type}," +
            "#{usaged}," +
            "#{remark}," +
            "#{totalHours}" +
            ")")
    int saveTiaoXiuDateToMysql(TiaoXiu tiaoXiu);

    @Select("SELECT\n" +
            "\taf.*\n" +
            "FROM\n" +
            "\tlinshiemp em\n" +
            "LEFT JOIN insurance af ON em.`name` = af.`name`\n" +
            "where em.name = 'ABC'  limit 1  ")
    Insurance getISByName(String name);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByConditionCount")
    int findAllMonthKQDataCountByCondition(Employee employee);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tfromDate as fromDateStr,\n" +
            "\thours,\n" +
            "\ttotalHours,\n" +
            "\ttoDate as toDateStr,\n" +
            "\tfromDateWeek,\n" +
            "\ttoDateWeek,\n" +
            "\ttype,\n" +
            "\tUSAGEd,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\ttiaoxiu order by fromDate desc,name asc limit #{currentPageTotalNum},#{pageSize}")
    List<TiaoXiu> findAllTiaoXiu(TiaoXiu tiaoXiu);

    @Select("select count(*) from tiaoxiu ")
    int findAllTiaoXiuCount();

    @SelectProvider(type = PseronDaoProvider.class, method = "deleteKQBeanByDateStrs")
    List<KQBean> deleteKQBeanByDateStrs(@Param("dateStrs") List<OutClockIn> dateStrs);

    @Select("SELECT\n" +
            "\t(totalHours - hours) as hours\n" +
            "FROM\n" +
            "\ttiaoxiu\n" +
            "WHERE\n" +
            "\tempNo = #{empNo} \n" +
            "AND fromDate = #{fromDateStr} \n" +
            "ORDER BY\n" +
            "\tid DESC limit 1")
    TiaoXiu getOldTiaoXiuDanByEmpNoandDateStr(TiaoXiu tiaoXiu);

    @Select("select ${titleDate} as empNo,${titleDateRemark} as remark from monthkqinfo where empNo = #{empNo} and yearMonth = #{yearMonth}  ")
    MonthKQInfo getMKbyEmpNoAndYearMonth(String empNo, String yearMonth, String titleDate, String titleDateRemark);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByCondition")
    List<MonthKQInfo> queryMKDataByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryMKDataByConditionCount")
    int queryMKDataByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "getAllYeBanByDateStrs")
    List<YeBan> getAllYeBanByDateStrs(@Param("dateStrs") List<OutClockIn> outClockInList);

    @DeleteProvider(type = PseronDaoProvider.class, method = "deleteKQBeanOlderDateByDates")
    void deleteKQBeanOlderDateByDates(@Param("dateStrs") List<OutClockIn> dateStrs);

    @UpdateProvider(type = PseronDaoProvider.class, method = "saveCheckKQBeanListByDates")
    void saveCheckKQBeanListByDates(@Param("dateStrs") List<OutClockIn> dateStrs);

    @SelectProvider(type = PseronDaoProvider.class, method = "getAlReadyCheckDatestr")
    String getAlReadyCheckDatestr(@Param("dateStrs") List<OutClockIn> dateStrs);

    @Insert("insert into employee (name,sex,deptId,empno,positionId,incompdate,conExpDate,birthDay,ID_NO,\n" +
            "nativePla,homeAddr,valiPeriodOfID,nation,marriaged,contactPhone,educationLe,\n" +
            "screAgreement,healthCerti,sateListAndLeaCerti,otherCerti,positionAttrId,worktype,isQuit)" +
            "values (#{name},#{sex},#{deptId},#{empNo},#{positionId},#{incompdateStr},#{conExpDateStr},#{birthDayStr},#{ID_NO}," +
            "#{nativePla},#{homeAddr},#{valiPeriodOfIDStr},#{nation},#{marriaged},#{contactPhone},#{educationLe}," +
            "#{screAgreement},#{healthCerti},#{sateListAndLeaCerti},#{otherCerti},#{positionAttrId},#{workType},#{isQuit})")
    void saveEmployeeByBean(Employee employee);

    @Insert("\n" +
            "INSERT into workdate (month,positionLevel,workDate,remark,type,empNostr)\n" +
            " values(#{month},#{positionLevel},#{workDate},#{remark},#{type},#{empNostr})\n")
    void saveWorkData(WorkDate workDate);

    @Insert("\n" +
            "INSERT into workset(workLevel,month,updatedate,morningon,morningonfrom,morningonend,morningoff," +
            "morningofffrom,morningoffend,noonon,noononfrom,noononend,noonoff,noonofffrom,noonoffend," +
            "extworkon,extworkonfrom,extworkonend,extworkoff,remark)\n" +
            " values(#{workLevel},#{month},#{updateDate},#{morningOnStr},#{morningOnFromStr},#{morningOnEndStr},#{morningOffStr}" +
            ",#{morningOffFromStr},#{morningOffEndStr},#{noonOnStr},#{noonOnFromStr},#{noonOnEndStr},#{noonOffStr},#{noonOffFromStr},#{noonOffEndStr}" +
            ",#{extworkonStr},#{extworkonFromStr},#{extworkonEndStr},#{extworkoffStr},#{remark})\n")
    void addWorkSetData(WorkSet workSet);


    @Update("update workdate set workDate =  #{workDate},remark = #{remark} " +
            ",type=#{type},empNostr = #{empNostr} where month = #{month} and positionLevel = #{positionLevel} and" +
            " type = #{type} ")
    void updateWorkData(WorkDate workDate);

    @Delete("delete from employee where id = #{id}")
    void deleteEmployeetById(Integer id);

    @Delete("delete from salary where empno = #{empNo}")
    void deleteEmployeeSalaryByEmpno(String empNo);

    @Delete("delete from salary where empno = #{empno}")
    void deleteSalaryByEmpno(String empno);

    @Delete("delete from userinfo where empno = #{empno}")
    void deleteUserInfoByEmpNo(String empno);

    @Select("SELECT\n" +
            "\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\totherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "\to.username,\n" +
            "\to.userpwd AS passowrd,\n" +
            "\to.userpwd AS passowrd22,\n" +
            "\ts.compresalary,\n" +
            "\ts.possalary,\n" +
            "\ts.jobsalary,\n" +
            "\ts.meritsalary,\n" +
            "\tn.positionname,\n" +
            "\tt.deptname,\n" +
            "\ts.remark,\n" +
            "\ts.state,e.isQuit \n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN salary s ON e.empno = s.empno\n" +
            "LEFT JOIN userinfo o ON o.empno = e.empno " +
            "LEFT JOIN position n ON n.id = e.positionId " +
            "LEFT JOIN dept t ON t.id = e.deptId " +
            "where e.id = #{id}")
    List<Employee> getEmployeeById(Integer id);


    @Select("SELECT\n" +
            "\te.NAME,\n" +
            "\tn.positionLevel,\n" +
            "\te.NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\totherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "\to.username,\n" +
            "\to.userpwd AS passowrd,\n" +
            "\to.userpwd AS passowrd22,\n" +
            "\ts.compresalary,\n" +
            "\ts.possalary,\n" +
            "\ts.jobsalary,\n" +
            "\ts.meritsalary,\n" +
            "\tn.positionname,\n" +
            "\tt.deptname,\n" +
            "\tn.positionlevel,\n" +
            "\ts.remark,\n" +
            "\ts.state,e.isQuit \n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN salary s ON e.empno = s.empno\n" +
            "LEFT JOIN userinfo o ON o.empno = e.empno " +
            "LEFT JOIN position n ON n.id = e.positionId " +
            "LEFT JOIN dept t ON t.id = e.deptId " +
            "where e.id = #{id}")
    Employee getEmployeeOneById(Integer id);

    @Select("select * from outdan where empNo = #{empNo} and date = #{dateStr}")
    Out getOutByEmpNoAndDateStr(String empNo, String dateStr);


    @Insert(" insert into pinshijiabanbgs (empNo,remark) values (#{empNo},#{remark}) ")
    void savePinShiDateToMysql(PinShiJiaBanBGS ps);


    @Update(" update pinshijiabanbgs set remark = {remark} where empNo = #{empNo} ")
    void updatePinShiDateToMysql(PinShiJiaBanBGS ps);


    @Select("select * from pinshijiabanbgs where empNo = #{empNo} ")
    PinShiJiaBanBGS getPinShiByEmpNo(String empNo);


    @Select("select count(*) from yeban where empNo = #{empNo} and date = #{dateStr}")
    int getYeBanByEmpNoAndDateStr(String empNo, String dateStr);


    @Select("select empNo,date as dateStr from yeban where empNo = #{empNo} and date = #{dateStr}")
    YeBan getYeBanByEmpNoAndDateStrNew(String empNo, String dateStr);


    @Delete("delete from qianka where id = #{id}")
    void deleteQianKaDateToMysql(Integer id);

    @Select("select id from position where positionName = #{deptName} limit 1 ")
    Integer getDeptIdByDeptName(String deptName);

    @Update("update employee set positionId = #{deptId} where id = #{empId} ")
    void updateEmployeeDeptIdById(Integer empId, Integer deptId);

    @Delete("delete from lianban where id = #{id}")
    void deleteLianBanDateToMysql(Integer id);

    @Delete("delete from linshihours where id = #{id}")
    void deleteLinShiDateToMysql(LinShiHours lsh);

    @Delete("delete from jiaban where id = #{id}")
    void deleteJiaBanDateToMysql(Integer id);


    @Select("SELECT\n" +
            "\tkq.id,\n" +
            "\tkq.enrollNumber,\n" +
            "\tkq.yearMonth,\n" +
            "\tkq.dateStr,\n" +
            "\tkq.`week`,\n" +
            "\tkq.timeStr,\n" +
            "\tIFNULL(\n" +
            "\t\tkq.clockResultByRenShi,\n" +
            "\t\tkq.clockResult\n" +
            "\t) AS clockResult,\n" +
            "\tkq.extWorkHours,\n" +
            "\tkq.remark,\n" +
            "\tkq.aOnTime,\n" +
            "\tkq.aOffTime,\n" +
            "\tkq.pOnTime,\n" +
            "\tkq.pOffTime,\n" +
            "\tkq.extWorkOnTime,\n" +
            "\tkq.extWorkOffTime,\n" +
            "\tkq.rensheCheck\n" +
            "FROM\n" +
            "\tkqbean kq\n" +
            "LEFT JOIN qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
            " where  kq.dateStr = #{dateStr} and zke.empNo =  #{empNo} limit 1 ")
    KQBean getKQBeanByDateStrAndEmpNo(String dateStr, String empNo);


    @Select("select * from kqbean where enrollNumber = #{num} and dateStr = #{date} ")
    KQBean getKQBeanByEnroNumAndDate(String num, String date);

    @Select("select * from lianban where empNo = #{empNo} and date = #{dateStr} ")
    LianBan getLianBanByEmpNoAndDateStr(String empNo, String dateStr);


    @Insert(" insert into lianban (empNo,\n" +
            "\tdate,\n" +
            "\tnoonHours,\n" +
            "\tnightHours,\n" +
            "\ttype,\n" +
            "\tremark) values (" +
            "  #{empNo},\n" +
            "\t#{dateStr},\n" +
            "\t#{noonHours},\n" +
            "\t#{nightHours},\n" +
            "\t#{type},\n" +
            "\t#{remark})")
    void saveLianBanBeanToSql(LianBan lianBan);


    @Insert(" insert into outdan (" +
            "empNo,\n" +
            "\tdate,\n" +
            "\toutaddr,\n" +
            "\toutfor,\n" +
            "\touttime,\n" +
            "\trealcomtime,\n" +
            "\tinterDays,\n" +
            "\tremark) values (" +
            "  #{empNo},\n" +
            "\t#{dateStr},\n" +
            "\t#{outaddr},\n" +
            "\t#{outfor},\n" +
            "\t#{outtimeStr},\n" +
            "\t#{realcomtimeStr}," +
            "\t#{interDays}," +
            " #{remark})")
    void saveOutBeanToSql(Out out);


    @Insert(" insert into yeban (" +
            "empNo,\n" +
            "\tdate,\n" +
            "\tremark) values (" +
            "  #{empNo},\n" +
            "\t#{dateStr},\n" +
            "\t#{remark})")
    void saveYeBanDateToMysql(YeBan yeBan);

    @Update(" update yeban set remark = #{remark} where empNo = #{empNo} and date = #{dateStr}")
    void updateYeBanDateToMysql(YeBan yeBan);

    @Insert(" insert into jiaBan (empNo,\n" +
            "\ttype,\n" +
            "\textDateFrom,\n" +
            "\textDateEnd,\n" +
            "\textWorkHours,\n" +
            "\tremark) values (" +
            "  #{empNo},\n" +
            "\t#{type},\n" +
            "\t#{extDateFromStr},\n" +
            "\t#{extDateEndStr},\n" +
            "\t#{extWorkHours},\n" +
            "\t#{remark})")
    void saveJiaBanDateToMysql(JiaBan jiaBan);

    @Update(" update lianban set" +
            " noonHours = #{noonHours}," +
            "nightHours = #{nightHours}, " +
            "type = #{type}, " +
            "remark = #{remark} " +
            "where empNo = #{empNo} and date = #{dateStr} ")
    void updateLianBanBean(LianBan lianBan);

    @Update(" update outdan set" +
            " outaddr = #{outaddr}," +
            "outfor = #{outfor}, " +
            "outtime = #{outtime}, " +
            "realcomtime = #{realcomtime}, " +
            "remark = #{remark}, " +
            "interDays = #{interDays} " +
            "where empNo = #{empNo} and date = #{dateStr} ")
    void updateOutBean(Out out);


    @Select("SELECT\n" +
            "\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\totherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "\to.username,\n" +
            "\to.userpwd AS passowrd,\n" +
            "\to.userpwd AS passowrd22,\n" +
            "\ts.compresalary,\n" +
            "\ts.possalary,\n" +
            "\ts.jobsalary,\n" +
            "\ts.meritsalary,\n" +
            "\tn.positionname,\n" +
            "\tt.deptname,\n" +
            "\ts.remark,\n" +
            "\ts.state,e.isQuit \n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN salary s ON e.empno = s.empno\n" +
            "LEFT JOIN userinfo o ON o.empno = e.empno " +
            "LEFT JOIN position n ON n.id = e.positionId " +
            "LEFT JOIN dept t ON t.id = e.deptId " +
            "where e.empNo = #{empNo}")
    Employee getEmployeeByEmpno(String empNo);

    @Select("SELECT\n" +
            "\tee.empno,\n" +
            "\tzk.userid AS enrollNumber\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN qyweixinbd zk ON ee.empno = zk.empNo\n" +
            "WHERE\n" +
            "\tee.isQuit = 0\n" +
            "\n" +
            "UNION\n" +
            "\n" +
            "SELECT\n" +
            "\tee.name as empno,\n" +
            "\tzk.userid AS enrollNumber\n" +
            "FROM\n" +
            "\tlinshiemp ee\n" +
            "JOIN qyweixinbd zk ON ee.name = zk.name\n" +
            "WHERE\n" +
            "\tee.isQuit = 0")
    List<Employee> findAllEmployeeNotIsQuitandhaveEnrollNum();


    @Select("SELECT\n" +
            "\tworkdate as workdate\n" +
            "FROM\n" +
            "\tworkdate\n" +
            "WHERE\n" +
            "\t`month` = #{yearMonth} \n" +
            "AND type = 1 and positionLevel = 'B' ")
    String getWorkDateByMonthC(String yearMonth);


    @Select("SELECT\n" +
            "\tmax(workdate) as workdate\n" +
            "FROM\n" +
            "\tworkdate\n" +
            "WHERE\n" +
            "\t`month` = #{yearMonth} \n" +
            "AND type = 0")
    String getWorkDateByMonthE(String yearMonth);

    @Select("SELECT\n" +
            "\tmax(workdate) as workdate\n" +
            "FROM\n" +
            "\tworkdate\n" +
            "WHERE\n" +
            "\t`month` = #{yearMonth} \n" +
            "AND type = 2")
    String getWorkDateByMonthD(String yearMonth);

    @Select("SELECT e.* " +
            "FROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId" +
            " where e.empno = #{empNo}")
    Employee getEmployeeByEmpNoo(String empNo);

    @Select("select * from position where positionName like  CONCAT('%',#{positionName},'%')")
    List<Position> queryPositionByName(String positionName);

    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\te.`name` as name,\n" +
            "\te.empno as empNo,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\ta.beginleave as beginLeaveStr,\n" +
            "\ta.endleave as endLeaveStr,\n" +
            "\ta.leavelong as leaveLong,\n" +
            "\ta.leaveDescrip as leaveDescrip,\n" +
            "\ta.remark as remark,\n" +
            "\ta.type as type,e.isQuit " +
            "\n" +
            "FROM\n" +
            "\tleavedata a\n" +
            "LEFT JOIN employee e ON a.employeeid = e.id\n" +
            "LEFT JOIN position n ON n.id = e.positionId\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "ORDER BY\n" +
            "\te.name DESC limit #{currentPageTotalNum},#{pageSize} ")
    List<Leave> findAllLeave(Leave leave);

    @Select("SELECT\n" +
            "\tod.id,\n" +
            "\tod.empNo,\n" +
            "\tee.`name`,\n" +
            "\tt.deptname,\n" +
            "\tod.date AS dateStr,\n" +
            "\tod.outaddr,\n" +
            "\tod.outfor,\n" +
            "\tod.outtime AS outtimeStr,\n" +
            "\tod.realcomtime AS realcomtimeStr,\n" +
            "\tod.remark\n" +
            "FROM\n" +
            "\toutdan od\n" +
            "LEFT JOIN employee ee ON od.empNo = ee.empNo\n" +
            "LEFT JOIN dept t ON t.id = ee.deptId ORDER BY od.date desc,od.empNo" +
            " limit #{currentPageTotalNum},#{pageSize}  ")
    List<Out> findAllOut(Out out);

    @Select(" SELECT\n" +
            "\tqk.id,\n" +
            "\tee.`name` as name,\n" +
            "\tee.empno,\n" +
            "\tt.deptname as deptName,\n" +
            "\tqk.date AS dateStr,\n" +
            "\tqk.timeStr AS timeStr,\n" +
            "\tqk.type,\n" +
            "\tqk.remark\n" +
            "FROM\n" +
            "\tqianka qk\n" +
            "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id order by ee.empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<QianKa> findAllQianKa(QianKa qianKa);


    @Select(" SELECT\n" +
            "\tqk.id,\n" +
            "\tee.`name` as name,\n" +
            "\tee.empno,\n" +
            "\tt.deptname as deptName,\n" +
            "\tqk.extDateFrom AS extDateFromStr,\n" +
            "\tqk.extDateEnd AS extDateEndStr,\n" +
            "\tqk.extWorkHours,\n" +
            "\tqk.type,\n" +
            "\tqk.remark\n" +
            "FROM\n" +
            "\tjiaban qk\n" +
            "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id order by ee.empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<JiaBan> findAllJiaBan(JiaBan jiaBan);

    @Select(" SELECT\n" +
            "\tqk.id,\n" +
            "\tee.`name` as name,\n" +
            "\tee.empno,\n" +
            "\tt.deptname as deptName,\n" +
            "\tqk.date AS dateStr,\n" +
            "\tqk.noonHours,\n" +
            "\tqk.nightHours,\n" +
            "\tqk.type,\n" +
            "\tqk.remark\n" +
            "FROM\n" +
            "\tlianban qk\n" +
            "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id order by ee.empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<LianBan> findAllLianBan(LianBan lianBan);


    @Select(" SELECT\n" +
            "\tlsh.id,\n" +
            "\tlsh.`name`,\n" +
            "\tlsh.yearMonth,\n" +
            "\tlsh.Date AS dateStr,\n" +
            "\tlsh.hours,\n" +
            "\tlsh.remark\n" +
            "FROM\n" +
            "\tlinshihours lsh\n" +
            "LEFT JOIN qyweixinbd lse ON lse.`name` = lsh.`name`\n" +
            "ORDER BY\n" +
            "\tlsh.`name` ASC,\n" +
            "\tlsh.Date DESC limit #{currentPageTotalNum},#{pageSize}")
    List<LinShiHours> findAllLinShiDan(LinShiHours linShiHours);


    @Select("select count(*) from linshihours ")
    int findAllLinShiDanCount();


    @Select(" SELECT\n" +
            "\tqk.id,\n" +
            "\tee.`name` as name,\n" +
            "\tee.empno,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionname,\n" +
            "\tqk.remark\n" +
            "FROM\n" +
            "\tpinshijiabanbgs qk\n" +
            "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id " +
            "LEFT JOIN position n ON ee.positionId = n.id " +
            "order by ee.empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<PinShiJiaBanBGS> findAllPinShi(PinShiJiaBanBGS ps);


    @Select("select count(*) from  pinshijiabanbgs  ")
    int findAllPinShiCount();


    @Select(" SELECT\n" +
            "\tqk.id,\n" +
            "\tee.`name` as name,\n" +
            "\tee.empno,\n" +
            "\tt.deptname as deptName,\n" +
            "\tqk.date AS dateStr,\n" +
            "\tqk.remark\n" +
            "FROM\n" +
            "\tyeban qk\n" +
            "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id order by ee.empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<YeBan> findAllYeBan(YeBan yeBan);

    @Select("select count(*) from yeban")
    int findAllYeBanCount();


    @Delete(" delete from yeban where id = #{id} ")
    void deleteYeBanDateToMysql(Integer id);


    @Select("select count(*) from jiaban ")
    int findAllJiaBanCount();

    @Select("select count(*) from lianban ")
    int findAllLianBanCount();

    @Select("select count(id) from qianka ")
    int findAllQianKaCount();

    @Select("SELECT\n" +
            "\tt.deptName\n" +
            "FROM\n" +
            "\temployee ee left join dept t on t.id = ee.deptId \n" +
            "WHERE\n" +
            "\tee.id = #{id} ")
    String getDeptNameByEmployId(Integer id);

    @Select("select count(id) from leavedata ")
    int findAllLeaveCount();

    @Select("select id,name from employee ")
    List<Employee> findAllEmployees();

    @Select("SELECT\n" +
            "\taf.*\n" +
            "FROM\n" +
            "\taccufund af\n" +
            "LEFT JOIN employee ee ON af.ID_NO = ee.ID_NO\n" +
            "WHERE\n" +
            "\tee.empNo = #{empNo}")
    AccuFund getAFByEmpNo(String empNo);

    @Select("SELECT\n" +
            "\taf.*\n" +
            "FROM\n" +
            "\tinsurance af\n" +
            "LEFT JOIN employee ee ON af.ID_NO = ee.ID_NO\n" +
            "WHERE\n" +
            "\tee.empno = #{empNo} ")
    Insurance getISByEmpNo(String empNo);

    @Update("update position set positionName =  #{positionName},positionLevel=#{positionLevel} where id = #{id} ")
    void saveUpdateData(Integer id, String positionName, String positionLevel);

    @Update("update monthkqinfo " +
            "set zhengbanHours =  #{zhengbanHours}," +
            "usualExtHours = #{usualExtHours}, " +
            "workendHours = #{workendHours}, " +
            "chinaPaidLeave = #{chinaPaidLeave}, " +
            "otherPaidLeave = #{otherPaidLeave}, " +
            "leaveOfAbsense = #{leaveOfAbsense}, " +
            "sickLeave = #{sickLeave}, " +
            "fullWorkReword = #{fullWorkReword} " +
            "where id = #{id} ")
    void updateMKById(MonthKQInfo mk);

    @Update("update workset set updatedate = #{updateDate},morningon = #{morningOnStr} , " +
            " morningonfrom = #{morningOnFromStr},morningonend = #{morningOnEndStr},morningoff = #{morningOffStr}," +
            " morningofffrom = #{morningOffFromStr},morningoffend = #{morningOffEndStr},noonon = #{noonOnStr}," +
            " noononfrom= #{noonOnFromStr},noononend = # {noonOnEndStr},noonoff = #{noonOffStr} ,noonofffrom = #{noonOffFromStr}," +
            " noonoffend = #{noonOffEndStr},extworkon = #{extworkonStr},extworkonFrom = #{extworkonFromStr},extworkonEnd = #{extworkonEndStr},extworkoff = #{extworkoffStr},remark = #{remark}" +
            " where id = #{id} ")
    void updateWorkSetDataById(WorkSet workSet);

    @Update("update dept set deptname =  #{deptName} where id = #{id} ")
    void saveUpdateData2(Integer id, String deptName);

    @Update("update employee set deptId = #{deptId},positionId = #{positionId},incompdate=#{incomdateStr},conExpDate=#{conExpDateStr}," +
            "birthDay=#{birthDayStr},ID_NO = #{ID_NO},\n" +
            "nativePla = #{nativePla},homeAddr=#{homeAddr},valiPeriodOfID=#{valiPeriodOfIDStr},nation=#{nation}," +
            "marriaged=#{marriaged},contactPhone=#{contactPhone},educationLe=#{educationLe},educationLeUrl=#{educationLeUrl},\n" +
            "screAgreement=#{screAgreement},healthCerti=#{healthCerti},sateListAndLeaCerti=#{sateListAndLeaCerti}," +
            "sateListAndLeaCertiUrl=#{sateListAndLeaCertiUrl},otherCerti=#{otherCerti},otherCertiUrl=#{otherCertiUrl}," +
            "positionAttrId=#{positionAttrId},isQuit = #{isQuit} " +
            "where empno = #{empNo}")
    void updateEmployeeData(Employee employee);

    @Update("update leavedata set beginleave = #{beginLeaveStr},endleave = #{endLeaveStr},leavelong =#{leaveLong}" +
            ",leaveDescrip = #{leaveDescrip},remark = #{remark},type = #{type }  where id = #{id} ")
    void updateLeaveToMysql(Leave leave);

    @Insert("insert into leavedata (employeeid,beginleave,endleave,leavelong,leaveDescrip,remark,type)\n" +
            "VALUES(#{employeeId},#{beginLeaveStr},#{endLeaveStr},#{leaveLong},#{leaveDescrip},#{remark},#{type})")
    void addLeaveData(Leave leave);

    @Insert("insert into insurance (name,ID_NO,endowInsur,medicalInsur,unWorkInsur) values (" +
            "#{name}," +
            "#{ID_NO}," +
            "#{endowInsur}," +
            "#{medicalInsur}," +
            "#{unWorkInsur}" +
            ") ")
    void saveInsuranceByBean(Insurance is);


    @Insert("insert into accufund (name,ID_NO,accuFundFee) values (" +
            "#{name}," +
            "#{ID_NO}," +
            "#{accuFundFee}" +
            ") ")
    void saveAccuFundByBean(AccuFund is);

    @Delete("delete from insurance ")
    void deleteAllInsurance();

    @Delete("delete from accufund")
    void deleteAllAccuFund();

    class PseronDaoProvider {

        public String queryEmployeeByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from employee where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }
            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate  >= #{startIncomDateStr} and incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }


        public String queryLinShiByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from linshiemp where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }


            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }
            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate  >= #{startIncomDateStr} and incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }


        public String queryKQBeanDataByCondition(KQBean kqBean) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tkq.id,\n" +
                    "\tee.`name` AS NAME,\n" +
                    "\tdeptname AS deptName,\n" +
                    "\tkq.enrollNumber,\n" +
                    "\tkq.yearMonth,\n" +
                    "\tkq.dateStr,\n" +
                    "\tkq.`week`,\n" +
                    "\tkq.timeStr,\n" +
                    "\tIFNULL(\n" +
                    "\t\tkq.clockResultByRenShi,\n" +
                    "\t\tkq.clockResult\n" +
                    "\t) AS clockResult,\n" +
                    "\tkq.extWorkHours,\n" +
                    "\tkq.remark,\n" +
                    "\tkq.aOnTime,\n" +
                    "\tkq.aOffTime,\n" +
                    "\tkq.pOnTime,\n" +
                    "\tkq.pOffTime,\n" +
                    "\tkq.extWorkOnTime,\n" +
                    "\tkq.extWorkOffTime,\n" +
                    "\tkq.rensheCheck\n" +
                    "FROM\n" +
                    "\tkqbean kq\n" +
                    "LEFT JOIN qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
                    "LEFT JOIN employee ee ON ee.empno = zke.empNo\n" +
                    "LEFT JOIN dept t ON t.id = ee.deptId\n" +
                    " where 1=1");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            if (kqBean.getClockDates() != null && kqBean.getClockDates().size() > 0) {
                if (kqBean.getClockDates().size() == 1 && !"undefined".equals(kqBean.getClockDates().get(0))) {
                    sb.append(" and  kq.dateStr in ('" + kqBean.getClockDates().get(0) + "')");
                } else if (kqBean.getClockDates().size() >= 2) {
                    sb.append(" and  kq.dateStr in (");
                    for (int i = 0; i < kqBean.getClockDates().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDates().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDates().get(kqBean.getClockDates().size() - 1) + "')");
                }
            }

            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by ee.name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.yearMonth ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(kqBean.getSortByName())) {
                    sb.append(" order by t.deptName ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("dateStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.dateStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("week".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.week ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("timeStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.timeStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockResult".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.clockResult ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("extWorkHours".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.extWorkHours ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.remark ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tkq.yearMonth DESC,\n" +
                        "\tkq.enrollNumber ASC ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryKQBeanDataByConditionCount(KQBean kqBean) {
            StringBuilder sb = new StringBuilder("select count(*) " +
                    "FROM\n" +
                    "\tkqbean kq\n" +
                    "LEFT JOIN qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
                    "LEFT JOIN employee ee ON ee.empno = zke.empNo\n" +
                    "LEFT JOIN dept t ON t.id = ee.deptId\n" +
                    " where 1=1 ");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");
            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            if (kqBean.getClockDates() != null && kqBean.getClockDates().size() > 0) {
                if (kqBean.getClockDates().size() == 1 && !"undefined".equals(kqBean.getClockDates().get(0))) {
                    sb.append(" and  kq.dateStr in ('" + kqBean.getClockDates().get(0) + "')");
                } else if (kqBean.getClockDates().size() >= 2) {
                    sb.append(" and  kq.dateStr in (");
                    for (int i = 0; i < kqBean.getClockDates().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDates().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDates().get(kqBean.getClockDates().size() - 1) + "')");
                }
            }

            return sb.toString();
        }

        public String queryEmployeeSalaryByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from employee where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }
            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate  >= #{startIncomDateStr} and incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

        public String queryWorkSetByCondition(WorkSet workSet) {
            StringBuilder sb = new StringBuilder("SELECT * from workset where 1=1 ");

            if (workSet.getMonths() != null && workSet.getMonths().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and month in ('" + workSet.getMonths().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and month in (");
                    for (int i = 0; i < workSet.getMonths().size() - 1; i++) {
                        sb.append("'" + workSet.getMonths().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getMonths().get(workSet.getMonths().size() - 1) + "')");
                }
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'" + workSet.getWorkLevels().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
            }
            sb.append(" order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryWorkSetByConditionCount(WorkSet workSet) {
            StringBuilder sb = new StringBuilder("SELECT count(*) from workset where 1=1 ");
            if (workSet.getMonths() != null && workSet.getMonths().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and month in ('" + workSet.getMonths().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and month in (");
                    for (int i = 0; i < workSet.getMonths().size() - 1; i++) {
                        sb.append("'" + workSet.getMonths().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getMonths().get(workSet.getMonths().size() - 1) + "')");
                }
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'" + workSet.getWorkLevels().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
            }
            return sb.toString();
        }


        public String queryEmployeeSalaryByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo," +
                    "\tIFNULL(s.compreSalary,0) AS compreSalary," +
                    "\tIFNULL(s.posSalary,0) AS posSalary," +
                    "\tIFNULL(s.jobSalary,0) AS jobSalary," +
                    "\tIFNULL(s.meritSalary,0) AS meritSalary," +
                    "\ts.id AS salaryId, " +
                    "\ts.state AS state,e.isQuit  " +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id " +
                    "LEFT JOIN salary s ON s.empno = e.empno " +
                    "where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getState() != null) {
                sb.append(" and s.state  = #{state} ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and n.id in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sexStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sex ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionAttrIdStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.positionAttrId ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("birthDayStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.birthDay ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("incomdateStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.incompdate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nativePlaStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.nativePla ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("contactPhone".equals(employee.getSortByName())) {
                    sb.append(" order by e.contactPhone ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("educationLeStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.educationLe ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sateListAndLeaCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sateListAndLeaCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("compreSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.compreSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("posSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.posSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("jobSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.jobSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("meritSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.meritSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("allMoney".equals(employee.getSortByName())) {
                    sb.append(" order by (compresalary+possalary+jobsalary+meritsalary) ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("state".equals(employee.getSortByName())) {
                    sb.append(" order by state ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isQuit".equals(employee.getSortByName())) {
                    sb.append(" order by isQuit ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryGongZhongHaoByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsgongzhonghaoBangDing() != null
                    && employee.getIsgongzhonghaoBangDing().size() > 0
                    && employee.getIsgongzhonghaoBangDing().get(0) == 1) {
                sb.append("  join gongzhonghao h on e.empno = h.empNo ");
            } else {
                sb.append(" left join gongzhonghao h on e.empno = h.empNo ");
            }

            sb.append(" where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }

        public String queryGongZhongHaoByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo," +
                    "e.isQuit," +
                    "h.gongzhonghaoId " +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsgongzhonghaoBangDing() != null
                    && employee.getIsgongzhonghaoBangDing().size() > 0
                    && employee.getIsgongzhonghaoBangDing().get(0) == 1
            ) {
                sb.append("  join gongzhonghao h on e.empno = h.empNo ");
            } else {
                sb.append(" left join gongzhonghao h on e.empno = h.empNo ");
            }

            sb.append(" where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("gongzhonghaoId".equals(employee.getSortByName())) {
                    sb.append(" order by h.gongzhonghaoId ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryZhongKongByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsZhongKongBangDing() != null
                    && employee.getIsZhongKongBangDing().size() > 0
                    && employee.getIsZhongKongBangDing().get(0) == 1
            ) {
                sb.append("  join qyweixinbd h on e.empno = h.empNo ");
            } else {
                sb.append(" left join qyweixinbd h on e.empno = h.empNo ");
            }

            sb.append(" where  e.isQuit = 0   ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }

        public String queryZhongKongByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo," +
                    "e.isQuit," +
                    "h.userid as enrollNumber1 " +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsZhongKongBangDing() != null
                    && employee.getIsZhongKongBangDing().size() > 0
                    && employee.getIsZhongKongBangDing().get(0) == 1
            ) {
                sb.append("  join qyweixinbd h on e.empno = h.empNo ");
            } else {
                sb.append(" left join qyweixinbd h on e.empno = h.empNo ");
            }

            sb.append(" where  e.isQuit = 0  ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("EnrollNumber".equals(employee.getSortByName())) {
                    sb.append(" order by h.userid ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryOutClockInByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\toutclockin o\n" +
                    "LEFT JOIN qyweixinbd g ON g.userid = o.userid\n" +
                    " JOIN employee e ON e.empNo = g.empNo\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id\n" +
                    "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
                    "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
                    "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
                    " where 1=1 ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            return sb.toString();
        }

        public String queryOutClockInByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\te.NAME,\n" +
                    "\to.id," +
                    "od.date," +
                    "\td.deptname,\n" +
                    "\tn.positionName,\n" +
                    "\to.clockInDate AS clockInDateStr,\n" +
                    "\to.clockInDateAMOn AS clockInDateAMOnStr,\n" +
                    "\to.clockInAddrAMOn,\n" +
                    "\tod.id as outDanId,\n" +
                    "\tCASE\n" +
                    "WHEN o.amOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.amOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS amOnUrlInt,\n" +
                    " o.clockInDatePMOn AS clockInDatePMOnStr,\n" +
                    " o.clockInAddrPMOn,\n" +
                    " CASE\n" +
                    "WHEN o.pmOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.pmOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS pmOnUrlInt,\n" +
                    " o.clockInDateNMOn AS clockInDateNMOnStr,\n" +
                    " o.clockInAddNMOn,\n" +
                    " CASE\n" +
                    "WHEN o.nmOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.nmOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS nmOnUrlInt\n" +
                    "FROM\n" +
                    "\toutclockin o\n" +
                    "LEFT JOIN qyweixinbd g ON g.userid = o.userid\n" +
                    " JOIN employee e ON e.empNo = g.empNo\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id " +
                    " LEFT JOIN outdan od ON od.empNo = e.empno  and od.date = o.clockInDate " +
                    " where 1=1 ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("outLeaveSheet".equals(employee.getSortByName())) {
                    sb.append(" order by ld.beginleave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateAMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDateAMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddrAMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddrAMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("amOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.amOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDatePMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDatePMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddrPMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddrPMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("pmOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.pmOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateNMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDateNMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddNMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddNMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nmOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.nmOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by e.name asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryEmployeeByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo,e.isQuit" +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sexStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sex ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionAttrIdStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.positionAttrId ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("birthDayStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.birthDay ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("incomdateStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.incompdate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nativePlaStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.nativePla ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("contactPhone".equals(employee.getSortByName())) {
                    sb.append(" order by e.contactPhone ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("educationLeStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.educationLe ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sateListAndLeaCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sateListAndLeaCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isQuit".equals(employee.getSortByName())) {
                    sb.append(" order by e.isQuit ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryLinShiByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.nation,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "e.isQuit" +
                    "\t\t FROM \n" +
                    "\tlinshiemp e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sexStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sex ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("birthDayStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.birthDay ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("incomdateStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.incompdate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nativePlaStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.nativePla ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isQuit".equals(employee.getSortByName())) {
                    sb.append(" order by e.isQuit ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.name asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryQKByConditionCount(QianKa qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tqianka qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getTypes() != null && qianKa.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(qianKa.getTypes().toString(), "[]") + ") ");
            }
            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginQianKaStr() != null && qianKa.getBeginQianKaStr().length() > 0 && qianKa.getEndQianKaStr() != null && qianKa.getEndQianKaStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginQianKaStr} and qk.date  <= #{endQianKaStr}");
            } else if (qianKa.getBeginQianKaStr() != null && qianKa.getBeginQianKaStr().length() > 0) {
                sb.append(" and qk.date >= #{beginQianKaStr}");
            } else if (qianKa.getEndQianKaStr() != null && qianKa.getEndQianKaStr().length() > 0) {
                sb.append(" and qk.date <= #{endQianKaStr}");
            }
            return sb.toString();

        }

        public String queryLBByConditionCount(LianBan qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tlianban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getTypes() != null && qianKa.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(qianKa.getTypes().toString(), "[]") + ") ");
            }
            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0 && qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginLianBanStr} and qk.date  <= #{endLianBanStr}");
            } else if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginLianBanStr}");
            } else if (qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endLianBanStr}");
            }
            return sb.toString();
        }


        public String queryYBByConditionCount(YeBan yeBan) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tyeban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (yeBan.getNames() != null && yeBan.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(yeBan.getNames().toString(), "[]") + ") ");
            }

            if (yeBan.getEmpNo() != null && yeBan.getEmpNo() != "" && yeBan.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (yeBan.getDeptIds() != null && yeBan.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(yeBan.getDeptIds().toString(), "[]") + ") ");
            }

            if (yeBan.getPositionIds() != null && yeBan.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(yeBan.getPositionIds().toString(), "[]") + ") ");
            }

            if (yeBan.getBeginYeBanStr() != null && yeBan.getBeginYeBanStr().length() > 0 && yeBan.getEndYeBanStr() != null && yeBan.getEndYeBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginYeBanStr} and qk.date  <= #{endYeBanStr}");
            } else if (yeBan.getBeginYeBanStr() != null && yeBan.getBeginYeBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginYeBanStr}");
            } else if (yeBan.getEndYeBanStr() != null && yeBan.getEndYeBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endYeBanStr}");
            }
            return sb.toString();
        }

        public String queryYBByCondition(YeBan yeBan) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tee.empno,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tqk.date AS dateStr,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tyeban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (yeBan.getNames() != null && yeBan.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(yeBan.getNames().toString(), "[]") + ") ");
            }

            if (yeBan.getEmpNo() != null && yeBan.getEmpNo() != "" && yeBan.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (yeBan.getDeptIds() != null && yeBan.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(yeBan.getDeptIds().toString(), "[]") + ") ");
            }

            if (yeBan.getPositionIds() != null && yeBan.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(yeBan.getPositionIds().toString(), "[]") + ") ");
            }

            if (yeBan.getBeginYeBanStr() != null && yeBan.getBeginYeBanStr().length() > 0 && yeBan.getEndYeBanStr() != null && yeBan.getEndYeBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginYeBanStr} and qk.date  <= #{endYeBanStr}");
            } else if (yeBan.getBeginYeBanStr() != null && yeBan.getBeginYeBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginYeBanStr}");
            } else if (yeBan.getEndYeBanStr() != null && yeBan.getEndYeBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endYeBanStr}");
            }
            sb.append(" order by qk.empNo asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryPSByConditionCount(PinShiJiaBanBGS qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tlianban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            sb.append(" order by qk.empNo asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryPSByCondition(PinShiJiaBanBGS qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tee.empno,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tlianban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            sb.append(" order by qk.empNo asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryLBByCondition(LianBan qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tee.empno,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tqk.date AS dateStr,\n" +
                    "\tqk.noonHours,\n" +
                    "\tqk.nightHours,\n" +
                    "\tqk.type,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tlianban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getTypes() != null && qianKa.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(qianKa.getTypes().toString(), "[]") + ") ");
            }
            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0 && qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginLianBanStr} and qk.date  <= #{endLianBanStr}");
            } else if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginLianBanStr}");
            } else if (qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endLianBanStr}");
            }
            sb.append(" order by qk.empNo asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryLSHByConditionCount(LinShiHours qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tlinshihours qk\n" +
                    "LEFT JOIN linshiemp ee ON ee.name = qk.name\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0 && qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginLianBanStr} and qk.date  <= #{endLianBanStr}");
            } else if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginLianBanStr}");
            } else if (qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endLianBanStr}");
            }
            return sb.toString();
        }


        public String queryLSHByCondition(LinShiHours qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tqk.date AS dateStr,\n" +
                    "\tqk.hours,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tlinshihours qk\n" +
                    "LEFT JOIN linshiemp ee ON ee.name = qk.name\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0 && qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginLianBanStr} and qk.date  <= #{endLianBanStr}");
            } else if (qianKa.getBeginLianBanStr() != null && qianKa.getBeginLianBanStr().length() > 0) {
                sb.append(" and qk.date >= #{beginLianBanStr}");
            } else if (qianKa.getEndLianBanStr() != null && qianKa.getEndLianBanStr().length() > 0) {
                sb.append(" and qk.date <= #{endLianBanStr}");
            }
            sb.append(" order by qk.name asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryJBByCondition(JiaBan jiaBan) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tee.empno,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tqk.extWorkHours,\n" +
                    "\tqk.extDateFrom as extDateFromStr,\n" +
                    "\tqk.extDateEnd as extDateEndStr,\n" +
                    "\tqk.type,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tjiaban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (jiaBan.getNames() != null && jiaBan.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(jiaBan.getNames().toString(), "[]") + ") ");
            }

            if (jiaBan.getEmpNo() != null && jiaBan.getEmpNo() != "" && jiaBan.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (jiaBan.getDeptIds() != null && jiaBan.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(jiaBan.getDeptIds().toString(), "[]") + ") ");
            }

            if (jiaBan.getTypes() != null && jiaBan.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(jiaBan.getTypes().toString(), "[]") + ") ");
            }
            if (jiaBan.getPositionIds() != null && jiaBan.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(jiaBan.getPositionIds().toString(), "[]") + ") ");
            }

            if (jiaBan.getExtDateFromStr() != null && jiaBan.getExtDateFromStr().length() > 0 && jiaBan.getExtDateEndStr() != null && jiaBan.getExtDateEndStr().length() > 0) {
                sb.append(" and qk.extDateFrom  >= #{extDateFromStr} and qk.extDateEnd  <= #{extDateEndStr}");
            } else if (jiaBan.getExtDateFromStr() != null && jiaBan.getExtDateFromStr().length() > 0) {
                sb.append(" and qk.extDateFrom >= #{extDateFromStr}");
            } else if (jiaBan.getExtDateEndStr() != null && jiaBan.getExtDateEndStr().length() > 0) {
                sb.append(" and qk.extDateEnd <= #{extDateEndStr}");
            }
            sb.append(" order by qk.empNo asc,qk.extDateFrom desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryJBByConditionCount(JiaBan jiaBan) {
            StringBuilder sb = new StringBuilder(" SELECT count(*) " +
                    "FROM\n" +
                    "\tjiaban qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (jiaBan.getNames() != null && jiaBan.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(jiaBan.getNames().toString(), "[]") + ") ");
            }

            if (jiaBan.getEmpNo() != null && jiaBan.getEmpNo() != "" && jiaBan.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (jiaBan.getDeptIds() != null && jiaBan.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(jiaBan.getDeptIds().toString(), "[]") + ") ");
            }

            if (jiaBan.getTypes() != null && jiaBan.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(jiaBan.getTypes().toString(), "[]") + ") ");
            }
            if (jiaBan.getPositionIds() != null && jiaBan.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(jiaBan.getPositionIds().toString(), "[]") + ") ");
            }

            if (jiaBan.getExtDateFromStr() != null && jiaBan.getExtDateFromStr().length() > 0 && jiaBan.getExtDateEndStr() != null && jiaBan.getExtDateEndStr().length() > 0) {
                sb.append(" and qk.extDateFrom  >= #{extDateFromStr} and qk.extDateEnd  <= #{extDateEndStr}");
            } else if (jiaBan.getExtDateFromStr() != null && jiaBan.getExtDateFromStr().length() > 0) {
                sb.append(" and qk.extDateFrom >= #{extDateFromStr}");
            } else if (jiaBan.getExtDateEndStr() != null && jiaBan.getExtDateEndStr().length() > 0) {
                sb.append(" and qk.extDateEnd <= #{extDateEndStr}");
            }
            return sb.toString();
        }

        public String queryQKByCondition(QianKa qianKa) {
            StringBuilder sb = new StringBuilder(" SELECT\n" +
                    "\tqk.id,\n" +
                    "\tee.`name` as name,\n" +
                    "\tee.empno,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tqk.date AS dateStr,\n" +
                    "\tqk.timeStr AS timeStr,\n" +
                    "\tqk.type,\n" +
                    "\tqk.remark\n" +
                    "FROM\n" +
                    "\tqianka qk\n" +
                    "LEFT JOIN employee ee ON ee.empno = qk.empNo\n" +
                    "LEFT JOIN dept t ON ee.deptId = t.id " +
                    "left join position n on ee.positionId = n.id where 1=1 ");
            if (qianKa.getNames() != null && qianKa.getNames().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(qianKa.getNames().toString(), "[]") + ") ");
            }

            if (qianKa.getEmpNo() != null && qianKa.getEmpNo() != "" && qianKa.getEmpNo().trim().length() > 0) {
                sb.append(" and qk.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (qianKa.getDeptIds() != null && qianKa.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(qianKa.getDeptIds().toString(), "[]") + ") ");
            }

            if (qianKa.getTypes() != null && qianKa.getTypes().size() > 0) {
                sb.append(" and qk.type in (" + StringUtils.strip(qianKa.getTypes().toString(), "[]") + ") ");
            }
            if (qianKa.getPositionIds() != null && qianKa.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(qianKa.getPositionIds().toString(), "[]") + ") ");
            }

            if (qianKa.getBeginQianKaStr() != null && qianKa.getBeginQianKaStr().length() > 0 && qianKa.getEndQianKaStr() != null && qianKa.getEndQianKaStr().length() > 0) {
                sb.append(" and qk.date  >= #{beginQianKaStr} and qk.date  <= #{endQianKaStr}");
            } else if (qianKa.getBeginQianKaStr() != null && qianKa.getBeginQianKaStr().length() > 0) {
                sb.append(" and qk.date >= #{beginQianKaStr}");
            } else if (qianKa.getEndQianKaStr() != null && qianKa.getEndQianKaStr().length() > 0) {
                sb.append(" and qk.date <= #{endQianKaStr}");
            }
            sb.append(" order by qk.empNo asc,qk.date desc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();

        }


        public String queryOutByConditionCount(Out leave) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM\n" +
                    "\t outdan a join  " +
                    "employee e on a.empNo = e.empNo \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");

            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }


            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginOutStr() != null && leave.getBeginOutStr().length() > 0 && leave.getEndOutStr() != null && leave.getEndOutStr().length() > 0) {
                sb.append(" and a.outtime  >= #{beginOutStr} and a.realcomtime  <= #{endOutStr}");
            } else if (leave.getBeginOutStr() != null && leave.getBeginOutStr().length() > 0) {
                sb.append(" and a.outtime >= #{beginOutStr}");
            } else if (leave.getEndOutStr() != null && leave.getEndOutStr().length() > 0) {
                sb.append(" and a.realcomtime <= #{endOutStr}");
            }
            return sb.toString();
        }

        public String queryOutByCondition(Out leave) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\ta.id,\n" +
                    "\ta.empNo,\n" +
                    "\te.`name`,\n" +
                    "\tt.deptname,\n" +
                    "\tdate_format(a.date, '%Y-%m-%d') AS dateStr,\n" +
                    "\ta.outaddr,\n" +
                    "\ta.outfor,\n" +
                    "\tdate_format(a.outtime, '%Y-%m-%d %h:%i:%s')  AS outtimeStr,\n" +
                    "\tdate_format(a.realcomtime, '%Y-%m-%d %h:%i:%s')   AS realcomtimeStr,\n" +
                    "\ta.remark " +
                    "FROM\n" +
                    "\t outdan a join  " +
                    "employee e on a.empNo = e.empNo \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");

            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }


            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginOutStr() != null && leave.getBeginOutStr().length() > 0 && leave.getEndOutStr() != null && leave.getEndOutStr().length() > 0) {
                sb.append(" and a.outtime  >= #{beginOutStr} and a.realcomtime  <= #{endOutStr}");
            } else if (leave.getBeginOutStr() != null && leave.getBeginOutStr().length() > 0) {
                sb.append(" and a.outtime >= #{beginOutStr}");
            } else if (leave.getEndOutStr() != null && leave.getEndOutStr().length() > 0) {
                sb.append(" and a.realcomtime <= #{endOutStr}");
            }
            sb.append(" order by e.name desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryLeaveByCondition(Leave leave) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\ta.id AS id,\n" +
                    "\te.`name` AS NAME,\n" +
                    "\te.sex AS sex,\n" +
                    "\te.empno AS empNo,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\tt.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(a.beginleave, '%Y-%m-%d %h:%i:%s')  AS beginLeaveStr,\n" +
                    "\tdate_format(a.endleave, '%Y-%m-%d %h:%i:%s')   AS endleaveStr,\n" +
                    "\ta.remark AS remark,\n" +
                    "\ta.leaveDescrip AS leaveDescrip,\n" +
                    "\ta.leavelong AS leaveLong," +
                    "\ta.type AS type,e.isQuit" +
                    "\n" +
                    "FROM\n" +
                    "\t leavedata a join  employee e on a.employeeid = e.id \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");

            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }
            if (leave.getSexs() != null && leave.getSexs().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(leave.getSexs().toString(), "[]") + ") ");
            }

            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getTypes() != null && leave.getTypes().size() > 0) {
                sb.append(" and a.type in (" + StringUtils.strip(leave.getTypes().toString(), "[]") + ") ");
            }
            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.beginleave  >= #{beginLeaveStr} and a.endleave  <= #{endLeaveStr}");
            } else if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0) {
                sb.append(" and a.beginleave >= #{beginLeaveStr}");
            } else if (leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.endleave <= #{endLeaveStr}");
            }
            sb.append(" order by e.name desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryLeaveByConditionCount(Leave leave) {
            StringBuilder sb = new StringBuilder("SELECT count(*) as a " +
                    " FROM\n" +
                    "\t leavedata a join  employee e on a.employeeid = e.id \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");
            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }
            if (leave.getSexs() != null && leave.getSexs().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(leave.getSexs().toString(), "[]") + ") ");
            }
            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getTypes() != null && leave.getTypes().size() > 0) {
                sb.append(" and a.type in (" + StringUtils.strip(leave.getTypes().toString(), "[]") + ") ");
            }

            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.beginleave  >= #{beginLeaveStr} and a.endleave  <= #{endLeaveStr}");
            } else if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0) {
                sb.append(" and a.beginleave >= #{beginLeaveStr}");
            } else if (leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.endleave <= #{endLeaveStr}");
            }
            return sb.toString();
        }

        public String getJiaBanDanByEmpIdAndFromDateAndEndDate(Integer empId, String extDateFromStr, String extDateEndStr) {
            StringBuilder sb = new StringBuilder("select count(*) from jiaban a left join employee e on e.empno = a.empNo where e.id = #{empId} ");
            if (extDateFromStr != null && extDateFromStr.length() > 0 && extDateFromStr != null && extDateFromStr.length() > 0) {
                sb.append(" and (a.extDateFrom  <= #{extDateFromStr} and a.extDateEnd  >= #{extDateFromStr}");
                sb.append(" or a.extDateFrom  <= #{extDateEndStr} and a.extDateEnd  >= #{extDateEndStr})");
            }
            return sb.toString();
        }

        public String checkBeginLeaveRight(Leave leave) {
            StringBuilder sb = new StringBuilder("select count(*) from leavedata a where a.employeeid = #{employeeId}");
            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and (a.beginleave  <= #{beginLeaveStr} and a.endleave  >= #{beginLeaveStr}");
                sb.append(" or a.beginleave  <= #{endLeaveStr} and a.endleave  >= #{endLeaveStr})");
            }
            return sb.toString();
        }

        public String queryPositionByNameA(Position position) {
            StringBuilder sb = new StringBuilder("select *,#{currentPage} as currentPage from position where 1=1 ");
            if (position.getPositionName() != null && position.getPositionName().trim().length() > 0) {
                sb.append(" and positionName like  CONCAT('%',#{positionName},'%') ");
            }
            if (position.getPositionLevel() != null && position.getPositionLevel().trim().length() > 0) {
                sb.append(" and positionLevel = #{positionLevel}");
            }
            sb.append(" order by positionName desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String findAllPositionByConditionCount(Position position) {
            StringBuilder sb = new StringBuilder("select count(*) from position where 1=1 ");
            if (position.getPositionName() != null && position.getPositionName().trim().length() > 0) {
                sb.append(" and positionName like  CONCAT('%',#{positionName},'%') ");
            }
            if (position.getPositionLevel() != null && position.getPositionLevel().trim().length() > 0) {
                sb.append(" and positionLevel = #{positionLevel}");
            }
            return sb.toString();
        }

        public String isClockInAlready(String openId, String dateStr, String titleName) {
            StringBuilder sb = new StringBuilder();
            if ("clockInDateAMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and clockInDate = #{dateStr} and clockInDateAMOn <> ''");
            } else if ("clockInDatePMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and clockInDatePMOn <> ''");
            } else if ("clockInDateNMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and clockInDateNMOn <> ''");
            } else if ("amOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and   clockInDate = #{dateStr} and amOnUrl <> ''");
            } else if ("pmOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and pmOnUrl <> ''");
            } else if ("nmOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and nmOnUrl <> ''");
            }
            return sb.toString();
        }


        public String queryZKOUTDataByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tzk.yearMonth,\n" +
                    "\tzk.EnrollNumber AS enrollNumber1,\n" +
                    "\tzk.dateStr as dateStr,\n" +
                    "\tzk.timeStr,\n" +
                    "\tee.`name`,\n" +
                    "\tt.deptname FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tzk.yearMonth,\n" +
                    "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\t\t\tzk.Date AS dateStr,\n" +
                    "\t\t\tzk.timeStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tzhongkongbean zk where (EnrollNumber REGEXP '[^0-9.]') <> 0\n" +
                    "\t\tORDER BY\n" +
                    "\t\t\tzk.yearMonth DESC,\n" +
                    "\t\t\tzk.EnrollNumber ASC limit 0,10 \n" +
                    "\t) zk \n" +
                    " left JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
                    "left JOIN employee ee ON zke.empNo = ee.empNo\n" +
                    " left JOIN dept t ON ee.deptid = t.id where 1=1 ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by ee.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by zk.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by t.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("enrollNumber".equals(employee.getSortByName())) {
                    sb.append(" order by zk.enrollNumber ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("dateStr".equals(employee.getSortByName())) {
                    sb.append(" order by zk.dateStr ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("timeStr".equals(employee.getSortByName())) {
                    sb.append(" order by zk.timeStr ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tzk.yearMonth DESC,\n" +
                        "\tzk.EnrollNumber ASC ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryZKOUTDataByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\tzhongkongbean zk\n" +
                    "LEFT JOIN qyweixinbd zke ON zk.EnrollNumber = zke.userid\n" +
                    "LEFT JOIN employee ee ON zke.empNo = ee.empno\n" +
                    "LEFT JOIN dept t ON ee.deptid = t.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

        public String findAllLeaveA(String monday, String tuesday, String wednesday, String thurday, String today) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tld.beginleave AS beginleave,\n" +
                    "\tld.beginleave AS beginleaveStr,\n" +
                    "\tld.endleave AS endleave,\n" +
                    "\tld.endleave AS endleaveStr,\n" +
                    "\tld.leavelong AS leavelong,\n" +
                    "\tld.leaveDescrip AS leaveDescrip,\n" +
                    "\tee.empno,\n" +
                    "\tee.`name`\n" +
                    "FROM\n" +
                    "\tleavedata ld\n" +
                    "LEFT JOIN employee ee ON ld.employeeid = ee.id\n" +
                    "WHERE ld.type=2 " +
                    " (AND date_format(ld.beginleave, '%Y-%m-%d') <= #{monday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{monday} \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{tuesday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{tuesday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{wednesday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{wednesday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{thurday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{thurday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{today} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{today})) ");
            return sb.toString();
        }

        public String getAllKQDataByYearMonthDays(List<OutClockIn> dateStr) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tifnull(ee.`name`,ls.name) as name,\n" +
                    "\tifnull(ee.empno,'') as empno,\n" +
                    "\tt.deptname,\n" +
                    "\tn.positionName,\n" +
                    "\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\tzk.Date AS dateStr,\n" +
                    "\tzk.timeStr,\n" +
                    "\tzk.yearMonth\n" +
                    "FROM\n" +
                    "\tzhongkongbean zk\n" +
                    "LEFT JOIN qyweixinbd zke ON zk.EnrollNumber = zke.userid\n" +
                    "LEFT JOIN employee ee ON ee.empno = zke.empNo\n" +
                    "LEFT JOIN linshiemp ls ON ls.name = zke.name\n" +
                    "LEFT JOIN dept t ON t.id = ee.deptId\n" +
                    "LEFT JOIN position n ON n.id = ee.positionId where 1=1 \n");
            if (dateStr != null && dateStr.size() > 0) {
                if (dateStr.size() == 1) {
                    sb.append(" and zk.date in ('" + dateStr.get(0).getClockInDateStr() + "')");
                } else if (dateStr.size() >= 2) {
                    sb.append(" and zk.date in (");
                    for (int i = 0; i < dateStr.size() - 1; i++) {
                        sb.append("'" + dateStr.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStr.get(dateStr.size() - 1).getClockInDateStr() + "')");
                }
            }
            sb.append(" ORDER BY zk.EnrollNumber ASC");
            return sb.toString();
        }


        public String updateRenShiByDates(List<String> dateStrs) {
            StringBuilder sb = new StringBuilder("update monthkqinfo set renShiChecked = 1 where ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  yearMonth in ('" + dateStrs.get(0) + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  yearMonth in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i) + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1) + "')");
                }
            }
            return sb.toString();
        }

        public String saveCheckKQBeanListByDates(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder("update kqbean set rensheCheck = 1 where ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  dateStr in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  dateStr in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            return sb.toString();
        }


        public String getAlReadyCheckDatestr(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tGROUP_CONCAT(dateStr)\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tdateStr AS dateStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tkqbean\n" +
                    "\t\tWHERE rensheCheck = 1 and \n");

            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  dateStr in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  dateStr in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            sb.append(" GROUP BY\n" +
                    "\t\t\tdateStr\n" +
                    "\t) AS a ");

            return sb.toString();
        }


        public String getJiaBanDanByDates(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tps.empNo AS havePinShi,\n" +
                    "\tkb.yearMonth,\n" +
                    "\tkb.dateStr,\n" +
                    "\tkb. WEEK,\n" +
                    "\tkb.timeStr,\n" +
                    "\tkb.clockResult,\n" +
                    "\tkb.clockResultByRenShi,\n" +
                    "\tkb.extWorkHours,\n" +
                    "\tkb.remark,\n" +
                    "\tkb.aOnTime,\n" +
                    "\tkb.aOffTime,\n" +
                    "\tkb.pOnTime,\n" +
                    "\tkb.pOffTime,\n" +
                    "\tkb.extWorkOnTime,\n" +
                    "\tkb.extWorkOffTime,\n" +
                    "\tkb.rensheCheck,\n" +
                    "\tee.empno,\n" +
                    "\tee.`name` AS nameReal,\n" +
                    "\tn.positionLevel AS positionLevel " +
                    "FROM\n" +
                    "\tkqbean kb\n" +
                    "LEFT JOIN qyweixinbd zke ON kb.enrollNumber = zke.userid\n" +
                    "LEFT JOIN employee ee ON ee.empno = zke.empNo " +
                    "LEFT JOIN position n ON n.id = ee.positionId " +
                    "LEFT JOIN pinshijiabanbgs ps ON ps.empNo = ee.empno " +
                    "where  ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  kb.dateStr in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  kb.dateStr in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            return sb.toString();
        }

        public String getAllYeBanByDateStrs(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder("SELECT empNo,date as dateStr FROM yeban  where  ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  date in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  date in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            return sb.toString();
        }

        public String deleteKQBeanByDateStrs(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tifnull(ps.empNo,ifnull(ee.worktype,ls.worktype)) AS havePinShi,\n" +
                    "\tkb.yearMonth,\n" +
                    "\tkb.dateStr,\n" +
                    "\tkb.WEEK,\n" +
                    "\tkb.timeStr,\n" +
                    "\tkb.clockResult,\n" +
                    "\tkb.clockResultByRenShi,\n" +
                    "\tkb.extWorkHours,\n" +
                    "\tkb.remark,\n" +
                    "\tkb.aOnTime,\n" +
                    "\tkb.aOffTime,\n" +
                    "\tkb.pOnTime,\n" +
                    "\tkb.pOffTime,\n" +
                    "\tkb.extWorkOnTime,\n" +
                    "\tkb.enrollNumber,\n" +
                    "\tkb.extWorkOffTime,\n" +
                    "\tkb.rensheCheck,\n" +
                    "\tifnull(ee.empno,ifnull(ls.name,'ABABAB')) as empno,\n" +
                    "\tifnull(ee.workType,ls.workType) as workType,\n" +
                    "\tifnull(ee.`name`,ls.name) AS nameReal,\n" +
                    "\tifnull(ee.id,ls.id) AS empId,\n" +
                    "\tifnull(n.positionLevel,nn.positionLevel) AS positionLevel " +
                    "FROM\n" +
                    "\tkqbean kb\n" +
                    "LEFT JOIN qyweixinbd zke ON kb.enrollNumber = zke.userid\n" +
                    "LEFT JOIN employee ee ON ee.empno = zke.empNo " +
                    "LEFT JOIN linshiemp ls ON ls.name = zke.name " +
                    "LEFT JOIN position n ON n.id = ee.positionId " +
                    "LEFT JOIN position nn ON nn.id = ls.positionId " +
                    "LEFT JOIN pinshijiabanbgs ps ON ps.empNo = ee.empno  ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  where   kb.dateStr in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("   where   kb.dateStr in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            return sb.toString();
        }


        public String deleteKQBeanOlderDateByDates(List<OutClockIn> dateStrs) {
            StringBuilder sb = new StringBuilder(" delete FROM kqbean  where ");
            if (dateStrs != null && dateStrs.size() > 0) {
                if (dateStrs.size() == 1) {
                    sb.append("  dateStr in ('" + dateStrs.get(0).getClockInDateStr() + "')");
                } else if (dateStrs.size() >= 2) {
                    sb.append("  dateStr in (");
                    for (int i = 0; i < dateStrs.size() - 1; i++) {
                        sb.append("'" + dateStrs.get(i).getClockInDateStr() + "'" + ",");
                    }
                    sb.append("'" + dateStrs.get(dateStrs.size() - 1).getClockInDateStr() + "')");
                }
            }
            return sb.toString();
        }


        public String findAllZKAndOutDataCondition(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tzk.yearMonth,\n" +
                    "\tzk.EnrollNumber AS enrollNumber1,\n" +
                    "\tzk.dateStr AS dateStr,\n" +
                    "\tzk.timeStr,\n" +
                    "\tee.`name`,\n" +
                    "\tt.deptname \n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tzk.yearMonth,\n" +
                    "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\t\t\tzk.Date AS dateStr,\n" +
                    "\t\t\tzk.timeStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tzhongkongbean zk\n" +
                    "\t\tWHERE\n" +
                    "\t\t\t(\n" +
                    "\t\t\t\tEnrollNumber REGEXP '[^0-9.]'\n" +
                    "\t\t\t) <> 0 ");
            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }
            sb.append("\t)  zk\n" +
                    "  JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
                    "  JOIN employee ee ON zke.empNo = ee.empNo\n" +
                    " JOIN dept t ON ee.deptid = t.id" +
                    " where 1=1");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }


            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by ee.name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.yearMonth ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(kqBean.getSortByName())) {
                    sb.append(" order by t.deptName ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("dateStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.dateStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("timeStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.timeStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tzk.yearMonth DESC,\n" +
                        "\tzk.enrollNumber ASC ");
            }

            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String findAllLSZKAndOutDataCondition(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tzk.yearMonth,\n" +
                    "\tzk.EnrollNumber AS enrollNumber1,\n" +
                    "\tzk.dateStr AS dateStr,\n" +
                    "\tzk.timeStr,\n" +
                    "\tee.`name`,\n" +
                    "\tt.deptname \n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tzk.yearMonth,\n" +
                    "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\t\t\tzk.Date AS dateStr,\n" +
                    "\t\t\tzk.timeStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tzhongkongbean zk\n" +
                    "\t\tWHERE\n" +
                    "\t\t\t(\n" +
                    "\t\t\t\tEnrollNumber REGEXP '[^0-9.]'\n" +
                    "\t\t\t) <> 0 ");
            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }
            sb.append("\t)  zk\n" +
                    " JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
                    " JOIN linshiemp ee ON zke.name = ee.name\n" +
                    " JOIN dept t ON ee.deptid = t.id" +
                    " where 1=1");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }


            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by ee.name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.yearMonth ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(kqBean.getSortByName())) {
                    sb.append(" order by t.deptName ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("dateStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.dateStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("timeStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by zk.timeStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tzk.yearMonth DESC,\n" +
                        "\tzk.enrollNumber ASC ");
            }

            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String findAllZKAndOutDataConditionCount(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tzk.yearMonth,\n" +
                    "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\t\t\tzk.Date AS dateStr,\n" +
                    "\t\t\tzk.timeStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tzhongkongbean zk\n" +
                    "\t\tWHERE\n" +
                    "\t\t\t(\n" +
                    "\t\t\t\tEnrollNumber REGEXP '[^0-9.]'\n" +
                    "\t\t\t) <> 0 ");
            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }
            sb.append("\t)  zk\n" +
                    " JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
                    " JOIN employee ee ON zke.empNo = ee.empNo\n" +
                    " JOIN dept t ON ee.deptid = t.id" +
                    " where 1=1");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }


        public String findAllLSZKAndOutDataConditionCount(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tzk.yearMonth,\n" +
                    "\t\t\tzk.EnrollNumber AS enrollNumber,\n" +
                    "\t\t\tzk.Date AS dateStr,\n" +
                    "\t\t\tzk.timeStr\n" +
                    "\t\tFROM\n" +
                    "\t\t\tzhongkongbean zk\n" +
                    "\t\tWHERE\n" +
                    "\t\t\t(\n" +
                    "\t\t\t\tEnrollNumber REGEXP '[^0-9.]'\n" +
                    "\t\t\t) <> 0 ");
            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }
            sb.append("\t)  zk\n" +
                    " JOIN qyweixinbd zke ON zke.userid = zk.enrollNumber\n" +
                    " JOIN linshiemp ee ON zke.name = ee.name\n" +
                    " JOIN dept t ON ee.deptid = t.id" +
                    " where 1=1");
            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }


            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }


        public String findAllKQBDataCondition(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tkq.id,\n" +
                    "\tifnull(ee.`name`,ls.name) AS NAME,\n" +
                    "\tdeptname AS deptName,\n" +
                    "\tkq.enrollNumber,\n" +
                    "\tkq.yearMonth,\n" +
                    "\tkq.dateStr,\n" +
                    "\tkq.`week`,\n" +
                    "\tkq.timeStr,\n" +
                    "\tIFNULL(\n" +
                    "\t\tkq.clockResultByRenShi,\n" +
                    "\t\tkq.clockResult\n" +
                    "\t) AS clockResult,\n" +
                    "\tkq.extWorkHours,\n" +
                    "\tkq.remark,\n" +
                    "\tkq.aOnTime,\n" +
                    "\tkq.aOffTime,\n" +
                    "\tkq.pOnTime,\n" +
                    "\tkq.pOffTime,\n" +
                    "\tkq.extWorkOnTime,\n" +
                    "\tkq.extWorkOffTime,\n" +
                    "\tkq.rensheCheck\n" +
                    "FROM\n" +
                    "\t (select * from kqbean where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            if (kqBean.getClockDates() != null && kqBean.getClockDates().size() > 0) {
                if (kqBean.getClockDates().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDates().get(0) + "')");
                } else if (kqBean.getClockDates().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDates().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDates().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDates().get(kqBean.getClockDates().size() - 1) + "')");
                }
            }

            sb.append(" ) AS kq\n" +
                    " JOIN  qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
                    " JOIN employee ee ON ee.empno = zke.empNo \n" +
                    " JOIN linshiemp ls ON ls.name = ee.name \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }


            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ls.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by ee.name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.yearMonth ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(kqBean.getSortByName())) {
                    sb.append(" order by t.deptName ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("dateStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.dateStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("week".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.week ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("timeStr".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.timeStr ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockResult".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.clockResult ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("extWorkHourselse".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.extWorkHourselse ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.remark ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("aOnTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.aOnTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("aOffTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.aOffTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("pOnTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.pOnTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("pOffTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.pOffTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("extWorkOnTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.extWorkOnTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("extWorkOffTime".equals(kqBean.getSortByName())) {
                    sb.append(" order by kq.extWorkOffTime ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tkq.yearMonth DESC,\n" +
                        "\tkq.enrollNumber ASC ");
            }

            sb.append("  limit #{currentPageTotalNum},#{pageSize}");

            return sb.toString();

        }

        public String findAllKQBDataConditionCount(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\t (select * from kqbean where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            if (kqBean.getClockDates() != null && kqBean.getClockDates().size() > 0) {
                if (kqBean.getClockDates().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDates().get(0) + "')");
                } else if (kqBean.getClockDates().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDates().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDates().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDates().get(kqBean.getClockDates().size() - 1) + "')");
                }
            }

            sb.append(" ) AS kq\n" +
                    " JOIN qyweixinbd zke ON zke.userid = kq.enrollNumber\n" +
                    " JOIN employee ee ON ee.empno = zke.empNo \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");

            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            return sb.toString();
        }

        public String queryMKDataByCondition(Employee kqBean) {
            StringBuilder sb = new StringBuilder("select * from (SELECT kq.*,t.deptName as deptName " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN employee ee ON ee.empno = kq.empNo \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }


            sb.append("union SELECT kq.*,t.deptName as deptName " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN linshiemp ee ON ee.name = kq.name \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");


            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");
            }

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            sb.append(" ) a");

            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tempNo asc ");
            }

            sb.append(" limit #{currentPageTotalNum},#{pageSize}");

            return sb.toString();
        }


        public String queryMKDataByConditionBDF(Employee kqBean) {


            StringBuilder sb = new StringBuilder(" SELECT kq.*,t.deptName as deptName,ee.workType as workType " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN linshiemp ee ON ee.name = kq.name \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");


            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");
            }

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }


            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tempNo asc ");
            }

            sb.append(" limit #{currentPageTotalNum},#{pageSize}");

            return sb.toString();
        }


        public String queryMKDataByConditionABC(Employee kqBean) {
            StringBuilder sb = new StringBuilder("SELECT kq.*,t.deptName as deptName " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN employee ee ON ee.empno = kq.empNo \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            if (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod()) && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null) {
                if ("name".equals(kqBean.getSortByName())) {
                    sb.append(" order by name ");
                    if ("asc".equals(kqBean.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(kqBean.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" ORDER BY\n" +
                        "\tempNo asc ");
            }

            sb.append(" limit #{currentPageTotalNum},#{pageSize}");

            return sb.toString();
        }

        public String queryMKDataByConditionCount(Employee kqBean) {
            StringBuilder sb = new StringBuilder("select count(*) from (SELECT kq.*,t.deptName as deptName " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN employee ee ON ee.empno = kq.empNo \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");

            }

            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");

            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }


            sb.append("union SELECT kq.*,t.deptName as deptName " +
                    "FROM\n" +
                    "\t (select * from monthkqinfo where 1=1 ");

            if (kqBean.getClockDateArray() != null && kqBean.getClockDateArray().size() > 0) {
                if (kqBean.getClockDateArray().size() == 1) {
                    sb.append(" and yearMonth in ('" + kqBean.getClockDateArray().get(0) + "')");
                } else if (kqBean.getClockDateArray().size() >= 2) {
                    sb.append(" and yearMonth in (");
                    for (int i = 0; i < kqBean.getClockDateArray().size() - 1; i++) {
                        sb.append("'" + kqBean.getClockDateArray().get(i) + "'" + ",");
                    }
                    sb.append("'" + kqBean.getClockDateArray().get(kqBean.getClockDateArray().size() - 1) + "')");
                }
            }


            sb.append(" ) AS kq\n" +
                    " JOIN linshiemp ee ON ee.name = kq.name \n" +
                    " JOIN dept t ON t.id = ee.deptId\n" +
                    "where 1=1 ");


            if (kqBean.getNameIds2() != null && kqBean.getNameIds2().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds2().toString(), "[]") + ") ");
            }

            if (kqBean.getNameIds() != null && kqBean.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(kqBean.getNameIds().toString(), "[]") + ") ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getEmpNo() != null && kqBean.getEmpNo() != "" && kqBean.getEmpNo().trim().length() > 0) {
                sb.append(" and ee.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (kqBean.getDeptIds() != null && kqBean.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(kqBean.getDeptIds().toString(), "[]") + ") ");
            }

            if (kqBean.getWorkTypes() != null && kqBean.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(kqBean.getWorkTypes().toString(), "[]") + ") ");
            }

            if (kqBean.getPositionIds() != null && kqBean.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(kqBean.getPositionIds().toString(), "[]") + ") ");
            }

            sb.append(" ) a");

            return sb.toString();
        }
    }

}
