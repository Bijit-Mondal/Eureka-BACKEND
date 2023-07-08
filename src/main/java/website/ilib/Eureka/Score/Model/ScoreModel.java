package website.ilib.Eureka.Score.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Team.Model.TeamModel;

@Document(collection = "Score")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreModel {
    @Id
    private String scoreId;

    @DBRef
    private TeamModel team;

    @DBRef
    private QuestionModel question;

    @NotNull
    private int score;
}