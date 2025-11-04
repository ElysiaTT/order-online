package com.example.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 订单数据模型
 */
public class Order {
    private String orderId;              // 订单号
    private String restaurantName;       // 餐厅名称
    private Date orderTime;              // 下单时间
    private OrderStatus status;          // 订单状态
    private double totalAmount;          // 总金额
    private List<OrderItem> items;       // 订单项列表

    public enum OrderStatus {
        PENDING("待付款"),
        PAID("已付款"),
        PREPARING("制作中"),
        DELIVERING("配送中"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public Order(String orderId, String restaurantName, Date orderTime, OrderStatus status, double totalAmount) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.orderTime = orderTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // Setters
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * 获取格式化的下单时间
     * @return 格式化时间字符串（例如：2023-10-05 12:30）
     */
    public String getFormattedOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(orderTime);
    }

    /**
     * 获取格式化的总金额
     * @return 格式化金额字符串（例如：¥128.00）
     */
    public String getFormattedTotalAmount() {
        return String.format(Locale.getDefault(), "¥%.2f", totalAmount);
    }
}
