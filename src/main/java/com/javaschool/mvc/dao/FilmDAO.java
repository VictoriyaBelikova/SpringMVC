package com.javaschool.mvc.dao;

import com.javaschool.mvc.models.Film;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmDAO {
    private static int FILMS_COUNT;
    private List<Film> favorites;

    {
        favorites = new ArrayList<>();
        favorites.add(new Film(++FILMS_COUNT, "Fringe"));
        favorites.add(new Film(++FILMS_COUNT, "Неукротимый"));
    }

    public List<Film> index() {
        return favorites;
    }

    public Film show(int id) { return favorites.stream().filter(film -> film.getId() == id).findAny().orElse(new Film()); }

    public void save(Film film) {
        film.setId(++FILMS_COUNT);
        favorites.add(film);
    }

    public void update(int id, Film updatedFilm) {
        Film filmToBeUpdated = show(id);

        filmToBeUpdated.setName(updatedFilm.getName());
    }

    public void delete(int id) {
        favorites.removeIf(f -> f.getId() == id);
    }
}
