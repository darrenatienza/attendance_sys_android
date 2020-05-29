package com.batstateu_ros_cpt.attendancesystem2.library.data.base;

import android.database.sqlite.SQLiteDatabase;

import com.javahelps.externalsqliteimporter.ExternalSQLiteOpenHelper;

public class DatabaseAccess {
    protected ExternalSQLiteOpenHelper openHelper;
    protected SQLiteDatabase database;


    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


}
