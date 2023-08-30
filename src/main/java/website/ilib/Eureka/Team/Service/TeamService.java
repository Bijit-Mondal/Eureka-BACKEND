package website.ilib.Eureka.Team.Service;

import java.util.Random;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import website.ilib.Eureka.Mail.Service.MailService;
import website.ilib.Eureka.SecurityConfig.Service.JWTService;
import website.ilib.Eureka.Team.LoginRequest;
import website.ilib.Eureka.Team.TeamRequest;
import website.ilib.Eureka.Team.TeamResponse;
import website.ilib.Eureka.Team.Model.TeamModel;
import website.ilib.Eureka.Team.Repository.TeamRepo;

@RequiredArgsConstructor
@Service
public class TeamService{
    private final TeamRepo teamRepo;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    public class UserAlreadyExistsException extends RuntimeException {

        public UserAlreadyExistsException(String message) {
            super(message);
        }

    }

    public TeamResponse entry(TeamRequest request){
        if(teamRepo.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Team with email "+request.getEmail()+" already exists");
        }
        String password = generatePassword();
        TeamModel team = TeamModel.builder()
                    .email(request.getEmail())
                    .teamName(request.getTeamName())
                    .level(-1)
                    .hintUsed(0)
                    .totalMarks(0)
                    .password(passwordEncoder.encode(password))
                    .build();
        teamRepo.save(team);

        try{
            mailService.sendVerificationMail(team, password);
        }catch(Exception e){
            throw e;
        }

        var jwtToken = jwtService.generateAccessToken(team);
        return TeamResponse.builder()
                    .email(team.getEmail())
                    .accessToken(jwtToken)
                    .level(team.getLevel())
                    .build();
    }

    public TeamResponse login(LoginRequest request){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Bad Credential, Invalid email/password");
        }

        TeamModel team = teamRepo.findByEmail(request.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
       
        var jwtToken = jwtService.generateAccessToken(team);
        return TeamResponse.builder()
                           .accessToken(jwtToken)
                           .email(request.getEmail())
                           .level(team.getLevel())
                           .build();
    }
    public String generatePassword() {
        int length = 5;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            char letter = (char) (random.nextInt(26) + 'a');
            sb.append(letter);
        }
        
        return sb.toString();
    }

    
}
