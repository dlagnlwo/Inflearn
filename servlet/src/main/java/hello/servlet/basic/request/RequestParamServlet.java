package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

/*
*  1. 파라미터 전송 기능
*  http://localhost:8080/request-param?username=hello&age=20
* */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("전체 파라미터 조회 - start");
        req.getParameterNames()
                .asIterator()
                        .forEachRemaining(paramName ->
                                System.out.println
                                        (paramName + "=" + req.getParameter(paramName)));

        System.out.println("전체 파라미터 조회 - end");
        System.out.println();

        System.out.println("단일 파라미터 조회 - start");
        String username = req.getParameter("username");
        String age = req.getParameter("age");

        /*
        http://localhost:8080/request-param?username=hello&age=20&username=hello2
        username이 위처럼 여러개인 경우는 우선순위가 앞임.
        * */
        System.out.println("username = " + username); //html의 name이 username
        System.out.println("age = " + age); //html의 name이 age
        System.out.println();

        System.out.println("이름이 같은 복수 파라미터 조회");
        //배열에 담을 때는 req.getParameterValues();
        String[] usernames = req.getParameterValues("username");
        for(String name : usernames) {
            System.out.println("username : " + name);
        }
        System.out.println();
        res.getWriter().write("ok");
    }
}
