package nuc.jyg.hikariy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nuc.jyg.hikariy.common.HostHolder;
import nuc.jyg.hikariy.model.Stock;
import nuc.jyg.hikariy.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 18:24 2019-04-27.
 * @description 页面跳转
 */
@Slf4j
@Controller
@RequestMapping(value = "/pages")
public class PageController {

    @Autowired
    private HostHolder hostHolder;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private IStockService iStockService;

    @GetMapping(value = "/users/login")
    public String login() {
        log.info("pageLogin");
        return "pages-login";
    }

    @GetMapping(value = "/users/reset_password_entrance")
    public String resetPasswordEntrance() {
        log.info("resetPasswordEntrance");
        return "pages-reset-password-entrance";
    }

    @GetMapping(value = "/lock_screen")
    public String locakScreen() {
        log.info("locakScreen");
        return "pages-lock-screen";
    }

    @GetMapping(value = "/users/reset_password")
    public String reset_password() {
        log.info("resetPassword");
        return "pages-reset-password";
    }

    @GetMapping(value = "/enterprises/add")
    public String addEnterpriseInfo() {
        log.info("addEnterpriseInfo");
        return "form-elements";
    }

    @GetMapping(value = "/stocks/add")
    public String addEnterpriseStockInfo() {
        log.info("addEnterpriseStockInfo");
        return "form-stocks-elements";
    }

    @PostMapping(value = "/stocks/info")
    @ResponseBody
    public String socksInfo() throws JsonProcessingException {
        log.info("socksInfo");
        ArrayList<ArrayList> values = (ArrayList) hostHolder.getValues();
        return objectMapper.writeValueAsString(values);
    }

    @PostMapping(value = "/stocks/average/info")
    @ResponseBody
    public String socksAllEnterpriseAverageInfo() throws JsonProcessingException {
        log.info("socksAllEnterpriseAverageInfo");
        ArrayList<ArrayList> values = (ArrayList) hostHolder.getAllValues();
        return objectMapper.writeValueAsString(values);
    }

    @GetMapping(value = "/stocks/enterprise")
    public String socksEnterprise(String enterpriseName) {
        log.info("socksEnterprise");
        List<Stock> stocks = iStockService.findAllStocksByEnterpriseName(enterpriseName);
        String stockNumber = stocks.get(0).getStockNumber();
        return "redirect:/stocks/info?stockNumber=" + stockNumber;
    }

    @GetMapping(value = "/users/add")
    public String usersAdd() {
        log.info("usersAdd");
        return "users-add";
    }
}
