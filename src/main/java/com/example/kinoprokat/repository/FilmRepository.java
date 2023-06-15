package com.example.kinoprokat.repository;

import com.example.kinoprokat.model.Film;
import com.example.kinoprokat.model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    public List<Film> findFilmsByGenres(Genres g);
}
