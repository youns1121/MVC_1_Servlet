package hello.servlet.web.frontcontroller.v2.controller;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v2/members/new-form
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*") // /front-controller/v2 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() { // controllerMap 'URL 매핑 정보에서 컨트롤러 조회' 패턴대로 실행된다
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2()); // A가 요청이 오면, B가 실행된다    }
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }
        @Override // 다형성 쓴 부분 잘 이해하기
        protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            ///front-controller/v2/members
            String requestURI = request.getRequestURI();

            ControllerV2 controller = controllerMap.get(requestURI);
            if (controller == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            //new MyView("/WEB-INT/views/new-form.jsp"); 결과값
            MyView view = controller.process(request, response);//조회가 됬다면
            view.render(request, response);
        }
    }
