package hnwj.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 */
@Controller
public class StompController {

    @GetMapping("/stomp")
    public String sockjs(Model model) {
        return "stomp";
    }

}
