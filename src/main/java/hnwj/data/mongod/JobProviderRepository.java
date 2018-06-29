package hnwj.data.mongod;

import hnwj.jetty.domain.Job;
import hnwj.jetty.domain.JobProvider;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 */
public interface JobProviderRepository extends MongoRepository<JobProvider, String> {




}
