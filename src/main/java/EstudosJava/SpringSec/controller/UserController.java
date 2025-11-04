package EstudosJava.SpringSec.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    public String Userdashboard(){
        return "Usuário autenticado(user ou admin) acessou o dashboard!";
    }
}
