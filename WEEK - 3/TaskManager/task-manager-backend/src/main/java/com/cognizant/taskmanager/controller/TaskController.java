package com.cognizant.taskmanager.controller;

import com.cognizant.taskmanager.model.Task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();

    private final AtomicLong idCounter = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        long newId = idCounter.getAndIncrement();
        task.setId(newId);

        taskStore.put(newId, task);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        List<Task> tasks = new ArrayList<>(taskStore.values());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        Task task = taskStore.get(id);

        if (task == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id,
                                           @RequestBody Task updatedTask) {
        Task existingTask = taskStore.get(id);

        if (existingTask == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompleted(updatedTask.isCompleted());

        taskStore.put(id, existingTask);

        return new ResponseEntity<>(existingTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        Task removed = taskStore.remove(id);

        if (removed == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
