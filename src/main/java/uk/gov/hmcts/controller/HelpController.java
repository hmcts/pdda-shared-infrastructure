package uk.gov.hmcts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelpController {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(HelpController.class);

    @GetMapping("/help")
    public ModelAndView help() {
        log.debug("help redirect");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("help/index.jsp");
        return modelAndView;
    }

    @GetMapping("/config")
    public ModelAndView config() {
        log.debug("help config redirect");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("help/config.jsp");
        return modelAndView;
    }

    @GetMapping("/status")
    public ModelAndView status() {
        log.debug("help status redirect");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("help/status.jsp");
        return modelAndView;
    }

}
