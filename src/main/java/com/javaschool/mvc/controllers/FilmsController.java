package com.javaschool.mvc.controllers;

import com.javaschool.mvc.dao.FilmDAO;
import com.javaschool.mvc.models.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Formatter;

@Controller
@RequestMapping("/favorites")
public class FilmsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilmsController.class);
    private static final Formatter FORMATTER = new Formatter();
    private final FilmDAO filmDAO;

    @Autowired
    public FilmsController(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
        LOGGER.info("Создан контроллер");
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("favorites", filmDAO.index());
        LOGGER.info("Открыта страница с избранными фильмами");
        return "favorites/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("film", filmDAO.show(id));
        LOGGER.info(FORMATTER.format("Открыта информация о фильме с id=%d", id).toString());
        return "favorites/show";
    }

    @GetMapping("/new")
    public String newFilm(@ModelAttribute("film") Film film) {
        LOGGER.info("Открыта страница добавления нового фильма");
        return "favorites/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("film") Film film) {
        filmDAO.save(film);
        LOGGER.info("Создан новый фильм");
        return "redirect:/favorites";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("film", filmDAO.show(id));
        LOGGER.info("Открыта страница редактирования информации о фильме");
        return "favorites/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("film") Film film, @PathVariable("id") int id) {
        filmDAO.update(id, film);
        LOGGER.info(FORMATTER.format("Данные о фильме #%d успешно изменены", id).toString());
        return "redirect:/favorites";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        filmDAO.delete(id);
        LOGGER.info(FORMATTER.format("Удален фильм с id=" + id).toString());
        return "redirect:/favorites";
    }
}