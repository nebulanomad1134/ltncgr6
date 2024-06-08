// src/components/Home.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Page.css';  

const Home = () => {
  return (
    <div className="home-container">
      <h1>Inventory Management System</h1>
      <div className="navigation">
        <Link to="/suppliers" className="nav-link">Manage Suppliers</Link>
        <Link to="/customers" className="nav-link">Manage Customers</Link>
        <Link to="/products" className="nav-link">Manage Product</Link>
        <Link to="/purchaseinfo" className="nav-link">Manage Purchases</Link>

        {/* Add more links as needed */}
      </div>
    </div>
  );
};

export default Home;
