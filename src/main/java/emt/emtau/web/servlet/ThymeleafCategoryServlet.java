package webp.testau.web.servlet;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import webp.testau.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="thymeleaf-category-servlet", urlPatterns = "/servlet/thymeleaf/category")
public class ThymeleafCategoryServlet extends HttpServlet {

    //ke go koristime za gradenje na html template
    private final SpringTemplateEngine springTemplateEngine;
    //za komunikacija so biznis slojot
    private final CategoryService categoryService;

    public ThymeleafCategoryServlet (SpringTemplateEngine springTemplateEngine,
                                     CategoryService categoryService){
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //vo nego da gi stavime site promenlivi sto ke se koristat vo thymeleaf
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("ipaddress", req.getRemoteAddr());
        context.setVariable("clientAgent", req.getHeader("User-Agent"));
        context.setVariable("categories", this.categoryService.listCategories());
        resp.setContentType("application/xhtml+xml");

        this.springTemplateEngine.process("categories.html",
                context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String categoryDesc = req.getParameter("desc");

        categoryService.create(categoryName, categoryDesc);

        //go redirektirame korisnikot na pocetnata strana kade se site
        resp.sendRedirect("/servlet/thymeleaf/category");
    }
}
