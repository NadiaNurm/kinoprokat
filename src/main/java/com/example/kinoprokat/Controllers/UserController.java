package com.example.kinoprokat.Controllers;

import com.example.kinoprokat.dto.FilmDTO;
import com.example.kinoprokat.model.*;
import com.example.kinoprokat.service.FilmService;
import com.example.kinoprokat.service.PurchaseService;
import com.example.kinoprokat.service.SubscriptionService;
import com.example.kinoprokat.service.UserService;
import com.example.kinoprokat.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    FilmService filmService;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("/personalArea")
    public String personalArea(@AuthenticationPrincipal User user, Model model) {
        List<Purchase> userPurchase = purchaseService.getAllUserPurchase(user.getId());
        ArrayList<FilmDTO> filmDTOList = purchaseService.allUserFilmsDto(userPurchase);
        model.addAttribute("filmDTOList", filmDTOList);
        return "personalArea";

    }

    @GetMapping("/buyFilm/{id}")
    public String buyFilm(@AuthenticationPrincipal User user, @PathVariable(name = "id") String id) {
        Film film = filmService.getFilmByID(id);
        if (purchaseService.existsByFilm(film)) {
            Purchase purchase = new Purchase(user, film, new Date());
            purchaseService.savePurchase(purchase);
        }

        return "redirect:/personalArea";
    }

    @GetMapping("/subscription")
    public String subscription(@AuthenticationPrincipal User user, Model model) {
        HashMap<Genres, List<Film>> filmsMap = subscriptionService.filmGenre();
        filmsMap.entrySet().removeIf(entries -> entries.getValue().size() == 0);
        model.addAttribute("filmByGenre", filmsMap);
        return "subscription";
    }

    @GetMapping("/buySubscription/{genre}")
    public String buySubscription(@AuthenticationPrincipal User user, @PathVariable(name = "genre") String genre, Model model) {
        model.addAttribute("genre", genre);
        Calendar calendar = Calendar.getInstance();
        LocalDate today = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        model.addAttribute("today", today);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("subTypes", SubType.values());

        return "buySubscription";
    }

    @PostMapping("/buySubscription")
    public String buySubscription(@AuthenticationPrincipal User user, @RequestParam(name = "genre") String genre,
                                  @RequestParam(name = "today") String today,
                                  @RequestParam(name = "subType") String type) {

        if (subscriptionService.checkSub(user, SubType.valueOf(type))) {
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setDateStart(LocalDate.parse(today));
            subscription.setSubTypes(SubType.valueOf(type));
            subscription.setGenres(Genres.valueOf(genre));
            subscription.setEndDate();
            subscriptionService.addSub(subscription);
        }
        return "redirect:/personalArea";
    }

    @GetMapping("/mainPage")
    public String printFilms(Model model) {
        List<Film> filmList = filmService.getAllFilms();
        ArrayList<FilmDTO> filmDTOList = Util.allFilmsDto(filmList);
        model.addAttribute("filmDTOList", filmDTOList);
        return "mainPage";
    }

    @GetMapping("/about/film/{id}")
    public String printFilm(@PathVariable(name = "id") String id, Model model) {
        Film film = filmService.getFilmByID(id);
        model.addAttribute("filmID", film.getId());
        model.addAttribute("filmName", film.getName());
        model.addAttribute("filmDate", film.getData());
        model.addAttribute("filmDirector", film.getDirector());
        model.addAttribute("filmAge", film.getAgeRestriction());
        model.addAttribute("filmLen", film.getLength());
        model.addAttribute("filmGenres", film.getStrGenres());
        model.addAttribute("filmAbout", film.getAbout());
        model.addAttribute("filename", film.getFilename());
        return "film";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrate(@RequestParam(name = "username", required = false) String username,
                             @RequestParam(name = "password", required = false) String password, @RequestParam(name = "password2", required = false) String password2, Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("error", "Passwords not the same");
            return "registration";
        }
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "User already exist");
            return "registration";
        }
        userService.addUser(username, password);
        return "redirect:/personalArea";
    }


}
