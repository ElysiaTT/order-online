package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * 订单详情页面
 */
public class OrderDetailActivity extends AppCompatActivity {

    private TextView restaurantNameText;
    private TextView orderStatusText;
    private TextView orderIdText;
    private TextView orderTimeText;
    private LinearLayout itemsContainer;
    private TextView subtotalText;
    private TextView deliveryFeeText;
    private TextView totalAmountText;
    private Button reorderButton;

    private OrderManager orderManager;
    private Order order;

    // 配送费（固定）
    private static final double DELIVERY_FEE = 5.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // 设置标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("订单详情");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        orderManager = OrderManager.getInstance();

        // 获取订单ID
        String orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) {
            Toast.makeText(this, "订单不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 获取订单详情
        order = orderManager.getOrderById(orderId);
        if (order == null) {
            Toast.makeText(this, "订单不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadOrderDetail();
        setupClickListener();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        restaurantNameText = findViewById(R.id.restaurantNameText);
        orderStatusText = findViewById(R.id.orderStatusText);
        orderIdText = findViewById(R.id.orderIdText);
        orderTimeText = findViewById(R.id.orderTimeText);
        itemsContainer = findViewById(R.id.itemsContainer);
        subtotalText = findViewById(R.id.subtotalText);
        deliveryFeeText = findViewById(R.id.deliveryFeeText);
        totalAmountText = findViewById(R.id.totalAmountText);
        reorderButton = findViewById(R.id.reorderButton);
    }

    /**
     * 加载订单详情
     */
    private void loadOrderDetail() {
        // 基本信息
        restaurantNameText.setText(order.getRestaurantName());
        orderIdText.setText(order.getOrderId());
        orderTimeText.setText(order.getFormattedOrderTime());

        // 订单状态
        orderStatusText.setText(order.getStatus().getDisplayName());
        orderStatusText.setTextColor(getStatusColor(order.getStatus()));

        // 清空之前的菜品项
        itemsContainer.removeAllViews();

        // 添加菜品项
        for (OrderItem item : order.getItems()) {
            View itemView = createItemView(item);
            itemsContainer.addView(itemView);
        }

        // 金额详情
        subtotalText.setText(order.getFormattedTotalAmount());
        deliveryFeeText.setText(String.format(Locale.getDefault(), "¥%.2f", DELIVERY_FEE));

        double totalWithDelivery = order.getTotalAmount() + DELIVERY_FEE;
        totalAmountText.setText(String.format(Locale.getDefault(), "¥%.2f", totalWithDelivery));
    }

    /**
     * 创建菜品项视图
     */
    private View createItemView(OrderItem item) {
        View view = LayoutInflater.from(this).inflate(
            android.R.layout.simple_list_item_2, itemsContainer, false);

        TextView text1 = view.findViewById(android.R.id.text1);
        TextView text2 = view.findViewById(android.R.id.text2);

        // 菜品名称和数量
        text1.setText(item.getFoodName() + " × " + item.getQuantity());
        text1.setTextSize(16);
        text1.setTextColor(0xFF333333);

        // 单价和总价
        String priceInfo = String.format(Locale.getDefault(),
            "单价：%s    小计：%s",
            item.getFormattedPrice(),
            item.getFormattedTotalPrice());
        text2.setText(priceInfo);
        text2.setTextSize(14);
        text2.setTextColor(0xFF666666);

        // 设置内边距
        view.setPadding(0, 16, 0, 16);

        return view;
    }

    /**
     * 设置点击监听器
     */
    private void setupClickListener() {
        reorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reorder();
            }
        });
    }

    /**
     * 再次购买
     */
    private void reorder() {
        ShoppingCart cart = ShoppingCart.getInstance();

        // 清空当前购物车
        cart.clear();

        // 将订单中的商品添加到购物车
        for (OrderItem orderItem : order.getItems()) {
            // 创建FoodItem
            FoodItem foodItem = new FoodItem(orderItem.getFoodName(), orderItem.getDescription(), orderItem.getPrice(), "" // category"" // imageUrl
            );

            // 添加到购物车
            for (int i = 0; i < orderItem.getQuantity(); i++) {
                cart.addItem(foodItem);
            }
        }

        Toast.makeText(this,
            "已将 " + order.getItems().size() + " 种商品加入购物车",
            Toast.LENGTH_SHORT).show();

        // 打开购物车页面
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    /**
     * 获取订单状态对应的颜色
     */
    private int getStatusColor(Order.OrderStatus status) {
        switch (status) {
            case PENDING:
                return 0xFFFF9800; // 橙色
            case PAID:
                return 0xFF2196F3; // 蓝色
            case PREPARING:
                return 0xFF9C27B0; // 紫色
            case DELIVERING:
                return 0xFF00BCD4; // 青色
            case COMPLETED:
                return 0xFF4CAF50; // 绿色
            case CANCELLED:
                return 0xFF757575; // 灰色
            default:
                return 0xFF333333;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
