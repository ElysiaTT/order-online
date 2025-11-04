package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 订单历史页面
 */
public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private LinearLayout emptyView;
    private OrderAdapter orderAdapter;

    private OrderManager orderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // 设置标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("订单历史");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        orderManager = OrderManager.getInstance();

        initViews();
        loadOrders();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        emptyView = findViewById(R.id.emptyView);

        // 设置RecyclerView
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 加载订单列表
     */
    private void loadOrders() {
        // 获取所有订单（按时间倒序）
        List<Order> orders = orderManager.getAllOrders();

        if (orders.isEmpty()) {
            // 显示空状态
            orderRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            // 显示订单列表
            orderRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            // 设置适配器
            orderAdapter = new OrderAdapter(this, orders);
            orderRecyclerView.setAdapter(orderAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面重新显示时刷新订单列表
        loadOrders();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
