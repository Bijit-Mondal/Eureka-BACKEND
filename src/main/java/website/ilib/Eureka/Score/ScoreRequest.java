package website.ilib.Eureka.Score;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequest {
    @NotBlank
    public String answer;

    @NotBlank
    public String QId;
}