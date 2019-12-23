package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:32
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:32
 */
public interface IFileUploadAndDownServ {

    FolderUpdateList checkFileisSame(DownloadView view, UserInfo userInfo, String filePathName) throws Exception;

    List<DownloadView> findAllUploadFileByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception;

    List<DownloadView> findAllUrlByParamThreeNew2(DownloadView view) throws Exception;
    DownloadView findMessageByOrderNo(String orderNo) throws Exception;

    String checkFileUpdateRight(String pathNames, DownloadView view, UserInfo userInfo) throws Exception;

    String checkIsExistFilesFolderforUpdate(String pathName,DownloadView view,UserInfo info) throws Exception;

    DownloadView findMessageByOrderNoandUid(String orderNo,String uId) throws Exception;

    List<FilemanUrl> findAllUrlByOrderNoAndUid(String orderNo,String uId) throws  Exception;

    int findAllFileUrlByConditionCount(Integer uId) throws Exception;

    String checkIsExistFilesforUpdate(String pathName,DownloadView view,UserInfo info) throws Exception;

    List<DownloadView> findAllUploadFileByUserId(Integer uid) throws Exception;

    void addFilesData(DownloadView view, UserInfo userInfo) throws Exception;

    FolderUpdateList isSameFolderNameorFileNameMethod(UserInfo userInfo, DownloadView view, String filePathName) throws Exception;

    List<String> findAllUrlOnlyByParamManyOrNo(DownloadView dv) throws Exception;

    FilemanUrl getUrlByFileNameAndOrderNo(String orderNo,String fileName) throws Exception;

    List<UserInfo> findAllUserOnlyDesigner() throws Exception;

    List<UserInfo> findAllUser() throws Exception;

    DownloadView uploadFileByMappedByteBuffer(MultipartFileParam param,UserInfo info) throws Exception;

    DownloadView uploadFileByMappedByteBuffer1(MultipartFileParam param,UserInfo info) throws Exception;

    List<Employee> findAllSalorByDeptName() throws Exception;

    List<String> findAllExtension() throws Exception;

    List<DownloadView> findAllFileUrlByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception;

    void modifyFolderByMappedByteBuffer(MultipartFileParam param,UserInfo info) throws Exception;

    void modifyFileByMappedByteBuffer(MultipartFileParam param,UserInfo info) throws Exception;

    void saveFolderMessageUpdate(DownloadView view,UserInfo info) throws Exception;

    void deleteByHeadIdAndItemId(List<DownloadView> deleteViews,List<File> files) throws Exception;

    void saveFileMessageUpdate(DownloadView view,UserInfo info) throws Exception;

    void saveOrUpdateFilePrivilege(List<DownloadView> views,List<String> privilegeusers,UserInfo info) throws Exception;

    int findAllUploadFileCountByUserId(Integer uId) throws Exception;

    void saveFileDownRecords(List<FilemanDownRecord> records) throws Exception;

    List<FilemanUrl> findAllUrlByOrderNo(String orderNo) throws Exception;

    FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUid(Integer urlId,Integer fileInfoId,Integer uId) throws Exception;

    FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUidBack(Integer urlId,Integer fileInfoId,Integer uId) throws Exception;

    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) throws Exception;

    List<String> findAllOrderNum(int currentPageTotalNum,int pageSize) throws Exception;

    int findAllOrderNumCount() throws Exception;

    void deleteAll() throws Exception;

    void init() throws Exception;

    void saveFolderMessage(DownloadView view,UserInfo userInfo) throws Exception;

    void saveFileMessage(DownloadView view,UserInfo userInfo) throws Exception;

    List<String> findAllUrlByOrderNo2(String orderNo) throws Exception;

    int findAllUploadFileCountByParaCondition(DownloadView view) throws Exception;

    void findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    boolean isFolderNameForEngDateOrderNoSalor(String filePathName) throws Exception;

    FileManFileInfo getFileInfoByOrderNo(String orderNo) throws Exception;

    List<ShowUpdateDownRecord> getFileModifyRecordByUrlId(Integer urlId) throws Exception;

    List<ShowUpdateDownRecord> getFileDownRecordByUrlId(Integer urlId) throws Exception;

    String checkFileisSame1(DownloadView view, UserInfo userInfo, String filePathName) throws Exception;


    List<ShowUpdateDownRecord> getFileModifyRecordByFolOrFilAndOrderNo(DownloadView view) throws Exception;

    List<ShowUpdateDownRecord> getFileDownRecordByFolOrdilAndOrderNo(DownloadView view) throws Exception;

    List<Employee> findAllSalor() throws Exception;

    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) throws Exception;

    List<DownloadView> findAllUrlByOrderNoAndUid1(String orderNo,Integer uId) throws Exception;

    List<DownloadView> findAllFilesByCondParam(DownloadView view) throws Exception;

    int findAllFilesByCondParamCount(DownloadView view) throws Exception;

    DownloadView findOrderNobyOrderNo(String orderNo) throws Exception;

    List<String> findAllUrlByParamThree(DownloadView view) throws Exception;

    int findAllUrlByParamThreeNew2Count(DownloadView view) throws Exception;

    List<String> findAllUrlByParamThreeNew(DownloadView view) throws Exception;

    List<DownloadView> findAllUrlByParamManyOrNo(DownloadView view) throws Exception;

    int findAllUrlByParamManyOrNoCount(DownloadView view) throws Exception;

}
