package website.ilib.Eureka.Team;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest{
    @Email
    @NotBlank
    private String email;

    @Size(min = 2, max = 20)
    @NotBlank
    private String teamName;

}
