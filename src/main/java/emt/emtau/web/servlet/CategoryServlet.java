package webp.testau.web.servlet;

//kako da se obrabotat GET i POST baranja

import webp.testau.model.Category;
import webp.testau.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="category-servlet", urlPatterns = "/servlet/category")
public class CategoryServlet  extends HttpServlet {

    //nova zavisnost od CategoryService
    private final CategoryService categoryService;
    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //zameneta so Category klasa vo model
    //posebna klasa kade go stavame zbirot od entiteti
//    class Category {
//        private String name; //name of category
//        private String description;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public Category (String name) {
//            this.name = name;
//        }
//
//        public Category(String name, String description) {
//            this.name = name;
//            this.description = description;
//        }
//    }

    //ova ke go cuvame vo temporary holder
    //treba da gi zacuvame site kategorii vo lista
    private List<Category> categoryList = null; //prazni se na pocetok

    //inicijalizacija na listata, odma stom ke se startuva Spring odma
    //odma koga ke se kreira category i servletot

    //nema potreba od ova veke
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        this.categoryList = new ArrayList<>();
//        this.categoryList.add(new Category("Software", "Software Category"));
//        this.categoryList.add(new Category("Books", "Books Category"));
//    }

    //biznis logika
    //ne ni treba poso metodot e implementiran
//    public void addCategory(String name, String description){
//        if (name != null && !name.isEmpty()) {
//            //dodavame ili kreirame kategorija
//            this.categoryList.add(new Category(name, description));
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //da mu prikazeme adresata na korisnikot
        String ipAddress = req.getRemoteAddr();

        //da mu prikazeme na klientot dali e od browser ili baranjeto
        // bilo podneseno od neso dr
        String clientAgent = req.getHeader("User-Agent");

        //        super.doGet(req, resp);
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>User Info</h3>");
        out.format("IP Address: %s <br/>", ipAddress);
        out.format("Client Agent: %s", clientAgent);
        out.println("<h3>Category List</h3>");
        out.println("<ul>");
        //preku service gi listame
        categoryService.listCategories().forEach(r->
                out.format("<li>%s (%s)</li>", r.getName(), r.getDescription()));
        out.println("</ul>");

        //forma
        out.println("<h3>Add Category Form</h3>");
        //za posigurno prakjanje podatoci koristime post\n" +
        out.println("<form method='POST' action='/servlet/category'>");
        out.println("<label for='name'>Name:</label>");
        out.println("<input id='name' type='text' name='name'/>");
        out.println("<label for='desc'>Description:</label>");
        out.println("<input id='desc' type='text' name='desc'/>");
        out.println("<input type='submit' value='Submit'/>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        //postirame
        String categoryName = req.getParameter("name");
        //ako ne go prerabotime tuka togas ke pokaze (null) kako description
        String categoryDesc = req.getParameter("desc");
        categoryService.create(categoryName, categoryDesc);
//        addCategory(categoryName, categoryDesc);

        //go redirektirame korisnikot na pocetnata strana kade se site
        resp.sendRedirect("/servlet/category");
    }
}
