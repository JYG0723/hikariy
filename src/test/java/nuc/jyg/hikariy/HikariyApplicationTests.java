package nuc.jyg.hikariy;

import nuc.jyg.hikariy.dao.StockRepository;
import nuc.jyg.hikariy.model.Stock;
import nuc.jyg.hikariy.util.BigDecimalUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HikariyApplicationTests {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insertFalseDataTest() {
        // 先为各个企业生成 100 天的股票(假数据，方便生成报表)
        // 测试数据的股票头 -> ts******
        // String stockNumber = "ts" + UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        for (int i = 0; i < 1; i++) {
            Stock stock = new Stock();
            // 企业id
            stock.setEnterpriseId(5);// 4,5,7,8
            // 企业名称
            stock.setEnterpriseName("京东");
            // 拼音编码
            stock.setShorthandNumber("jd");
            // 股票编号
            stock.setStockNumber("ts782356");

            // 该值填充从数据库中读出的昨日该支股票的收盘价
            Stock newestStock = stockRepository.findByBiggestId();
            stock.setBeforeClosingPrice(newestStock.getClosingPrice());

            // 第一条开盘数据由手动录入，防止出现逻辑上的死锁。
            // 随机数决定今天的开盘价相较于昨日是涨还是跌(模拟动荡的政治形势)，随机出的数是 2，3，4的倍数涨，大环境涨
            int seed = (int) (Math.random() * 10);
            boolean upsAndDowns = false;
            if (seed % 2 == 0 || seed % 3 == 0 || seed % 4 == 0)
                upsAndDowns = true;

            double floatNumber = 0.35 + (int) (Math.random() * 10) / 10.0;
            if (upsAndDowns) {
                stock.setOpeningPrice(BigDecimalUtils.add(stock.getBeforeClosingPrice().doubleValue(), floatNumber));
            } else stock.setOpeningPrice(BigDecimalUtils.sub(stock.getBeforeClosingPrice().doubleValue(), floatNumber));

            // 当前价格(不具有展示意义，固定值给死，不随市场浮动)
            // 开盘价 [+-] i*0.01，一搬一天的开盘价就定了当天的股票高低值得中点，所以不应该超出开盘价太多的浮动值。
            if (i % 2 == 0) {// 偶加，奇掉
                stock.setCurrentPrice(BigDecimalUtils.add(stock.getOpeningPrice().doubleValue(), i * 0.03));
            } else stock.setCurrentPrice(BigDecimalUtils.sub(stock.getOpeningPrice().doubleValue(), i * 0.03));

            // 开盘价 从当天一开始便反应了该公司当天股票一天的大体走向，从开盘价和当前价二者和取均值即可。因为无论当前价是比开盘价涨还是跌都是合理的，如果是跌，即股价回归理性，如果是涨，即今日形势大好。政治决定经济走势
            BigDecimal addResult = BigDecimalUtils.add(stock.getOpeningPrice().doubleValue(),
                    stock.getCurrentPrice().doubleValue());
            stock.setClosingPrice(addResult.divide(new BigDecimal("2")));

            // 最高点 -> 如果当前价高于开盘价那么即当前价，如果低于开盘价，那么开盘价高几点即最高点。
            if (stock.getCurrentPrice().doubleValue() > stock.getOpeningPrice().doubleValue())
                stock.setHighestPoint(stock.getCurrentPrice());
            else stock.setHighestPoint(BigDecimalUtils.sub(stock.getOpeningPrice().doubleValue(), i * 0.03));

            // 最低点 -> 如果当前价低于开盘价那么即当前价，如果高于开盘价，那么开盘价低几点即最低点。
            if (stock.getCurrentPrice().doubleValue() < stock.getOpeningPrice().doubleValue())
                stock.setLowestPoint(stock.getCurrentPrice());
            else stock.setLowestPoint(BigDecimalUtils.add(stock.getOpeningPrice().doubleValue(), i * 0.03));

            stock.setCreateTime(new Date());
            stock.setUpdateTime(new Date());
            stockRepository.save(stock);
        }
    }

    @Test
    public void insertBaseDataTest() {
        Stock stock = new Stock();
        // 企业id
        stock.setEnterpriseId(4);// 4,5,7,8
        // 企业名称
        stock.setEnterpriseName("京东");
        // 拼音编码
        stock.setShorthandNumber("jd");
        // 股票编号
        stock.setStockNumber("ts782356");

        stock.setBeforeClosingPrice(new BigDecimal("0.00"));

        stock.setOpeningPrice(new BigDecimal("28.36"));
        stock.setCurrentPrice(new BigDecimal("28.36"));
        stock.setClosingPrice(new BigDecimal("28.36"));

        stock.setHighestPoint(new BigDecimal("28.66"));
        stock.setLowestPoint(new BigDecimal("28.16"));

        stock.setCreateTime(new Date());
        stock.setUpdateTime(new Date());
        stockRepository.save(stock);
    }

}
