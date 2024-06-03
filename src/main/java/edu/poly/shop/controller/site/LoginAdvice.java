package edu.poly.shop.controller.site;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class LoginAdvice {

    @ModelAttribute
    public void addLoggedInUserToModel(Model model, HttpServletRequest request) {
        String loggedInUser = (String) SessionUtils.getAttribute(request, "loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
    }
}
