package scaffold.framework.demo.config.springAuth.rules;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RulesConf {
    public void loginPresent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // if (request.getSession().getAttribute("usr") == null) {
        // System.out.println("not passed");
        // response.sendRedirect(request.getContextPath() + "/students/list");
        // } else {
        // System.out.println("passed");
        // }
        System.out.println("ok");
    }
}
