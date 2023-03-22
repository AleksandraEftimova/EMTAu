package webp.testau.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webp.testau.model.User;
import webp.testau.model.exceptions.InvalidUserCredentialsException;
import webp.testau.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

//@RestController vrakja podatoci
//@Controller pravi api i vrakja view
//oti koristime templates so html, ke koristime @Controller
@Controller
@RequestMapping("/login")
public class LoginController {

    //za logiranje
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //za da bide get baranje treba da go mapirame
    @GetMapping
    public String getLoginPage(Model model){
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    //za postiranje
    @PostMapping
    public String login(HttpServletRequest request, Model model){
        User user = null;
        try {
            //login method
            user = this.authenticationService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            //po uspesno logiranje vrati na pocetna strana
            return "redirect:/home";
        }
        //ako ima greska da vrati exception
        catch (InvalidUserCredentialsException exception) {
            //kako atributi gi davame tie hasError i error od login stranata
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());

            //i da go vratime na login
            return "login";
        }
    }
}
