package pl.javastart.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.javastart.dto.NewTaskDto;

import java.util.Scanner;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TodoApplication.class, args);
        TaskService taskService = context.getBean(TaskService.class);
        populateTestData(taskService);
        TaskController taskController = context.getBean(TaskController.class);
        taskController.loop();
    }

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }

    private static void populateTestData(TaskService taskService) {
        taskService.saveTask(new NewTaskDto("Nauka Springa", "Nauczyć się obsługiwać bazy danych w Springu", 90));
        taskService.saveTask(new NewTaskDto("Poprawić budżet domowy", "Sprawdzić arkusz, który błędnie liczy budżet domowy", 50));
        taskService.saveTask(new NewTaskDto("Auto do mechanika", "Umówić i zawieźć auto do mechanika na przegląd", 80));
        taskService.saveTask(new NewTaskDto("Zaplanować wakacje", "Wyszukać i zaklepać wakacje w biurze podróży", 70));
        taskService.saveTask(new NewTaskDto("Kupić farbę", "Kupić białą farbę do odmalowania mieszkania", 30));
        taskService.startTask(1L);
        taskService.completeTask(1L);
        taskService.startTask(3L);
        taskService.completeTask(3L);
    }

}
