package com.tryden.nook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tryden.nook.database.dao.ItemEntityDao
import com.tryden.nook.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) return appDatabase!!

            appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                name = "nook-database"
            )
//                .addMigrations(MIGRATION_1_2())  ** EXAMPLE **
                .build()
            return appDatabase!!
        }
    }

    // Class for migrating database versions
    class MIGRATION_1_2: Migration(1,2) {
        override fun migrate(database: SupportSQLiteDatabase) {
//            Example:
//            database.execSQL("CREATE TABLE IF NOT EXISTS `category_entity` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
        }
    }

    abstract fun itemEntityDao(): ItemEntityDao

}