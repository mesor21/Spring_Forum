package com.example.demo.controller;

import com.example.demo.entity.Massage;
import com.example.demo.entity.User;
import com.example.demo.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForumController {
    @Autowired
    private ForumService forumService;
    @Async
    @GetMapping("/")
    public String getMainPage(Model model){
        model.addAttribute("list",forumService.getAllMassage());
        model.addAttribute("percentile",forumService.calculatePercentile());
        return "massageList";
    }
    @Async
    @GetMapping("/new")
    public String getNewMassage(Model model){
        model.addAttribute("massage",new Massage());
        model.addAttribute("userList",forumService.getAllUsers());
        return "addMassage";
    }
    @Async
    @PostMapping("/new")
    public String saveNewMassage(@RequestParam(value = "id",required = false) String id,
                                 @RequestParam(value = "massage",required = false) String massage){
        forumService.saveNewMassage(id,massage);
        return "redirect:/";
    }
    @Async
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        forumService.deleteMassage(id);
        return "redirect:/";
    }
    @Async
    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("list",forumService.getAllUsers());
        return "userList";
    }
    @Async
    @GetMapping("/users/{id}")
    public String getForEdit(@PathVariable("id") String id,Model model){
        model.addAttribute("user",forumService.getUserById(id));
        return "updateUser";
    }
    @Async
    @PostMapping("/users/{id}")
    public String update(@PathVariable("id") String id,
                         @RequestParam(value = "login",required = false) String login,
                         @RequestParam(value = "email",required = false) String email
                         ){
        forumService.update(new User(Long.parseLong(id),login,"",email,-1));
        return "redirect:/users";
    }
    @Async
    @GetMapping("/users/new")
    public String getNewUser(Model model){
        model.addAttribute("user",new User());
        return "addUser";
    }
    @Async
    @PostMapping("/users/new")
    public String saveNew(@RequestParam(value = "login",required = false) String login,
                          @RequestParam(value = "email",required = false) String email){
        forumService.saveNewUser(login,email);
        return "redirect:/users";
    }
    @Async
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String id){
        forumService.deleteUser(id);
        return "redirect:/users";
    }
}
