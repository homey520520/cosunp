package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Extension;
import com.cosun.cosunp.entity.Rules;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IrulesServ {

    List<Rules> queryRulesByCondition(Rules rules) throws Exception;

    List<Rules> findAllRulesAll() throws Exception;

    Rules getRulesByName(String name) throws Exception;

    int queryRulesByConditionCount(Rules rules) throws Exception;

    List<Rules> findAllRulesByDeptId(Rules rules) throws Exception;

    void saveExtension(List<Extension> exts) throws Exception;

    int findAllRulesByDeptIdCount(Rules rules) throws Exception;

    Rules getRulesByFirst() throws Exception;

    void saveFirstShowById(Rules rules) throws Exception;

    int getRulesByNameAndId(String filename, Integer deptId) throws Exception;

    int getRulesByDeptId(Integer deptId) throws Exception;

    boolean saveRuleByRuleBean(MultipartFile file, Rules rules) throws Exception;

    List<Rules> findAllRules(Rules rules) throws Exception;

    int findAllRulesCount() throws Exception;

    void deleteRulesByBatch(List<Integer> ids) throws Exception;

    Rules getRulesById(Integer id) throws Exception;

    boolean updateRulesById(MultipartFile file, Rules rules) throws Exception;

    void deleteRulesById(Integer id) throws Exception;
}
