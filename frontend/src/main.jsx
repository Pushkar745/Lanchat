import React from 'react';
import ReactDOM from 'react-dom/client';

import './index.css';  // Global styles and design tokens loaded first
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
  // StrictMode renders components twice in development only
  // This helps catch side effects in hooks early — has zero
  // impact on the production build
  <React.StrictMode>
    <App />
  </React.StrictMode>
);