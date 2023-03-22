package webp.testau.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webp.testau.model.enumerations.Role;
import webp.testau.model.exceptions.InvalidUserCredentialsException;
import webp.testau.model.exceptions.PasswordDoNotMatchException;
import webp.testau.service.AuthenticationService;
import webp.testau.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    //implementirame od service-ot
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public RegisterController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    //ni treba post mapiranje, stavame request param oti gi zemame od baranjeto
    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam Role role) {

        //handling errors and giving exceptions
        try {
            //mesto od AuthenticationService zemame od UserService
            this.userService.register(username, password, repeatedPassword, name, surname, role);
            //vrakjame redirect kon login
            return "redirect:/login";
        }
        catch (PasswordDoNotMatchException | InvalidUserCredentialsException exception){
            //go vrakjame na register i + ja prikazuvame porakata od exception-ot
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    //ni treba i getmapping za vrakjanje na taa strana
    //(required=false) znaci deka moze da se povika i bez greska, ne e required parameter
    @GetMapping
    public String getRegisterPage(@RequestParam (required = false) String error, Model model){
        if (error !=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("bodyContent", "register");
        return "master-template";
    }
}
