package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") // /front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() { // controllerMap 'URL 매핑 정보에서 컨트롤러 조회' 패턴대로 실행된다
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); // 회원 가입, A가 요청이 오면, B가 실행된다
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1()); //회원 가입후
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1()); // 회원 목록
    }
        @Override // 다형성 쓴 부분 잘 이해하기
        protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.println("FrontControllerServletV1.service");

            ///front-controller/v1/members
            String requestURI = request.getRequestURI();

            ControllerV1 controller = controllerMap.get(requestURI);
            if (controller == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            controller.process(request, response); //조회가 됐다면
        }
    }
