package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoMapper {


    @Select("SELECT * FROM userinfo WHERE username = #{userName} and userpwd=#{userPwd}")
    UserInfo findUserByUserNameandPassword(@Param("userName") String userName, @Param("userPwd") String userPwd);

    @Select("SELECT mobilenum FROM userinfo WHERE username = #{userName}")
    String getMobileNumByUserName(String userName);

    @Select("SELECT * FROM userinfo ")
    List<UserInfo> findAllUser();

    @Select("select o.* from userinfo o left join employee e on o.empno = e.empno\n" +
            "left join dept t on e.deptId = t.id where deptname in ( \"研发中心\" ,\"技术部\") or o.uid = '1000005' ")
    List<UserInfo> findAllUserOnlyDesigner();

    @Select("SELECT count(*) FROM userinfo ")
    int findAllUserCount();

    @Delete("delete from userinfo where uid = #{uId}")
    void deleteUserInfotByUId(Integer uId);


    @Delete({
            "<script>",
            "delete",
            "from userinfo",
            "where uid in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteUserByBatch(@Param("ids") List<Integer> ids);

    @Update("update userinfo set userpwd = #{newPassword} where uid = #{uId} ")
    void setNewPasswordByuId(Integer uId, String newPassword);

    @Select("SELECT count(*) FROM userinfo where empno = #{empNo}  ")
    int getUserInfoCountByEmpNo(String empNo);

    @Select("SELECT count(*) FROM userinfo where username = #{userName}  ")
    int getUserInfoCountByUserName(String userName);

    @Select("SELECT e.name as name,t.deptname as deptName FROM employee e left join dept t on e.deptId = t.id where e.empno = #{empNo} ")
    Employee getUserInfoByEmpNo(String empNo);


    @Select("SELECT\n" +
            "\tu.uid as uId,\n" +
            "\tu.fullname as fullName,\n" +
            "\te.empno as empNo,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\tu.username as userName,\n" +
            "\tu.useractor as userActor,\n" +
            "\tu.useruploadright as useruploadright,\n" +
            "\tu.newestlogintime as newestlogintime,\n" +
            "\tu.state as state\n" +
            "FROM\n" +
            "\tuserinfo u\n" +
            "LEFT JOIN employee e ON u.empno = e.empno\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "LEFT JOIN position n ON n.id = e.positionId limit #{currentPageTotalNum},#{pageSize}  ")
    List<UserInfo> findAllUserA(UserInfo userInfo);

    @Select("SELECT\n" +
            "\tu.uid as uId,\n" +
            "\tu.fullname as fullName,\n" +
            "\te.empno as empNo,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\tu.username as userName,\n" +
            "\tu.useractor as userActor,\n" +
            "\tu.useruploadright as useruploadright,\n" +
            "\tu.newestlogintime as newestlogintime,\n" +
            "\tu.state as state\n" +
            "FROM\n" +
            "\tuserinfo u\n" +
            "LEFT JOIN employee e ON u.empno = e.empno\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "LEFT JOIN position n ON n.id = e.positionId where uId = #{uId}")
    UserInfo getUserInfoByUId(Integer uId);


    @Insert("INSERT into userinfo (fullname,username,userpwd,state,empno,useruploadright,useractor)  " +
            "values(#{fullName},#{userName},#{userPwd},#{state},#{empNo},#{useruploadright},#{userActor})\n ")
    void saveUserInfoByBean(UserInfo userInfo);

    @Update("update userinfo set username = #{userName},userpwd = #{userPwd} where empno = #{empNo}\n ")
    void updateUserInfoByBean(UserInfo userInfo);

    @Update("update userinfo set state = #{state},useractor = #{userActor},useruploadright=#{useruploadright} where uid = #{uId} ")
    void updateUserInfo(UserInfo userInfo);

    @Update("update userinfo set newestlogintime = #{dateTime} where uid = #{uId} ")
    void updateUserInfoLoginTime(Integer uId,String dateTime);

    @SelectProvider(type = UserDaoProvider.class, method = "queryUserByCondition")
    List<UserInfo> queryUserByCondition(UserInfo userInfo);

    @SelectProvider(type = UserDaoProvider.class, method = "queryUserByConditionCount")
    int queryUserByConditionCount(UserInfo userInfo);

    class UserDaoProvider {


        public String queryUserByCondition(UserInfo userInfo) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tu.uid as uId,\n" +
                    "\tu.fullname as fullName,\n" +
                    "\te.empno as empNo,\n" +
                    "\tt.deptname as deptName,\n" +
                    "\tn.positionName as positionName,\n" +
                    "\tu.username as userName,\n" +
                    "\tu.useractor as userActor,\n" +
                    "\tu.useruploadright as useruploadright,\n" +
                    "\tu.newestlogintime as newestlogintime,\n" +
                    "\tu.state as state\n" +
                    "FROM\n" +
                    "\tuserinfo u\n" +
                    "LEFT JOIN employee e ON u.empno = e.empno\n" +
                    "LEFT JOIN dept t ON t.id = e.deptId\n" +
                    "LEFT JOIN position n ON n.id = e.positionId where 1=1");
            if (userInfo.getNameIds() != null && userInfo.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(userInfo.getNameIds().toString(), "[]") + ") ");

            }

            if (userInfo.getSexIds() != null && userInfo.getSexIds().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(userInfo.getSexIds().toString(), "[]") + ") ");
            }

            if (userInfo.getEmpNo() != null && userInfo.getEmpNo() != "" && userInfo.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (userInfo.getDeptIds() != null && userInfo.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(userInfo.getDeptIds().toString(), "[]") + ") ");
            }

            if (userInfo.getStates() != null && userInfo.getStates().size() > 0) {
                sb.append(" and u.state in (" + StringUtils.strip(userInfo.getStates().toString(), "[]") + ") ");
            }

            if (userInfo.getUserActors() != null && userInfo.getUserActors().size() > 0) {
                sb.append(" and u.useractor in (" + StringUtils.strip(userInfo.getUserActors().toString(), "[]") + ") ");
            }

            if (userInfo.getUseruploadrights() != null && userInfo.getUseruploadrights().size() > 0) {
                sb.append(" and u.useruploadright in (" + StringUtils.strip(userInfo.getUseruploadrights().toString(), "[]") + ") ");
            }

            if (userInfo.getPositionIds() != null && userInfo.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(userInfo.getPositionIds().toString(), "[]") + ") ");
            }

            if (userInfo.getStartIncomDateStr() != null && userInfo.getStartIncomDateStr().length() > 0 && userInfo.getEndIncomDateStr() != null && userInfo.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (userInfo.getStartIncomDateStr() != null && userInfo.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (userInfo.getEndIncomDateStr() != null && userInfo.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }

            if (userInfo.getSortMethod() != null && !"undefined".equals(userInfo.getSortMethod())&& !"undefined".equals(userInfo.getSortByName()) && userInfo.getSortByName() != null) {
                if("name".equals(userInfo.getSortByName())){
                    sb.append(" order by e.name ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("empNo".equals(userInfo.getSortByName())) {
                    sb.append(" order by e.empNo ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("detpName".equals(userInfo.getSortByName())) {
                    sb.append(" order by t.detpName ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("positionName".equals(userInfo.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("userName".equals(userInfo.getSortByName())) {
                    sb.append(" order by u.username  ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("state".equals(userInfo.getSortByName())) {
                    sb.append(" order by u.state ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("userActor".equals(userInfo.getSortByName())) {
                    sb.append(" order by u.userActor ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("uploadPrivi".equals(userInfo.getSortByName())) {
                    sb.append(" order by u.useruploadright ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if("newestLoginTime".equals(userInfo.getSortByName())) {
                    sb.append(" order by u.newestlogintime ");
                    if("asc".equals(userInfo.getSortMethod())){
                        sb.append(" asc ");
                    }else if("desc".equals(userInfo.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" order by e.empno asc ");
            }

            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryUserByConditionCount(UserInfo userInfo) {
           StringBuilder sb = new StringBuilder("SELECT count(u.uid)" +
                    "FROM\n" +
                    "\tuserinfo u\n" +
                    "LEFT JOIN employee e ON u.empno = e.empno\n" +
                    "LEFT JOIN dept t ON t.id = e.deptId\n" +
                    "LEFT JOIN position n ON n.id = e.positionId where 1=1");
            if (userInfo.getNameIds() != null && userInfo.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(userInfo.getNameIds().toString(), "[]") + ") ");

            }

            if (userInfo.getSexIds() != null && userInfo.getSexIds().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(userInfo.getSexIds().toString(), "[]") + ") ");
            }

            if (userInfo.getEmpNo() != null && userInfo.getEmpNo() != "" && userInfo.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (userInfo.getDeptIds() != null && userInfo.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(userInfo.getDeptIds().toString(), "[]") + ") ");
            }

            if (userInfo.getPositionIds() != null && userInfo.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(userInfo.getPositionIds().toString(), "[]") + ") ");
            }

            if (userInfo.getStates() != null && userInfo.getStates().size() > 0) {
                sb.append(" and u.state in (" + StringUtils.strip(userInfo.getStates().toString(), "[]") + ") ");
            }

            if (userInfo.getUserActors() != null && userInfo.getUserActors().size() > 0) {
                sb.append(" and u.useractor in (" + StringUtils.strip(userInfo.getUserActors().toString(), "[]") + ") ");
            }

            if (userInfo.getUseruploadrights() != null && userInfo.getUseruploadrights().size() > 0) {
                sb.append(" and u.useruploadright in (" + StringUtils.strip(userInfo.getUseruploadrights().toString(), "[]") + ") ");
            }


            if (userInfo.getStartIncomDateStr() != null && userInfo.getStartIncomDateStr().length() > 0 && userInfo.getEndIncomDateStr() != null && userInfo.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (userInfo.getStartIncomDateStr() != null && userInfo.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (userInfo.getEndIncomDateStr() != null && userInfo.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

    }


}
