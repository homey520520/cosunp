package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Extension;
import com.cosun.cosunp.entity.Rules;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RulesMapper {

    @Select("select count(*) from rules where deptId = #{deptId} ")
    int getRulesByDeptId(Integer deptId);

    @Select("select * from extension where extension = #{name}")
    Extension getExtensionByName(String name);

    @Insert("INSERT into rules(deptId,uploaderId,uploadDate,titleName,filename,filedir,remark,ftpdir)\n" +
            "values(#{deptId},#{uploaderId},#{uploadDate},#{titleName},#{fileName},#{fileDir},#{remark},#{ftpDir} )\n ")
    void saveRulesBean(Rules rules);

    @Insert("INSERT into extension(extension) values (#{extension})")
    void saveExtensionByBean(Extension ext);

    @Update("update rules set " +
            "uploaderId = #{uploaderId}," +
            "uploadDate = #{uploadDate}," +
            "filename = #{fileName}," +
            "filedir = #{fileDir}," +
            "titlename = #{titleName}," +
            "remark = #{remark}," +
            "ftpdir = #{ftpDir}" +
            " where id = #{id}")
    void updateRulesBean(Rules rules);

    @Update("update rules set " +
            "firstshow = 1" +
            " where id = #{id}")
    void saveFirstShowById(Rules rules);

    @Update("update rules set " +
            "firstshow = 0")
    void cancelFritShowAll();

    @Delete({
            "<script>",
            "delete",
            "from rules",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteRulesByBatch(@Param("ids") List<Integer> ids);

    @Select({
            "<script>",
            "select * ",
            "from rules",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Rules> getRulesByIds(@Param("ids") List<Integer> ids);

    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid limit #{currentPageTotalNum},#{pageSize} ")
    List<Rules> findAllRules(Rules rules);

    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid where t.deptname = #{deptName} and s.firstshow = 1 limit 1")
    Rules getRulesByName(String deptName);

    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid  ")
    List<Rules> findAllRulesAll();

    @Select("select count(*) from rules  ")
    int findAllRulesCount();

    @Delete("delete from rules where id = #{id}")
    void deleteRulesById(Integer id);

    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid where s.id= #{id} ")
    Rules getRulesById(Integer id);

    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid where firstshow = 1 limit 1 ")
    Rules getRulesByFirst();


    @Select("SELECT\n" +
            "\ts.id AS id,\n" +
            "\ts.filename AS fileName,\n" +
            "\ts.deptId AS deptId,\n" +
            "\ts.uploaderId AS uploaderId,\n" +
            "\ts.uploadDate AS uploadDateStr,\n" +
            "\ts.titleName AS titleName,\n" +
            "\ts.filedir AS fileDir,\n" +
            "\ts.remark AS remark,\n" +
            "\tt.deptname AS deptName,\n" +
            "\to.fullname AS uploaderName,\n" +
            "\ts.firstshow AS firstShow,\n" +
            "\ts.ftpdir AS ftpDir\n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid where s.deptId= #{deptId} ")
    List<Rules>  findAllRulesByDeptId(Rules rules);

    @Select("SELECT count(*) \n" +
            "FROM\n" +
            "\trules s\n" +
            "LEFT JOIN dept t ON s.deptId = t.id\n" +
            "LEFT JOIN userinfo o ON s.uploaderId = o.uid where s.deptId= #{deptId} ")
    int  findAllRulesByDeptIdCount(Rules rules);

    @Select("select count(*) from rules where substring_index(filename,'.',1) = #{fileName} and deptId = #{deptId}")
    int getRulesByNameAndId(String fileName,Integer deptId);


    @SelectProvider(type = RulesMapper.RulesDaoProvider.class, method = "queryRulesByCondition")
    List<Rules> queryRulesByCondition(Rules rules);

    @SelectProvider(type = RulesMapper.RulesDaoProvider.class, method = "queryRulesByConditionCount")
    int queryRulesByConditionCount(Rules rules);

    class RulesDaoProvider {

        public String queryRulesByCondition(Rules rules) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\ts.id AS id,\n" +
                    "\ts.filename AS fileName,\n" +
                    "\ts.deptId AS deptId,\n" +
                    "\ts.uploaderId AS uploaderId,\n" +
                    "\ts.uploadDate AS uploadDateStr,\n" +
                    "\ts.titleName AS titleName,\n" +
                    "\ts.filedir AS fileDir,\n" +
                    "\ts.remark AS remark,\n" +
                    "\tt.deptname AS deptName,\n" +
                    "\to.fullname AS uploaderName\n" +
                    "FROM\n" +
                    "\trules s\n" +
                    "LEFT JOIN dept t ON s.deptId = t.id\n" +
                    "LEFT JOIN userinfo o ON s.uploaderId = o.uid where 1=1");
            if (rules.getDeptIds() != null && rules.getDeptIds().size() > 0) {
                sb.append(" and s.deptId in (" + StringUtils.strip(rules.getDeptIds().toString(), "[]") + ") ");
            }

            sb.append(" limit #{currentPageTotalNum},#{pageSize} ");
            return sb.toString();
        }

        public String queryRulesByConditionCount(Rules rules) {
            StringBuilder sb = new StringBuilder("SELECT count(s.id) " +
                    "FROM\n" +
                    "\trules s\n" +
                    "LEFT JOIN dept t ON s.deptId = t.id\n" +
                    "LEFT JOIN userinfo o ON s.uploaderId = o.uid where 1=1");
            if (rules.getDeptIds() != null && rules.getDeptIds().size() > 0) {
                sb.append(" and s.deptId in (" + StringUtils.strip(rules.getDeptIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }

    }

}
