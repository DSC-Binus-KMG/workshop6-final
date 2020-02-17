package com.example.sharedprefandroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    private SharedPreferencesConfig preferencesConfig;
    public static MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        TextView tv = findViewById(R.id.text);
        tv.setText(tv.getText().toString() + preferencesConfig.readLoginUsername());

        Button btn = findViewById(R.id.btnlogout);
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencesConfig.writeLoginStatus(false);
                preferencesConfig.writeLoginUsername("");
                startActivity(new Intent(view.getContext(), MainActivity.class));
                finish();
            }
        };
        btn.setOnClickListener(btnClick);

        myDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "userdb").allowMainThreadQueries().build();
        Button addBtn = findViewById(R.id.btnadd);
        final EditText addText = findViewById(R.id.usertext);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = addText.getText().toString();
                List<User> users = myDatabase.myDao().getUsers();

                User tempUser = users.get(users.size()-1);
                User user = new User();
                user.setId(tempUser.getId()+1);
                user.setUsername(username);

                myDatabase.myDao().addUser(user);
                addText.setText("");
                Toast.makeText(view.getContext(), "Successfully added user!", Toast.LENGTH_LONG).show();
            }
        });

        Button delBtn = findViewById(R.id.btndel);
        final EditText deleteText = findViewById(R.id.userdelete);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = deleteText.getText().toString();
                List<User> users = myDatabase.myDao().getUsers();
                boolean flag = false;

                User tempUser = new User();
                for(User usr:users){
                    if(usr.getUsername().equals(username)){
                        tempUser = usr;
                        flag=true;
                        break;
                    }
                }

                if(flag){
                    deleteText.setText("");
                    myDatabase.myDao().deleteUser(tempUser);
                    if(tempUser.getUsername().equals(preferencesConfig.readLoginUsername())){
                        preferencesConfig.writeLoginStatus(false);
                        preferencesConfig.writeLoginUsername("");
                        startActivity(new Intent(view.getContext(), MainActivity.class));
                        finish();
                    }
                    Toast.makeText(view.getContext(), "Successfully deleted user!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(view.getContext(), "Cannot find user!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
