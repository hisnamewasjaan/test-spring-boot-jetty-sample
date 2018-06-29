package hnwj.data.mongod;

import hnwj.jetty.domain.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 */
public interface JobRepository extends MongoRepository<Job, String> {




}
