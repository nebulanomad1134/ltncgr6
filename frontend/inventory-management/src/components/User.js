// src/components/User.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css';  // Assuming Page.css is the renamed CSS file

const API_URL = process.env.REACT_APP_API_URL;

const User = () => {
  const [users, setUsers] = useState([]);
  const [form, setForm] = useState({
    fullname: '',
    location: '',
    phone: '',
    username: '',
    password: '',
    category: 'NORMAL'
  });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(`${API_URL}/users`);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/users/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/users`, form);
      }
      setForm({
        fullname: '',
        location: '',
        phone: '',
        username: '',
        password: '',
        category: 'NORMAL'
      });
      setEditMode(false);
      setCurrentId(null);
      fetchUsers();
    } catch (error) {
      console.error('Error saving user', error);
    }
  };

  const handleEdit = (user) => {
    setForm({
      fullname: user.fullname,
      location: user.location,
      phone: user.phone,
      username: user.username,
      password: user.password,
      category: user.category
    });
    setEditMode(true);
    setCurrentId(user.id);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/users/${id}`);
      fetchUsers();
    } catch (error) {
      console.error('Error deleting user', error);
    }
  };

  return (
    <div className="container">
      <h1>User Management</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="fullname"
          placeholder="Full Name"
          value={form.fullname}
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
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
        />
        <select name="category" value={form.category} onChange={handleChange}>
          <option value="ADMIN">ADMIN</option>
          <option value="NORMAL">NORMAL</option>
        </select>
        <button type="submit">{editMode ? 'Update' : 'Create'}</button>
      </form>

      <h2>User List</h2>
      <table>
        <thead>
          <tr>
            <th>Full Name</th>
            <th>Location</th>
            <th>Phone</th>
            <th>Username</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.fullname}</td>
              <td>{user.location}</td>
              <td>{user.phone}</td>
              <td>{user.username}</td>
              <td>{user.category}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(user)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(user.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default User;
