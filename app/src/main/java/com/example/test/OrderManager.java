package com.example.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 订单管理器（单例模式）
 * 管理用户的所有订单
 */
public class OrderManager {
    private static OrderManager instance;
    private List<Order> orders;

    private OrderManager() {
        this.orders = new ArrayList<>();
        // 初始化一些测试订单
        initTestOrders();
    }

    /**
     * 获取OrderManager单例
     */
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     * 初始化测试订单
     */
    private void initTestOrders() {
        // 创建一些历史订单用于测试

        // 订单1 - 中华小馆
        Order order1 = new Order(
                "202310051230001",
                "中华小馆",
                new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000), // 7天前
                Order.OrderStatus.COMPLETED,
                160.00
        );
        order1.addItem(new OrderItem("北京烤鸭", 128.00, 1, "经典北京烤鸭，皮脆肉嫩"));
        order1.addItem(new OrderItem("酸辣汤", 16.00, 2, "开胃酸辣汤"));
        orders.add(order1);

        // 订单2 - 意式厨房
        Order order2 = new Order(
                "202310101845002",
                "意式厨房",
                new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000), // 3天前
                Order.OrderStatus.COMPLETED,
                158.00
        );
        order2.addItem(new OrderItem("玛格丽特披萨", 78.00, 1, "经典玛格丽特披萨"));
        order2.addItem(new OrderItem("提拉米苏", 42.00, 1, "正宗意式提拉米苏"));
        order2.addItem(new OrderItem("凯撒沙拉", 38.00, 1, "新鲜凯撒沙拉"));
        orders.add(order2);

        // 订单3 - 闪电汉堡
        Order order3 = new Order(
                "202310151200003",
                "闪电汉堡",
                new Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000), // 1天前
                Order.OrderStatus.COMPLETED,
                89.00
        );
        order3.addItem(new OrderItem("经典芝士牛肉汉堡", 45.00, 1, "经典美式汉堡"));
        order3.addItem(new OrderItem("薯条", 18.00, 1, "黄金薯条"));
        order3.addItem(new OrderItem("可乐", 8.00, 1, "冰爽可乐"));
        order3.addItem(new OrderItem("巧克力奶昔", 18.00, 1, "浓郁巧克力奶昔"));
        orders.add(order3);

        // 订单4 - 中华小馆（最近的订单）
        Order order4 = new Order(
                "202310161830004",
                "中华小馆",
                new Date(System.currentTimeMillis() - 5 * 60 * 60 * 1000), // 5小时前
                Order.OrderStatus.DELIVERING,
                89.00
        );
        order4.addItem(new OrderItem("宫保鸡丁", 48.00, 1, "经典川菜"));
        order4.addItem(new OrderItem("扬州炒饭", 25.00, 1, "正宗扬州炒饭"));
        order4.addItem(new OrderItem("酸梅汤", 8.00, 2, "清凉解暑"));
        orders.add(order4);
    }

    /**
     * 创建新订单
     */
    public Order createOrder(String restaurantName, List<CartItem> cartItems) {
        // 生成订单号（时间戳 + 随机数）
        String orderId = String.format("%d%03d",
            System.currentTimeMillis() / 1000,
            (int)(Math.random() * 1000));

        // 计算总金额
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotalPrice();
        }

        // 创建订单
        Order order = new Order(
            orderId,
            restaurantName,
            new Date(),
            Order.OrderStatus.PAID,
            totalAmount
        );

        // 添加订单项
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(cartItem);
            order.addItem(orderItem);
        }

        // 保存订单
        orders.add(order);

        return order;
    }

    /**
     * 获取所有订单（按时间倒序）
     */
    public List<Order> getAllOrders() {
        // 创建副本并排序
        List<Order> sortedOrders = new ArrayList<>(orders);
        Collections.sort(sortedOrders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                // 时间倒序（最新的在前）
                return o2.getOrderTime().compareTo(o1.getOrderTime());
            }
        });
        return sortedOrders;
    }

    /**
     * 根据订单ID获取订单
     */
    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    /**
     * 更新订单状态
     */
    public void updateOrderStatus(String orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
        }
    }

    /**
     * 取消订单
     */
    public boolean cancelOrder(String orderId) {
        Order order = getOrderById(orderId);
        if (order != null && order.getStatus() == Order.OrderStatus.PENDING) {
            order.setStatus(Order.OrderStatus.CANCELLED);
            return true;
        }
        return false;
    }

    /**
     * 清空所有订单
     */
    public void clearOrders() {
        orders.clear();
    }

    /**
     * 获取订单数量
     */
    public int getOrderCount() {
        return orders.size();
    }
}
