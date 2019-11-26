package nuc.jyg.hikariy.controller;

import nuc.jyg.hikariy.model.Enterprise;
import nuc.jyg.hikariy.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Ji YongGuang.
 * @date 19:48 2019-04-25.
 * @description 企业信息接口
 */
@Controller
@RequestMapping(value = "/enterprises")
public class EnterpriseController {

    @Autowired
    private IEnterpriseService iEnterpriseService;

    @PostMapping
    public String addEnterpriseInfo(Enterprise enterprise, Model model) {
        Enterprise existEnterprise = iEnterpriseService.findByEnterpriseName(enterprise.getEnterpriseName());
        if (Objects.nonNull(existEnterprise)) {
            model.addAttribute("message", "该企业名已经存在");
            // 重新录入
            return "form-elements";
        }

        Enterprise savedEnterprise = iEnterpriseService.save(enterprise);
        // todo 添加成功可以推到详情页
        model.addAttribute("enterprise", savedEnterprise);
        return "redirect:/enterprises";
    }

    @PostMapping(value = "/{id}")
    public String modifyEnterpriseInfo(@PathVariable(value = "id") Integer id, Enterprise enterprise, Model model) {
        enterprise.setId(id);
        Enterprise updatedEnterprise = iEnterpriseService.modifyInfo(enterprise);
        model.addAttribute("enterprise", updatedEnterprise);
        return "form-advanced";
    }

    @GetMapping(value = "")
    public String getAllEnterprises(Model model) {
        List<Enterprise> enterprises = iEnterpriseService.findAllEnterprise();
        model.addAttribute("enterprises", enterprises);
        return "enterprise-list";
    }

    @GetMapping(value = "/info")
    public String getAllEnterprises(@RequestParam(value = "id") Integer id, Model model) {
        Enterprise enterprise = iEnterpriseService.findById(id);
        model.addAttribute("enterprise", enterprise);
        return "form-advanced";
    }
}
