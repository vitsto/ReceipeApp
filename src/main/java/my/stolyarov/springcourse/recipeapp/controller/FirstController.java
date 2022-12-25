package my.stolyarov.springcourse.recipeapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String helloApp() {
        return "Приложение запущено";
    }

    @GetMapping ("/info")
    public String info() {
        return "ФИО: Столяров Виталий Яварович<br>Название: РецептLike<br>" +
                "Дата создания: 14.12.2022<br>Описание: хранилка для рецептов";
    }
}
