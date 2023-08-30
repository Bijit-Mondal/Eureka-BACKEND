package website.ilib.Eureka.Question.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Question.HintResponse;
import website.ilib.Eureka.Question.QuestionRequest;
import website.ilib.Eureka.Question.QuestionResponse;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Question.Repository.QuestionRepo;
import website.ilib.Eureka.Team.Model.TeamModel;
import website.ilib.Eureka.Team.Repository.TeamRepo;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;

    private final TeamRepo teamRepo;

    public QuestionResponse addQuestion(QuestionRequest request){
        try{
            QuestionModel question = QuestionModel.builder()
                                .title(request.getTitle())
                                .question(request.getQuestion())
                                .timeToHint(request.getTimeToHint())
                                .hints(request.getHints())
                                .marks(request.getMarks())
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
            existingQuestion.setMarks(request.getMarks());
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

    public HintResponse getHintById(String questionId,Integer hintId) {
        QuestionModel question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("No question found"));

        List<String> hints = question.getHints();

        if (hintId < 0 || hintId >= hints.size()) {
            throw new IllegalArgumentException("Invalid hint number");
        }

        String hint = hints.get(hintId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        TeamModel team = (authentication != null && authentication.getPrincipal() instanceof TeamModel)
                    ? (TeamModel) authentication.getPrincipal()
                    : null;
        if(team == null){
            return null;
        }else{
            Integer hintUsed = team.getHintUsed()+1;
            team.setHintUsed(hintUsed);
            teamRepo.save(team);
        }

        return HintResponse.builder()
                .hint(hint)
                .build();
    }
}
