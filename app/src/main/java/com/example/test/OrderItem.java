package com.example.test;

import java.util.Locale;

/**
 * 订单项数据模型（订单中的单个菜品）
 */
public class OrderItem {
    private String foodName;         // 菜品名称
    private double price;            // 单价
    private int quantity;            // 数量
    private String description;      // 描述

    public OrderItem(String foodName, double price, int quantity, String description) {
        this.foodName = foodName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    // 从CartItem创建OrderItem的便捷构造函数
    public OrderItem(CartItem cartItem) {
        this.foodName = cartItem.getFoodItem().getName();
        this.price = cartItem.getFoodItem().getPrice();
        this.quantity = cartItem.getQuantity();
        this.description = cartItem.getFoodItem().getDescription();
    }

    // Getters
    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取该订单项的总价
     * @return 单价 * 数量
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * 获取格式化的单价
     * @return 格式化单价字符串（例如：¥32.00）
     */
    public String getFormattedPrice() {
        return String.format(Locale.getDefault(), "¥%.2f", price);
    }

    /**
     * 获取格式化的总价
     * @return 格式化总价字符串（例如：¥64.00）
     */
    public String getFormattedTotalPrice() {
        return String.format(Locale.getDefault(), "¥%.2f", getTotalPrice());
    }
}
