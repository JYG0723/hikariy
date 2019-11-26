package nuc.jyg.hikariy.dao;

import nuc.jyg.hikariy.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ji YongGuang.
 * @date 20:32 2019-04-25.
 * @description
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    Enterprise findByEnterpriseName(String enterpriseName);
}
