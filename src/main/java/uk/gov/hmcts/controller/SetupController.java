package uk.gov.hmcts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/setup")
public class SetupController {

    /** Logger. */
    private static Logger log = LoggerFactory.getLogger(SetupController.class);

    @GetMapping("/DisplaySelectorServlet")
    public ModelAndView displaySelectorServlet(ModelMap model) {
        log.debug("Display selector servlet redirect");
        return new ModelAndView("redirect:/DisplaySelectorServlet", model);
    }

}
