package hnwj.jetty.web;

import com.mongodb.WriteConcern;
import hnwj.data.mongod.JobProviderRepository;
import hnwj.data.mongod.JobRepository;
import hnwj.jetty.domain.Job;
import hnwj.jetty.domain.JobProvider;
import hnwj.jetty.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Controller
@EnableMongoRepositories(basePackages = {"hnwj.data.mongod"})
@Profile("mongod")
public class SmallJobsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmallJobsController.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobProviderRepository jobProviderRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping("jobs")
    public String jobs(Model model) {

//        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);

        JobProvider jobProvider1 = jobProviderRepository.insert(new JobProvider("Else Pelse"));
        JobProvider jobProvider2 = jobProviderRepository.insert(new JobProvider("Betty Høy"));
        Location location1 = new Location(0, 0, null);
        Location location2 = new Location(1212, 1122, null);

        ZoneOffset zoneOffset = ZoneOffset.of("-01:00");

        jobRepository.deleteAll();

        Job rengøring = job(jobProvider1, "Rengøring", "Ugentlig rengøring hos ældre dame");
        rengøring.setWhen(LocalDateTime.of(2018, Month.APRIL, 1, 12, 0).toInstant(zoneOffset));
        rengøring = jobRepository.insert(rengøring);

        Job tagrenderens = job(jobProvider2, "Tagrenderens", "Halvårlig rengøring af tagrender");
        tagrenderens.setWhen(LocalDateTime.of(2018, Month.APRIL, 1, 16, 30).toInstant(zoneOffset));
        tagrenderens = jobRepository.insert(tagrenderens);

        Job indkøb = job(jobProvider1, "Indkøb", "Ugentlig hjælp med indkøb");
        indkøb.setWhen(LocalDate.of(2017, Month.SEPTEMBER, 16).atStartOfDay().toInstant(zoneOffset));
        indkøb = jobRepository.insert(indkøb);

        Job græsslåning = job(jobProvider2, "Græsslåning", "");
//        græsslåning.setWhen(LocalDateTime.of());
        græsslåning = jobRepository.insert(græsslåning);



        LOG.info("Inserted '{}'", rengøring);
        LOG.info("Inserted '{}'", tagrenderens);
        LOG.info("Inserted '{}'", indkøb);
        LOG.info("Inserted '{}'", græsslåning);

        List<Job> jobs = jobRepository.findAll();

        List<String> ids = jobs.stream().map(job -> job.name).collect(Collectors.toList());

        model.addAttribute("jobs", ids);

        return "jobs";

    }

    private Job job(JobProvider jobProvider, String name, String description) {
        return new Job(name, description, jobProvider);
    }


//    @RequestMapping("mongod/customer")
//    public String mongodCustomer(String id, Model model) {
//        Customer one = customerRepository.findOne(id);
//
//        model.addAttribute("customer", one);
//
//
//        List<Customer> customers = customerRepository.findAll();
//        List<String> ids = customers.stream().map(customer -> customer.id).collect(Collectors.toList());
//        model.addAttribute("customerIds", ids);
//
//        return "mongod";
//
//    }
}
