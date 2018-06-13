package ie.markoconnor.controller;

import ie.markoconnor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("name")
public class LoginController {

    @Autowired
    LoginService service;

    //@RequestMapping(value="/login", method= RequestMethod.GET)
    @GetMapping("/login")
    public String loginMessage(ModelMap model){
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String ShowWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
        boolean isValidUser = service.validateUser(name, password);
        if (!isValidUser){
            model.put("errorMessage", "Invalid credentials");
            return "login";
        }
        model.put("name", name);
        model.put("password", password);
        return "welcome";
    }
}
