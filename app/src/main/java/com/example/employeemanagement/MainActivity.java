package com.example.employeemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.employeemanagement.adapter.EmployeeAdapter;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.retrofit.APIClient;
import com.example.employeemanagement.retrofit.APIService;
import com.example.employeemanagement.retrofit.APIUtils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button btnSearch;
    private EditText txtSearch;
    private ListView lvEmployees;

    APIService apiService;
    private ArrayList<User> arrEmployees;
    private EmployeeAdapter employeeAdapter;

    User userSeleted;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //currentUser = (User) this.getIntent().getSerializableExtra("CURRENT_USER");
        SharedPreferences sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        currentUser = new Gson().fromJson(sharedPreferences.getString("current_user", ""), User.class);
        apiService = APIUtils.getServer();
        initControls();
        addEvents();
        registerForContextMenu(lvEmployees);
        loadData();
    }

    private void addEvents() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtSearch.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your search text!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<User> arrResults = new ArrayList<>();

                String searchContent = txtSearch.getText().toString().trim().toLowerCase();
                for (User user : arrEmployees) {
                    String name = user.getName().toLowerCase();
                    if (name.indexOf(searchContent) >= 0) {
                        arrResults.add(user);
                    }
                }

                if (arrResults.size() > 0) {
                    EmployeeAdapter resultAdapter = new EmployeeAdapter(MainActivity.this, R.layout.employee_item, arrResults);
                    lvEmployees.setAdapter(resultAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "No results were found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvEmployees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                userSeleted = arrEmployees.get(position);
                return false;
            }
        });
    }

    private void initControls() {
        btnSearch = findViewById(R.id.btnSearch);
        txtSearch = findViewById(R.id.txtSearch);
        lvEmployees = findViewById(R.id.lvEmployees);
    }

    private void loadData() {
        apiService.getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                arrEmployees = response.body();
                employeeAdapter = new EmployeeAdapter(MainActivity.this, R.layout.employee_item, arrEmployees);
                lvEmployees.setAdapter(employeeAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error - Get all users" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuCall:
                callTo(userSeleted);
                break;
            case R.id.menuMessage:
                messageTo(userSeleted);
                break;
            case R.id.menuEdit:
                editInfo(userSeleted);
                break;
            case R.id.menuDelete:
                delEmployee(userSeleted);
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menuUser:
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("MY_INFO", currentUser);
                startActivity(intent);
                break;
            case R.id.menuLogout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void delEmployee(final User userSeleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                apiService.delEmployee(userSeleted.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Success")){
                            Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Something when wrong - " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ERR", "onFailure: " + t.toString() );
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void editInfo(User userSeleted) {
        Intent intent = new Intent(MainActivity.this, EditInfoActivity.class);
        intent.putExtra("USER_UPDATE_INFO", (Serializable) userSeleted);
        startActivity(intent);
    }

    private void messageTo(User userSeleted) {
        String sms = "smsto: " + userSeleted.getPhone();
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(sms)));
    }

    private void callTo(User userSeleted) {
        String dial = "tel:" + userSeleted.getPhone();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }
}
