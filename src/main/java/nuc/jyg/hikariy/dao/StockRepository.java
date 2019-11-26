package nuc.jyg.hikariy.dao;

import nuc.jyg.hikariy.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 21:38 2019-05-02.
 * @description
 */
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(nativeQuery = true, value = "select * from stock where stock_number = : stockNumber and id = (select max" +
            "(id) from stock)")
    Stock findByStockNumberRecentlyEntity(String stockNumber);

    @Query(nativeQuery = true, value = "select * from stock where to_days(create_time) >= to_days(now());")
    List<Stock> findByToDay();

    @Query(nativeQuery = true, value = "select * from stock where stock_number = :stockNumber order by id asc")
    List<Stock> findByStockNumber(String stockNumber);

    @Query(nativeQuery = true, value = "select * from stock where enterprise_name = :enterprise order by id asc")
    List<Stock> findByEnterpriseName(String enterprise);

    @Query(nativeQuery = true, value = "select * from stock where id = (select max(id) from stock)")
    Stock findByBiggestId();
}
