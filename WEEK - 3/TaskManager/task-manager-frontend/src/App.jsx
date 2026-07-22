import { useState, useEffect } from 'react';
import { fetchAllTasks, createTask, updateTask, deleteTask } from './api';

/**
 * ============================================================
 * App — Main Component for the Task Manager
 * ============================================================
 *
 * State:
 *   tasks       — array of task objects from the backend
 *   newTitle    — controlled input for the "add task" field
 *   editingId   — id of the task currently being edited (null if none)
 *   editTitle   — controlled input for the inline edit field
 *   loading     — true while the initial fetch is in progress
 *   error       — error message string (null if no error)
 *
 * All CRUD operations call the Spring Boot REST API and then
 * refresh the local state from the response.
 */
export default function App() {
  const [tasks, setTasks] = useState([]);
  const [newTitle, setNewTitle] = useState('');
  const [editingId, setEditingId] = useState(null);
  const [editTitle, setEditTitle] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // ── Load tasks on mount ─────────────────────────────────
  useEffect(() => {
    loadTasks();
  }, []);

  async function loadTasks() {
    try {
      setLoading(true);
      const data = await fetchAllTasks();
      setTasks(data);
      setError(null);
    } catch (err) {
      setError('Could not connect to the server. Make sure the Spring Boot backend is running on port 8080.');
    } finally {
      setLoading(false);
    }
  }

  // ── CREATE ──────────────────────────────────────────────
  async function handleAdd(e) {
    e.preventDefault();
    const title = newTitle.trim();
    if (!title) return;

    try {
      const created = await createTask(title);
      setTasks((prev) => [...prev, created]);
      setNewTitle('');
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // ── TOGGLE completed ───────────────────────────────────
  async function handleToggle(task) {
    try {
      const updated = await updateTask(task.id, {
        title: task.title,
        completed: !task.completed,
      });
      setTasks((prev) =>
        prev.map((t) => (t.id === task.id ? updated : t))
      );
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // ── START EDIT ──────────────────────────────────────────
  function handleEditStart(task) {
    setEditingId(task.id);
    setEditTitle(task.title);
  }

  // ── SAVE EDIT ───────────────────────────────────────────
  async function handleEditSave(task) {
    const title = editTitle.trim();
    if (!title) return;

    try {
      const updated = await updateTask(task.id, {
        title,
        completed: task.completed,
      });
      setTasks((prev) =>
        prev.map((t) => (t.id === task.id ? updated : t))
      );
      setEditingId(null);
      setEditTitle('');
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // ── CANCEL EDIT ─────────────────────────────────────────
  function handleEditCancel() {
    setEditingId(null);
    setEditTitle('');
  }

  // ── DELETE ──────────────────────────────────────────────
  async function handleDelete(id) {
    try {
      await deleteTask(id);
      setTasks((prev) => prev.filter((t) => t.id !== id));
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }

  // ── Computed stats ──────────────────────────────────────
  const completedCount = tasks.filter((t) => t.completed).length;
  const pendingCount = tasks.length - completedCount;

  // ── Render ──────────────────────────────────────────────
  return (
    <div className="app-container">
      {/* ── Header ─────────────────────────────────────── */}
      <header className="app-header">
        <h1>✦ Task Manager</h1>
        <p>In-Memory CRUD — React + Spring Boot</p>
      </header>

      {/* ── Stats Bar ──────────────────────────────────── */}
      {tasks.length > 0 && (
        <div className="stats-bar">
          <div className="stat-item">
            <span className="stat-count">{tasks.length}</span>
            <span className="stat-label">Total</span>
          </div>
          <div className="stat-item completed">
            <span className="stat-count">{completedCount}</span>
            <span className="stat-label">Done</span>
          </div>
          <div className="stat-item pending">
            <span className="stat-count">{pendingCount}</span>
            <span className="stat-label">Pending</span>
          </div>
        </div>
      )}

      {/* ── Error Banner ───────────────────────────────── */}
      {error && (
        <div className="error-banner">
          <span>⚠️ {error}</span>
          <button onClick={() => setError(null)} title="Dismiss">✕</button>
        </div>
      )}

      {/* ── Add Task Form ──────────────────────────────── */}
      <form className="add-task-form" onSubmit={handleAdd}>
        <input
          id="new-task-input"
          type="text"
          placeholder="What needs to be done?"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
          autoFocus
        />
        <button
          id="add-task-btn"
          type="submit"
          className="btn-add"
          disabled={!newTitle.trim()}
        >
          + Add Task
        </button>
      </form>

      {/* ── Loading State ──────────────────────────────── */}
      {loading && (
        <div className="loading">
          <div className="spinner"></div>
          <p>Loading tasks…</p>
        </div>
      )}

      {/* ── Task List ──────────────────────────────────── */}
      {!loading && tasks.length === 0 && (
        <div className="empty-state">
          <div className="empty-icon">📋</div>
          <h3>No tasks yet</h3>
          <p>Add your first task above to get started!</p>
        </div>
      )}

      {!loading && tasks.length > 0 && (
        <div className="task-list">
          {tasks.map((task) => (
            <div
              key={task.id}
              className={`task-item ${task.completed ? 'completed' : ''}`}
              id={`task-${task.id}`}
            >
              {/* ── Checkbox ───────────────────────────── */}
              <label className="task-checkbox">
                <input
                  type="checkbox"
                  checked={task.completed}
                  onChange={() => handleToggle(task)}
                />
                <span className="checkmark"></span>
              </label>

              {/* ── Title / Edit Input ─────────────────── */}
              {editingId === task.id ? (
                <input
                  className="edit-input"
                  type="text"
                  value={editTitle}
                  onChange={(e) => setEditTitle(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === 'Enter') handleEditSave(task);
                    if (e.key === 'Escape') handleEditCancel();
                  }}
                  autoFocus
                />
              ) : (
                <span className="task-title">{task.title}</span>
              )}

              {/* ── Action Buttons ─────────────────────── */}
              <div className="task-actions">
                {editingId === task.id ? (
                  <>
                    <button
                      className="btn-icon btn-save"
                      onClick={() => handleEditSave(task)}
                      title="Save"
                    >
                      ✓
                    </button>
                    <button
                      className="btn-icon btn-cancel"
                      onClick={handleEditCancel}
                      title="Cancel"
                    >
                      ✕
                    </button>
                  </>
                ) : (
                  <>
                    <button
                      className="btn-icon btn-edit"
                      onClick={() => handleEditStart(task)}
                      title="Edit"
                    >
                      ✎
                    </button>
                    <button
                      className="btn-icon btn-delete"
                      onClick={() => handleDelete(task.id)}
                      title="Delete"
                    >
                      🗑
                    </button>
                  </>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
