package com.example.employeemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.employeemanagement.R;
import com.example.employeemanagement.model.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<User> {
    private Context context;
    private int layout;
    private ArrayList<User> arrUsers;

    public EmployeeAdapter(Context context, int layout, ArrayList<User> arrUsers) {
        super(context, layout, arrUsers);
        this.context = context;
        this.layout = layout;
        this.arrUsers = arrUsers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtPhone = convertView.findViewById(R.id.txtPhone);
            viewHolder.txtPosition = convertView.findViewById(R.id.txtPosition);
            viewHolder.txtDepartment = convertView.findViewById(R.id.txtDepartment);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = arrUsers.get(position);
        viewHolder.txtName.setText("Name: " + user.getName());
        viewHolder.txtPhone.setText("Phone: " + user.getPhone());
        viewHolder.txtPosition.setText("Position: " + user.getPosition());
        viewHolder.txtDepartment.setText("Department: " + user.getDepartment());
        return convertView;
    }

    public class ViewHolder{
        TextView txtName, txtPhone, txtPosition, txtDepartment;
    }
}
