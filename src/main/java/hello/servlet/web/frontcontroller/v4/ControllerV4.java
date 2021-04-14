package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV4 {

    public String process(Map<String, String> paramMap, Map<String, Object> model);
}
