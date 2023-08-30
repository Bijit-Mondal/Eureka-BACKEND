package website.ilib.Eureka.Question;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String question;

    @NotNull
    private Integer marks;

    @Nullable
    private String imgSrc;

    @NotBlank
    private String answer;

    @NotNull
    private int timeToHint;

    private List<String> hints;

    public boolean isHintsValid() {
        return hints != null && hints.size() >= 2;
    }
}
