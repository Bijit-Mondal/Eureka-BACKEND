package website.ilib.Eureka.Score.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import website.ilib.Eureka.Score.Model.ScoreModel;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Team.Model.TeamModel;


public interface ScoreRepository extends MongoRepository<ScoreModel,String> {
    Optional<ScoreModel> findByQuestionQIdAndTeamID(QuestionModel questionId, TeamModel teamId);
}
