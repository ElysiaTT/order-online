package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 订单列表适配器
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.restaurantNameText.setText(order.getRestaurantName());
        holder.orderIdText.setText("订单号：" + order.getOrderId());
        holder.orderTimeText.setText(order.getFormattedOrderTime());
        holder.totalAmountText.setText(order.getFormattedTotalAmount());

        // 设置订单状态和颜色
        holder.orderStatusText.setText(order.getStatus().getDisplayName());
        holder.orderStatusText.setTextColor(getStatusColor(order.getStatus()));

        // 查看详情按钮
        holder.viewDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", order.getOrderId());
                context.startActivity(intent);
            }
        });

        // 再次购买按钮
        holder.reorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reorder(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
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

    /**
     * 再次购买
     */
    private void reorder(Order order) {
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

        Toast.makeText(context,
            "已将 " + order.getItems().size() + " 种商品加入购物车",
            Toast.LENGTH_SHORT).show();

        // 打开购物车页面
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }

    /**
     * 更新订单列表
     */
    public void updateOrders(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameText;
        TextView orderIdText;
        TextView orderStatusText;
        TextView orderTimeText;
        TextView totalAmountText;
        Button viewDetailButton;
        Button reorderButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameText = itemView.findViewById(R.id.restaurantNameText);
            orderIdText = itemView.findViewById(R.id.orderIdText);
            orderStatusText = itemView.findViewById(R.id.orderStatusText);
            orderTimeText = itemView.findViewById(R.id.orderTimeText);
            totalAmountText = itemView.findViewById(R.id.totalAmountText);
            viewDetailButton = itemView.findViewById(R.id.viewDetailButton);
            reorderButton = itemView.findViewById(R.id.reorderButton);
        }
    }
}
