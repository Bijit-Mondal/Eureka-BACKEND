package website.ilib.Eureka.Question.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Question.QuestionRequest;
import website.ilib.Eureka.Question.QuestionResponse;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Question.Repository.QuestionRepo;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;

    public QuestionResponse addQuestion(QuestionRequest request){
        try{
            QuestionModel question = QuestionModel.builder()
                                .title(request.getTitle())
                                .question(request.getQuestion())
                                .timeToHint(request.getTimeToHint())
                                .hints(request.getHints())
                                .answer(request.getAnswer())
                                .imgSrc(request.getImgSrc())
                                .build();
            questionRepo.save(question);
            return QuestionResponse.builder()
                    .message("Question Added Successfully")
                    .questionTitle(request.getTitle())
                    .build();
        }catch(Exception e){
            throw e;
        }
    }

    public QuestionResponse updateQuestion(String questionId,QuestionRequest request) {
        try {
            QuestionModel existingQuestion = questionRepo.findById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            existingQuestion.setTitle(request.getTitle());
            existingQuestion.setQuestion(request.getQuestion());
            existingQuestion.setTimeToHint(request.getTimeToHint());
            existingQuestion.setHints(request.getHints());
            existingQuestion.setAnswer(request.getAnswer());
            existingQuestion.setImgSrc(request.getImgSrc());

            questionRepo.save(existingQuestion);

            return QuestionResponse.builder()
                    .message("Question Updated Successfully")
                    .questionTitle(existingQuestion.getTitle())
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }



    public QuestionResponse deleteQuestion(String questionId) {
        try {
            QuestionModel question = questionRepo.findById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            questionRepo.delete(question);

            return QuestionResponse.builder()
                    .message("Question Deleted Successfully")
                    .questionTitle(question.getTitle())
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }

    public Map<String, Object> getQuestionSummary() {
        int numberOfQuestions = questionRepo.findAll().size();
        List<String> questionIds = new ArrayList<String>();
    
        for (QuestionModel question : questionRepo.findAll()) {
            questionIds.add(question.getQId());
        }
    
        Map<String, Object> questionSummary = new HashMap<>();
        questionSummary.put("noquestion", numberOfQuestions);
        questionSummary.put("questionId", questionIds);
    
        return questionSummary;
    }

    public QuestionModel getQuestionById(String questionId) {
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }
      

}
