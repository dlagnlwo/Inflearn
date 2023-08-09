package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // status-line
        res.setStatus(HttpServletResponse.SC_OK); // HTTP 응답코드 200

        //res.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 응답코드 400

        //response-headers
        res.setHeader("Content-Type", "text/html;charset=utf-8");
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("my-header", "hello"); //사용자 지정 헤더

        //Header의 편의 메소드
        content(res); // 한글 인코딩
        cookie(res); // 쿠키 저장
        redirect(res); //sendRedirect 강제로 보내기

        PrintWriter writer = res.getWriter();
        writer.print("ok");

    }

    //한글 인코딩
    private void content(HttpServletResponse res) {
        res.setContentType("text/html;");
        res.setCharacterEncoding("utf-8");
    }

    //쿠키 저장
    private void cookie(HttpServletResponse res){
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        res.addCookie(cookie);
    }

    // sendRedirect로 강제로 이동(보통 POST로 값을 받고 바로 넘겨줄때 사용)
    private void redirect(HttpServletResponse res) throws IOException {
        //res.setStatus(HttpServletResponse.SC_FOUND); // HTTP 응답코드 302
        //res.setHeader("Location", "/basic/hello-Form.html");
        res.sendRedirect("/basic/hello-Form.html");
    }
}
