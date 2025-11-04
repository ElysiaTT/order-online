package com.example.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 修改密码页面
 */
public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPasswordInput;
    private EditText newPasswordInput;
    private EditText confirmPasswordInput;
    private Button confirmButton;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // 设置标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("修改密码");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userManager = UserManager.getInstance();

        initViews();
        setupClickListener();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        oldPasswordInput = findViewById(R.id.oldPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        confirmButton = findViewById(R.id.confirmButton);
    }

    /**
     * 设置点击监听器
     */
    private void setupClickListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    /**
     * 修改密码
     */
    private void changePassword() {
        String oldPassword = oldPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "新密码长度至少6位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "请确认新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (oldPassword.equals(newPassword)) {
            Toast.makeText(this, "新密码不能与旧密码相同", Toast.LENGTH_SHORT).show();
            return;
        }

        // 调用UserManager修改密码
        boolean success = userManager.changePassword(oldPassword, newPassword);

        if (success) {
            Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            oldPasswordInput.setText("");
            newPasswordInput.setText("");
            confirmPasswordInput.setText("");
            // 返回上一页
            finish();
        } else {
            Toast.makeText(this, "旧密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            oldPasswordInput.setText("");
            oldPasswordInput.requestFocus();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
