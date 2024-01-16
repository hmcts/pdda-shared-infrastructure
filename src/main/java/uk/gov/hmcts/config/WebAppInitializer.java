package uk.gov.hmcts.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import uk.gov.hmcts.pdda.web.publicdisplay.initialization.servlet.InitServlet;

public class WebAppInitializer implements ServletContextInitializer {

    public static final String SERVLET_NAME = "InitServlet";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try (AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext()) {
            ctx.setServletContext(servletContext);
    
            ServletRegistration.Dynamic servlet =
                servletContext.addServlet(SERVLET_NAME, new InitServlet());
            servlet.setLoadOnStartup(1);
        }
    }

}
