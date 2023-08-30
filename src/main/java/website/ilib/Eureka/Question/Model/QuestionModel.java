package website.ilib.Eureka.Question.Model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Question")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel{
    @Id
    private String QId;

    @NotBlank
    private String title;

    @NotBlank
    private String question;

    @Nullable
    private String imgSrc;
    
    @NotBlank
    @JsonIgnore
    private String answer;
    
    @NotNull
    private int timeToHint;
    
    @NotNull
    private Integer marks;

    @JsonIgnore
    private List<String> hints;
}