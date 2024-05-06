package scaffold.framework.demo.config;

import java.io.IOException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Autowired
    private ApplicationContext context;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest theRequest = (HttpServletRequest) request;
        
        // Récupérer le RequestMappingHandlerMapping
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

        // Obtenir le HandlerMethod qui correspond à l'URI
        HandlerMethod handlerMethod = null;
        try {
            handlerMethod = (HandlerMethod) handlerMapping.getHandler(theRequest).getHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si on a trouvé un HandlerMethod, obtenir les détails de la classe et de la
        // méthode
        if (handlerMethod != null) {
            Method controllerMethod = handlerMethod.getMethod();
            // verifier si l'annotation @Auth est presente sur la methode
            if (controllerMethod.isAnnotationPresent(Auth.class)) {
                Auth authAnnotation = controllerMethod.getAnnotation(Auth.class);
                String rule = authAnnotation.rule();
                Class<?> classSource = authAnnotation.classSource();
                try {
                    Object obj = classSource.getConstructor().newInstance();
                    classSource.getMethod(rule).invoke(obj);
                    // si tsisy olana sinon tsy ato mi regle azy fa a partir anle rule 
                    chain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            // Continuer avec le reste de la chaîne de filtres
            chain.doFilter(request, response);
        }

    }

}
