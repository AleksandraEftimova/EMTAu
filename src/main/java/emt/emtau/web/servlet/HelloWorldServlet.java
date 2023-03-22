package webp.testau.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="hello", urlPatterns = "/hello")
public class HelloWorldServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //zemame podatoci od query vo requestot,
        //ocekuvam name+surname kako parametri
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (name==null || surname==null)
            name = surname = "Unknown";

        //vrakjame response (html code) do browser
        PrintWriter writer = resp.getWriter();
        writer.format("<html><head></head><body><h1>Helloooo</h1><h2>Welcome %s %s</h2</body</html>", name, surname);
        writer.flush();
    }
}
