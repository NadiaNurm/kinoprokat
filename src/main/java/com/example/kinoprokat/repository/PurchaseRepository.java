package com.example.kinoprokat.repository;

import com.example.kinoprokat.model.Film;
import com.example.kinoprokat.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    public boolean existsByFilm(Film film);
}
