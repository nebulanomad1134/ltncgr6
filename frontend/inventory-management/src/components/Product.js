import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Page.css'; 

const API_URL = process.env.REACT_APP_API_URL;

const Product = () => {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({ productCode: '', productName: '', costPrice: '', sellingPrice: '', brand: '', quantity: '' });
  const [editMode, setEditMode] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axios.get(`${API_URL}/products`);
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products', error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editMode) {
        await axios.put(`${API_URL}/products/${currentId}`, form);
      } else {
        await axios.post(`${API_URL}/products`, form);
      }
      setForm({ productCode: '', productName: '', costPrice: '', sellingPrice: '', brand: '', quantity: '' });
      setEditMode(false);
      setCurrentId(null);
      fetchProducts();
    } catch (error) {
      console.error('Error saving product', error);
    }
  };

  const handleEdit = async (product) => {
    try {
      setForm({
        productCode: product.productCode,
        productName: product.productName,
        costPrice: product.costPrice,
        sellingPrice: product.sellingPrice,
        brand: product.brand,
        quantity: product.quantity
      });
      setEditMode(true);
      setCurrentId(product.pid);  // Make sure to set the ID correctly
    } catch (error) {
      console.error('Error fetching product stock', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/products/${id}`);
      fetchProducts();
    } catch (error) {
      console.error('Error deleting product', error);
    }
  };

  return (
    <div className="container">
      <h1>Product Management</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="productCode"
          placeholder="Product Code"
          value={form.productCode}
          onChange={handleChange}
        />
        <input
          type="text"
          name="productName"
          placeholder="Product Name"
          value={form.productName}
          onChange={handleChange}
        />
        <input
          type="text"
          name="costPrice"
          placeholder="Cost Price"
          value={form.costPrice}
          onChange={handleChange}
        />
        <input
          type="text"
          name="sellingPrice"
          placeholder="Selling Price"
          value={form.sellingPrice}
          onChange={handleChange}
        />
        <input
          type="text"
          name="brand"
          placeholder="Brand"
          value={form.brand}
          onChange={handleChange}
        />
        <input
          type="number"
          name="quantity"
          placeholder="Quantity"
          value={form.quantity}
          onChange={handleChange}
        />
        <button type="submit">{editMode ? 'Update' : 'Create'}</button>
      </form>

      <h2>Product List</h2>
      <table>
        <thead>
          <tr>
            <th>Product Code</th>
            <th>Product Name</th>
            <th>Cost Price</th>
            <th>Selling Price</th>
            <th>Brand</th>
            <th>Quantity</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.pid}>
              <td>{product.productCode}</td>
              <td>{product.productName}</td>
              <td>{product.costPrice}</td>
              <td>{product.sellingPrice}</td>
              <td>{product.brand}</td>
              <td>{product.quantity}</td>
              <td>
                <button className="edit-button" onClick={() => handleEdit(product)}>Edit</button>
                <button className="delete-button" onClick={() => handleDelete(product.pid)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Product;
