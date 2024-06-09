// src/components/Supplier.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css';  // Assuming Page.css is the renamed CSS file

const API_URL = process.env.REACT_APP_API_URL;

const Supplier = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [form, setForm] = useState({ supplierCode: '', fullName: '', location: '', phone: '' });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchSuppliers();
  }, []);

  const fetchSuppliers = async () => {
    try {
      const response = await axios.get(`${API_URL}/suppliers`);
      setSuppliers(response.data);
    } catch (error) {
      console.error('Error fetching suppliers', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/suppliers/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/suppliers`, form);
      }
      setForm({ supplierCode: '', fullName: '', location: '', phone: '' });
      setEditMode(false);
      setCurrentId(null);
      fetchSuppliers();
    } catch (error) {
      console.error('Error saving supplier', error);
    }
  };

  const handleEdit = (supplier) => {
    setForm({
      supplierCode: supplier.supplierCode,
      fullName: supplier.fullName,
      location: supplier.location,
      phone: supplier.phone,
    });
    setEditMode(true);
    setCurrentId(supplier.id);  // Make sure to set the ID correctly
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/suppliers/${id}`);
      fetchSuppliers();
    } catch (error) {
      console.error('Error deleting supplier', error);
    }
  };

  return (
    <div className="container">
      <h1>Supplier Management</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="supplierCode"
          placeholder="Supplier Code"
          value={form.supplierCode}
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

      <h2>Supplier List</h2>
      <table>
        <thead>
          <tr>
            <th>Supplier Code</th>
            <th>Full Name</th>
            <th>Location</th>
            <th>Phone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {suppliers.map((supplier) => (
            <tr key={supplier.id}>
              <td>{supplier.supplierCode}</td>
              <td>{supplier.fullName}</td>
              <td>{supplier.location}</td>
              <td>{supplier.phone}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(supplier)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(supplier.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Supplier;