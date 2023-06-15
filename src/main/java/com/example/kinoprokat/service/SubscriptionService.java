package com.example.kinoprokat.service;

import com.example.kinoprokat.model.*;
import com.example.kinoprokat.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    FilmService filmService;
    @Autowired
    PurchaseService purchaseService;

    public void addSub(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubs() {
        return subscriptionRepository.findAll();
    }

    public HashMap<Genres, List<Film>> filmGenre() {
        List<Film> allFilms = filmService.getAllFilms();
        HashMap<Genres, List<Film>> result = new HashMap<>();
        for (Genres genres : Genres.values()) {
            result.put(genres, new ArrayList<>());
        }
        for (Film f : allFilms) {
            for (Genres g : f.getGenres()) {
                if (result.containsKey(g)) {
                    List<Film> filmList = result.get(g);
                    filmList.add(f);
                    result.replace(g, filmList);
                } else {
                    continue;
                }
            }
        }

        for (Map.Entry<Genres, List<Film>> entry : result.entrySet()) {
            Genres key = entry.getKey();
            List<Film> value = entry.getValue();
            List<Film> sortedFilms = sortedFilms(value);
            result.replace(key, sortedFilms);

        }

        return result;
    }

    public List<Film> sortedFilms(List<Film> films) {
        HashMap<Film, Integer> filmPopularity = purchaseService.FilmStatistic();
        films.sort(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return filmPopularity.get(o2) - filmPopularity.get(o1);
            }
        });
        return films;
    }

    public boolean checkSub(User user, SubType subType) {
        List<Subscription> subscriptions = getAllSubs();
        for (Subscription s : subscriptions) {
            if (s.getUser().equals(user) & s.getSubTypes().equals(subType)) {
                System.out.println("no");
                return false;
            }
        }
        return true;
    }


}
