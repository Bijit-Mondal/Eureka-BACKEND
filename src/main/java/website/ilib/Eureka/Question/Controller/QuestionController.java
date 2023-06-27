package website.ilib.Eureka.Question.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    @GetMapping("/get/{id}")
    public String getMethodName(@PathVariable("id") String id) {
        return id;
    }
}