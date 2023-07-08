package website.ilib.Eureka.Team.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Team.LoginRequest;
import website.ilib.Eureka.Team.TeamRequest;
import website.ilib.Eureka.Team.TeamResponse;
import website.ilib.Eureka.Team.Exception.ApiError;
import website.ilib.Eureka.Team.Service.TeamService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    
    @PostMapping("/enter")
    public ResponseEntity<?> enter(@RequestBody @Valid TeamRequest request){
        try{
            TeamResponse response = teamService.entry(request);
            return ResponseEntity.ok(response);
        }
        catch(TeamService.UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        try{
            TeamResponse response = teamService.login(request);
            return ResponseEntity.ok(response);
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        String errorMessage = fieldError.getDefaultMessage();
        String fieldName = fieldError.getField();
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", errorMessage, fieldName);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
