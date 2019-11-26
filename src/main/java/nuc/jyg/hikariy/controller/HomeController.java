package nuc.jyg.hikariy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ji YongGuang.
 * @date 16:36 2019-04-25.
 * @description
 */
@Controller
public class HomeController {

    @GetMapping(value = {"/", ""})
    public String home() {
        return "index";
    }
}
