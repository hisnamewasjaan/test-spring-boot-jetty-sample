package hnwj.jetty.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@Controller
public class ElmController {


    @RequestMapping("elm")
    public String elm() {
        return "elm";
    }

    @RequestMapping("elm/websocket")
    public String elmWebsocket() {
        return "elm-websocket";
    }

}
