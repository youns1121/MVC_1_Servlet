package hello.servlet.web.frontcontroller.v3.controller;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v3/members/new-form
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*") // /front-controller/v3 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() { // controllerMap 'URL 매핑 정보에서 컨트롤러 조회' 패턴대로 실행된다
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3()); // A가 요청이 오면, B가 실행된다    }
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }
        @Override // 다형성 쓴 부분 잘 이해하기
        protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            ///front-controller/v3/members
            String requestURI = request.getRequestURI();

            ControllerV3 controller = controllerMap.get(requestURI);
            if (controller == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Map<String, String> paramMap = createParamMap(request);
            ModelView mv = controller.process(paramMap);//조회가 됬다면

            //new-form
            String viewName = mv.getViewName();// 논리이름 new-form
            MyView view = viewResolver(viewName);

            view.render(mv.getModel(), request, response);
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
