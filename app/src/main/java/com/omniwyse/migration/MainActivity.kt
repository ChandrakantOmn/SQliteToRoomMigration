package com.omniwyse.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.omniwyse.migration.sqlitehlper.UserDbHelper
import com.omniwyse.migration.roomdb.AppDatabase
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        val userDbHelper = UserDbHelper(this)
        userDbHelper.addUser(User("11Hulk", "11-445-9999"));
        userDbHelper.addUser(User("11Dominic", "11-445-9999"));
        userDbHelper.addUser(User("11Thor", "11-445-9999"));
        userDbHelper.addUser(User("11Batwoman", "11-445-9999"));
        userDbHelper.addUser(User("11Wonderman", "11-445-9999"));
        Log.v("TAG", "DB Created using SQLiteOpenHelper and 5 users added");

        val MIGRATION_1_2 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val version = database.version
                Log.e("Old version", version.toString() + "")
            }
        }


//create db


        val userDB = Room.databaseBuilder(this, AppDatabase::class.java, "userDB")
                .addMigrations(MIGRATION_1_2)
                .build()

        populateWithTestData(userDB)

    }

    private fun addUser(db: AppDatabase, user: User): User {
        db.userDao().insertAll(user)
        return user
    }

    private fun populateWithTestData(db: AppDatabase) {
        val user = User(null)
        user.uName = "Ajay"
        user.uContact = "86000585555"
//        addUser(db, user)
//

        thread(start = true) {
            var list = db.userDao().all
            list.forEach {
                Log.e("TAG", it.uName)
            }
        }
    }
}
