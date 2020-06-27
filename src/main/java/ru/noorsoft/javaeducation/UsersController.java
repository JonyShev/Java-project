package ru.noorsoft.javaeducation;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class UsersController {

    private UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String userMain(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/")
    public String addUser(@RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam String phoneNumber, @RequestParam String email) {
        User user = new User(firstName, lastName, phoneNumber, email);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/";
    }

    @GetMapping("/{id}/update")
    public String userEdit(@PathVariable("id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "user_update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable(value = "id") long id, @RequestParam String firstName, @RequestParam String lastName,
                             @RequestParam String phoneNumber, @RequestParam String email,  Model model) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/";
    }

}