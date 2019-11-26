package nuc.jyg.hikariy.service.impl;

import nuc.jyg.hikariy.dao.StockRepository;
import nuc.jyg.hikariy.model.Stock;
import nuc.jyg.hikariy.service.IStockService;
import nuc.jyg.hikariy.util.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 21:37 2019-05-02.
 * @descriptio 每天结束将每个公司的股票存入数据库中
 */
@Service(value = "iStockService")
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findAllStocksInToday() {
        // 获取今天的数据
        List<Stock> stocks = stockRepository.findByToDay();
        return stocks;
    }

    @Override
    public List<Stock> findAllStocksByStockNumber(String stockNumber) {
        return stockRepository.findByStockNumber(stockNumber);
    }

    @Override
    public List<Stock> findAllStocksByEnterpriseName(String enterpriseName) {
        return stockRepository.findByEnterpriseName(enterpriseName);
    }

    @Override
    public List<Double> getEnterpriseStocksGainsByEnterpriseName(String enterpriseName) {
        List<Stock> enterpriseStocks = findAllStocksByEnterpriseName(enterpriseName);
        List<Double> enterpriseGains = new ArrayList<>();
        for (int i = 0; i < enterpriseStocks.size(); i++) {
            if (i == 0)
                continue;

            Stock stock1 = enterpriseStocks.get(i);
            Stock stock2 = enterpriseStocks.get(i - 1);

            BigDecimal subResult = BigDecimalUtils.sub(stock1.getClosingPrice().doubleValue(),
                    stock2.getClosingPrice().doubleValue());
            BigDecimal divResult = BigDecimalUtils.div(subResult.doubleValue(), stock2.getClosingPrice().doubleValue());

            Double result =
                    new BigDecimal(divResult.doubleValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            enterpriseGains.add(result);
        }
        return enterpriseGains;
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }
}
