package com.example.kinoprokat.service;

import com.example.kinoprokat.dto.FilmDTO;
import com.example.kinoprokat.model.Film;
import com.example.kinoprokat.model.Purchase;
import com.example.kinoprokat.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    FilmService filmService;

    public List<Purchase> getAllPurchase() {
        return purchaseRepository.findAll();
    }

    public void savePurchase(Purchase purchase) {

        purchaseRepository.save(purchase);
    }

    public List<Purchase> getAllUserPurchase(Long id) {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        List<Purchase> userPurchase = new ArrayList<>();
        for (Purchase p : purchaseList) {
            if (Objects.equals(p.getUser().getId(), id)) {
                userPurchase.add(p);
            }
        }
        return userPurchase;
    }

    public ArrayList<FilmDTO> allUserFilmsDto(List<Purchase> purchaseList) {
        ArrayList<FilmDTO> userFilmDTOList = new ArrayList<>();
        for (Purchase p : purchaseList) {
            Film f = filmService.getFilmByID(p.getFilm().getId());
            userFilmDTOList.add(new FilmDTO(f.getId(), f.getName(), f.getAbout(), f.getFilename()));
        }
        return userFilmDTOList;
    }

    public HashMap<Film, Integer> FilmStatistic() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        HashMap<Film, Integer> filmPopularity = new HashMap<>();
        for (Purchase p : purchaseList) {
            if (filmPopularity.containsKey(p.getFilm())) {
                filmPopularity.replace(p.getFilm(), filmPopularity.get(p.getFilm()) + 1);
            } else {
                filmPopularity.put(p.getFilm(), 0);
            }
        }
        // System.out.println(filmPopularity);
        return filmPopularity;
    }

    public boolean existsByFilm(Film film) {
        return purchaseRepository.existsByFilm(film);
    }
}
