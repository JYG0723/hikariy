package nuc.jyg.hikariy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nuc.jyg.hikariy.common.HostHolder;
import nuc.jyg.hikariy.model.Enterprise;
import nuc.jyg.hikariy.model.Stock;
import nuc.jyg.hikariy.service.IEnterpriseService;
import nuc.jyg.hikariy.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Ji YongGuang.
 * @date 21:36 2019-05-02.
 * @description
 */
@Controller
@RequestMapping(value = "/stocks")
public class StockController {

    @Autowired
    private IStockService iStockService;

    @Autowired
    private IEnterpriseService iEnterpriseService;

    @Autowired
    private HostHolder hostHolder;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "")
    public String getAllStocks(Model model) {
        List<Stock> stocks = iStockService.findAllStocksInToday();
        model.addAttribute("stocks", stocks);
        return "stock-list";
    }

    @PostMapping(value = "")
    public String addEnterpriseStock(Stock stock, Model model) {
        Enterprise enterprise = iEnterpriseService.findByEnterpriseName(stock.getEnterpriseName());
        if (!Objects.isNull(enterprise)) {
            stock.setEnterpriseId(enterprise.getId());
            iStockService.save(stock);
        } else model.addAttribute("message", "添加股票失败，所填企业不存在");
        return "redirect:/stocks";
    }

    @GetMapping(value = "/info")
    public String getInfoByStockNumber(String stockNumber, HttpSession httpSession) {
        List<Stock> enterpriseStocks = iStockService.findAllStocksByStockNumber(stockNumber);
        String enterpriseName = enterpriseStocks.get(0).getEnterpriseName();

        List<List<Object>> values = new ArrayList<>();
        enterpriseStocks.forEach(item -> {
            List<Object> valueItem = new ArrayList<>();
            valueItem.add(item.getCreateTime().toString().substring(0, 11));
            valueItem.add(item.getClosingPrice());
            values.add(valueItem);
        });

        hostHolder.setValues(values);
        Enterprise enterprise = iEnterpriseService.findByEnterpriseName(enterpriseName);
        httpSession.setAttribute("enterprise", enterprise);
        return "echarts-stocks";
    }

    @GetMapping(value = "/compare/average")
    public String compareWithAllEnterpriseStockAverage(String enterpriseName) {
        // TODO 先拿到一个企业的所有股票，然后算他每天的收盘价比前一天的收盘价的差值。如果大于则代表是涨，小于则是跌。然后除以前一天的收盘价，则是涨跌的百分比。用集合记录每天的百分比即是企业数据。
        // todo 企业的平均值即所有企业每天的数据都这么算，然后平均一下。
        List<Double> targetEnterpriseGains = iStockService.getEnterpriseStocksGainsByEnterpriseName(enterpriseName);

        List<Enterprise> allEnterprise = iEnterpriseService.findAllEnterprise();
        List<String> allEnterpriseName = new ArrayList<>();
        allEnterprise.forEach(item -> allEnterpriseName.add(item.getEnterpriseName()));

        List<List<Double>> gains = new ArrayList<>();
        Map<Integer, Double> gainsWithDay = new HashMap<>();
        allEnterpriseName.forEach(item -> {
            List<Double> currentEnterpriseGains = iStockService.getEnterpriseStocksGainsByEnterpriseName(item);
            for (int i = 0; i < currentEnterpriseGains.size(); i++) {
                if (!gainsWithDay.containsKey(i)) {
                    gainsWithDay.put(i, currentEnterpriseGains.get(i));
                } else gainsWithDay.put(i, (gainsWithDay.get(i) + currentEnterpriseGains.get(i)) / 2);
            }
            gains.add(currentEnterpriseGains);
        });

        // targetEnterpriseGains & values
        List<List<Object>> result = new ArrayList<>();
        Collection<Double> values = gainsWithDay.values();

        List<Stock> stocks = iStockService.findAllStocksByEnterpriseName(enterpriseName);
        List<String> dateResult = new ArrayList<>();
        for (int i = 0; i < stocks.size(); i++) {
            if (i == 0)
                continue;
            dateResult.add(stocks.get(i).getCreateTime().toString().substring(0, 11));
        }

        result.add(Collections.singletonList(targetEnterpriseGains));
        result.add(Collections.singletonList(values));
        result.add(Collections.singletonList(dateResult));

        hostHolder.setAllValues(result);
        return "echarts-average";
    }
}
