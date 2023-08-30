package website.ilib.Eureka.Team.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import website.ilib.Eureka.Team.Model.TeamModel;

public interface TeamRepo extends MongoRepository<TeamModel, String> {    
    Optional<TeamModel> findByEmail(String email);

    List<TeamModel> findTop10ByOrderByTotalMarksDescHintUsedAscUpdatedAtAsc();

    boolean existsByEmail(String email);
}
