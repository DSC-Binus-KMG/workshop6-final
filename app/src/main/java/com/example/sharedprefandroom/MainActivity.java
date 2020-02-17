package com.example.sharedprefandroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferencesConfig preferencesConfig;
    private EditText UserName;
    public static MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesConfig = new SharedPreferencesConfig((getApplicationContext()));
        myDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "userdb").allowMainThreadQueries().build();

        if(preferencesConfig.readLoginStatus()){
            startActivity(new Intent(this, SuccessActivity.class));
            finish();
        }

        UserName = findViewById(R.id.username);

        Button btn = findViewById(R.id.button);
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = UserName.getText().toString();
                List<User> users = myDatabase.myDao().getUsers();
                if(users.size() > 0){
                    String receivedUsername = "";
                    boolean flag = false;
                    for(User user:users) {
                        receivedUsername = user.getUsername();
                        if (receivedUsername.equals(textUsername)) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        startActivity(new Intent(view.getContext(), SuccessActivity.class));
                        preferencesConfig.writeLoginStatus(true);
                        preferencesConfig.writeLoginUsername(textUsername);
                        finish();
                    }
                    else{
                        Toast.makeText(view.getContext(), "Username was not found!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    User user = new User();
                    user.setId(1);
                    user.setUsername(textUsername);
                    myDatabase.myDao().addUser(user);
                    Toast.makeText(view.getContext(), "New username created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(view.getContext(), SuccessActivity.class));
                    preferencesConfig.writeLoginStatus(true);
                    preferencesConfig.writeLoginUsername(textUsername);
                    finish();
                }
            }
        };
        btn.setOnClickListener(btnClick);
    }
}
