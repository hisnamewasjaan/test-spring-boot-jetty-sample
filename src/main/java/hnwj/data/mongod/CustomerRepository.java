package hnwj.data.mongod;

import hnwj.jetty.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

//    Customer findByFirstName(String firstName);
//    List<Customer> findByLastName(String firstName);
}
