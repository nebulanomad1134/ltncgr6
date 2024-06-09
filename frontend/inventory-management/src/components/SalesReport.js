// src/components/SalesReport.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css';  // Assuming Page.css is the renamed CSS file

const API_URL = process.env.REACT_APP_API_URL;

const SalesReport = () => {
  const [salesReports, setSalesReports] = useState([]);
  const [form, setForm] = useState({
    date: '',
    customercode: '',
    productcode: '',
    quantity: '',
    revenue: '',
    soldBy: ''
  });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchSalesReports();
  }, []);

  const fetchSalesReports = async () => {
    try {
      const response = await axios.get(`${API_URL}/salesreport`);
      setSalesReports(response.data);
    } catch (error) {
      console.error('Error fetching sales reports', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/salesreport/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/salesreport`, form);
      }
      setForm({
        date: '',
        customercode: '',
        productcode: '',
        quantity: '',
        revenue: '',
        soldBy: ''
      });
      setEditMode(false);
      setCurrentId(null);
      fetchSalesReports();
    } catch (error) {
      console.error('Error saving sales report', error);
    }
  };

  const handleEdit = (salesReport) => {
    setForm({
      date: salesReport.date,
      customercode: salesReport.customercode,
      productcode: salesReport.productcode,
      quantity: salesReport.quantity,
      revenue: salesReport.revenue,
      soldBy: salesReport.soldBy
    });
    setEditMode(true);
    setCurrentId(salesReport.salesid);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/salesreport/${id}`);
      fetchSalesReports();
    } catch (error) {
      console.error('Error deleting sales report', error);
    }
  };

  return (
    <div className="container">
      <h1>Sales Report</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="date"
          name="date"
          placeholder="Date"
          value={form.date}
          onChange={handleChange}
        />
        <input
          type="text"
          name="customercode"
          placeholder="Customer Code"
          value={form.customercode}
          onChange={handleChange}
        />
        <input
          type="text"
          name="productcode"
          placeholder="Product Code"
          value={form.productcode}
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
          name="revenue"
          placeholder="Revenue"
          value={form.revenue}
          onChange={handleChange}
        />
        <input
          type="text"
          name="soldBy"
          placeholder="Sold By (Username)"
          value={form.soldBy}
          onChange={handleChange}
        />
        <button type="submit">{editMode ? 'Update' : 'Create'}</button>
      </form>

      <h2>Sales Report List</h2>
      <table>
        <thead>
          <tr>
            <th>Date</th>
            <th>Customer Code</th>
            <th>Product Code</th>
            <th>Quantity</th>
            <th>Revenue</th>
            <th>Sold By (Username)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {salesReports.map((salesReport) => (
            <tr key={salesReport.salesid}>
              <td>{salesReport.date}</td>
              <td>{salesReport.customercode}</td>
              <td>{salesReport.productcode}</td>
              <td>{salesReport.quantity}</td>
              <td>{salesReport.revenue}</td>
              <td>{salesReport.soldBy}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(salesReport)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(salesReport.salesid)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SalesReport;
