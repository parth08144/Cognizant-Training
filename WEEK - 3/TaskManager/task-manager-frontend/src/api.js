// ============================================================
// API Service — Communicates with the Spring Boot REST API
// ============================================================
// Base URL points to the Vite proxy (/api/tasks) which forwards
// to http://localhost:8080/api/tasks on the Spring Boot server.
// ============================================================

const API_BASE = 'http://localhost:8080/api/tasks';

/**
 * Fetch all tasks from the backend.
 * GET /api/tasks → returns a JSON array of Task objects.
 */
export async function fetchAllTasks() {
  const response = await fetch(API_BASE);
  if (!response.ok) {
    throw new Error(`Failed to fetch tasks: ${response.status}`);
  }
  return response.json();
}

/**
 * Create a new task.
 * POST /api/tasks with { title, completed } in the body.
 * Returns the created Task (with server-assigned id).
 */
export async function createTask(title) {
  const response = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, completed: false }),
  });
  if (!response.ok) {
    throw new Error(`Failed to create task: ${response.status}`);
  }
  return response.json();
}

/**
 * Update an existing task.
 * PUT /api/tasks/{id} with updated { title, completed } in the body.
 * Returns the updated Task, or 404 if id doesn't exist.
 */
export async function updateTask(id, updatedData) {
  const response = await fetch(`${API_BASE}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updatedData),
  });
  if (response.status === 404) {
    throw new Error(`Task with id ${id} not found`);
  }
  if (!response.ok) {
    throw new Error(`Failed to update task: ${response.status}`);
  }
  return response.json();
}

/**
 * Delete a task by id.
 * DELETE /api/tasks/{id}
 * Returns 204 No Content on success, or 404 if not found.
 */
export async function deleteTask(id) {
  const response = await fetch(`${API_BASE}/${id}`, {
    method: 'DELETE',
  });
  if (response.status === 404) {
    throw new Error(`Task with id ${id} not found`);
  }
  if (!response.ok) {
    throw new Error(`Failed to delete task: ${response.status}`);
  }
}
