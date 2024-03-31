package kg.kamalov.sat.controller;

import jakarta.validation.Valid;
import kg.kamalov.sat.dto.JwtAuthResponse;
import kg.kamalov.sat.dto.SignInRequest;
import kg.kamalov.sat.dto.SignUpRequest;
import kg.kamalov.sat.model.User;
import kg.kamalov.sat.security.AuthenticationService;
import kg.kamalov.sat.service.EmailService;
import kg.kamalov.sat.utils.EmailValidator;
import kg.kamalov.sat.utils.ErrorSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    private final EmailValidator emailValidator;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request,
                                 BindingResult bd){
        emailValidator.validate(new User(request.getEmail()), bd);
        if (bd.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(bd);
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequest request,
                                         BindingResult bd){
        if (bd.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(bd);
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(authService.signIn(request));
    }

    private int generateCode(){
        Random random  = new Random();
        return random.nextInt(1000);
    }
}
