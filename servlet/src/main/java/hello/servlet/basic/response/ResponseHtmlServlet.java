package hello.servlet.basic.response;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //getWriter()를 쓰기 전, ContentType과 CharecterEncoding을 정해줘야 한다.
        res.setContentType("text/html; charset=utf-8");
        res.setCharacterEncoding("utf-8");
        PrintWriter writer = res.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div> 안녕! </div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
