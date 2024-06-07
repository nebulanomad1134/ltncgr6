// src/components/Customer.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css';  // Assuming Page.css is the renamed CSS file

const API_URL = process.env.REACT_APP_API_URL;

const Customer = () => {
  const [customers, setCustomers] = useState([]);
  const [form, setForm] = useState({ customerCode: '', fullName: '', location: '', phone: '' });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      const response = await axios.get(`${API_URL}/customers`);
      setCustomers(response.data);
    } catch (error) {
      console.error('Error fetching customers', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/customers/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/customers`, form);
      }
      setForm({ customerCode: '', fullName: '', location: '', phone: '' });
      setEditMode(false);
      setCurrentId(null);
      fetchCustomers();
    } catch (error) {
      console.error('Error saving customer', error);
    }
  };

  const handleEdit = (customer) => {
    setForm({
      customerCode: customer.customerCode,
      fullName: customer.fullName,
      location: customer.location,
      phone: customer.phone,
    });
    setEditMode(true);
    setCurrentId(customer.id);  // Make sure to set the ID correctly
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/customers/${id}`);
      fetchCustomers();
    } catch (error) {
      console.error('Error deleting customer', error);
    }
  };

  return (
    <div className="container">
      <h1>Customer Management</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="customerCode"
          placeholder="Customer Code"
          value={form.customerCode}
          onChange={handleChange}
        />
        <input
          type="text"
          name="fullName"
          placeholder="Full Name"
          value={form.fullName}
          onChange={handleChange}
        />
        <input
          type="text"
          name="location"
          placeholder="Location"
          value={form.location}
          onChange={handleChange}
        />
        <input
          type="text"
          name="phone"
          placeholder="Phone"
          value={form.phone}
          onChange={handleChange}
        />
        <button type="submit">{editMode ? 'Update' : 'Create'}</button>
      </form>

      <h2>Customer List</h2>
      <table>
        <thead>
          <tr>
            <th>Customer Code</th>
            <th>Full Name</th>
            <th>Location</th>
            <th>Phone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.id}>
              <td>{customer.customerCode}</td>
              <td>{customer.fullName}</td>
              <td>{customer.location}</td>
              <td>{customer.phone}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(customer)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(customer.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Customer;
