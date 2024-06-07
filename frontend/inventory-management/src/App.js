// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Supplier from './components/Supplier';
import Customer from './components/Customer';
import './App.css';  // Assuming you have some global styles in App.css

const App = () => {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/suppliers" element={<Supplier />} />
          <Route path="/customers" element={<Customer />} />
          {/* Add more routes as needed */}
        </Routes>
      </div>
    </Router>
  );
};

export default App;
