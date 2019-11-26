package nuc.jyg.hikariy.service;

import nuc.jyg.hikariy.model.Stock;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 21:37 2019-05-02.
 * @description
 */
public interface IStockService {

    List<Stock> findAllStocksInToday();

    List<Stock> findAllStocksByStockNumber(String stockNumber);

    List<Stock> findAllStocksByEnterpriseName(String enterpriseName);

    List<Double> getEnterpriseStocksGainsByEnterpriseName(String enterpriseName);

    Stock save(Stock stock);
}
