package website.ilib.Eureka.Score.Service;

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
import website.ilib.Eureka.Score.ScoreResponse;
import website.ilib.Eureka.Score.Model.ScoreModel;
import website.ilib.Eureka.Score.Repository.ScoreRepository;
import website.ilib.Eureka.Team.Model.TeamModel;
import website.ilib.Eureka.Team.Repository.TeamRepo;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final QuestionRepo questionRepo;

    private final ScoreRepository scoreRepository;

    private final TeamRepo teamRepo;

    public ScoreResponse addScore(@RequestBody @Valid ScoreRequest request) {
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
                    ScoreResponse response = ScoreResponse.builder()
                                                .msg("Already answered")
                                                .build();
                    return response;
                }
    
                ScoreModel score = ScoreModel.builder()
                        .score(question.getMarks())
                        .team(team)
                        .question(question)
                        .build();
    
                scoreRepository.save(score);

                Integer updatedScore = team.getTotalMarks() + question.getMarks();

                Integer level  = team.getLevel()+1;

                team.setLevel(level);
                team.setTotalMarks(updatedScore);

                teamRepo.save(team);
    
                ScoreResponse response = ScoreResponse.builder()
                    .msg("Correct Answer")
                    .build();
                return response;
            } else {
                ScoreResponse response = ScoreResponse.builder()
                    .msg("Wrong Answer")
                    .build();
                return response;
            }
        }catch (Exception e) {
            throw e;
        }
    }

    // public List<TeamResultResponse> getTotalScoresGroupedByTeamAndQuestion() {
    //     try {
    //         return scoreRepository.getTotalScoresGroupedByTeamAndQuestion();
    //     } catch (Exception e) {
    //         // Handle exceptions here (e.g., log the error, throw a custom exception, etc.)
    //         throw new RuntimeException("Error while fetching total scores grouped by team and question", e);
    //     }
    // }   
}