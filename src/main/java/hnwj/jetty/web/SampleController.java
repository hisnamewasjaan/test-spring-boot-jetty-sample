/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hnwj.jetty.web;

import hnwj.jetty.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${app.description}")
	private String description;

	@Autowired
	private HelloWorldService helloWorldService;

	@GetMapping("/")
	@ResponseBody
	public String helloWorld() {
		return this.helloWorldService.getHelloMessage();
	}

	@GetMapping("/other")
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
}
