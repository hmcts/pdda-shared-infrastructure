package uk.gov.hmcts.pdda.web.test;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdda.business.entities.xhbcppstaginginbound.XhbCppStagingInboundDao;
import uk.gov.hmcts.pdda.business.services.cppstaginginboundejb3.CppStagingInboundControllerBean;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class BeanTesterServlet.
 */
@WebServlet("/n")
public class BeanTesterServlet extends HttpServlet {

    private static final long serialVersionUID = 4723679809450870621L;
    private static final Logger LOG = LoggerFactory.getLogger(BeanTesterServlet.class);

    @jakarta.ejb.EJB
    @Inject
    private CppStagingInboundControllerBean sessionBean2;

    /**
     * doGet.
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        try (java.io.Writer writer = response.getWriter();) {
            // Tests 1-3
            writer.append("<html>");
            writer.append("<body>");
            writer.append("<h2>Hello Servlet!!</h2>");
            LOG.debug("About to call cppstagingcontrollerbean");
            List<XhbCppStagingInboundDao> unprocessedDocs =
                sessionBean2.getLatestUnprocessedDocument();
            writer.append("no of docs:" + unprocessedDocs.size());
            if (!unprocessedDocs.isEmpty()) {
                writer.append("going to set it to processed");
                sessionBean2.updateStatusProcessingSuccess(unprocessedDocs.get(0), "FredBloggs");
                writer.append("Doc updated");
            }
        } catch (Exception e) {
            LOG.error("Error in doGet(): {}", e.getMessage());
        }
    }

}
