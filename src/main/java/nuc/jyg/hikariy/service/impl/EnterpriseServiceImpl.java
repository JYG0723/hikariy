package nuc.jyg.hikariy.service.impl;

import nuc.jyg.hikariy.dao.EnterpriseRepository;
import nuc.jyg.hikariy.model.Enterprise;
import nuc.jyg.hikariy.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 20:31 2019-04-25.
 * @description
 */
@Service(value = "iEnterpriseService")
public class EnterpriseServiceImpl implements IEnterpriseService {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Override
    public Enterprise findByEnterpriseName(String enterpriseName) {
        return enterpriseRepository.findByEnterpriseName(enterpriseName);
    }

    @Override
    public Enterprise save(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Enterprise modifyInfo(Enterprise newEnterprise) {
        return enterpriseRepository.saveAndFlush(newEnterprise);
    }

    @Override
    public List<Enterprise> findAllEnterprise() {
        return enterpriseRepository.findAll();
    }

    @Override
    public Enterprise findById(Integer id) {
        return enterpriseRepository.findById(id).orElse(null);
    }
}
