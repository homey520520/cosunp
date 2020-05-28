package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.tool.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cosun.cosunp.tool.StringUtil.formateString;
import static com.cosun.cosunp.tool.StringUtil.subAfterString;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:37
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:37
 */
@Service
@Transactional(value="test1TransactionManager",rollbackFor = Exception.class)
public class FileUploadAndDownServiceImpl implements IFileUploadAndDownServ {

    private static Logger logger = LogManager.getLogger(FileUploadAndDownServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private FileUploadAndDownMapper fileUploadAndDownMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Path rootPaht;

    @Value("${spring.thymeleaf.reactive.max-chunk-size}")
    private long CHUNK_SIZE;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    public FileUploadAndDownServiceImpl(@Value("${spring.servlet.multipart.location}") String location) {
        this.rootPaht = Paths.get(location);
    }

    @Override
    public void saveOrUpdateFilePrivilege(List<DownloadView> views, List<String> privilegeusers, UserInfo in) throws Exception {
        try {
            DownloadView view = null;
            List<UserInfo> uis = null;
            List<String> exts = fileUploadAndDownMapper.findAllExtension();
            FileManFileInfo info = null;
            FilemanRight right = null;
            FilemanUrl url = null;
            List<FilemanUrl> urls = null;
            String extName = "";
            for (DownloadView vi : views) {
                info = fileUploadAndDownMapper.getFileInfoByOrderNo(vi.getOrderNo());
                for (String operrighter : privilegeusers) {
                    if (vi.getFolderOrFileName().contains(".")) {
                        extName = StringUtil.subAfterString(vi.getFolderOrFileName(), ".");
                    }
                    if (vi.getFolderOrFileName().contains(".") && exts.contains(extName.toLowerCase())) {//代表是文件名
                        right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileNameAndUid(info.getId(), vi.getFolderOrFileName(), Integer.valueOf(operrighter));
                        if (right == null) {
                            if (vi.getOpRight().trim().length() > 0) {
                                right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileNameAndUid2(info.getId(), vi.getFolderOrFileName());
                                right.setOpRight(vi.getOpRight());
                                right.setuId(Integer.valueOf(operrighter));
                                fileUploadAndDownMapper.updateFileRight0OprightById(right.getId());
                                fileUploadAndDownMapper.saveFileManRightBySampleRightBean(right);
                            }
                        } else {
                            if (vi.getOpRight() == null || vi.getOpRight() == "") {
                                fileUploadAndDownMapper.updateFileRightNullOprightById(right.getId());
                                fileUploadAndDownMapper.deleteFileRightPrivileg(right.getId());
                            } else {
                                if (vi.getOpRight() != "" && !right.getOpRight().equals(vi.getOpRight())) {
                                    right.setOpRight(vi.getOpRight());
                                    right.setUpdateUser(in.getUserName());
                                    right.setUpdateTime(new Date());
                                    fileUploadAndDownMapper.upDateFileRightOprightById(right.getId(), right.getOpRight(), right.getUpdateUser(), right.getUpdateTime());
                                }
                            }

                        }
                    } else {
                        urls = fileUploadAndDownMapper.getFileManUrlByFileInfoId(info.getId());
                        for (FilemanUrl u : urls) {
                            if (u.getLogur1().indexOf("/" + vi.getFolderOrFileName() + "/") > -1) {
                                right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileRightIdandUid(info.getId(), u.getId(), Integer.valueOf(operrighter));
                                if (right == null) {
                                    if (vi.getOpRight().trim().length() > 0) {
                                        right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileUrlId(info.getId(), u.getId());
                                        right.setOpRight(vi.getOpRight());
                                        right.setuId(Integer.valueOf(operrighter));
                                        fileUploadAndDownMapper.updateFileRight0OprightById(right.getId());
                                        fileUploadAndDownMapper.saveFileManRightBySampleRightBean(right);
                                    }
                                } else {
                                    if (vi.getOpRight() == null || vi.getOpRight() == "") {
                                        fileUploadAndDownMapper.updateFileRightNullOprightById(right.getId());
                                        fileUploadAndDownMapper.deleteFileRightPrivileg(right.getId());
                                    } else {
                                        if (vi.getOpRight() != "" && !right.getOpRight().equals(vi.getOpRight())) {
                                            right.setOpRight(vi.getOpRight());
                                            right.setUpdateUser(in.getUserName());
                                            right.setUpdateTime(new Date());
                                            fileUploadAndDownMapper.upDateFileRightOprightById(right.getId(), right.getOpRight(), right.getUpdateUser(), right.getUpdateTime());
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    @Override
    public List<UserInfo> findAllUserOnlyDesigner() throws Exception {
        return userInfoMapper.findAllUserOnlyDesigner();
    }

    @Override
    public List<UserInfo> findAllUser() throws Exception {
        return userInfoMapper.findAllUser();
    }

    @Override
    public List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findFileUrlDatabyOrderNoandSalorandUserName(view.getUserName(), view.getSalor(), view.getOrderNo());
    }

    /**
     * 功能描述:
     *
     * @auther: homey Wong
     * @date: 2019/2/22  下午 7:34
     * @param:
     * @return:
     * @describtion
     */
    public List<DownloadView> findAllUrlByOrderNoAndUid1(String orderNo, Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByOrderNoAndUid1(orderNo, uId);
    }

    public void saveFileDownRecords(List<FilemanDownRecord> records) throws Exception {
        for (FilemanDownRecord record : records) {
            fileUploadAndDownMapper.saveFilemanDownRecord(record);
        }
    }


    @Override
    public String checkIsExistFilesforUpdate(String pathName, DownloadView view, UserInfo info) throws Exception {
        pathName = pathName.replace("*", ",");
        String[] names = null;
        if (pathName.contains(":")) {
            names = pathName.split(":");
        } else {
            names[0] = pathName;
        }
        String returnMessage = "OK";
        boolean isAllExistFile = true;
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<String> centerUrls = new ArrayList<String>();
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }

            for (int i = 0; i < names.length; i++) {
                if (!strnames.contains(subAfterString(names[i], "/"))) {
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += subAfterString(names[i], "/") + "===";
                }
            }
            if (!isAllExistFile) {
                returnMessage = "您本次要更新的文件里,有部分文件系统中不存在,共有" + isNoExsitFileNum + "个，分别是:" + noExistFileNames;
                return returnMessage;
            }

        } else {
            returnMessage = "您本次要更新的为新订单，请去上传中心";
        }

        return returnMessage;
    }


    /**
     *
     * @auther: homey Wong
     * @date: 2019/1/11  上午 9:37
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllExistFile = true;
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<String> centerUrls = new ArrayList<String>();
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }

            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/"))) {
                    newFileArray.add(file);
                }
            }
            if (isAllExistFile) {
                view.setFlag("-18");
            }

        } else {
            view.setFlag("-11");
            return view;
        }

        return view;
    }

    public List<Employee> findAllSalor() throws Exception {
        return fileUploadAndDownMapper.findAllSalor();
    }

    /**
     *
     * @auther: homey Wong
     * @date: 2019/2/27 0027 上午 10:13
     * @param:
     * @return:
     * @describtion
     */
    public boolean isFolderNameForEngDateOrderNoSalor(String filePathName) throws Exception {
        filePathName = filePathName.replace("*", ",");
        String[] pathName = null;
        if (filePathName.contains(":")) {
            pathName = filePathName.split(":");
        } else {
            pathName = new String[1];
            pathName[0] = filePathName;
        }
        boolean isFolderNameForEngDateOrderNoSalor = true;
        List<String> salorNames = fileUploadAndDownMapper.getAllSalorNames();
        List<String> designers = fileUploadAndDownMapper.getAllDesigners();
        Pattern orderNoRegex = Pattern.compile("^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$");
        Pattern dataRegex = Pattern.compile("^2019[0|1][0-9]$");
        String[] foldersName = null;
        if (pathName != null) {
            for (int i = 0; i < pathName.length; i++) {
                foldersName = pathName[i].split("/");
                for (String strname : foldersName) {
                    if (salorNames.contains(strname) || designers.contains(strname)) {
                        isFolderNameForEngDateOrderNoSalor = false;
                        return isFolderNameForEngDateOrderNoSalor;
                    }
                    Matcher match = orderNoRegex.matcher(strname);
                    Matcher match1 = dataRegex.matcher(strname);
                    if (match.matches() || match1.matches()) {
                        isFolderNameForEngDateOrderNoSalor = false;
                        return isFolderNameForEngDateOrderNoSalor;
                    }
                }
            }
        }
        return isFolderNameForEngDateOrderNoSalor;
    }

    /**
     *
     * @auther: homey Wong
     * @date: 2019/1/16 0016 下午 2:40
     * @param:
     * @return:
     * @describtion
     */
    public DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllNewFile = true;
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            if (isAllNewFile) {
                addOldOrderNoNewFilesByFolder(view, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
                view.setFlag("1");
            }
        } else {
            this.addFilesDatabyFolder(view, userInfo);
            view.setFlag("1");

        }

        return view;
    }

    @Override
    public String checkIsExistFilesFolderforUpdate(String pathName, DownloadView view, UserInfo info) throws Exception {
        pathName = pathName.replace("*", ",");
        String returnMessage = "OK";
        String[] urlPaths = null;
        if (pathName.contains(":")) {
            urlPaths = pathName.split(":");
        } else {
            urlPaths[0] = pathName;
        }
        boolean isAllExistFile = true;
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> centerUrls = new ArrayList<String>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }


            for (int i = 0; i < urlPaths.length; i++) {
                if (!strnames.contains(subAfterString(urlPaths[i], "/")) || !centerUrls.contains(urlPaths[i])) {
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += urlPaths[i] + "===";
                }
            }

            if (!isAllExistFile) {
                returnMessage = "您本次更新的文件夹内有些许新文件，共有" + isNoExsitFileNum + "个，分别是:" + noExistFileNames + "或者您本次更新的文件夹结构与上次上传的文件夹结构不一致，请检查!";
                return returnMessage;
            }

        } else {
            returnMessage = "您本次要更新的文件夹结构不对，请按上次上传的文件夹层次结构来!";
            return returnMessage;
        }

        return returnMessage;
    }


    @Override
    public DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllExistFile = true;
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        ;
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> centerUrls = new ArrayList<String>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }


            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/")) && centerUrls.contains(file.getOriginalFilename())) {//查看文件夹下的文件是否完全一样
                    newFileArray.add(file);
                }
            }

            if (isAllExistFile) {
                view.setFlag("-18");
            }

        } else {
            view.setFlag("-11");
            return view;
        }

        return view;
    }

    public String checkFileUpdateRight(String pathName, DownloadView view, UserInfo userInfo) throws Exception {
        pathName = pathName.replace("*", ",");
        String[] pathNames = null;
        if (pathName.contains(":")) {
            pathNames = pathName.split(":");
        } else {
            pathNames[0] = pathName;
        }
        FilemanRight right = null;
        String fileName = null;
        FilemanUrl url = null;
        String returnMessage = "OK";
        for (int i = 0; i < pathNames.length; i++) {
            fileName = subAfterString(pathNames[i], "/");
            right = fileUploadAndDownMapper.getFileRightByOrderNoUidfileName(view.getOrderNo(), fileName, userInfo.getuId());
            if (right == null) {
                url = fileUploadAndDownMapper.getFileUrlByOrderNoSo(view.getOrderNo(), view.getSalor(), userInfo.getuId(), fileName);
                if (url == null) {
                    List<FilemanUrl> urls = fileUploadAndDownMapper.getFileUrlByOrderNo(view.getOrderNo());
                    if (urls.size() == 0) {
                        returnMessage = "您本次要更新的订单信息不存在.";
                    } else {
                        urls = fileUploadAndDownMapper.getFileUrlByOrderNoSalorDisgner(view.getOrderNo(), view.getSalor(), userInfo.getuId());
                        if (urls.size() == 0) {
                            returnMessage = "您本次要更新的订单信息与已存在的订单信息不匹配!请检查设计师或业务员。";
                        } else {
                            returnMessage = "您本次要更新的文件不存在，应去上传区进行上传，如:" + fileName + "系统里就不存在";
                        }
                    }
                } else {
                    returnMessage = "您没有权限，如:" + fileName + "就没有权限!";
                }

            } else {
                url = fileUploadAndDownMapper.getFileUrlByOrderNoSo(view.getOrderNo(), view.getSalor(), userInfo.getuId(), fileName);
                if (url == null) {
                    List<FilemanUrl> urls = fileUploadAndDownMapper.getFileUrlByOrderNo(view.getOrderNo());
                    if (urls.size() == 0) {
                        returnMessage = "您本次要更新的订单信息不存在.";
                    } else {
                        urls = fileUploadAndDownMapper.getFileUrlByOrderNoSalorDisgner(view.getOrderNo(), view.getSalor(), userInfo.getuId());
                        if (urls.size() == 0) {
                            returnMessage = "您本次要更新的订单信息与已存在的订单信息不匹配!请检查设计师或业务员。";
                        } else {
                            returnMessage = "您本次要更新的文件不存在，应去上传区进行上传，如:" + fileName + "系统里就不存在";
                        }
                    }
                }
                if (!right.getOpRight().contains("2")) {
                    returnMessage = "您没有权限，如:" + fileName + "就没有权限!";
                }
            }
        }
        return returnMessage;
    }


    public void updateFilesDataFolder(List<FileManFileInfo> fileManFileInfo, DownloadView view, UserInfo userInfo) throws Exception {
        String[] splitPathNames = view.getFilePathNames().split(":");
        FileManFileInfo ffi = null;
        FilemanUrl filemanUrl;
        FilemanUpdateRecord record = null;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(userInfo.getUserName());
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (int i = 0; i < splitPathNames.length; i++) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(subAfterString(splitPathNames[i], "/"), fileManFileInfo.get(0).getId());
                if (filemanUrl != null) {
                    record = new FilemanUpdateRecord();
                    record.setFileName(filemanUrl.getOrginName());
                    record.setOrderNum(ffi.getOrderNum());
                    record.setProjectName(ffi.getProjectName());
                    record.setUpdateDate(new Date());
                    record.setUpdateReason(view.getModifyReason());
                    record.setUpdateUid(userInfo.getuId());
                    record.setFileurlid(filemanUrl.getId());
                    fileUploadAndDownMapper.saveFilemanUpdateRecord(record);
                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum() + 1);
                    filemanUrl.setUpdateuser(userInfo.getUserName());
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(new Date(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId(), filemanUrl.getUpdateuser());
                }
            }
        }

    }

    public void addFilesDatabyFolder(DownloadView view, UserInfo userInfo) throws Exception {
        String splitPaths[] = view.getFilePathNames().split(":");
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FileManFileInfo fileManFileInfo;
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        fileManFileInfo = new FileManFileInfo();
        fileManFileInfo.setCreateTime(new Date());
        fileManFileInfo.setCreateUser(userInfo.getUserName());
        fileManFileInfo.setUserName(userInfo.getUserName());
        fileManFileInfo.setuId(userInfo.getuId());
        fileManFileInfo.setExtInfo1(view.getSalor());
        fileManFileInfo.setOrderNum(view.getOrderNo());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileManFileInfo.setTotalFilesNum(splitPaths.length);
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);
        String centerUrl = userInfo.getUserName() + "/" + formateString(new Date()) + "/" + view.getSalor() + "/"
                + view.getRandom8() + "/" + view.getOrderNo() + "/";
        String orginname = null;
        for (int i = 0; i < splitPaths.length; i++) {
            try {
                orginname = subAfterString(splitPaths[i], "/");
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setuId(userInfo.getuId());
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1");
                filemanUrl.setLogur1(centerUrl + splitPaths[i]);
                filemanUrl.setUpTime(new Date());
                filemanUrl.setFileInfoId(fileManFileInfo.getId());
                filemanUrls.add(filemanUrl);
                fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

                filemanRight = new FilemanRight();
                filemanRight.setCreateTime(view.getCreateTime());
                filemanRight.setCreateUser(userInfo.getUserName());
                filemanRight.setFileName(orginname);
                filemanRight.setUserName(userInfo.getUserName());
                filemanRight.setFileUrlId(filemanUrl.getId());
                filemanRight.setFileInfoId(fileManFileInfo.getId());
                filemanRights.add(filemanRight);
                fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

    }



    public void addOldOrderNoNewFilesByFolder(DownloadView view, UserInfo userInfo, String oldPath, List<FileManFileInfo> fileManFileInfos) throws Exception {
        String[] splitPaths = view.getFilePathNames().split(":");
        Integer pointindex = StringUtils.ordinalIndexOf(oldPath, "/", 5);
        String oldsPath = oldPath.substring(0, pointindex + 1);
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + splitPaths.length);
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setUpdateTime(new Date());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileManFileInfo.setProjectName(view.getProjectName());
        String fileName = null;
        String allPathName = null;
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId(), fileManFileInfo.getFiledescribtion(), fileManFileInfo.getRemark(), fileManFileInfo.getProjectName());
        for (int i = 0; i < splitPaths.length; i++) {
            fileName = StringUtil.subAfterString(splitPaths[i], "/");
            allPathName = oldsPath + splitPaths[i];
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(fileName);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(allPathName);
            filemanUrl.setuId(userInfo.getuId());
            filemanUrl.setUpTime(new Date());
            filemanUrl.setFileInfoId(fileManFileInfo.getId());
            filemanUrls.add(filemanUrl);
            fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

            filemanRight = new FilemanRight();
            filemanRight.setCreateTime(view.getCreateTime());
            filemanRight.setCreateUser(userInfo.getUserName());
            filemanRight.setFileName(fileName);
            filemanRight.setUserName(userInfo.getUserName());
            filemanRight.setFileUrlId(filemanUrl.getId());
            filemanRight.setFileInfoId(fileManFileInfo.getId());

            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }
    }


    @Override
    public void findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> urlStr = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        String pointFolder = null;
        boolean f = true;
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                if (view.getSaveFolderName() != null) {
                    if (view.getSaveFolderName() != "" && f) {
                        if (fu.getLogur1().contains(view.getSaveFolderName())) {
                            pointFolder = fu.getLogur1();
                            f = false;
                        }
                    }
                }
                urlStr.add(fu.getLogur1());
            }

            if (pointFolder != null && pointFolder.length() > 0 && !view.getSaveFolderName().equals("")) {
                int lia = pointFolder.indexOf(view.getSaveFolderName());
                pointFolder = pointFolder.substring(0, lia + view.getSaveFolderName().length() + 1);
                addOFilesByPointFile(view, userInfo, pointFolder, fileManFileInfo);
            } else {
                addOldOrderNoNewFiles(view, userInfo, urlStr, fileManFileInfo);
            }

        } else {
            this.addFilesData(view, userInfo);
        }
    }


    public void updateFilesData(List<FileManFileInfo> fileManFileInfo, DownloadView view, UserInfo userInfo) throws Exception {
        FileManFileInfo ffi = null;
        String[] splitNames = view.getFilePathNames().split(":");
        FilemanUpdateRecord record = null;
        FilemanUrl filemanUrl;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(userInfo.getUserName());
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (int i = 0; i < splitNames.length; i++) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(splitNames[i], fileManFileInfo.get(0).getId());
                if (filemanUrl != null) {
                    record = new FilemanUpdateRecord();
                    record.setFileName(filemanUrl.getOrginName());
                    record.setOrderNum(ffi.getOrderNum());
                    record.setProjectName(ffi.getProjectName());
                    record.setUpdateDate(new Date());
                    record.setUpdateReason(view.getModifyReason());
                    record.setUpdateUid(userInfo.getuId());
                    record.setFileurlid(filemanUrl.getId());
                    fileUploadAndDownMapper.saveFilemanUpdateRecord(record);
                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum() + 1);
                    filemanUrl.setUpdateuser(userInfo.getUserName());
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(new Date(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId(), filemanUrl.getUpdateuser());
                }
            }
        }

    }

    @Override
    public int findAllFileUrlByConditionCount(Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllFileUrlByConditionCount(uId);
    }

    public void addOFilesByPointFile(DownloadView view, UserInfo userInfo, String pointpath,
                                     List<FileManFileInfo> fileManFileInfos) throws Exception {
        String[] splitNames = view.getFilePathNames().split(":");
        String orginname = "";
        String deskName = "";
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + splitNames.length);
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setUpdateTime(new Date());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId(), fileManFileInfo.getFiledescribtion(), fileManFileInfo.getRemark(), fileManFileInfo.getProjectName());
        for (int i = 0; i < splitNames.length; i++) {
            orginname = splitNames[i];
            deskName = pointpath + orginname;
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(orginname);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(deskName);
            filemanUrl.setuId(userInfo.getuId());
            filemanUrl.setUpTime(new Date());
            filemanUrl.setFileInfoId(fileManFileInfo.getId());
            filemanUrls.add(filemanUrl);
            fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

            filemanRight = new FilemanRight();
            filemanRight.setCreateTime(view.getCreateTime());
            filemanRight.setCreateUser(userInfo.getUserName());
            filemanRight.setFileName(orginname);
            filemanRight.setUserName(userInfo.getUserName());
            filemanRight.setFileUrlId(filemanUrl.getId());
            filemanRight.setFileInfoId(fileManFileInfo.getId());
            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }
    }

    public void addOldOrderNoNewFiles(DownloadView view, UserInfo userInfo, List<String> oldPaths,
                                      List<FileManFileInfo> fileManFileInfos) throws Exception {
        String[] splitNames = view.getFilePathNames().split(":");
        String oldPath = null;
        Integer pointindex = null;
        String oldsPath = null;
        oldPath = oldPaths.get(0);
        pointindex = StringUtils.ordinalIndexOf(oldPath, "/", 5);
        oldsPath = oldPath.substring(0, pointindex + 1);
        String orginname = "";
        String deskName = "";
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + splitNames.length);
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setUpdateTime(new Date());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId(), fileManFileInfo.getFiledescribtion(), fileManFileInfo.getRemark(), fileManFileInfo.getProjectName());
        for (int i = 0; i < splitNames.length; i++) {
            orginname = splitNames[i];
            deskName = oldsPath + orginname;
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(orginname);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(deskName);
            filemanUrl.setuId(userInfo.getuId());
            filemanUrl.setUpTime(new Date());
            filemanUrl.setFileInfoId(fileManFileInfo.getId());
            filemanUrls.add(filemanUrl);
            fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

            filemanRight = new FilemanRight();
            filemanRight.setCreateTime(view.getCreateTime());
            filemanRight.setCreateUser(userInfo.getUserName());
            filemanRight.setFileName(orginname);
            filemanRight.setUserName(userInfo.getUserName());
            filemanRight.setFileUrlId(filemanUrl.getId());
            filemanRight.setFileInfoId(fileManFileInfo.getId());
            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }


    }


    @Override
    public void addFilesData(DownloadView view, UserInfo userInfo) throws Exception {
        String[] splitNames = view.getFilePathNames().split(":");
        String randomNum = MathUtil.getRandom620(8);
        String orginname = "";
        String deskName = "";
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FileManFileInfo fileManFileInfo;
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;

        fileManFileInfo = new FileManFileInfo();
        fileManFileInfo.setCreateTime(new Date());
        fileManFileInfo.setCreateUser(userInfo.getUserName());
        fileManFileInfo.setUserName(userInfo.getUserName());
        fileManFileInfo.setuId(userInfo.getuId());
        fileManFileInfo.setFileName(view.getFileName());
        fileManFileInfo.setExtInfo1(view.getSalor());
        fileManFileInfo.setOrderNum(view.getOrderNo());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileManFileInfo.setTotalFilesNum(splitNames.length);
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);


        for (int i = 0; i < splitNames.length; i++) {
            try {
                deskName = userInfo.getUserName() + "/" + formateString(new Date()) + "/" + view.getSalor() + "/"
                        + view.getRandom8() + "/" + view.getOrderNo() + "/" + splitNames[i];
                orginname = splitNames[i];
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setuId(userInfo.getuId());
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1");
                filemanUrl.setLogur1(deskName);
                filemanUrl.setUpTime(new Date());
                filemanUrl.setFileInfoId(fileManFileInfo.getId());
                filemanUrls.add(filemanUrl);
                fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

                filemanRight = new FilemanRight();
                filemanRight.setCreateTime(view.getCreateTime());
                filemanRight.setCreateUser(userInfo.getUserName());
                filemanRight.setFileName(orginname);
                filemanRight.setUserName(userInfo.getUserName());
                filemanRight.setFileUrlId(filemanUrl.getId());
                filemanRight.setFileInfoId(fileManFileInfo.getId());
                filemanRights.add(filemanRight);
                fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

    }


    /**
     * @author:homey Wong
     * @Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByUserId(Integer uid) throws Exception {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByUserId(uid);
        return list;
    }

    @Override
    public DownloadView findMessageByOrderNo(String orderNo) throws Exception {
        return fileUploadAndDownMapper.findMessageByOrderNo(orderNo);
    }

    /**
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 下午 3:11
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public List<DownloadView> findAllUrlByParamThreeNew2(DownloadView view) throws Exception {
        String linshiId = view.getLinshiId();
        if (linshiId == null || linshiId.trim().length() <= 0) {
            view.setuId(0);
        } else {
            view.setuId(Integer.valueOf(linshiId.trim()));
        }
        return fileUploadAndDownMapper.findAllUrlByParamThreeNew2(view);
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws
            Exception {
        return fileUploadAndDownMapper.findAllUploadFileByCondition(uid, currentPageTotalNum, PageSize);

    }

    /**
     *
     * @auther: homey Wong
     * @date: 2018/12/29 0029 上午 9:47
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public int findAllUploadFileCountByUserId(Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileCountByUserId(uId);

    }

    @Override
    public List<FilemanUrl> findAllUrlByOrderNo(String orderNo) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByOrderNo(orderNo);
    }

    @Override
    public FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUid(Integer urlId, Integer fileInfoId, Integer uId) throws
            Exception {
        return fileUploadAndDownMapper.getFileRightByUrlIdAndFileInfoIdAnaUid(urlId, fileInfoId, uId);
    }

    @Override
    public FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUidBack(Integer urlId, Integer fileInfoId, Integer uId) throws Exception {
        FilemanRight right = fileUploadAndDownMapper.getFileRightByUrlIdAndFileInfoIdAnaUid(urlId, fileInfoId, uId);
        if (right == null) {
            right = fileUploadAndDownMapper.getFileRightByUrlIdAndFileInfoIdAnaUidBack(urlId, fileInfoId);
        }
        return right;
    }

    @Override
    public List<String> findAllUrlByOrderNo2(String orderNo) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByOrderNo2(orderNo);
    }

    @Override
    public List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileByParaCondition(view);
    }

    @Override
    public List<DownloadView> findAllFileUrlByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws
            Exception {
        return fileUploadAndDownMapper.findAllFileUrlByCondition(uid, currentPageTotalNum, PageSize);
    }

    @Override
    public List<String> findAllUrlByParamThree(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByParamThree(view);
    }

    @Override
    public List<String> findAllUrlByParamThreeNew(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByParamThreeNew(view);
    }

    @Override
    public int findAllUploadFileCountByParaCondition(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
    }

    @Override
    public List<DownloadView> findAllFilesByCondParam(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllFilesByCondParam(view);
    }

    @Override
    public int findAllFilesByCondParamCount(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllFilesByCondParamCount(view);
    }


    @Override
    public FileManFileInfo getFileInfoByOrderNo(String orderNo) throws Exception {
        return fileUploadAndDownMapper.getFileInfoByOrderNo(orderNo);
    }

    /**
     *
     * @auther: homey Wong
     * @date: 2019/1/18 0018 下午 4:31
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public FolderUpdateList checkFileisSame(DownloadView view, UserInfo userInfo, String filePathName) throws Exception {
        filePathName = filePathName.replace("*", ",");
        FolderUpdateList ful = new FolderUpdateList();
        String[] names = null;
        if (filePathName.contains(":")) {
            names = filePathName.split(":");
        } else {
            names[0] = filePathName;
        }
        List<String> originNames = new ArrayList<String>();
        String isSameFileUpload = "OK";
        ful.setIsSameFileUploadFolderName(isSameFileUpload);
        for (int i = 0; i < names.length; i++) {
            if (originNames.contains(names[i])) {
                isSameFileUpload = "您要上传的文件文件名有重复,如:" + names[i];
                ful.setIsSameFileUploadFolderName(isSameFileUpload);
                return ful;
            } else {
                originNames.add(names[i]);
            }

        }

        List<String> urls = fileUploadAndDownMapper.findNoFolderUrlNoFileUrl(userInfo.getuId(), view.getSalor(), view.getOrderNo());

        for (String s : originNames) {
            if (urls.contains(s)) {
                isSameFileUpload = "同一订单下您的文件已上传过,如:" + s;
                ful.setIsSameFileUploadFolderName(isSameFileUpload);
                return ful;
            }
        }
        return ful;

    }

    @Override
    public String checkFileisSame1(DownloadView view, UserInfo userInfo, String filePathName) throws Exception {
        filePathName = filePathName.replace("*", ",");
        String[] names = null;
        if (filePathName.contains(":")) {
            names = filePathName.split(":");
        } else {
            names[0] = filePathName;
        }
        List<String> originNames = new ArrayList<String>();
        String isSameFileUpload = "OK";
        for (int i = 0; i < names.length; i++) {
            if (originNames.contains(names[i])) {
                isSameFileUpload = "您要更新的文件名有重复,如:" + names[i];
                return isSameFileUpload;
            } else {
                originNames.add(names[i]);
            }

        }
        return isSameFileUpload;
    }

    /**
     *
     * @auther: homey Wong
     * @date: 2019/1/18 0018 下午 4:45
     * @param:
     * @return:
     * @describtion
     */

    public FilemanUrl getUrlByFileNameAndOrderNo(String orderNo, String fileName) throws Exception {
        return fileUploadAndDownMapper.getUrlByFileNameAndOrderNo(orderNo, fileName);
    }


    @Override
    public FolderUpdateList isSameFolderNameorFileNameMethod(UserInfo userInfo, DownloadView view, String folderPathName) throws Exception {
        folderPathName = folderPathName.replace("*", ",");
        FolderUpdateList ful = new FolderUpdateList();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateStr = sDateFormat.format(new Date());
        String[] paths = null;
        if (folderPathName.contains(":")) {
            paths = folderPathName.split(":");
        } else {
            paths = new String[1];
            paths[0] = folderPathName;
        }
        String isNotSameF = "OK";
        List<String> FinalNames = new ArrayList<String>();
        List<String[]> foldernameLists = new ArrayList<String[]>();
        String afterstr = null;
        String[] arrayArrayaar = null;
        List<String> fileNames = fileUploadAndDownMapper.findNoFolderUrlNoFileUrl(userInfo.getuId(), view.getSalor(), view.getOrderNo());
        for (int ii = 0; ii < paths.length; ii++) {
            if (paths[ii].contains(":")) {
                isNotSameF = "文件夹或文件名不能包含:号!如:" + paths[ii];
                ful.setIsSameFileUploadFolderName(isNotSameF);
                return ful;
            } else {
                String centerurl = StringUtil.subMyString(paths[ii], "/");
                if (FinalNames.contains(subAfterString(paths[ii], "/"))) {
                    isNotSameF = "文件夹内有重复文件名文件！如:" + subAfterString(paths[ii], "/");//表示上传文件夹中就有相同文件名字存在
                    ful.setIsSameFileUploadFolderName(isNotSameF);
                    return ful;
                } else {
                    if (!subAfterString(paths[ii], "/").contains(".")) {
                        isNotSameF = "文件夹内的文件必须要有扩展名，如:" + subAfterString(paths[ii], "/") + "没有扩展名";//有重复,即刻返回
                        ful.setIsSameFileUploadFolderName(isNotSameF);
                        return ful;
                    }
                    FinalNames.add(subAfterString(paths[ii], "/"));
                    if (fileNames.contains(subAfterString(paths[ii], "/"))) {
                        isNotSameF = "您订单下的文件夹内文件与系统中已存在的文件重复!如:" + subAfterString(paths[ii], "/");//有重复,即刻返回
                        ful.setIsSameFileUploadFolderName(isNotSameF);
                        return ful;
                    }
                }
                if (paths[ii] != null) {
                    if (paths[ii].contains("/")) {
                        afterstr = paths[ii].replaceAll("/", "\\\\");
                        arrayArrayaar = afterstr.split("\\\\");
                        foldernameLists.add(arrayArrayaar);
                    }
                }
            }
        }

        int folderNum = 100;
        String[] aa = null;
        List<String> singleFolder = new ArrayList<String>();
        for (int a = 0; a < foldernameLists.size(); a++) {
            aa = foldernameLists.get(a);
            for (int b = 0; b < aa.length - 1; b++) {
                if (singleFolder.contains(aa[b])) {
                    aa[b] = aa[b] + dateStr + folderNum++;
                    foldernameLists.remove(a);
                    foldernameLists.add(aa);
                } else {
                    singleFolder.add(aa[b]);
                }

            }
            singleFolder.clear();
        }

        String[] centerfolderone1 = null;
        String[] centerfoldertwo2 = null;
        for (int i = 0; i < foldernameLists.size(); i++) {
            centerfolderone1 = foldernameLists.get(i);
            for (int j = 1; j < foldernameLists.size(); j++) {
                centerfoldertwo2 = foldernameLists.get(j);
                for (int k = 0; k < centerfolderone1.length - 1; k++) {
                    for (int f = 0; f < centerfoldertwo2.length - 1; f++) {
                        if (k == f && centerfolderone1[k].equals(centerfoldertwo2[f])) {
                            for (int o = 0; o <= k; o++) {
                                if (!centerfolderone1[o].equals(centerfoldertwo2[o])) {
                                    centerfolderone1[k] = centerfolderone1[k] + dateStr + folderNum++;
                                    foldernameLists.remove(i);
                                    foldernameLists.add(centerfolderone1);
                                }
                            }
                        } else {
                            if (centerfolderone1[k].equals(centerfoldertwo2[f])) {
                                {
                                    centerfolderone1[k] = centerfolderone1[k] + dateStr + folderNum++;
                                    foldernameLists.remove(i);
                                    foldernameLists.add(centerfolderone1);
                                }
                            }
                        }
                    }
                }
            }
        }


        List<String> urls = fileUploadAndDownMapper.findAllFileUrlLogursByOrderNoandSalorUserName(userInfo.getuId(), view.getSalor(), view.getOrderNo());
        List<String[]> mysqlcenterurls = new ArrayList<String[]>();
        List<String[]> mysqlcenterurlsAll = new ArrayList<String[]>();
        Integer pointindex = null;
        String olderCenterstr = null;
        String centerstr = null;
        for (String urladdr : urls) {
            pointindex = StringUtils.ordinalIndexOf(urladdr, "/", 5);
            if (pointindex > 0) {
                olderCenterstr = urladdr.substring(0, pointindex + 1);
                centerstr = urladdr.substring(pointindex + 1, urladdr.length());
                if (centerstr != null && centerstr != "") {
                    if (centerstr.contains("/")) {
                        mysqlcenterurls.add(centerstr.split("/"));
                    }
                }
            }
        }


        String[] marrray;
        for (int m = 0; m < mysqlcenterurls.size(); m++) {
            marrray = mysqlcenterurls.get(m);
            for (int n = 0; n < marrray.length; n++) {
                marrray[n] = marrray[n].concat("*");
            }
        }

        String[] centerfolderone = null;
        String[] centerfoldertwo = null;
        mysqlcenterurlsAll.addAll(foldernameLists);
        mysqlcenterurlsAll.addAll(mysqlcenterurls);

        String[] bigOne;
        for (int i = 0; i < mysqlcenterurlsAll.size() - 1; i++) {
            for (int j = 1; j < mysqlcenterurlsAll.size() - i; j++) {
                if (mysqlcenterurlsAll.get(j - 1).length > mysqlcenterurlsAll.get(j).length) {
                    bigOne = mysqlcenterurlsAll.get(j - 1);
                    mysqlcenterurlsAll.set((j - 1), mysqlcenterurlsAll.get(j));
                    mysqlcenterurlsAll.set(j, bigOne);
                }
            }
        }


        for (int i = 0; i < mysqlcenterurlsAll.size(); i++) {
            centerfolderone = mysqlcenterurlsAll.get(i);
            for (int j = 1; j < mysqlcenterurlsAll.size(); j++) {
                centerfoldertwo = mysqlcenterurlsAll.get(j);
                for (int k = 0; k < centerfolderone.length - 1; k++) {
                    for (int f = 0; f < centerfoldertwo.length - 1; f++) {
                        if (k == f && centerfolderone[k].replace("*", "").equals(centerfoldertwo[f].replace("*", ""))) {
                            for (int o = 0; o <= k; o++) {
                                if (!centerfolderone[o].replace("*", "").equals(centerfoldertwo[o].replace("*", ""))) {
                                    if (!centerfolderone[k].contains("*")) {
                                        centerfolderone[k] = centerfolderone[k].replace("*", "") + dateStr + folderNum++;
                                        mysqlcenterurlsAll.remove(j);
                                        mysqlcenterurlsAll.add(centerfolderone);
                                    } else {
                                        centerfoldertwo[o] = centerfoldertwo[o].replace("*", "") + dateStr + folderNum++;
                                        mysqlcenterurlsAll.remove(j);
                                        mysqlcenterurlsAll.add(centerfoldertwo);
                                    }
                                }
                            }
                        } else {
                            if (centerfolderone[k].replace("*", "").equals(centerfoldertwo[f].replace("*", ""))) {
                                {
                                    if (!centerfolderone[k].contains("*")) {
                                        centerfolderone[k] = centerfolderone[k].replace("*", "") + dateStr + folderNum++;
                                        mysqlcenterurlsAll.remove(j);
                                        mysqlcenterurlsAll.add(centerfolderone);
                                    } else {
                                        centerfoldertwo[f] = centerfoldertwo[f].replace("*", "") + dateStr + folderNum++;
                                        mysqlcenterurlsAll.remove(j);
                                        mysqlcenterurlsAll.add(centerfoldertwo);
                                    }

                                }
                            }
                        }
                    }
                }
            }

        }
        String[] marrray1;
        for (int m = 0; m < mysqlcenterurlsAll.size(); m++) {
            marrray1 = mysqlcenterurlsAll.get(m);
            for (int n = 0; n < marrray1.length; n++) {
                marrray1[n] = marrray1[n].replace("*", "");
            }
        }
        ful.setIsSameFileUploadFolderName(isNotSameF);
        ful.setUrlAfterUpdateForNoRepeat(mysqlcenterurlsAll);
        return ful;

    }

    public List<String> findAllOrderNum(int currentPageTotalNum, int pageSize) throws Exception {
        return fileUploadAndDownMapper.findAllOrderNum(currentPageTotalNum, pageSize);
    }

    public int findAllOrderNumCount() throws Exception {
        return fileUploadAndDownMapper.findAllOrderNumCount();
    }

    public List<DownloadView> findAllUrlByParamManyOrNo(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByParamManyOrNo(view);
    }

    public int findAllUrlByParamManyOrNoCount(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByParamManyOrNoCount(view);
    }

    public List<String> findAllUrlOnlyByParamManyOrNo(DownloadView dv) throws Exception {
        return fileUploadAndDownMapper.findAllUrlOnlyByParamManyOrNo(dv);
    }


    public int findAllUrlByParamThreeNew2Count(DownloadView view) throws Exception {
        String linshiId = view.getLinshiId();
        if (linshiId == null || linshiId.trim().length() <= 0) {
            view.setuId(0);
        } else {
            view.setuId(Integer.valueOf(linshiId.trim()));
        }
        return fileUploadAndDownMapper.findAllUrlByParamThreeNew2Count(view);
    }

    public DownloadView findMessageByOrderNoandUid(String orderNo, String linshiId) throws Exception {
        Integer uId = 0;
        if (linshiId == null || linshiId.trim().length() <= 0) {
            uId = 0;
        } else {
            uId = Integer.valueOf(linshiId.trim());
        }
        return fileUploadAndDownMapper.findMessageByOrderNoandUid(orderNo, uId);
    }

    public List<FilemanUrl> findAllUrlByOrderNoAndUid(String orderNo, String linshiId) throws Exception {
        Integer uId = 0;
        if (linshiId == null || linshiId.trim().length() <= 0) {
            uId = 0;
        } else {
            uId = Integer.valueOf(linshiId.trim());
        }
        return fileUploadAndDownMapper.findAllUrlByOrderNoAndUid2(orderNo, uId);
    }


    public DownloadView findOrderNobyOrderNo(String orderNo) throws Exception {
        return fileUploadAndDownMapper.findOrderNobyOrderNo(orderNo);
    }

    @Override
    public void saveFolderMessageUpdate(DownloadView view, UserInfo info) throws Exception {
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(info.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {
            updateFilesDataFolder(fileManFileInfo, view, info);
        }

    }

    @Override
    public void saveFileMessageUpdate(DownloadView view, UserInfo info) throws Exception {
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(info.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {
            updateFilesData(fileManFileInfo, view, info);

        }

    }

    public void saveFileMessage(DownloadView view, UserInfo userInfo) throws Exception {
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> urlStr = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(userInfo.getUserName(), view.getOrderNo(), view.getSalor());
        String pointFolder = null;
        boolean f = true;
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                if (view.getSaveFolderName() != null) {
                    if (view.getSaveFolderName() != "" && f) {
                        if (fu.getLogur1().contains("/" + view.getSaveFolderName() + "/")) {
                            pointFolder = fu.getLogur1();
                            f = false;
                        }
                    }
                }
                urlStr.add(fu.getLogur1());
            }

            if (pointFolder != null && pointFolder.length() > 0 && !view.getSaveFolderName().equals("")) {
                int lia = pointFolder.indexOf("/" + view.getSaveFolderName() + "/");
                pointFolder = pointFolder.substring(0, lia + view.getSaveFolderName().length() + 2);
                addOFilesByPointFile(view, userInfo, pointFolder, fileManFileInfo);
            } else {
                addOldOrderNoNewFiles(view, userInfo, urlStr, fileManFileInfo);
            }

        } else {
            this.addFilesData(view, userInfo);
        }
    }


    public void saveFolderMessage(DownloadView view, UserInfo userInfo) throws Exception {
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(userInfo.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            addOldOrderNoNewFilesByFolder(view, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
        } else {
            try {
                this.addFilesDatabyFolder(view, userInfo);
            } catch (Exception e) {
                throw e;
            }
        }

    }

    public DownloadView uploadFileByMappedByteBuffer(MultipartFileParam param, UserInfo userInfo) throws Exception {
        param.setFilefolderNames(param.getFilefolderNames().replace("*", ","));
        String[] newArrayFileFolderName = param.getFilefolderNames().split(":");
        String fileNameNew;
        String filePathName = "";
        for (int i = 0; i < newArrayFileFolderName.length; i++) {
            if (newArrayFileFolderName[i].trim().length() > 0) {
                fileNameNew = subAfterString(newArrayFileFolderName[i], "/");
                if (fileNameNew.equals(param.getName())) {
                    filePathName = StringUtil.subMyString(newArrayFileFolderName[i], "/");
                }
            }
        }
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        String fileName = param.getName();
        File tmpDir;
        String uploadDirPath = finalDirPath;
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(userInfo.getUserName(), param.getOrderNo(), param.getSalor());
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            Integer pointindex = StringUtils.ordinalIndexOf(oldFileUrls.get(0).getLogur1(), "/", 5);
            String oldsPath = oldFileUrls.get(0).getLogur1().substring(0, pointindex + 1);
            tmpDir = new File(uploadDirPath + oldsPath + "/" + filePathName);
        } else {
            tmpDir = new File(uploadDirPath + userInfo.getUserName() + "/" + formateString(new Date()) + "/" + param.getSalor() + "/"
                    + param.getRandomNum() + "/" + param.getOrderNo() + "/" + filePathName);
        }
        File tmpFile = new File(tmpDir, fileName);
        if (fileName != null) {
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
            RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
            FileChannel fileChannel = tempRaf.getChannel();
            long offset = CHUNK_SIZE * param.getChunk();
            byte[] fileData = param.getFile().getBytes();
            try {
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
                mappedByteBuffer.put(fileData);
                FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
                fileChannel.close();
                tempRaf.close();
                mappedByteBuffer.clear();

                boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
                if (isOk) {
                    System.out.println("");

                    boolean isdeleteconf = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                boolean isdeleteconf1 = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
            }
        }
        return null;
    }


    public DownloadView uploadFileByMappedByteBuffer1(MultipartFileParam param, UserInfo userInfo) throws Exception {
        File tmpDir = null;
        String uploadDirPath = finalDirPath;
        param.setName(param.getName().replace("*", ","));
        String fileName = param.getName();
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> urlStr = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(userInfo.getUserName(), param.getOrderNo(), param.getSalor());
        String pointFolder = null;
        boolean f = true;
        if (fileManFileInfo.size() > 0) {
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                if (param.getSaveFolderName() != null) {
                    if (param.getSaveFolderName() != "" && f) {
                        if (fu.getLogur1().contains("/" + param.getSaveFolderName() + "/")) {
                            pointFolder = fu.getLogur1();
                            f = false;
                        }
                    }
                }
                urlStr.add(fu.getLogur1());
            }

            if (pointFolder != null && pointFolder.length() > 0 && !param.getSaveFolderName().equals("")) {//代表为用户指定目录
                int lia = pointFolder.indexOf("/" + param.getSaveFolderName() + "/");
                pointFolder = pointFolder.substring(0, lia + param.getSaveFolderName().length() + 1);
                tmpDir = new File(uploadDirPath + pointFolder);

            } else {
                String oldPath = null;
                Integer pointindex = null;
                String oldsPath = null;
                oldPath = urlStr.get(0);
                pointindex = StringUtils.ordinalIndexOf(oldPath, "/", 5);
                oldsPath = oldPath.substring(0, pointindex + 1);
                tmpDir = new File(uploadDirPath + oldsPath);
            }

        } else {
            tmpDir = new File(uploadDirPath + userInfo.getUserName() + "/" + formateString(new Date()) + "/" + param.getSalor() + "/"
                    + param.getRandomNum() + "/" + param.getOrderNo() + "/");
        }

        File tmpFile = new File(tmpDir, fileName);
        if (fileName != null) {
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
            RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
            FileChannel fileChannel = tempRaf.getChannel();
            long offset = CHUNK_SIZE * param.getChunk();
            byte[] fileData = param.getFile().getBytes();
            try {
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
                mappedByteBuffer.put(fileData);
                FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
                fileChannel.close();
                tempRaf.close();
                mappedByteBuffer.clear();

                boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
                if (isOk) {
                    System.out.println("");

                    boolean isdeleteconf = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                boolean isdeleteconf1 = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
            }
        }
        return null;
    }


    public void modifyFolderByMappedByteBuffer(MultipartFileParam param, UserInfo info) throws Exception {
        param.setName(param.getName().replace("*", ","));
        String fileName = param.getName();
        String uploadDirPath = finalDirPath;
        String filePathName = StringUtil.subMyString(param.getWebkitRelativePath(), "/");
        FilemanUrl filemanUrl;
        int index;
        String centerPath = null;
        String filePath = null;
        Integer pointindex = 0;
        String oldsPath = null;
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(info.getUserName(), param.getOrderNo(), param.getSalor());
        if (fileManFileInfo.size() > 0) {
            filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(fileName, fileManFileInfo.get(0).getId());
            filePath = StringUtil.subMyString(filemanUrl.getLogur1(), "/");
            File tmpDir = new File(uploadDirPath + filePath);
            if (tmpDir.exists()) {
                FileUtil.delFile(tmpDir + "/" + fileName);
                File tmpFile = new File(tmpDir, fileName);
                if (!tmpFile.exists()) {
                    tmpFile.getParentFile().mkdir();
                    tmpFile.createNewFile();
                }
                RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
                FileChannel fileChannel = tempRaf.getChannel();
                long offset = CHUNK_SIZE * param.getChunk();
                byte[] fileData = param.getFile().getBytes();
                try {
                    MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
                    mappedByteBuffer.put(fileData);
                    // 释放
                    FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
                    fileChannel.close();
                    tempRaf.close();
                    mappedByteBuffer.clear();
                    boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
                    if (isOk) {
                        System.out.println("");
                        boolean isdeleteconf = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                } finally {
                    boolean isdeleteconf1 = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
                }

            }
        }
    }

    public void modifyFileByMappedByteBuffer(MultipartFileParam param, UserInfo info) throws Exception {
        param.setName(param.getName().replace("*", ","));
        String fileName = param.getName();
        String uploadDirPath = finalDirPath;
        String oldsPath = null;
        FilemanUrl filemanUrl = null;
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(info.getUserName(), param.getOrderNo(), param.getSalor());
        if (fileManFileInfo.size() > 0) {
            filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(fileName, fileManFileInfo.get(0).getId());
            if (filemanUrl != null) {
                oldsPath = StringUtil.subMyString(filemanUrl.getLogur1(), "/");
            }
        }

        File tmpDir = new File(uploadDirPath + oldsPath);
        if (tmpDir.exists()) {
            FileUtil.delFile(tmpDir + "/" + fileName);
            File tmpFile = new File(tmpDir, fileName);
            if (!tmpFile.exists()) {
                tmpFile.getParentFile().mkdir();
                tmpFile.createNewFile();
            }
            RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
            FileChannel fileChannel = tempRaf.getChannel();
            long offset = CHUNK_SIZE * param.getChunk();
            byte[] fileData = param.getFile().getBytes();
            try {
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
                mappedByteBuffer.put(fileData);
                FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
                fileChannel.close();
                tempRaf.close();
                mappedByteBuffer.clear();

                boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
                if (isOk) {
                    System.out.println("");
                    boolean isdeleteconf = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                boolean isdeleteconf1 = deleteFile(uploadDirPath.concat(fileName).concat(".conf"));
            }
        }
    }


    @Override
    public void deleteAll() throws Exception {
        stringRedisTemplate.delete(Constants.FILE_UPLOAD_STATUS);
        stringRedisTemplate.delete(Constants.FILE_MD5_KEY);
    }

    @Override
    public void init() throws Exception {
        try {
            Files.createDirectory(rootPaht);
        } catch (FileAlreadyExistsException e) {
        } catch (IOException e) {
        }
    }

    public void uploadFileRandomAccessFile(MultipartFileParam param) throws Exception {
        String fileName = param.getName();
        String tempDirPath = finalDirPath + param.getMd5();
        String tempFileName = fileName + "_tmp";
        File tmpDir = new File(tempDirPath);
        File tmpFile = new File(tempDirPath, tempFileName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
        long offset = CHUNK_SIZE * param.getChunk();
        accessTmpFile.seek(offset);
        accessTmpFile.write(param.getFile().getBytes());
        accessTmpFile.close();

        boolean isOk = checkAndSetUploadProgress(param, tempDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
        }
    }



    private boolean deleteFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public List<Employee> findAllSalorByDeptName() throws Exception {
        return fileUploadAndDownMapper.findAllSalorByDeptName();
    }


    private boolean checkAndSetUploadProgress(MultipartFileParam param, String uploadDirPath) throws IOException {
        String fileName = param.getName();
        File confFile = new File(uploadDirPath, fileName + ".conf");
        RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
        accessConfFile.setLength(param.getChunks());
        accessConfFile.seek(param.getChunk());
        accessConfFile.write(Byte.MAX_VALUE);

        byte[] completeList = FileUtils.readFileToByteArray(confFile);
        byte isComplete = Byte.MAX_VALUE;
        for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
            isComplete = (byte) (isComplete & completeList[i]);
        }

        accessConfFile.close();
        if (isComplete == Byte.MAX_VALUE) {
            stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "true");
            stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName);
            return true;
        } else {
            if (!stringRedisTemplate.opsForHash().hasKey(Constants.FILE_UPLOAD_STATUS, param.getMd5())) {
                stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "false");
            }
            if (stringRedisTemplate.hasKey(Constants.FILE_MD5_KEY + param.getMd5())) {
                stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName + ".conf");
            }
            return false;
        }
    }

    /**
     *
     * @param
     * @param
     * @return
     */
    public boolean renameFile(File toBeRenamed, String toFileNewName) throws Exception {
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        return toBeRenamed.renameTo(newFile);
    }

    public List<String> findAllExtension() throws Exception {
        return fileUploadAndDownMapper.findAllExtension();
    }


    /**
     *
     * @auther: homey Wong
     * @date: 2019/3/22  上午 10:06
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public void deleteByHeadIdAndItemId(List<DownloadView> deleteViews, List<File> files) throws Exception {
        List<Integer> singleHeadIds = new ArrayList<Integer>();
        int pointindex = 0;
        int uppointindex = 0;
        String fourleverPath = "";
        String upfourleverPath = "";
        List<String> deleteUrls = new ArrayList<String>();
        List<Integer> itemIds = new ArrayList<Integer>();
        boolean isIn = false;
        List<DownloadView> singleHeadIdList = new ArrayList<DownloadView>();
        for (DownloadView view : deleteViews) {
            itemIds.add(view.getFileUrlId());
            deleteUrls.add(view.getUrlAddr());
            if (!singleHeadIds.contains(view.getId())) {
                singleHeadIds.add(view.getId());
                singleHeadIdList.add(view);
            }
        }
        fileUploadAndDownMapper.deleteUrlByHeadIdAndItemIds(itemIds);
        fileUploadAndDownMapper.deleteRightItemIds(itemIds);
        fileUploadAndDownMapper.deleteRecordByUrIds(itemIds);

        for (DownloadView vi : singleHeadIdList) {
            int itemCount = fileUploadAndDownMapper.getItemCountByHeadId(vi.getId());
            if (itemCount == 0) {
                pointindex = StringUtils.ordinalIndexOf(vi.getUrlAddr(), "/", 5);
                uppointindex = StringUtils.ordinalIndexOf(vi.getUrlAddr(), "/", 4);
                fourleverPath = vi.getUrlAddr().substring(0, pointindex);
                upfourleverPath = vi.getUrlAddr().substring(0, uppointindex);
                FileUtil.delFolder(finalDirPath + fourleverPath);
                FileUtil.delFolderIFNoFiles(finalDirPath + upfourleverPath);
                fileUploadAndDownMapper.deleteFileManFileInfo(vi.getId());
            } else {
                fileUploadAndDownMapper.updateFileInfoTotalFilesNum(vi.getId(), itemCount);
                isIn = true;
            }
        }
        File file = null;
        boolean flag = true;
        String deleteFileDirectory = "";
        if (isIn) {
            for (String url : deleteUrls) {
                FileUtil.delFile(finalDirPath + url);
            }
        }
    }

    public List<ShowUpdateDownRecord> getFileModifyRecordByUrlId(Integer urlId) throws Exception {
        return fileUploadAndDownMapper.getFileModifyRecordByUrlId(urlId);
    }

    public List<ShowUpdateDownRecord> getFileDownRecordByUrlId(Integer urlId) throws Exception {
        return fileUploadAndDownMapper.getFileDownRecordByUrlId(urlId);
    }

    public List<ShowUpdateDownRecord> getFileDownRecordByFolOrdilAndOrderNo(DownloadView view) throws Exception {
        Pattern orderNoRegex = Pattern.compile("^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$");
        List<FilemanUrl> urls = null;
        List<Integer> urlIds = null;
        Matcher match = orderNoRegex.matcher(view.getFolderOrFileName());
        if (view.getFolderOrFileName().contains(".")) {
            return fileUploadAndDownMapper.getFileDownRecordByOrderNoAndFileName(view.getOrderNo(), view.getFolderOrFileName());
        } else {
            if (match.matches()) {
                return fileUploadAndDownMapper.getFileDownRecordByOrderNo(view.getFolderOrFileName());
            } else {
                urls = fileUploadAndDownMapper.getFileUrlByOrderNo(view.getOrderNo());
                urlIds = new ArrayList<Integer>();
                if (urls != null && urls.size() > 0) {
                    for (FilemanUrl url : urls) {
                        if (url.getLogur1().indexOf("/" + view.getFolderOrFileName() + "/") > 0) {
                            urlIds.add(url.getId());
                        }
                    }
                }
                return fileUploadAndDownMapper.getFileDownRecordByUrlIds(urlIds);
            }
        }
    }


    public List<ShowUpdateDownRecord> getFileModifyRecordByFolOrFilAndOrderNo(DownloadView view) throws Exception {
        Pattern orderNoRegex = Pattern.compile("^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$");
        List<FilemanUrl> urls = null;
        List<Integer> urlIds = null;
        Matcher match = orderNoRegex.matcher(view.getFolderOrFileName());
        if (view.getFolderOrFileName().contains(".")) {
            return fileUploadAndDownMapper.getFileModifyRecordByOrderNoAndFileName(view.getOrderNo(), view.getFolderOrFileName());
        } else {
            if (match.matches()) {
                return fileUploadAndDownMapper.getFileModifyRecordByOrderNo(view.getFolderOrFileName());
            } else {
                urls = fileUploadAndDownMapper.getFileUrlByOrderNo(view.getOrderNo());
                urlIds = new ArrayList<Integer>();
                if (urls != null && urls.size() > 0) {
                    for (FilemanUrl url : urls) {
                        if (url.getLogur1().indexOf("/" + view.getFolderOrFileName() + "/") > 0) {
                            urlIds.add(url.getId());
                        }
                    }
                }
                return fileUploadAndDownMapper.getFileModifyRecordByUrlIds(urlIds);
            }
        }
    }
}
