package hnwj.jetty.web;

import hnwj.jetty.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Simple controller with a couple of mappings to '/' and '/other'
 */
@Controller
public class SampleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${app.description}")
	private String description;

	@Autowired
	private HelloWorldService helloWorldService;

//	@GetMapping("/sample/hello")
//	@ResponseBody
//	public String helloWorld() {
//		return this.helloWorldService.getHelloMessage();
//	}

	@GetMapping("/sample/hello")
	public String helloWorld(Model model) {
		model.addAttribute("helloMessage", this.helloWorldService.getHelloMessage());
		return "sampleHello";
	}

	@GetMapping("/other/hello")
	@ResponseBody
	public String helloOther() {
		log.debug("helloOther - debug");
		log.info("helloOther - info");
		log.warn("helloOther - warn");
		log.error("helloOther - error");
		return String.format("<html><head><title>%s</title></head><body>%s</body></html>",
				description,
				this.helloWorldService.getOtherHelloMessage()) ;
	}

	@RequestMapping("/")
	public String s(@RequestParam(name = "name", defaultValue = "n/a") String name) {
		return "forward:/sample";
//		return String.format("forward:/sample?name=%s", name);
	}
	@RequestMapping("/sample")
	public String sample(@RequestParam(name = "name", defaultValue = "n/a") String name, Model model) {
		model.addAttribute("name", name);
		return "index";
	}
}
