/*
package com.example.itemsdatabasestartup;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

public class RoomHelper {




    @Entity
    public class DailySaleTally
    {
        @PrimaryKey
        @ColumnInfo(name = "Date")
        public String Date;

        @ColumnInfo(name = "Total_Sale")
        public Double TotalSale;


    }



    @Dao
    public interface UserDao {
        @Query("SELECT * FROM DailySaleTally")
        List<DailySaleTally> getAll();

        @Query("SELECT * FROM DailySaleTally WHERE Date IN (:userIds)")
        List<DailySaleTally> loadAllByIds(int[] userIds);

       */
/* @Query("SELECT * FROM DailySaleTally WHERE Total_Sale LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        DailySaleTally findByName(String first, String last);
*//*

        @Insert
        void insertAll(DailySaleTally... users);

        @Delete
        void delete(DailySaleTally user);
    }

    @Database(entities = {DailySaleTally.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract UserDao userDao();
    }
}
*/
