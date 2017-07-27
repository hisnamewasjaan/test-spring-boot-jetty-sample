package hnwj.jetty.web;

import hnwj.data.gemfire.PersonRepository;
import hnwj.jetty.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@Controller
@Profile("gemfire")
public class GemfireRestDataController {

    private static final Logger LOG = LoggerFactory.getLogger(GemfireRestDataController.class);

    @Autowired
    PersonRepository repository;

    @Autowired
    private RepositoryEntityLinks entityLinks;


    @RequestMapping("/gemfire/rest")
    public String redis(Model model) {
        LOG.info("/gemfire/rest");

        Links links = entityLinks.linksToSearchResources(Person.class);

        model.addAttribute("links", links);

        return "gemfire";
    }


}
