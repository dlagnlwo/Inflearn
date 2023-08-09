package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name ="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //setContentType에 charset=utf-8 넣을 필요가 없다.
        res.setContentType("application/json;");
        res.setCharacterEncoding("utf-8");

        //이거를 JSON방식으로 바꿔야 함.
        HelloData helloData = new HelloData();
        helloData.setUsername("홍길동");
        helloData.setAge(30);

        //{"username":"홍길동", "age": 30}
        //objectMapper는 JSON타입으로 직렬화 해주는 객체
        String result = objectMapper.writeValueAsString(helloData);

        res.getWriter().write(result);

    }
}
