package hello.servlet.web.frontcontroller.v4.controller;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v4/members/new-form
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*") // /front-controller/v3 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() { // controllerMap 'URL 매핑 정보에서 컨트롤러 조회' 패턴대로 실행된다
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4()); // A가 요청이 오면, B가 실행된다    }
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }
        @Override // 다형성 쓴 부분 잘 이해하기
        protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            ///front-controller/v4/members
            String requestURI = request.getRequestURI();

            ControllerV4 controller = controllerMap.get(requestURI);
            if (controller == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Map<String, String> paramMap = createParamMap(request);
            Map<String, Object> model = new HashMap<>(); //추가

            String viewName = controller.process(paramMap, model);//조회가 됐다면


            MyView view = viewResolver(viewName);

            view.render(model, request, response);
        }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter((paramName))));
        return paramMap;
    }
}
