package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:40
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:40
 */
@Repository
@Mapper
public interface FileUploadAndDownMapper {

    @Delete({
            "<script>",
            "delete",
            "from filemanurl",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteUrlByHeadIdAndItemIds(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from filemanupdaterecord",
            "where fileurlid in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteRecordByUrIds(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from filemanright",
            "where fileurlid in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteRightItemIds(@Param("ids") List<Integer> ids);

    @Select({
            "<script>",
            "SELECT a.filename,a.updatedate,a.updatereason,b.fullname as updateFullName,a.updatereason,0 as typeK " +
                    " from filemanupdaterecord a left join userinfo b on a.updateuid = b.uid where a.fileurlid in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "ORDER BY a.filename,a.updatedate ",
            "</script>"
    })
    List<ShowUpdateDownRecord> getFileModifyRecordByUrlIds(@Param("ids") List<Integer> ids);

    @Select({
            "<script>",
            "SELECT a.filename AS filename,a.downdate AS downdate,b.fullname AS downFullName,a.ipAddr AS ipAddr,a.ipName AS ipName,1 AS typeK " +
                    " from filemandownrecord a left join userinfo b on a.downuid = b.uid where a.fileurlid in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "ORDER BY a.filename,a.downdate ",
            "</script>"
    })
    List<ShowUpdateDownRecord> getFileDownRecordByUrlIds(@Param("ids") List<Integer> ids);

    @Select("select count(*) from filemanurl where fileInfoId = #{id}")
    int getItemCountByHeadId(Integer id);

    @Select("select extension from extension order by extension asc ")
    List<String> findAllExtension();

    @Delete("delete from filemanfileinfo where id = #{id}")
    void deleteFileManFileInfo(Integer id);

    @Update("update filemanfileinfo set totalfilesnum = #{totalfilesnum} where id = #{id}")
    void updateFileInfoTotalFilesNum(Integer id, int totalfilesnum);

    @Select("select fio.username,fio.ordernum as orderNo,fio.extinfo1 as salor,fu.orginname as fileName,fu.uptime as lastUpdateTime,fu.singlefileupdatenum as singleFileUpdateNum,  fio.filedescribtion as filedescribtion,\n" +
            "  fio.remark as remark,fio.projectname as projectName from filemanurl fu " +
            "left join filemanfileinfo fio on fu.fileInfoId = fio.id " +
            "where " +
            " fu.username= #{userName}  " +
            "and fio.extinfo1 = #{salor} " +
            "and fio.ordernum = #{orderNo} ")
    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(String userName, String salor, String orderNo);

    @Insert("insert into FilemanRight(uId,userName,fileName,createUser,createTime,fileInfoId,fileUrlId,opRight)" +
            " values (#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{fileInfoId},#{fileUrlId},#{opRight})")
    void addFilemanRightDataByUpload(FilemanRight filemanRight);

    @Insert("INSERT INTO filemandownrecord (\n" +
            "\tipAddr,\n" +
            "\tipName,\n" +
            "\tfilename,\n" +
            "\tordernum,\n" +
            "\tdownuid,\n" +
            "\tdowndate,\n" +
            "\tfileurlid\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t #{ipAddr},\n" +
            "\t\t #{ipName},\n" +
            "\t\t#{fileName},\n" +
            "\t\t#{orderNum},\n" +
            "\t\t#{downUid},\n" +
            "\t\t #{downDate},\n" +
            "\t\t#{fileurlid}\n" +
            "\t)")
    void saveFilemanDownRecord(FilemanDownRecord record);

    @Insert("insert into FilemanRight(uId,userName,fileName,createUser,createTime,fileInfoId,fileUrlId,opRight)" +
            " values (#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{fileInfoId},#{fileUrlId},#{opRight})")
    void saveFileManRightBySampleRightBean(FilemanRight filemanRight);

    @Insert("insert into FilemanUrl(uid,fileInfoId,userName,orginname,opRight,logur1,uptime) " +
            "values (#{uId},#{fileInfoId},#{userName},#{orginName},#{opRight},#{logur1},#{upTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addfilemanUrlByUpload(FilemanUrl filemanUrl);

    @Insert("insert into FileManFileInfo(uid,username,filename,createuser,createtime,extinfo1,ordernum,projectname,totalFilesNum,filedescribtion,remark) " +
            "values (#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{extInfo1},#{orderNum},#{projectName},#{totalFilesNum},#{filedescribtion},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addfileManFileDataByUpload(FileManFileInfo fileManFile);

    @Select("SELECT ffi.id, " +
            "                      IFNULL(fo.fullname,\n" +
            "\t\to.fullname) as lastUpdator, " +
            "                     IFNULL(fmu.updateTime,fmu.uptime) as lastUpdateTime, " +
            "                      fmu.orginname AS fileName, " +
            "                      ffi.extinfo1 AS salor, " +
            "                     ffi.ordernum AS orderNo, " +
            "                     fmu.modifyReason AS modifyReason, " +
            "                    ffi.projectname AS projectName, " +
            "                      fmu.singlefileupdatenum AS singleFileUpdateNum, " +
            "                    fmu.logur1 AS urlAddr, " +
            "                      ffi.filedescribtion as filedescribtion, " +
            "                      ffi.remark as remark,fmu.id as fileUrlId " +
            "                     FROM  " +
            "                     filemanfileinfo ffi " +
            "                     LEFT JOIN filemanurl fmu ON ffi.id = fmu.fileInfoId " +
            " left join userinfo o on ffi.username = o.username\n" +
            " left join userinfo fo on fo.username = ffi.updateuser" +
            " ORDER BY " +
            "   ffi.createtime DESC limit #{currentPageTotalNum},#{PageSize} ")
    List<DownloadView> findAllFileUrlByCondition(Integer uid, int currentPageTotalNum, int PageSize);


    @Select("SELECT count(ffi.id) as totalnum " +
            "                     FROM  " +
            "                     filemanfileinfo ffi " +
            "                     LEFT JOIN filemanurl fmu ON ffi.id = fmu.fileInfoId ")
    int findAllFileUrlByConditionCount(Integer uId);

    @Select("SELECT    \n" +
            "\ta.ordernum as orderNo,\n" +
            "\ta.extinfo1 as salor,\n" +
            "\tc.fullname as engineer,\n" +
            "\tIFNULL(a.updatetime, a.createtime) as createTime,\n" +
            "\tb.uid as oprighter,\n" +
            "  b.opright as opRight, \n" +
            "  a.id as id \n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
            "left join userinfo c on c.username = b.username  \n" +
            " where b.uid is null GROUP BY a.ordernum " +
            "ORDER BY a.createtime DESC limit #{currentPageTotalNum},#{PageSize} ")
    List<DownloadView> findAllUploadFileByCondition(Integer uId, int currentPageTotalNum, int PageSize);

    @Select("SELECT\n" +
            "\ta.extinfo1 as salor,\n" +
            "\ta.createuser as engineer,\n" +
            "\ta.ordernum as orderNo,\n" +
            "\tIFNULL(a.updatetime, a.createtime) as createTime,\n" +
            "\tb.uid as oprighter,\n" +
            "  b.opright as opRight,c.fullName as fullName    \n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
            "left join userinfo c on c.uid = b.uid\n" +
            " where a.ordernum = #{orderNo} " +
            "ORDER BY a.createtime DESC limit 1  ")
    DownloadView findMessageByOrderNo(String orderNo);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findMessageByOrderNoandUid")
    DownloadView findMessageByOrderNoandUid(String orderNo, Integer uId);

    @Select("select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor," +
            "    ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.createtime as lastUpdateTime" +
            ",ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " +
            "    from filemanfileinfo ffi" +
            "    LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" +
            "    and ffi.uid = #{uId}  order by ffi.createtime desc ")
    List<DownloadView> findAllUploadFileByUserId(Integer uId);


    @Select("select a.filename,a.updatedate,a.updatereason,b.fullname as updateFullName,a.updatereason,0 as typeK from filemanupdaterecord a left join userinfo b on a.updateuid = b.uid where a.fileurlid = #{urlId}")
    List<ShowUpdateDownRecord> getFileModifyRecordByUrlId(Integer urlId);

    @Select("SELECT\n" +
            "\ta.filename AS filename,a.downdate AS downdate,b.fullname AS downFullName,a.ipAddr AS ipAddr,a.ipName AS ipName,1 AS typeK\n" +
            "FROM\n" +
            "\tfilemandownrecord a\n" +
            "LEFT JOIN userinfo b ON a.downuid = b.uid\n" +
            "WHERE\n" +
            "\ta.fileurlid =#{urlId}\n" +
            "ORDER BY\n" +
            "\ta.filename,\n" +
            "\ta.downdate")
    List<ShowUpdateDownRecord> getFileDownRecordByUrlId(Integer urlId);

    @Select("select count(ffi.id) as recordCount " +
            " FROM\n" +
            "\t filemanfileinfo ffi ")
    int findAllUploadFileCountByUserId(Integer uId);

    @Select("select * from  filemanurl where orginname = #{orginName}")
    List<FilemanUrl> findIsExistFile(String orginName);

    @Select("select * from  filemanurl where orginname = #{orginName} and fileInfoId = #{fileInfoId} ")
    FilemanUrl findFileUrlByFileInFoDataAndFileName(String orginName, Integer fileInfoId);


    @Select("SELECT\n" +
            "\ta.*\n" +
            "FROM\n" +
            "\tfilemanurl a\n" +
            "LEFT JOIN filemanfileinfo b ON a.fileInfoId = b.id\n" +
            "WHERE\n" +
            "\tb.ordernum = #{orderNo}")
    List<FilemanUrl> getFileUrlByOrderNo(String orderNo);

    @Select("select * from  filemanurl where fileInfoId = #{id}")
    List<FilemanUrl> findFileUrlByFileInFoData(Integer id);


    @Select("select * from  filemanfileinfo where createuser= #{createUser} and ordernum= #{orderNum} and extinfo1= #{extInfo1} ")
    List<FileManFileInfo> isSameOrderNoandOtherMessage(@Param("createUser") String createUser, @Param("orderNum") String orderNum, @Param("extInfo1") String extInfo1);

    @Select("select ordernum from filemanfileinfo order by ordernum desc LIMIT #{currentPageTotalNum},#{pageSize}")
    List<String> findAllOrderNum(int currentPageTotalNum, int pageSize);

    @Select("select mu.* from filemanurl mu LEFT JOIN filemanfileinfo fi on mu.fileInfoId = fi.id\n" +
            "where mu.orginname = #{fileName}  and fi.ordernum = #{orderNo} limit 1 ")
    FilemanUrl getUrlByFileNameAndOrderNo(String orderNo,String fileName);

    @Select("select count(*) from filemanfileinfo  ")
    int findAllOrderNumCount();

    @Select("select * from userinfo ")
    List<UserInfo> findAllUserInfo();

    @Select(" select * from employee ")
    List<Employee> findAllSalor();

    @Select(" select e.* from employee e LEFT JOIN  dept d on e.deptId = d.id\n" +
            " where d.deptname in (\"销售中心\",\"项目中心\") ")
    List<Employee> findAllSalorByDeptName();

    @Select("select * from filemanright where id = #{fileurlid}  and uid = #{uid} ")
    FilemanRight findFileRightByUserIdandFileUrlId(Integer uid, Integer fileurlid);

    @Select("SELECT\n" +
            "\ta.opright as opright\n" +
            "FROM\n" +
            "\tfilemanright a\n" +
            "LEFT JOIN filemanfileinfo b ON a.fileInfoId = b.id\n" +
            "where b.ordernum = #{orderNo} \n" +
            "and a.filename= #{fileName} \n" +
            "and a.uid = #{uId} \n")
    FilemanRight getFileRightByOrderNoUidfileName(String orderNo, String fileName, Integer uId);

    @Select("SELECT\n" +
            "a.filename,a.updatedate,a.updatereason,b.fullname as updateFullName,a.updatereason,0 as typeK" +
            " from filemanupdaterecord a left join userinfo b on a.updateuid = b.uid where" +
            "\t a.ordernum = #{orderNo}\n" +
            "AND a.filename = #{fileName} order by a.filename,a.updatedate")
    List<ShowUpdateDownRecord> getFileModifyRecordByOrderNoAndFileName(String orderNo, String fileName);

    @Select("SELECT\n" +
            "\ta.filename AS filename,\n" +
            "\ta.downdate AS downdate,\n" +
            "\tb.fullname AS downFullName,\n" +
            "\ta.ipAddr AS ipAddr,\n" +
            "\ta.ipName AS ipName,\n" +
            "\t1 AS typeK\n" +
            "FROM\n" +
            "\tfilemandownrecord a\n" +
            "LEFT JOIN userinfo b ON a.downuid = b.uid\n" +
            "WHERE\n" +
            "\ta.ordernum = #{orderNo}\n" +
            "AND a.filename = #{fileName}\n" +
            "ORDER BY\n" +
            "\ta.filename,\n" +
            "\ta.downdate")
    List<ShowUpdateDownRecord> getFileDownRecordByOrderNoAndFileName(String orderNo, String fileName);

    @Select("SELECT\n" +
            " a.filename,a.updatedate,a.updatereason,b.fullname as updateFullName,a.updatereason,0 as typeK " +
            "from filemanupdaterecord a left join userinfo b on a.updateuid = b.uid where a.ordernum " +
            "= #{orderNo} order by a.filename,a.updatedate")
    List<ShowUpdateDownRecord> getFileModifyRecordByOrderNo(String orderNo);


    @Select("SELECT\n" +
            "\ta.filename AS filename,\n" +
            "\ta.downdate AS downdate,\n" +
            "\tb.fullname AS downFullName,\n" +
            "\ta.ipAddr AS ipAddr,\n" +
            "\ta.ipName AS ipName,\n" +
            "\t1 AS typeK" +
            "\n" +
            "FROM\n" +
            "\tfilemandownrecord a\n" +
            "LEFT JOIN userinfo b ON a.downuid = b.uid\n" +
            "WHERE\n" +
            "\ta.ordernum = #{orderNo}\n" +
            "ORDER BY\n" +
            "\ta.filename,\n" +
            "\ta.downdate")
    List<ShowUpdateDownRecord> getFileDownRecordByOrderNo(String orderNo);

    @Select("SELECT\n" +
            "\tb.orginname AS orginName\n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanurl b ON a.id = b.fileInfoId\n" +
            "WHERE\n" +
            "\ta.ordernum = #{orderNo}\n" +
            "AND a.extinfo1 = #{salor}\n" +
            "AND a.uid = #{uId}\n" +
            "AND b.orginname = #{fileName}")
    FilemanUrl getFileUrlByOrderNoSo(String orderNo, String salor, Integer uId, String fileName);

    @Select("SELECT\n" +
            "\tb.orginname AS orginName\n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanurl b ON a.id = b.fileInfoId\n" +
            "WHERE\n" +
            "\ta.ordernum = #{orderNo}\n" +
            "AND a.extinfo1 = #{salor}\n" +
            "AND a.uid = #{uId} ")
    List<FilemanUrl> getFileUrlByOrderNoSalorDisgner(String orderNo, String salor, Integer uId);

    @Select("SELECT\n" +
            "\tfr.uid,fr.id as fileRightId,fr.filename,fi.extinfo1,fi.ordernum\n" +
            "FROM\n" +
            "\tfilemanright fr\n" +
            "LEFT JOIN filemanfileinfo fi ON fr.fileInfoId = fi.id  where fr.filename = #{fileName} " +
            " and fi.extinfo1 = #{salor} and fi.ordernum = #{orderNum} and fr.uid = #{uId} order by fr.filename desc   ")
    DownloadView findFielRightFileByUidOrderNoSalorFileName(String fileName, String salor, String orderNum, Integer uId);

    @Delete("delete from filemanright where id = #{id} ")
    void deleteFileRightPrivileg(Integer id);

    @Select("select * from userinfo where uid = #{uId}")
    UserInfo getUserInfoByUid(Integer uId);

    @Select("select * from  filemanfileinfo where id= #{id} ")
    FileManFileInfo getFileManFileInfoByInfoId(Integer id);

    @Select("select * from  filemanfileinfo where ordernum= #{orderNo} limit 1 ")
    FileManFileInfo getFileInfoByOrderNo(String orderNo);

    @Select("select * from filemanurl where fileInfoId = #{fileInfoId} and orginname = #{fileName} ")
    FilemanUrl getFileManUrlByFileInfoIdandFileName(Integer fileInfoId, String fileName);

    @Select("select * from filemanright where filename = #{fileName} and fileInfoId= #{fileinfoId} and uid= #{uId} ")
    FilemanRight getFileRightByFileInfoIdAndFileNameAndUid(Integer fileinfoId, String fileName, Integer uId);

    @Select("select * from filemanright where filename = #{fileName} and fileInfoId= #{fileinfoId} and uid is null  ")
    FilemanRight getFileRightByFileInfoIdAndFileNameAndUid2(Integer fileinfoId, String fileName);

    @Select("select * from filemanright where fileInfoId = #{fileInfoId} and fileurlid = #{FileUrlId} and uid= #{Uid} ")
    FilemanRight getFileRightByFileInfoIdAndFileRightIdandUid(Integer fileInfoId, Integer FileUrlId, Integer Uid);

    @Select("select * from filemanright where fileInfoId = #{fileInfoId} and fileurlid = #{FileUrlId} and uid is null ")
    FilemanRight getFileRightByFileInfoIdAndFileUrlId(Integer fileInfoId, Integer FileUrlId);

    @Select("select * from filemanurl where fileInfoId = #{fileinfoId} ")
    List<FilemanUrl> getFileManUrlByFileInfoId(Integer fileinfoId);


    @Select("select b.* from filemanfileinfo a left join filemanurl b on a.id = b.fileInfoId where a.ordernum =#{orderNo}")
    List<FilemanUrl> findAllUrlByOrderNo(String orderNo);

    @Select("select b.logur1 from filemanfileinfo a left join filemanurl b on a.id = b.fileInfoId where a.ordernum =#{orderNo}")
    List<String> findAllUrlByOrderNo2(String orderNo);

    @Update("update filemanright set opright= #{privileflag},updateuser = #{userName},updatetime = #{date} where id = #{fileurlid}  and uid = #{uid} ")
    void updateFileRightPrivileg(Integer uid, Integer fileurlid, String privileflag, String userName, Date date);


    @Update("update filemanright set opright=0 where id = #{id}")
    void updateFileRight0OprightById(Integer id);

    @Update("update filemanright set opright= null where id = #{id}")
    void updateFileRightNullOprightById(Integer id);

    @Insert("INSERT INTO filemanright (uid,fileurlid,createuser,createtime,opright,fileInfoId,filename) VALUES (#{uid},#{fileurlid},#{userName},#{date},#{privileflag},#{fileInfoId},#{fileName}) ")
    void saveFileRightPrivileg(Integer uid, Integer fileurlid, String privileflag, String userName, Date date, Integer fileInfoId, String fileName);

    @Update("update filemanfileinfo set totalFilesNum = #{totalFilesNum} , updatecount = #{updateCount} , updatetime = #{updateTime},filedescribtion = #{filedescribtion},remark=#{remark},projectname=#{projectname}  where id= #{id} ")
    int updateFileManFileInfo(@Param("totalFilesNum") Integer totalFilesNum, @Param("updateCount")
            Integer updateCount, @Param("updateTime") Date updateTime, @Param("id") Integer id,
                              @Param("filedescribtion") String filedescribtion,
                              @Param("remark") String remark, @Param("projectname") String projectname);

    @Update("update filemanurl set opRight= #{privileflag} where fileInfoId = #{filesId}  ")
    void saveOrUpdateFileUrlPrivilege(Integer filesId, String privileflag);

    @Update("update filemanright set opright= #{opright},updateuser= #{updateuser},updatetime=#{updatetime} where id = #{id}  ")
    void upDateFileRightOprightById(Integer id, String opright, String updateuser, Date updatetime);


    @Update("update filemanurl set singleFileUpdateNum= #{singleFileUpdateNum},updateTime = #{updateTime},modifyReason= #{modifyReason},updateUser= #{updateUser} where id = #{id}")
    void updateFileUrlById(Date updateTime, Integer singleFileUpdateNum, String modifyReason, Integer id, String updateUser);

    @Insert("INSERT INTO filemanupdaterecord (ordernum,projectname,filename,updateuid,updatereason,updatedate,fileurlid) VALUES  (#{orderNum},#{projectName},#{fileName},#{updateUid},#{updateReason},#{updateDate},#{fileurlid}) ")
    void saveFilemanUpdateRecord(FilemanUpdateRecord record);

    @Update("update filemanfileinfo set  updatecount = #{updateCount} , updatetime = #{updateTime},updateUser = #{updateUser}  where id= #{id} ")
    void updateFileManFileInfo2(@Param("updateCount") Integer updateCount, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser, @Param("id") Integer id);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllByParaCondition")
    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "getFileRightByUrlIdAndFileInfoIdAnaUid")
    FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUid(Integer urlId, Integer fileInfoId, Integer uId);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "getFileRightByUrlIdAndFileInfoIdAnaUidBack")
    FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUidBack(Integer urlId, Integer fileInfoId);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByOrderNoAndUid2")
    List<FilemanUrl> findAllUrlByOrderNoAndUid2(String orderNo, Integer uId);


    @Select("select * from filemanright where id = #{id}")
    FilemanRight findFileRightById(Integer id);

    @Select("SELECT\n" +
            "\tfm.orginname\n" +
            "FROM\n" +
            "\tfilemanurl fm\n" +
            "LEFT JOIN filemanfileinfo ffi ON fm.fileInfoId = ffi.id\n" +
            "WHERE\n" +
            "\tfm.uId = #{uId} \n" +
            "AND ffi.extinfo1 = #{salor} \n" +
            "AND ffi.ordernum = #{orderNo}  ")
    List<String> findNoFolderUrlNoFileUrl(Integer uId, String salor, String orderNo);

    @Select(" SELECT\n" +
            "\t fm.logur1 \n" +
            "FROM\n" +
            "\tfilemanurl fm\n" +
            "LEFT JOIN filemanfileinfo ffi ON fm.fileInfoId = ffi.id\n" +
            "WHERE\n" +
            "\tfm.uId = #{uId} \n" +
            "AND ffi.extinfo1 = #{salor} \n" +
            "AND ffi.ordernum = #{orderNo}  ")
    List<String> findAllFileUrlLogursByOrderNoandSalorUserName(Integer uId, String salor, String orderNo);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllCountByParaCondition")
    int findAllUploadFileCountByParaCondition(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllFilesByParam")
    List<DownloadView> findAllFilesByCondParam(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllFilesCountByParam")
    int findAllFilesByCondParamCount(DownloadView view);

    @Select("select * from filemanright where uid = #{uId}")
    List<FilemanRight> getFileRightByOperighter(Integer uId);

    @Select("select * from filemanfileinfo where ordernum = #{orderNo}")
    DownloadView findOrderNobyOrderNo(String orderNo);

    @Select("select name from employee")
    List<String> getAllSalorNames();

    @Select("select username from userinfo")
    List<String> getAllDesigners();

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParam")
    List<String> findAllUrlByParamThree(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParamNew")
    List<String> findAllUrlByParamThreeNew(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParamNew2")
    List<DownloadView> findAllUrlByParamThreeNew2(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParamNew5")
    List<DownloadView> findAllUrlByParamThreeNew5(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParamNew2Count")
    int findAllUrlByParamThreeNew2Count(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByThreeParamNew3")
    List<DownloadView> findAllUrlByParamThreeNew3(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByParamManyOrNo")
    List<DownloadView> findAllUrlByParamManyOrNo(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlByParamManyOrNoCount")
    int findAllUrlByParamManyOrNoCount(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllUrlOnlyByParamManyOrNo")
    List<String> findAllUrlOnlyByParamManyOrNo(DownloadView view);

    @Select("SELECT\n" +
            "\ta.createuser AS engineer,\n" +
            "\ta.extinfo1 AS salor,\n" +
            "\ta.ordernum AS orderNo,\n" +
            "\tIFNULL(a.updatetime, a.createtime) AS createTime,\n" +
            "\tb.uid AS oprighter,\n" +
            "\tb.opright AS opRight,\n" +
            "\td.logur1 AS urlAddr,\n" +
            "\ta.id AS id,\n" +
            "\td.id AS fileUrlId, b.filename as fileName \n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
            "LEFT JOIN filemanurl d ON b.fileurlid = d.id\n" +
            "LEFT JOIN userinfo c ON c.uid = b.uid\n" +
            "WHERE\n" +
            "\tb.uid = #{uId} and a.ordernum = #{orderNum}\n" +
            "GROUP BY\n" +
            "\t a.ordernum ,b.fileurlid\n" +
            "\n" +
            "union all \n" +
            "\n" +
            "\n" +
            "SELECT\n" +
            "\ta.createuser AS engineer,\n" +
            "\ta.extinfo1 AS salor,\n" +
            "\ta.ordernum AS orderNo,\n" +
            "\tIFNULL(a.updatetime, a.createtime) AS createTime,\n" +
            "\tb.uid AS oprighter,\n" +
            "\tb.opright AS opRight,\n" +
            "\td.logur1 AS urlAddr,\n" +
            "\ta.id AS id,\n" +
            "\td.id AS fileUrlId, b.filename as fileName \n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
            "LEFT JOIN filemanurl d ON b.fileurlid = d.id\n" +
            "LEFT JOIN userinfo c ON c.uid = b.uid\n" +
            "WHERE\n" +
            "\tb.uid is null and a.ordernum = #{orderNum}\n" +
            "and d.id not in(SELECT\n" +
            "\td.id AS fileUrlId\n" +
            "FROM\n" +
            "\tfilemanfileinfo a\n" +
            "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
            "LEFT JOIN filemanurl d ON b.fileurlid = d.id\n" +
            "LEFT JOIN userinfo c ON c.uid = b.uid\n" +
            "WHERE\n" +
            "\tb.uid = #{uId} and a.ordernum = #{orderNum}\n" +
            "GROUP BY\n" +
            "\t a.ordernum ,b.fileurlid)\n" +
            "GROUP BY\n" +
            "\t a.ordernum ,b.fileurlid")
    List<DownloadView> findAllUrlByOrderNoAndUid1(String orderNum, Integer uId);

    /**
     * 功能描述: 多条件查询 建内部类拼SQL
     *
     * @auther: homey Wong
     * @date: 2019/1/2 0002 下午 6:13
     * @param:
     * @return:
     * @describtion
     */
    class DownloadViewDaoProvider {


        public String getFileRightByUrlIdAndFileInfoIdAnaUidBack(Integer urlId, Integer fileInfoId) {
            StringBuilder sql = new StringBuilder(" SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tfilemanright\n" +
                    "where fileInfoId = #{fileInfoId} \n" +
                    "and fileurlid = #{urlId} \n");

            sql.append(" and uid is null ");

            return sql.toString();
        }


        public String getFileRightByUrlIdAndFileInfoIdAnaUid(Integer urlId, Integer fileInfoId, Integer uId) {
            StringBuilder sql = new StringBuilder(" SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tfilemanright\n" +
                    "where fileInfoId = #{fileInfoId} \n" +
                    "and fileurlid = #{urlId} \n");
            if (uId == 0) {
                sql.append(" and uid is null ");
            } else {
                sql.append(" and uid = #{uId} ");
            }
            return sql.toString();
        }

        public String findAllUrlByThreeParamNew5(DownloadView view) {
            StringBuilder sql = new StringBuilder(" SELECT " +
                    "\ta.createuser AS engineer,\n" +
                    "\ta.extinfo1 AS salor,\n" +
                    "\ta.ordernum AS orderNo,\n" +
                    "\tIFNULL(a.updatetime, a.createtime) AS createTime,\n" +
                    "\tb.uid AS oprighter,\n" +
                    "\tCONCAT(\n" +
                    "\t\tmax(IF (\n" +
                    "\t\t\tLOCATE('2', b.opright) <> 0,\n" +
                    "\t\t\t'2',\n" +
                    "\t\t\t''\n" +
                    "\t\t)),\n" +
                    "\n" +
                    "\tmax(IF (\n" +
                    "\t\tLOCATE('3', b.opright) <> 0,\n" +
                    "\t\t'3',\n" +
                    "\t\t''\n" +
                    "\t)),\n" +
                    "\n" +
                    "max(IF (\n" +
                    "\tLOCATE('4', b.opright) <> 0,\n" +
                    "\t'4',\n" +
                    "\t''\n" +
                    "))\n" +
                    "\t) AS opRight," +
                    "d.logur1 as urlAddr, \n" +
                    "a.id as id \n" +
                    "FROM\n" +
                    "\tfilemanfileinfo a\n" +
                    "  LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
                    "  left join filemanurl d on b.fileurlid = d.id\n" +
                    "  LEFT JOIN userinfo c ON c.uid = b.uid  where 1=1 and b.uid is  NULL");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and a.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and a.uid = #{uId} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and a.ordernum = #{orderNo} ");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and a.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and d.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and a.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and d.uptime  >= #{startNewestSaveDateStr} and d.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and d.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and d.uptime <= #{endNewestSaveDateStr}");
            }

            if (view.getuId() != null && view.getuId() != 0) {
                sql.append("  AND b.uid = #{uId} or b.uid is null and b.fileInfoId not in (select fileInfoId from filemanright where uid = #{uId}) ");
            }
            sql.append("  GROUP BY a.ordernum,b.uid" +
                    "  ORDER BY\n" +
                    " a.createtime DESC limit #{currentPageTotalNum},#{pageSize} ");
            return sql.toString();
        }

        public String findAllUrlByThreeParamNew2(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select * from (SELECT\n" +
                    "\tuu.fullname AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tfr.opright AS opRight,\n" +
                    "\tfu.logur1 AS urlAddr,\n" +
                    "\tinfo.id AS id,u.fullname as fullName \n" +
                    "\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id  " +
                    "left join userinfo u on fr.uid = u.uid  \n" +
                    "left join userinfo uu on fr.createuser = uu.username  \n" +
                    "where fr.uid is null  ");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and info.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and info.uid = #{uId} ");
            }
            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and info.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }


            sql.append(" group by info.ordernum HAVING id not in(\n" +
                    "SELECT\n" +
                    "\tfr.fileInfoId\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id\n" +
                    "where");
            if (view.getuId() != null && view.getuId() != 0) {
                sql.append("   fr.uid = #{uId}  ");
            } else {
                sql.append("   fr.uid is not null  ");
            }
            sql.append("group by info.ordernum,fr.uid\n" +
                    ") \n" +
                    "UNION ALL\n" +
                    "\n" +
                    "SELECT\n" +
                    "\tuu.fullname AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tCONCAT(\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('2', fr.opright) <> 0,\n" +
                    "\t\t\t\t'2',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('3', fr.opright) <> 0,\n" +
                    "\t\t\t\t'3',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('4', fr.opright) <> 0,\n" +
                    "\t\t\t\t'4',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t)\n" +
                    "\t) AS opRight,\n" +
                    "\tfu.logur1 AS urlAddr,\n" +
                    "\tinfo.id AS id,u.fullname as fullName   \n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id " +
                    " left join userinfo u on u.uid = fr.uid " +
                    " left join userinfo uu on uu.username = info.createuser" +
                    "\n" +
                    "where 1=1 ");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and info.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and info.uid = #{uId} ");
            }
            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and info.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }

            if (view.getuId() != null && view.getuId() != 0) {
                sql.append("  AND fr.uid = #{uId}  ");
            } else {
                sql.append("  and fr.uid is not null  ");
            }
            sql.append("group by info.ordernum,fr.uid) as aa limit #{currentPageTotalNum},#{pageSize}  ");
            return sql.toString();
        }

//        public String findAllUrlByThreeParamNew2(DownloadView view) {
//            StringBuilder sql = new StringBuilder(" SELECT " +
//                    "\ta.createuser AS engineer,\n" +
//                    "\ta.extinfo1 AS salor,\n" +
//                    "\ta.ordernum AS orderNo,\n" +
//                    "\tIFNULL(a.updatetime, a.createtime) AS createTime,\n" +
//                    "\tb.uid AS oprighter,\n" +
//                    "\tCONCAT(\n" +
//                    "\t\tmax(IF (\n" +
//                    "\t\t\tLOCATE('2', b.opright) <> 0,\n" +
//                    "\t\t\t'2',\n" +
//                    "\t\t\t''\n" +
//                    "\t\t)),\n" +
//                    "\n" +
//                    "\tmax(IF (\n" +
//                    "\t\tLOCATE('3', b.opright) <> 0,\n" +
//                    "\t\t'3',\n" +
//                    "\t\t''\n" +
//                    "\t)),\n" +
//                    "\n" +
//                    "max(IF (\n" +
//                    "\tLOCATE('4', b.opright) <> 0,\n" +
//                    "\t'4',\n" +
//                    "\t''\n" +
//                    "))\n" +
//                    "\t) AS opRight," +
//                    "d.logur1 as urlAddr, \n" +
//                    "a.id as id \n" +
//                    "FROM\n" +
//                    "\tfilemanfileinfo a\n" +
//                    "  LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
//                    "  left join filemanurl d on b.fileurlid = d.id\n" +
//                    "  LEFT JOIN userinfo c ON c.uid = b.uid  where 1=1 and b.uid is not NULL ");
//            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
//                sql.append(" and a.extinfo1 = #{salor} ");
//            }
//            if (view.getEngineer() != null && view.getEngineer() != "") {
//                view.setuId(Integer.valueOf(view.getEngineer()));
//                sql.append(" and a.uid = #{uId} ");
//            }
//            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
//                sql.append(" and a.projectname like CONCAT('%',#{projectName},'%')");
//            }
//
//            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
//                sql.append(" and d.orginname like  CONCAT('%',#{fileName},'%')");
//            }
//
//            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
//                sql.append(" and a.uid = #{engineer} ");
//            }
//
//            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
//                sql.append(" and d.uptime  >= #{startNewestSaveDateStr} and d.uptime  <= #{endNewestSaveDateStr}");
//            } else if (view.getStartNewestSaveDateStr() != null) {
//                sql.append(" and d.uptime >= #{startNewestSaveDateStr}");
//            } else if (view.getEndNewestSaveDateStr() != null) {
//                sql.append(" and d.uptime <= #{endNewestSaveDateStr}");
//            }
//
//            if (view.getuId() != null && view.getuId() != 0) {
//                sql.append("  AND b.uid = #{uId} or b.uid is null and b.fileInfoId not in (select fileInfoId from filemanright where uid = #{uId}) ");
//            }
//            sql.append("  GROUP BY a.ordernum,b.uid ");
//
//            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
//                sql.append(" HAVING  ordernum  = #{orderNo} ");
//            }
//
//            sql.append("  ORDER BY\n" +
//                    " a.createtime DESC limit #{currentPageTotalNum},#{pageSize} ");
//            return sql.toString();
//        }


        public String findAllUrlByThreeParamNew2Count(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select count(*) from (SELECT\n" +
                    "\tinfo.createuser AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tfr.opright AS opRight,\n" +
                    "\tfu.logur1 AS urlAddr,\n" +
                    "\tinfo.id AS id\n" +
                    "\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id\n" +
                    "where fr.uid is null  ");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and info.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and info.createuser = #{uId} ");
            }
            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and info.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }


            sql.append(" group by info.ordernum HAVING id not in(\n" +
                    "SELECT\n" +
                    "\tfr.fileInfoId\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id\n" +
                    "where");
            if (view.getuId() != null && view.getuId() != 0) {
                sql.append("   fr.uid = #{uId}  ");
            } else {
                sql.append("   fr.uid is not null  ");
            }
            sql.append("group by info.ordernum,fr.uid\n" +
                    ") \n" +
                    "UNION ALL\n" +
                    "\n" +
                    "SELECT\n" +
                    "\tinfo.createuser AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tCONCAT(\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('2', fr.opright) <> 0,\n" +
                    "\t\t\t\t'2',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('3', fr.opright) <> 0,\n" +
                    "\t\t\t\t'3',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('4', fr.opright) <> 0,\n" +
                    "\t\t\t\t'4',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t)\n" +
                    "\t) AS opRight,\n" +
                    "\tfu.logur1 AS urlAddr,\n" +
                    "\tinfo.id AS id\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id\n" +
                    "where 1=1 ");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and info.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and info.createuser = #{uId} ");
            }
            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and info.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }

            if (view.getuId() != null && view.getuId() != 0) {
                sql.append("  AND fr.uid = #{uId}  ");
            } else {
                sql.append("  and fr.uid is not null  ");
            }
            sql.append("group by info.ordernum,fr.uid) as aa   ");
            return sql.toString();
        }


        public String findAllUrlByThreeParamNew3(DownloadView view) {
            StringBuilder sql = new StringBuilder(" SELECT " +
                    "\ta.createuser AS engineer,\n" +
                    "\ta.extinfo1 AS salor,\n" +
                    "\ta.ordernum AS orderNo,\n" +
                    "\tIFNULL(a.updatetime, a.createtime) AS createTime,\n" +
                    "\tb.uid AS oprighter,\n" +
                    "\tb.opright AS opRight,\n" +
                    "d.logur1 as urlAddr, \n" +
                    "a.id as id \n" +
                    "FROM\n" +
                    "\tfilemanfileinfo a\n" +
                    "  LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
                    "  left join filemanurl d on b.fileurlid = d.id\n" +
                    "  LEFT JOIN userinfo c ON c.uid = b.uid  where 1=1 ");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and a.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "") {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and a.uid = #{uId} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and a.ordernum = #{orderNo} ");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and a.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and d.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and a.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and d.uptime  >= #{startNewestSaveDateStr} and d.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and d.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and d.uptime <= #{endNewestSaveDateStr}");
            }


            sql.append("  and b.uid is null GROUP BY a.ordernum" +
                    "  ORDER BY\n" +
                    " a.createtime DESC ");
            return sql.toString();
        }

        public String findAllUrlByParamManyOrNo(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select ffi.ordernum as orderNo from filemanurl fu left join ");
            sql.append(" filemanfileinfo ffi on fu.fileInfoId = ffi.id ");
            sql.append(" where 1=1");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "" && view.getEngineer().trim().length() > 0) {
                sql.append(" and ffi.uid = #{engineer} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum = #{orderNo} ");
            } else {
                if (view.getOrderNoMessage() != null) {
                    if (view.getOrderNoMessage().contains(",")) {
                        String[] orderNums = view.getOrderNoMessage().split(",");
                        sql.append(" and ffi.ordernum  in( '" + orderNums[0] + "'");
                        for (int a = 1; a < orderNums.length; a++) {
                            sql.append(",'" + orderNums[a] + "'");
                        }
                        sql.append(") ");
                    } else {
                        sql.append(" and ffi.ordernum = '" + view.getOrderNoMessage() + "' ");
                    }

                }
            }

            if (view.getProjectName() != null && view.getProjectName() != "" && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname = #{projectName} ");
            }
            if (view.getFileName() != null && view.getFileName() != "" && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname = #{fileName} ");
            }
            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }


            sql.append(" group by ffi.ordernum order by  ffi.ordernum desc limit #{currentPageTotalNum},#{pageSize} ");
            return sql.toString();
        }


        public String findAllUrlOnlyByParamManyOrNo(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select fu.logur1 as urlAddr  from filemanurl fu left join ");
            sql.append(" filemanfileinfo ffi on fu.fileInfoId = ffi.id ");
            sql.append(" where 1=1");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "" && view.getEngineer().trim().length() > 0) {
                sql.append(" and ffi.uid = #{engineer} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum = #{orderNo} ");
            } else {
                if (view.getOrderNoMessage() != null) {
                    if (view.getOrderNoMessage().contains(",")) {
                        String[] orderNums = view.getOrderNoMessage().split(",");
                        sql.append(" and ffi.ordernum  in( '" + orderNums[0] + "'");
                        for (int a = 1; a < orderNums.length; a++) {
                            sql.append(",'" + orderNums[a] + "'");
                        }
                        sql.append(") ");
                    } else {
                        sql.append(" and ffi.ordernum = '" + view.getOrderNoMessage() + "' ");
                    }

                }
            }

            if (view.getProjectName() != null && view.getProjectName() != "" && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname = #{projectName} ");
            }
            if (view.getFileName() != null && view.getFileName() != "" && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname = #{fileName} ");
            }
            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }


            sql.append(" group by ffi.ordernum order by  ffi.ordernum desc limit #{currentPageTotalNum},#{pageSize} ");
            return sql.toString();
        }



        public String findAllUrlByParamManyOrNoCount(DownloadView view) {
            StringBuilder sql = new StringBuilder(" SELECT count(a.cc) as c FROM ( select count(*) as cc from filemanurl fu left join ");
            sql.append(" filemanfileinfo ffi on fu.fileInfoId = ffi.id ");
            sql.append(" where 1=1");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "" && view.getEngineer().trim().length() > 0) {
                sql.append(" and ffi.uid = #{engineer} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum = #{orderNo} ");
            } else {
                if (view.getOrderNoMessage() != null) {
                    if (view.getOrderNoMessage().contains(",")) {
                        String[] orderNums = view.getOrderNoMessage().split(",");
                        sql.append(" and ffi.ordernum  in( '" + orderNums[0] + "'");
                        for (int a = 1; a < orderNums.length; a++) {
                            sql.append(",'" + orderNums[a] + "'");
                        }
                        sql.append(") ");
                    } else {
                        sql.append(" and ffi.ordernum = '" + view.getOrderNoMessage() + "' ");
                    }

                }
            }

            if (view.getProjectName() != null && view.getProjectName() != "" && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname = #{projectName} ");
            }
            if (view.getFileName() != null && view.getFileName() != "" && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname = #{fileName} ");
            }
            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }

            sql.append(" group by ffi.ordernum ) AS a ");

            return sql.toString();
        }

        public String findAllUrlByThreeParamNew(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select fu.logur1 as addr from filemanurl fu left join ");
            sql.append(" filemanfileinfo ffi on fu.fileInfoId = ffi.id ");
            sql.append(" where 1=1");
            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "" && !view.getEngineer().equals("0")) {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and ffi.uid = #{uId} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum = #{orderNo} ");
            }


            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }
            return sql.toString();
        }


        public String findAllUrlByThreeParam(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select fu.logur1 as addr from filemanurl fu left join ");
            sql.append(" filemanfileinfo ffi on fu.fileInfoId = ffi.id ");
            sql.append(" where 1=1");

            if (view.getSalor() != "" && view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 = #{salor} ");
            }
            if (view.getEngineer() != null && view.getEngineer() != "" && !view.getEngineer().equals("0")) {
                view.setuId(Integer.valueOf(view.getEngineer()));
                sql.append(" and ffi.uid = #{uId} ");
            }
            if (view.getOrderNo() != null && view.getOrderNo() != "" && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum = #{orderNo} ");
            }
            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }
            return sql.toString();
        }

        public String findAllFilesByParam(DownloadView view) {

            StringBuilder sql = new StringBuilder(" SELECT\n");
            sql.append("\t fr.id,\n");
            sql.append("\t fu.username AS creator,\n");
            sql.append("\t fu.updateuser AS lastUpdator,\n");
            sql.append("\t fr.filename AS fileName,\n");
            sql.append("\t ffi.extinfo1 AS salor,\n");
            sql.append("\t ffi.ordernum AS orderNo,\n");
            sql.append("\t ffi.projectname AS projectName,\n");
            sql.append("  fr.createtime as createTime,\n");
            sql.append("\t fr.updatetime AS lastUpdateTime,\n");
            sql.append("\t fu.singlefileupdatenum AS singleFileUpdateNum,\n");
            sql.append("\t fu.modifyReason as modifyReason,\n");
            sql.append("\t fr.opRight as opRight\n");
            sql.append(" FROM\n");
            sql.append("\t filemanfileinfo ffi\n");
            sql.append(" LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n");
            sql.append(" left join filemanurl fu on fu.id = fr.fileurlid\n");
            sql.append("   WHERE  ");
            sql.append("    1 = 1  ");

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOprighter() != null && view.getOprighter().length() > 0) {
                sql.append(" and fr.uid =  #{oprighter}    ");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and fu.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }

            sql.append("  order by fu.uptime desc limit #{currentPageTotalNum},#{pageSize}");
            return sql.toString();
        }

        public String findAllFilesCountByParam(DownloadView view) {

            StringBuilder sql = new StringBuilder(" SELECT count(ffi.id)  ");
            sql.append(" FROM\n");
            sql.append("\t filemanfileinfo ffi\n");
            sql.append(" LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n");
            sql.append(" left join filemanurl fu on fu.id = fr.fileurlid\n");
            sql.append("   WHERE  ");
            sql.append("    1 = 1  ");

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOprighter() != null && view.getOprighter().length() > 0) {
                sql.append(" and fr.uid =  #{oprighter}    ");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and fu.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fmu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }
            return sql.toString();
        }

        public String findAllCountByParaCondition(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select count(ffi.id) ");
            sql.append("                from filemanfileinfo ffi ");
            sql.append("                LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId");
            sql.append("                where 1=1   ");

            if (view.getuId() != null && view.getuId() != 0) {
                sql.append(" and fmu.uid = #{uId}");
            }

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and  fmu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime <= #{endNewestSaveDateStr}");
            }

            return sql.toString();

        }


        public String findAllByParaCondition(DownloadView view) {
            StringBuilder sql = new StringBuilder(" SELECT \tffi.id,\n" +
                    "  IFNULL(fo.fullname,\n" +
                    "\t\to.fullname) as lastUpdator,\n" +
                    "  IFNULL(fmu.updateTime,fmu.uptime) as lastUpdateTime,\n" +
                    "\tfmu.orginname AS fileName,\n" +
                    "\tffi.extinfo1 AS salor,\n" +
                    "\tffi.ordernum AS orderNo,\n" +
                    "\tfmu.modifyReason AS modifyReason,\n" +
                    "\tffi.projectname AS projectName,\n" +
                    "  fmu.singlefileupdatenum AS singleFileUpdateNum,\n" +
                    "\tfmu.logur1 AS urlAddr,\n" +
                    "  ffi.filedescribtion as filedescribtion,\n" +
                    "  ffi.remark as remark,fmu.id as fileUrlId " +
                    " FROM " +
                    " filemanfileinfo ffi" +
                    " LEFT JOIN filemanurl fmu ON ffi.id = fmu.fileInfoId" +
                    " left join userinfo o on fmu.username = o.username\n" +
                    " left join userinfo fo on fo.username = fmu.updateuser" +
                    " WHERE " +
                    " 1=1 ");
            if (view.getuId() != null && view.getuId() != 0) {
                sql.append(" and fmu.uid =#{uId} ");
            }

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fmu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime <= #{endNewestSaveDateStr}");
            }

            sql.append("  order by ffi.createtime desc limit #{currentPageTotalNum},#{pageSize}");
            return sql.toString();

        }

        public String deleteRightItemIds(List<DownloadView> views) {
            StringBuilder sql = new StringBuilder(" delete from filemanright where fileurlid in( ");
            for (int i = 0; i < views.size() - 1; i++) {
                sql.append(views.get(i).getFileUrlId() + ",");
            }
            sql.append(views.get(views.size()).getFileUrlId() + ")");
            return sql.toString();
        }

        public String deleteUrlByHeadIdAndItemIds(List<DownloadView> views) {
            StringBuilder sql = new StringBuilder(" delete from filemanurl where id in( ");
            for (int i = 0; i < views.size() - 1; i++) {
                sql.append(views.get(i).getFileUrlId() + ",");
            }
            sql.append(views.get(views.size()).getFileUrlId() + ")");
            return sql.toString();
        }

        public String findMessageByOrderNoandUid(String orderNo, Integer uId) {
            StringBuilder sql = new StringBuilder("SELECT\n" +
                    "\ta.extinfo1 as salor,\n" +
                    "\tifnull(cc.fullname,'') as engineer,\n" +
                    "\ta.ordernum as orderNo,\n" +
                    "\tIFNULL(a.updatetime, a.createtime) as createTime,\n" +
                    "\tb.uid as oprighter,\n" +
                    "  b.opright as opRight,c.fullName as fullName    \n" +
                    "FROM\n" +
                    "\tfilemanfileinfo a\n" +
                    "LEFT JOIN filemanright b ON a.id = b.fileInfoId\n" +
                    "left join userinfo c on c.uid = b.uid\n" +
                    " left join userinfo cc on cc.username = b.createuser\n" +
                    " where a.ordernum = #{orderNo} ");
            if (uId != 0) {
                sql.append(" and b.uId = #{uId} ");
            } else {
                sql.append(" and b.uId is null ");
            }

            sql.append("ORDER BY a.createtime DESC limit 1  ");
            return sql.toString();
        }

        public String findAllUrlByOrderNoAndUid2(String orderNo, Integer uId) {
            StringBuilder sql = new StringBuilder(" select * from (SELECT\n" +
                    "\tinfo.createuser AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tfr.opright AS opRight,\n" +
                    "\tfu.logur1 AS logur1,\n" +
                    "\tinfo.id AS fileInfoId,u.fullname as fullName,fu.id as id \n" +
                    "\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id  left join userinfo u on fr.uid = u.uid  \n" +
                    "where fr.uid is null  ");
            if (orderNo != null && orderNo != "" && orderNo.trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }
            sql.append(" group by fr.uid,fr.fileurlid HAVING id not in(\n" +
                    "SELECT\n" +
                    "\tfr.fileurlid\n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id\n" +
                    "where");
            if (uId != null && uId != 0) {
                sql.append("   fr.uid = #{uId}  ");
            } else {
                sql.append("   fr.uid is not null  ");
            }
            sql.append("group by fr.uid,fr.fileurlid\n" +
                    ") \n" +
                    "UNION ALL\n" +
                    "\n" +
                    "SELECT\n" +
                    "\tinfo.createuser AS engineer,\n" +
                    "\tinfo.extinfo1 AS salor,\n" +
                    "\tinfo.ordernum AS orderNo,\n" +
                    "\tIFNULL(info.updatetime, info.createtime) AS createTime,\n" +
                    "\tfr.uid AS oprighter,\n" +
                    "\tCONCAT(\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('2', fr.opright) <> 0,\n" +
                    "\t\t\t\t'2',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('3', fr.opright) <> 0,\n" +
                    "\t\t\t\t'3',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t),\n" +
                    "\t\tmax(\n" +
                    "\n" +
                    "\t\t\tIF (\n" +
                    "\t\t\t\tLOCATE('4', fr.opright) <> 0,\n" +
                    "\t\t\t\t'4',\n" +
                    "\t\t\t\t''\n" +
                    "\t\t\t)\n" +
                    "\t\t)\n" +
                    "\t) AS opRight,\n" +
                    "\tfu.logur1 AS logur1,\n" +
                    "\tinfo.id AS fileInfoId,u.fullname as fullName,fu.id as id   \n" +
                    "FROM\n" +
                    "\tfilemanfileinfo info\n" +
                    "JOIN filemanright fr ON info.id = fr.fileInfoId\n" +
                    "JOIN filemanurl fu ON fr.fileurlid = fu.id  left join userinfo u on u.uid = fr.uid \n" +
                    "where 1=1 ");
            if (orderNo != null && orderNo != "" && orderNo.trim().length() > 0) {
                sql.append(" and  info.ordernum  = #{orderNo} ");
            }

            if (uId != null && uId != 0) {
                sql.append("  AND fr.uid = #{uId}  ");
            } else {
                sql.append("  and fr.uid is not null  ");
            }
            sql.append("group by fr.uid,fr.fileurlid) as aa   ");
            return sql.toString();
        }
    }
}
