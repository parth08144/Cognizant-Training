import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,        // React dev server on port 3000
    open: true,        // Auto-open browser on start
    proxy: {
      // Proxy API calls to the Spring Boot backend (port 8080)
      // This avoids CORS issues during development
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
