package com.omniwyse.migration.roomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.omniwyse.migration.User;

import java.util.List;

/**
 * Created by Chandra Kant on 19/12/18.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where uName LIKE  :firstName AND uContact LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
