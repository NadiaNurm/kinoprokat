package com.example.kinoprokat.util;

import com.example.kinoprokat.dto.FilmDTO;
import com.example.kinoprokat.model.Film;


import java.util.ArrayList;
import java.util.List;


public class Util {
    public static ArrayList<FilmDTO> allFilmsDto(List<Film> filmList) {
        ArrayList<FilmDTO> filmDTOList = new ArrayList<>();
        for (Film f : filmList) {
            FilmDTO film = new FilmDTO(f.getId(), f.getName(), f.getAbout(), f.getFilename());
            filmDTOList.add(film);
        }
        return filmDTOList;
    }


}
