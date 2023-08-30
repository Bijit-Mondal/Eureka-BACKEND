package website.ilib.Eureka.Score;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Team.Model.TeamModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResultResponse {
    private TeamModel team;

    private int totalScore;
}