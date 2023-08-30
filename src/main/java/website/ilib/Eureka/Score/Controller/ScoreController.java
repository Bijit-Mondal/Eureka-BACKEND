package website.ilib.Eureka.Score.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Score.ScoreRequest;
import website.ilib.Eureka.Score.ScoreResponse;
import website.ilib.Eureka.Score.Service.ScoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/add")
    public ResponseEntity<ScoreResponse> addScore(@RequestBody @Valid ScoreRequest request) {
        try {
            ScoreResponse response = scoreService.addScore(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ScoreResponse response = ScoreResponse.builder().msg(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // @GetMapping("/getAll")
    // public List<TeamResultResponse> getTotalScoresGroupedByTeamAndQuestion() {
    //     System.out.println("Hello");
    //     try {
    //         return scoreService.getTotalScoresGroupedByTeamAndQuestion();
    //     } catch (Exception e) {
    //         // Handle exceptions here (e.g., log the error, throw a custom exception, etc.)
    //         throw new RuntimeException("Error while fetching total scores grouped by team and question", e);
    //     }
    // }
}
