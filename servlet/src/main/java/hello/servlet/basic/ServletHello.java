package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "servletHello", urlPatterns = "/hello")
public class ServletHello extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //단축키 : sout + ctrl + space바
        System.out.println("HelloServlet");
        //단축키 : soutv
        System.out.println("req = " + req);
        System.out.println("res = " + res);
        String userName = req.getParameter("username");
        System.out.println("username : " + userName);

        res.setContentType("text/html; charset=utf-8");
        res.setCharacterEncoding("utf-8");
        res.getWriter().write("hello " + userName);
    }
}
