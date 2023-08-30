package website.ilib.Eureka.Question.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Question.HintResponse;
import website.ilib.Eureka.Question.QuestionRequest;
import website.ilib.Eureka.Question.QuestionResponse;
import website.ilib.Eureka.Question.Model.QuestionModel;
import website.ilib.Eureka.Question.Service.QuestionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public ResponseEntity<?> getMethodName() {
        try {
            Map<String, Object> questionSummary = questionService.getQuestionSummary();
            return ResponseEntity.ok(questionSummary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable("id") String questionId) {
        try {
            QuestionModel question = questionService.getQuestionById(questionId);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            // Handle the case when the question is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/get/{id}/hint/{num}")
    public ResponseEntity<?> getHintById(@PathVariable("id") String questionId,@PathVariable("num") Integer hintId) {
        try {
            HintResponse hint = questionService.getHintById(questionId,hintId);
            return ResponseEntity.ok(hint);
        } catch (IllegalArgumentException e) {
            // Handle the case when the question is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid QuestionRequest request){
        try{
            QuestionResponse responseBody = questionService.addQuestion(request);
            return ResponseEntity.ok(responseBody);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable("questionId") String questionId,
                                                           @RequestBody QuestionRequest request) {
        QuestionResponse response = questionService.updateQuestion(questionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<QuestionResponse> deleteQuestion(@PathVariable("questionId") String questionId) {
        try {
            QuestionResponse response = questionService.deleteQuestion(questionId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle the case when the question is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}