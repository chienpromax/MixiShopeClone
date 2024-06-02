package edu.poly.shop.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("site")
public class DoanhMucController {
    
    @RequestMapping("doanhmuc")
    public String index() {
        return "site/doanhmuc";
    }
    
}
