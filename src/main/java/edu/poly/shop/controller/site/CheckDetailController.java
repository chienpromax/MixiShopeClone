package edu.poly.shop.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("site/carts/")
public class CheckDetailController {
    
    @RequestMapping("checkdetail")
    public String home() {
        return "site/carts/checkdetail";
    }
    
}
