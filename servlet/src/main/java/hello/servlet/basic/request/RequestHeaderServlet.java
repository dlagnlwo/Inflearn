package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        printStartLine(req);
        System.out.println();
        printHeaders(req);
        pringHeaderUtil(req);
    }

    private void printStartLine(HttpServletRequest req){
        //현재 메소드 = GET
        System.out.println("req.getMethod() : " + req.getMethod());
        //프로토콜 정보 = HTTP/1.1
        System.out.println("req.getProtocol() : " + req.getProtocol());
        //http
        System.out.println("req.getScheme() : " + req.getScheme());
        // 현재 url = http://localhost:8080/request-header
        System.out.println("req.getRequestURL() : " + req.getRequestURL());
        // /request-header
        System.out.println("req.getRequestURI() : " + req.getRequestURI());
        // username=hi
        System.out.println("req.getQueryString() : " + req.getQueryString());
        //https 사용유무 = false 또는 true
        System.out.println("req.isSecure() : " + req.isSecure());
    }

    //header 모든 정보
    private void printHeaders(HttpServletRequest req){

//        Enumeration<String> headerNames = req.getHeaderNames();
//        while(headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            System.out.println(headerName + ": " + headerName);
//        }

        req.getHeaderNames().asIterator()
                .forEachRemaining(headerName ->
                        System.out.println(headerName + ": " + headerName));
        //req.getHeader()
    }

    // header 편의 조회
    private void pringHeaderUtil(HttpServletRequest req){
        System.out.println("--- header 편의 조회 start ---");
        // Host 헤더 = localhost
        System.out.println("req.getServerName() : " + req.getServerName());
        // Host 헤더 = 8080
        System.out.println("req.getServerPort() : " + req.getServerPort());
        System.out.println();

        System.out.println("[Accept - Language 편의조회]");
        req.getLocales().asIterator() //asIterator() : 반복자로 변환하는 역할
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("req.getLocale() = " + req.getLocales());
        System.out.println();
        System.out.println("--- header 편의 조회 end ---");
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if(req.getCookies() != null) {
            for(Cookie cookie : req.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }

        System.out.println();

        System.out.println("[Content 편의 조회]");
        System.out.println("req.getContentType() = " +
                req.getContentType());
        System.out.println("req.getContentLength() = " +
                req.getContentLength());
        System.out.println("req.getCharacterEncoding() = " +
                req.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();

        System.out.println("[Remote 정보]");
        System.out.println("req.getRemoteHost() = " + req.getRemoteHost());
        System.out.println("req.getRemoteAddr() = " + req.getRemoteAddr());
        System.out.println("req.getRemotePort() = " + req.getRemotePort());
        System.out.println();

        // 자기 서버 정보
        System.out.println("[Local 정보]");
        System.out.println("req.getLocalName() = " + req.getLocalName());
        System.out.println("req.getLocalAddr() = " + req.getLocalAddr());
        System.out.println("req.getLocalPort() = " + req.getLocalPort());
        System.out.println();


    }

}
