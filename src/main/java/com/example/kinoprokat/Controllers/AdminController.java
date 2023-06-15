package com.example.kinoprokat.Controllers;

import com.example.kinoprokat.model.Genres;
import com.example.kinoprokat.model.User;
import com.example.kinoprokat.service.FilmService;
import com.example.kinoprokat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//http://localhost:8080/mainPage
@Controller
@PreAuthorize("hasAuthority('admin')")
public class AdminController {
    @Value("${upload.path}")
    private String path;

    @Autowired
    FilmService filmService;
    @Autowired
    UserService userService;

    @GetMapping("/adminAddFilm")
    public String addPage(Model model) {
        List<Genres> listGenres = List.of(Genres.values());
        model.addAttribute("genres", listGenres);
        model.addAttribute("error", "");
        return "adminAddFilm";
    }

    @PostMapping("/adminAddFilm")
    public String addFilm(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data,
                          @RequestParam(name = "genres", required = false) String genres, @RequestParam(name = "director", required = false) String director,
                          @RequestParam(name = "ageRestriction", required = false) String ageRestriction, @RequestParam(name = "length", required = false) String length,
                          @RequestParam(name = "about", required = false) String about, @RequestParam("f") MultipartFile f, Model model) throws IOException {
        // Добавление фильма
        String fullName = "";
        String fileName = UUID.randomUUID().toString().substring(0, 9) + f.getOriginalFilename();
        if (f.getOriginalFilename().equals("")) {
            fullName = "C:\\Users\\User\\IdeaProjects\\kinoprokat\\src\\main\\resources\\static\\images\\049b3000-фото.jpg";
        } else {

            fullName = path + '/' + fileName;
        }
        f.transferTo(new File(fullName));
        String result = filmService.checkAdd(name, data, genres, ageRestriction, director, length, about, fileName);

        if (result != null) {
            model.addAttribute("error", result);
            return "adminAddFilm";
        }
        model.addAttribute("error", "");
        return "adminAddFilm";
    }


    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        // Получаем список пользователей. Admin может забанить юзера или обновить.
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/allUsers/bane/{id}")
    public String userBane(@PathVariable(name = "id") String id) {
        // Баним пользователя
        userService.userBane(Long.valueOf(id));
        return "redirect:/allUsers";
    }

    @GetMapping("/allUsers/update/{id}")
    public String updateUser(@PathVariable(name = "id") String id, Model model) {
        User user = userService.getUserById(Long.valueOf(id));
        model.addAttribute("id", id);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("status", user.isStatus());
        return "/updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                             @RequestParam(name = "role") String role, @RequestParam(name = "userId") String id) {
        // Обновляем пользователя
        User user = userService.getUserById(Long.valueOf(id));
        userService.updateUser(user, username, password, role);
        return "redirect:/allUsers";
    }
}
