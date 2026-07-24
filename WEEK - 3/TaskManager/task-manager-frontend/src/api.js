const API_BASE = 'http://localhost:8080/api/tasks';

export async function fetchAllTasks() {
  const response = await fetch(API_BASE);
  if (!response.ok) {
    throw new Error(`Failed to fetch tasks: ${response.status}`);
  }
  return response.json();
}

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
