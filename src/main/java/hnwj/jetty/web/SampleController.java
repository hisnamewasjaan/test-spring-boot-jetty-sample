package hnwj.jetty.web;

import hnwj.jetty.service.HelloWorldService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple controller with a couple of mappings to '/' and '/other'
 */
@Controller
public class SampleController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleController.class);

    @Value("${app.description}")
    private String description;

    @Autowired
    private HelloWorldService helloWorldService;


    @GetMapping("/sample/hello")
    public String helloWorld(Model model, HttpServletRequest request, Authentication authentication, Principal principal) {
        LOG.info("helloWorld, Model: '{}'", model);
        LOG.info("Request: '{}'", request);
        LOG.info("Auth: '{}'", authentication);
        LOG.info("Principal: '{}'", principal);

        model.addAttribute("helloMessage", this.helloWorldService.getHelloMessage());
        model.addAttribute("modelStr", model.toString());

        Cookie[] cookies1 = request.getCookies();
        if (cookies1 != null) {
            List<String> cookies = Arrays.stream(cookies1)
                    .map(cookie -> String.format("%s = %s", cookie.getName(), cookie.getValue()))
                    .collect(Collectors.toList());
            model.addAttribute("cookies", cookies);
        }
        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("remoteAdr", request.getRemoteAddr());
        model.addAttribute("remoteHost", request.getRemoteHost());
        model.addAttribute("remotePort", request.getRemotePort());
        model.addAttribute("remoteUser", request.getRemoteUser());

        model.addAttribute("authentication", authentication);
        if (authentication != null) {
            model.addAttribute("authname", authentication.getName());
            model.addAttribute("authAuthorities", authentication.getAuthorities());
            model.addAttribute("authCreds", authentication.getCredentials());
            model.addAttribute("authDetails", authentication.getDetails());
        }
        if (principal != null) {
            model.addAttribute("principal", principal.toString());
            model.addAttribute("principal name", principal.getName());
        }
        return "sampleHello";
    }

    @GetMapping("/other/hello")
    @ResponseBody
    public String helloOther() {
        LOG.debug("helloOther - debug");
        LOG.info("helloOther - info");
        LOG.warn("helloOther - warn");
        LOG.error("helloOther - error");
        return String.format("<html><head><title>%s</title></head><body>%s</body></html>",
                description,
                this.helloWorldService.getOtherHelloMessage());
    }

    @RequestMapping(value = {"/", "/index"})
    public String s(@RequestParam(name = "name", defaultValue = "n/a") String name) {
        return "forward:/sample";
    }

    @RequestMapping("/sample")
    public String sample(@RequestParam(name = "name", defaultValue = "n/a") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("description", description);
        return "index";
    }
}
