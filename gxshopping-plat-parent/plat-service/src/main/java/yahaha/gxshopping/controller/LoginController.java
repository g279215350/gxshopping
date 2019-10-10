package yahaha.gxshopping.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yahaha.gxshopping.domain.User;
import yahaha.gxshopping.util.AjaxResult;

@RestController
@Api(tags = "用于登陆的Controller")
public class LoginController {

    @PostMapping("/login")
    @ApiOperation(value = "登陆接口")
    public AjaxResult login(@RequestBody User user){
        if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())){
            return AjaxResult.me().setSuccess(true).setMassage("登陆成功！");
        }
        return AjaxResult.me().setSuccess(false).setMassage("登陆失败！");
    }
}
