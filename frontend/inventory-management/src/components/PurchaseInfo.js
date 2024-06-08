import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css';  // Assuming Page.css is the renamed CSS file

const API_URL = process.env.REACT_APP_API_URL;

const PurchaseInfo = () => {
  const [purchaseInfo, setPurchaseInfo] = useState([]);
  const [form, setForm] = useState({
    supplierCode: '',
    productCode: '',
    date: '',
    quantity: '',
    totalCost: ''
  });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchPurchaseInfo();
  }, []);

  const fetchPurchaseInfo = async () => {
    try {
      const response = await axios.get(`${API_URL}/purchaseinfo`);
      setPurchaseInfo(response.data);
    } catch (error) {
      console.error('Error fetching purchase info', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/purchaseinfo/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/purchaseinfo`, form);
      }
      setForm({
        supplierCode: '',
        productCode: '',
        date: '',
        quantity: '',
        totalCost: ''
      });
      setEditMode(false);
      setCurrentId(null);
      fetchPurchaseInfo();
    } catch (error) {
      console.error('Error saving purchase info', error);
    }
  };

  const handleEdit = (purchase) => {
    setForm({
      supplierCode: purchase.supplierCode,
      productCode: purchase.productCode,
      date: purchase.date,
      quantity: purchase.quantity,
      totalCost: purchase.totalCost
    });
    setEditMode(true);
    setCurrentId(purchase.purchaseid);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/purchaseinfo/${id}`);
      fetchPurchaseInfo();
    } catch (error) {
      console.error('Error deleting purchase info', error);
    }
  };

  return (
    <div className="container">
      <h1>Purchase Information</h1>
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
          name="productCode"
          placeholder="Product Code"
          value={form.productCode}
          onChange={handleChange}
        />
        <input
          type="date"
          name="date"
          placeholder="Date"
          value={form.date}
          onChange={handleChange}
        />
        <input
          type="number"
          name="quantity"
          placeholder="Quantity"
          value={form.quantity}
          onChange={handleChange}
        />
        <input
          type="number"
          name="totalCost"
          placeholder="Total Cost"
          value={form.totalCost}
          onChange={handleChange}
        />
        <button type="submit">{editMode ? 'Update' : 'Create'}</button>
      </form>

      <h2>Purchase Info List</h2>
      <table>
        <thead>
          <tr>
            <th>Supplier Code</th>
            <th>Supplier Name</th>
            <th>Product Code</th>
            <th>Product Name</th>
            <th>Date</th>
            <th>Quantity</th>
            <th>Total Cost</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {purchaseInfo.map((purchase) => (
            <tr key={purchase.purchaseid}>
              <td>{purchase.supplierCode}</td>
              <td>{purchase.supplierName}</td>
              <td>{purchase.productCode}</td>
              <td>{purchase.productName}</td>
              <td>{purchase.date}</td>
              <td>{purchase.quantity}</td>
              <td>{purchase.totalCost}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(purchase)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(purchase.purchaseid)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PurchaseInfo;
