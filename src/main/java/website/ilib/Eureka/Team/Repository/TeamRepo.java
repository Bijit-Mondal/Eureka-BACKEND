package website.ilib.Eureka.Team.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import website.ilib.Eureka.Team.Model.TeamModel;

@Repository
public interface TeamRepo extends MongoRepository<TeamModel, String> {    
    Optional<TeamModel> findByEmail(String email);

    boolean existsByEmail(String email);
}
