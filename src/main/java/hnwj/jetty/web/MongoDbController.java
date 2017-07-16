package hnwj.jetty.web;

import hnwj.data.CustomerRepository;
import hnwj.jetty.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Controller
@EnableMongoRepositories(basePackages = {"hnwj.data"})
public class MongoDbController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("mongod")
    public String mongod(Model model) {

        customerRepository.deleteAll();
        customerRepository.save(new Customer("Alice", "Ape"));
        customerRepository.save(new Customer("Alice", "Babe"));
        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Smith"));


        List<Customer> customers = customerRepository.findAll();

//        Customer alice = customerRepository.findByFirstName("Alice");
//        List<Customer> smithLastName = customerRepository.findByLastName("Smith");

        List<String> ids = customers.stream().map(customer -> customer.id).collect(Collectors.toList());

        model.addAttribute("customerIds", ids);

        return "mongod";

    }


    @RequestMapping("mongod/customer")
    public String mongodCustomer(String id, Model model) {
        Customer one = customerRepository.findOne(id);

        model.addAttribute("customer", one);


        List<Customer> customers = customerRepository.findAll();
        List<String> ids = customers.stream().map(customer -> customer.id).collect(Collectors.toList());
        model.addAttribute("customerIds", ids);

        return "mongod";

    }
}
