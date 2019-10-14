package yahaha.gxshopping.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yahaha.gxshopping.util.VelocityUtils;

@RestController
public class StaticPageController {

    @PostMapping("/page")
    public void creatStaticPage(
            @RequestParam("templatePath") String templatePath,
            @RequestParam("targetPath") String targetPath,
            @RequestBody Object model){
        VelocityUtils.staticByTemplate(model, templatePath, targetPath);
    }
}
