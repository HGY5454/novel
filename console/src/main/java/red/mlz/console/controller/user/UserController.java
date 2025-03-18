package red.mlz.console.controller.user;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.console.annotations.VerifiedUser;
import red.mlz.console.domain.user.UserInfoVo;
import red.mlz.module.module.user.entity.User;
import red.mlz.module.module.user.service.UserService;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.Response;
import red.mlz.module.utils.SpringUtils;


import javax.servlet.http.HttpSession;


@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user/login/web")
    public Response loginWeb(@VerifiedUser User loginUser,
                             HttpSession httpSession,
                             @RequestParam(name = "phone") String phone,
                             @RequestParam(name = "password") String password) {
        if (!BaseUtils.isEmpty(loginUser)) {
            return new Response(4004);
        }

        boolean result;
        if (true) {
            result = userService.login(phone, password);
        } else {
            result = userService.login(phone, password);
        }
        if (!result) {
            return new Response(1010);
        }

        User user = userService.getUserByPhone(phone);
        userService.refreshUserLoginContext(user.getId(), BaseUtils.currentSeconds());

        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setNickName(user.getNickName());
        userInfo.setPhone(user.getPhone());

        // 写session
        httpSession.setAttribute(SpringUtils.getProperty("application.session.key"), JSON.toJSONString(user));

        return new Response(1001, userInfo);
    }

}
