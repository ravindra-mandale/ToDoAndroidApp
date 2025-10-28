package com.programminginmyway.todoappnew.data.database.roomDb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.programminginmyway.todoappnew.data.database.roomDb.dao.TaskDao
import com.programminginmyway.todoappnew.data.database.USER_TODO_DB
import com.programminginmyway.todoappnew.data.database.roomDb.dao.UserDao
import com.programminginmyway.todoappnew.data.database.roomDb.model.TaskList
import com.programminginmyway.todoappnew.data.database.roomDb.model.UsersNew
import com.programminginmyway.todoappnew.data.database.OurConverters

@Database(entities = [UsersNew::class, TaskList::class],
        version = 2,
        exportSchema = false)  // ✅ easiest fix)
@TypeConverters(OurConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao() : TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    USER_TODO_DB
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = tempInstance
                tempInstance
            }
        }

        // Migration rule for adding datetime column- version 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE task ADD COLUMN dateTime INTEGER NOT NULL DEFAULT 0")
            }
        }

    }
}