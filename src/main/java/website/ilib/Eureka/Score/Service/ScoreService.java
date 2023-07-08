package website.ilib.Eureka.Score.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Question.Repository.QuestionRepo;
import website.ilib.Eureka.Score.ScoreRequest;
import website.ilib.Eureka.Score.Model.ScoreModel;
import website.ilib.Eureka.Score.Repository.ScoreRepository;
import website.ilib.Eureka.Team.Model.TeamModel;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final QuestionRepo questionRepo;

    private final ScoreRepository scoreRepository;

    public Map<String, Object> addScore(@RequestBody @Valid ScoreRequest request) {
        String answer = request.getAnswer();
        try {
            QuestionModel question = questionRepo.findById(request.getQId()).orElse(null);
    
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            TeamModel team = (authentication != null && authentication.getPrincipal() instanceof TeamModel)
                    ? (TeamModel) authentication.getPrincipal()
                    : null;
    
            if (question != null && team != null && answer != null && answer.trim().equalsIgnoreCase(question.getAnswer().trim())) {
                Optional<ScoreModel> existingScore = scoreRepository.findByQuestionQIdAndTeamID(question, team);
    
                if (existingScore.isPresent()) {
                    throw new RuntimeException("Already answered that question");
                }
    
                ScoreModel score = ScoreModel.builder()
                        .score(50)
                        .team(team)
                        .question(question)
                        .build();
    
                scoreRepository.save(score);
    
                // Prepare the response with message and score
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Correct Answer");
                response.put("score", score.getScore());
                return response;
            } else {
                throw new RuntimeException("Invalid request or answer");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    
}