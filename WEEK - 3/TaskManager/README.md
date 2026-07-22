# In-Memory Task Manager

## Project Structure

```
TaskManager/
├── task-manager-backend/          ← Spring Boot REST API
│   ├── pom.xml
│   └── src/main/java/com/cognizant/taskmanager/
│       ├── TaskManagerApplication.java      ← @SpringBootApplication
│       ├── model/
│       │   └── Task.java                    ← POJO (id, title, completed)
│       └── controller/
│           └── TaskController.java          ← @RestController with CRUD
│
└── task-manager-frontend/         ← React (Vite) UI
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── main.jsx
        ├── App.jsx                          ← Main component
        ├── api.js                           ← HTTP calls to backend
        └── index.css                        ← Styling
```

## How to Run

### 1. Start the Spring Boot Backend
```bash
cd task-manager-backend
mvn spring-boot:run
```
→ Starts on **http://localhost:8080**

### 2. Start the React Frontend
```bash
cd task-manager-frontend
npm install
npm run dev
```
→ Opens on **http://localhost:3000**

## REST API Endpoints

| Method | URL                | Description       | Request Body              |
|--------|--------------------|--------------------|--------------------------|
| GET    | /api/tasks         | List all tasks     | –                        |
| GET    | /api/tasks/{id}    | Get one task       | –                        |
| POST   | /api/tasks         | Create a task      | `{ "title": "..." }`     |
| PUT    | /api/tasks/{id}    | Update a task      | `{ "title": "...", "completed": true }` |
| DELETE | /api/tasks/{id}    | Delete a task      | –                        |

## Annotations Used

| Annotation         | Purpose                                          |
|--------------------|--------------------------------------------------|
| @SpringBootApplication | Marks the main application class            |
| @RestController    | Marks the controller (returns data, not views)   |
| @RequestMapping    | Sets base path `/api/tasks`                      |
| @GetMapping        | Read operations (list all / get one)             |
| @PostMapping       | Create a new task                                |
| @PutMapping        | Update an existing task                          |
| @DeleteMapping     | Delete a task                                    |
| @PathVariable      | Extracts `{id}` from the URL path                |
| @RequestBody       | Maps incoming JSON to a Java object              |
| @CrossOrigin       | Allows React dev server to call the API (CORS)   |
