package com.example.employeemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.employeemanagement.model.User;
import com.example.employeemanagement.retrofit.APIService;
import com.example.employeemanagement.retrofit.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInfoActivity extends AppCompatActivity {
    private User user;
    private EditText txtNewName, txtNewPhone, txtNewPostion, txtNewDepartment;
    private Button btnUpdateInfo;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initControlls();
        addEvents();
        apiService = APIUtils.getServer();
        user = (User) getIntent().getSerializableExtra("USER_UPDATE_INFO");
        loadData2Form();
    }

    private void loadData2Form() {
        txtNewName.setText(user.getName());
        txtNewPhone.setText(user.getPhone());
        txtNewPostion.setText(user.getPosition());
        txtNewDepartment.setText(user.getDepartment());
    }

    private void addEvents() {
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = txtNewName.getText().toString().trim();
                String newPhone = txtNewPhone.getText().toString().trim();
                String newPosition = txtNewPostion.getText().toString();
                String newDepartment = txtNewDepartment.getText().toString().trim();
                if (TextUtils.isEmpty(newName)) {
                    Toast.makeText(EditInfoActivity.this, "Name is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(newPosition)) {
                    Toast.makeText(EditInfoActivity.this, "Position is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(newDepartment)) {
                    Toast.makeText(EditInfoActivity.this, "Department is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                apiService.updateInfo(user.getId(),
                        newName,
                        newPhone,
                        newPosition,
                        newDepartment).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Success")){
                            Toast.makeText(EditInfoActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditInfoActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(EditInfoActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(EditInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void initControlls() {
        txtNewName = findViewById(R.id.txtNameUpdate);
        txtNewPhone = findViewById(R.id.txtPhoneUpdate);
        txtNewPostion = findViewById(R.id.txtPositionUpdate);
        txtNewDepartment = findViewById(R.id.txtDepartmentUpdate);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
    }
}
