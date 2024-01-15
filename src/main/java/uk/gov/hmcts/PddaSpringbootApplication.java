package uk.gov.hmcts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import uk.gov.hmcts.config.WebAppInitializer;

@SpringBootApplication
@ServletComponentScan
@EntityScan
public class PddaSpringbootApplication extends SpringBootServletInitializer {
    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(PddaSpringbootApplication.class);

    public static void main(String[] args) {
        log.debug("Starting PDDA Springboot application...");
        SpringApplication
            .run(new Class[] {PddaSpringbootApplication.class, WebAppInitializer.class}, args);
    }

}
