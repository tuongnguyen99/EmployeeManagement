package com.example.employeemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeemanagement.model.User;

public class UserInfoActivity extends AppCompatActivity {
    User me;
    private TextView txtName, txtPhone, txtPosition, txtDepartment;
    private Button btnUpdate, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        me = (User) this.getIntent().getSerializableExtra("MY_INFO");
        initControls();
        addEvents();
        loadFormData();

    }

    private void loadFormData() {
        txtName.setText("Name: "+ me.getName());
        txtPhone.setText("Phone: " + me.getPhone());
        txtPosition.setText("Position: " + me.getPosition());
        txtDepartment.setText("Department: " + me.getDepartment());
    }

    private void addEvents() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                intent.putExtra("CURRENT_USER", me);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, EditInfoActivity.class);
                intent.putExtra("USER_UPDATE_INFO", me);
                startActivity(intent);
            }
        });
    }

    private void initControls() {
        txtName = findViewById(R.id.infoName);
        txtPhone = findViewById(R.id.infoPhone);
        txtPosition = findViewById(R.id.infoPosition);
        txtDepartment = findViewById(R.id.infoDepartment);

        btnUpdate = findViewById(R.id.btnSelfUpdate);
        btnHome = findViewById(R.id.btnHome);
    }
}
