package edu.poly.shop.controller.site.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("site/page")
public class ThongBao {
    
    @RequestMapping("thongBao")
    public String home() {
        return "site/page/thongBao";
    }
    
}
