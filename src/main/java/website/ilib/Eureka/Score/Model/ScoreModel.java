package website.ilib.Eureka.Score.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Score")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreModel {
    @Id
    private String ScoreId;

    @DBRef
    private String TeamId;

    @DBRef
    private String QuestionId;

    @NotNull
    private int Score;
}
