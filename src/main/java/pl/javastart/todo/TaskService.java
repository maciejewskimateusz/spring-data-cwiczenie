package pl.javastart.todo;

import org.springframework.stereotype.Service;
import pl.javastart.dto.NewTaskDto;
import pl.javastart.dto.TaskDurationDto;
import pl.javastart.exception.TaskAlreadyCompletedException;
import pl.javastart.exception.TaskAlreadyStartedException;
import pl.javastart.exception.TaskNotFoundException;
import pl.javastart.exception.TaskNotStartedException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Long saveTask(NewTaskDto task) {
        Task taskToSave = new Task(task.getTitle(), task.getDescription(), task.getPriority());
        Task savedTask = taskRepository.save(taskToSave);
        return savedTask.getId();
    }

    @Transactional
    public LocalDateTime startTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (task.getStartTime() != null) {
            throw new TaskAlreadyStartedException();
        }
        task.setStartTime(LocalDateTime.now());
        return task.getStartTime();
    }

    @Transactional
    public TaskDurationDto completeTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (task.getStartTime() == null) {
            throw new TaskNotStartedException();
        } else if (task.getCompletionTime() != null) {
            throw new TaskAlreadyCompletedException();
        }
        task.setCompletionTime(LocalDateTime.now());
        return new TaskDurationDto(task.getStartTime(), task.getCompletionTime());
    }

    public Optional<String> getTaskInfo(Long taskId) {
        return taskRepository.findById(taskId)
                .map(Task::toString);
    }

    List<String> getAllNotStartedTasks(){
        return taskRepository.findAllByStartTimeIsNullOrderByPriorityDesc()
                .stream()
                .map(Task::toString)
                .collect(Collectors.toList());
    }

    List<String> getAllCompletedTasks(){
        return taskRepository.findAllByCompletionTimeNotNullOrderByCompletionTimeDesc()
                .stream()
                .map(Task::toString)
                .collect(Collectors.toList());
    }


}
