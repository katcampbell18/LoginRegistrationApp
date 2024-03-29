package com.example.demo;

import com.sun.xml.internal.ws.developer.SchemaValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
    @GetMapping("/register")
    public String showRegPage(Model model){
        model.addAttribute("user", new User());

        return "registration";
    }
    @Autowired
    RoleRepository roleRepository;
    @PostMapping("/register")
    public String processRegForm(@ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        if (result.hasErrors()){
            return "registration";
        }
        else{
            Role userRole = roleRepository.findByRole("USER");
            user.setRoles((Arrays.asList(userRole)));
            userService.saveUser(user);
            model.addAttribute("message", "User account created");
        }
        return "index";
    }

}
