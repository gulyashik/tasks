package org.gul.tasks.controllers;


import org.gul.tasks.converters.TaskConverter;
import org.gul.tasks.domain.Status;
import org.gul.tasks.domain.Task;
import org.gul.tasks.dto.ResponseDto;
import org.gul.tasks.dto.TaskDto;
import org.gul.tasks.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/task", produces = "application/json")
public class TasksController {
    @Autowired
    private TasksRepository tasksRepository;

    @PostMapping
    public ResponseEntity<ResponseDto> CreateTask() {
        UUID id = UUID.randomUUID();

        Task task = new Task(id, Status.Created);
        tasksRepository.save(task);
        RunTask(id);

        return new ResponseEntity<>(new ResponseDto(id.toString()), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDto> GetTask(@PathVariable String id) {
        Pattern p = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        if (!p.matcher(id).find()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        UUID uuid = UUID.fromString(id);
        Optional<Task> task = tasksRepository.findById(uuid);
        if (!task.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        TaskDto taskDto = TaskConverter.toDto(task.get());
        return new ResponseEntity<>(taskDto, HttpStatus.OK);

    }

    private void RunTask(UUID id) {
        new Thread(() -> {
            try {
                tasksRepository.updateStatus(id, Status.Running);
                Thread.sleep(2 * 60 * 1000);
                tasksRepository.updateStatus(id, Status.Finished);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
