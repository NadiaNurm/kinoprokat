package com.example.kinoprokat.service;

import com.example.kinoprokat.model.AgeRestriction;
import com.example.kinoprokat.model.Film;
import com.example.kinoprokat.model.Genres;
import com.example.kinoprokat.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    FilmRepository filmRepository;

    public String checkAdd(String name, Date data, String genres, String ageRestriction, String director, String length, String about, String fullName) {
        List<Genres> genresList = new ArrayList<>();
        if (name.equals("")) {
            return "name";
        }
        if (data == null) {
            return "data";
        }
        if (genres == null) {
            return "genres";
        }
        if (ageRestriction == null) {
            return "ageRestriction";
        }
        if (director.equals("")) {
            return "director";
        }
        if (length.equals("")) {
            return "length";
        }
        if (about.equals("")) {
            return "about";
        }
        for (String s : genres.split(",")) {
            genresList.add(Genres.valueOf(s));
        }

        Integer ar = AgeRestriction.valueOf(ageRestriction).getNumVal();
        Film film = new Film(name, data, genresList, ar, director, length, about, fullName);
        filmRepository.save(film);
        return null;
    }

    public void saveFilm(Film film) {
        filmRepository.save(film);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmByID(String id) {
        return filmRepository.findById(Long.valueOf(id)).get();
    }

    public Film getFilmByID(Long id) {
        return filmRepository.findById(id).get();
    }

    public List<Film> getFilmByGenres(Genres genres) {
        return filmRepository.findFilmsByGenres(genres);
    }
}
