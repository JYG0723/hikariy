package nuc.jyg.hikariy.service;

import nuc.jyg.hikariy.model.Enterprise;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 20:30 2019-04-25.
 * @description
 */
public interface IEnterpriseService {

    /**
     * 根据企业名称检索是否该企业已存在
     *
     * @param enterpriseName
     * @return
     */
    Enterprise findByEnterpriseName(String enterpriseName);

    /**
     * 新增企业
     *
     * @param enterprise 企业信息
     * @return 1/0
     */
    Enterprise save(Enterprise enterprise);

    /**
     * 更新企业信息
     *
     * @param newEnterprise 新企业信息
     * @return true/false
     */
    Enterprise modifyInfo(Enterprise newEnterprise);

    /**
     * 获取所有企业列表
     *
     * @return 企业列表集合
     */
    List<Enterprise> findAllEnterprise();

    /**
     * 根据id寻找企业信息
     * @param id id
     * @return 企业信息
     */
    Enterprise findById(Integer id);
}
