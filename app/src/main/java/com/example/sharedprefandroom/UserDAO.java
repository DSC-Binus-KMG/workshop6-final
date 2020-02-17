package com.example.sharedprefandroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public void addUser(User user);
//    public User getUser();
    @Query("select * from users")
    public List<User> getUsers();
    @Delete
    public void deleteUser(User user);
    @Update
    public void updateUser(User user);
}
