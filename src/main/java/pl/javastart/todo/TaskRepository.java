package pl.javastart.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByStartTimeIsNullOrderByPriorityDesc();

    List<Task> findAllByCompletionTimeNotNullOrderByCompletionTimeDesc();
}
