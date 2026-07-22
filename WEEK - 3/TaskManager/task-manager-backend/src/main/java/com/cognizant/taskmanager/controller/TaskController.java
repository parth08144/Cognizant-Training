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

/**
 * ============================================================
 * TaskController — REST Controller for Task CRUD Operations
 * ============================================================
 *
 * Annotations explained:
 *
 *   @RestController
 *     Combines @Controller + @ResponseBody.
 *     Every method return value is serialized directly to
 *     the HTTP response body as JSON (via Jackson).
 *
 *   @RequestMapping("/api/tasks")
 *     Sets the base URL path for all endpoints in this
 *     controller. All mappings below are relative to this.
 *
 *   @CrossOrigin(origins = "http://localhost:3000")
 *     Allows the React development server (port 3000) to
 *     call these endpoints. Without this, the browser blocks
 *     the requests due to the Same-Origin Policy (CORS).
 *
 * Storage:
 *   Tasks are stored in a ConcurrentHashMap (thread-safe).
 *   Data lives only in JVM memory — it resets on restart.
 *   No JPA, no database, no persistence layer.
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    // ── In-Memory Storage ───────────────────────────────────

    /**
     * ConcurrentHashMap stores all tasks keyed by their id.
     * Thread-safe for concurrent HTTP requests.
     * Data is lost when the application restarts.
     */
    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();

    /**
     * AtomicLong generates unique, auto-incrementing task ids.
     * Thread-safe counter — no duplicate ids even under concurrency.
     */
    private final AtomicLong idCounter = new AtomicLong(1);

    // ── CREATE ──────────────────────────────────────────────

    /**
     * POST /api/tasks
     * Creates a new task.
     *
     * @PostMapping maps HTTP POST requests to this method.
     *
     * @RequestBody tells Spring to deserialize the incoming
     *   JSON request body into a Task object using Jackson.
     *   Example request body: { "title": "Buy groceries" }
     *
     * @param task the Task object parsed from the JSON body
     * @return the created Task with a server-assigned id
     *         and HTTP 201 (Created) status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Assign a unique id (ignore any id sent by the client)
        long newId = idCounter.getAndIncrement();
        task.setId(newId);

        // Default: new tasks are not completed unless specified
        // (Jackson will set completed from JSON if provided)

        // Store the task in our in-memory map
        taskStore.put(newId, task);

        // Return 201 Created with the saved task in the response body
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // ── READ (all) ──────────────────────────────────────────

    /**
     * GET /api/tasks
     * Returns all tasks as a JSON array.
     *
     * @GetMapping maps HTTP GET requests to this method.
     *
     * @return List of all tasks and HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        // Convert the map values to a List for JSON array output
        List<Task> tasks = new ArrayList<>(taskStore.values());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // ── READ (single) ───────────────────────────────────────

    /**
     * GET /api/tasks/{id}
     * Returns a single task by its id.
     *
     * @PathVariable extracts the {id} segment from the URL
     *   path and binds it to the method parameter.
     *   Example: GET /api/tasks/3  →  id = 3
     *
     * @param id the task id from the URL path
     * @return the Task if found (200 OK), or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        Task task = taskStore.get(id);

        if (task == null) {
            // Task with this id does not exist → 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    // ── UPDATE ──────────────────────────────────────────────

    /**
     * PUT /api/tasks/{id}
     * Updates an existing task (title and/or completed status).
     *
     * @PutMapping maps HTTP PUT requests to this method.
     *
     * @PathVariable extracts {id} from the URL path.
     * @RequestBody maps the JSON body to the updatedTask object.
     *
     * @param id          the task id to update
     * @param updatedTask the new task data from the request body
     * @return the updated Task (200 OK), or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id,
                                           @RequestBody Task updatedTask) {
        Task existingTask = taskStore.get(id);

        if (existingTask == null) {
            // Task with this id does not exist → 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the fields
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompleted(updatedTask.isCompleted());

        // Save back to the map (same key, updated object)
        taskStore.put(id, existingTask);

        return new ResponseEntity<>(existingTask, HttpStatus.OK);
    }

    // ── DELETE ──────────────────────────────────────────────

    /**
     * DELETE /api/tasks/{id}
     * Deletes a task by its id.
     *
     * @DeleteMapping maps HTTP DELETE requests to this method.
     *
     * @PathVariable extracts {id} from the URL path.
     *
     * @param id the task id to delete
     * @return 204 No Content if deleted, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        Task removed = taskStore.remove(id);

        if (removed == null) {
            // Task with this id does not exist → 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 204 No Content — successful deletion, no body
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
