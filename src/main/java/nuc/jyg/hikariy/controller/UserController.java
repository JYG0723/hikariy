package nuc.jyg.hikariy.controller;

import nuc.jyg.hikariy.common.Const;
import nuc.jyg.hikariy.model.User;
import nuc.jyg.hikariy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Ji YongGuang.
 * @date 12:50 2019-04-22.
 * @description 用户接口
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping(value = "/add")
    public String addUser(User user, Model model) {
        user.setPassword("123456");
        user.setRole(1);
        User addedUser = iUserService.save(user);
        return "index";
    }

    @PutMapping(value = "/{id}")
    public String modifyPassword(@PathVariable(value = "id") Integer id,
                                 @RequestParam(value = "oldPassword") String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword,
                                 Model model) {

        User user = iUserService.findByIdAndPassword(id, oldPassword);
        if (Objects.isNull(user)) {
            model.addAttribute("message", "密码错误");
        }

        user.setPassword(newPassword);
        User newUser = iUserService.updatePassword(user);
        if (newUser.getPassword().equals(newPassword)) {
            model.addAttribute("message", "修改成功");
        }
        return "index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password,
                        HttpSession httpSession, Model model) {

        User user = iUserService.findByEmailAndPassword(email, password);
        if (Objects.isNull(user)) {
            model.addAttribute("message", "邮箱或者密码错误");
            return "pages-login";
        }
        model.addAttribute("user", user);
        httpSession.setAttribute("user", user);
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "pages-login";
    }

    @PostMapping(value = "/forget_get_question")
    public String forgetGetQuestion(@RequestParam(value = "email") String email, Model model) {
        String question = iUserService.findQuestionByEmail(email);
        model.addAttribute("question", question);
        model.addAttribute("email", email);
        return "pages-password-question";
    }

    @PostMapping(value = "/forget_check_answer")
    public String forgetCheckAnswer(@RequestParam(value = "email") String email,
                                    @RequestParam(value = "answer") String answer,
                                    Model model) {
        Boolean result = iUserService.checkQuestionAnswer(email, answer);
        if (result) {
            // todo 如果正确跳转到修改密码页面，如果错误则提示他问题答案错误
            model.addAttribute("email", email);
            return "pages-reset-password";
        }
        model.addAttribute("message", "密保问题回答错误");
        return "pages-reset-password";
    }

    @PostMapping(value = "/forget_reset_password")
    public String forgetResetPassword(String email, String emailSession, String passwordNew,
                                      HttpSession httpSession) {
        if (Objects.isNull(emailSession)) {
            // 非登录状态修改密码
            iUserService.modifyPassword(email, passwordNew);
        } else {
            // 登录状态修改密码
            iUserService.modifyPassword(emailSession, passwordNew);
        }
        httpSession.removeAttribute("user");
        return "redirect:/";
    }


    @PostMapping(value = "/reset_password")
    public String resetPassword(HttpSession httpSession, String passwordNew) {
        // 登录状态修改密码
        User currentUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        iUserService.modifyPassword(currentUser.getEmail(), passwordNew);
        return "pages-login";
    }

}
