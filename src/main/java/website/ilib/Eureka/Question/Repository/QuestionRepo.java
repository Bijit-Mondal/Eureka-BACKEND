package website.ilib.Eureka.Question.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import website.ilib.Eureka.Question.Model.QuestionModel;

public interface QuestionRepo extends MongoRepository<QuestionModel,String> {
    Optional<QuestionModel> findById(String questionId);
}
