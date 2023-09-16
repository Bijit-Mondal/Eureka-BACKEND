package website.ilib.Eureka.Hint.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import website.ilib.Eureka.Hint.Model.HintModel;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Team.Model.TeamModel;

public interface HintRepo extends MongoRepository<HintModel,String> {
    Optional<HintModel> findByQuestionQIdAndTeamID(QuestionModel questionId, TeamModel teamId);

}