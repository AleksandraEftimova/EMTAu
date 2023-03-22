package webp.testau.web.servlet;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import webp.testau.model.User;
import webp.testau.model.exceptions.InvalidUserCredentialsException;
import webp.testau.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/servlet/login")
public class LoginServlet extends HttpServlet {

    //thymeleaf
    private final SpringTemplateEngine springTemplateEngine;
    private final AuthenticationService authenticationService;

    public LoginServlet(SpringTemplateEngine springTemplateEngine, AuthenticationService authenticationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.authenticationService = authenticationService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //forma za vnesuvanje username i lozinka
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //citame parametri od requestot
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //avtentikacija na korisnikot, kreirame nova instanca
        User user = null;
        //handling exceptions by returning an error message
        try {
            user = authenticationService.login(username, password);
        } catch (InvalidUserCredentialsException exception){
            //ja vrakjame login stranata i mu kazuvame deka ima greska
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("hasError", true);
            context.setVariable("error", exception.getMessage());
            //procesirame spring template t.e. prikazuvame login stranata so porakata (uslovna)
            springTemplateEngine.process("login.html", context, resp.getWriter());
        }

        //dodavanje podatoci vo ramki na odredena sesija
        //koristime request so get session i setirame kluc i objekt
        //vo sesija mozeme da postavime bilo koja instanca od bilo koja klasa
        req.getSession().setAttribute("user", user);
        //kreirana e nova sesija za odreden korisnik

        //po najavuvanje pravime redirect
        resp.sendRedirect("/servlet/thymeleaf/category");


    }
}
