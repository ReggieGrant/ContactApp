package com.example.contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database declares this class as the Room database
// entities: list of all table classes in this database
// Contact::class is a Kotlin class reference — Room reads it to know which tables exist
// Add more classes separated by commas if you add more tables in the future
// version: the current schema version — start at 1
// Increment this number every time you change the structure of Contact.kt
// Room will crash intentionally if the version does not match — protecting existing data
// exportSchema: false for learning apps — set true in production to track schema history
@Database(entities = [Contact::class], version = 1, exportSchema = false)

// abstract because Room generates the full implementation at compile time
// Must extend RoomDatabase — Room requires this as the base class for all databases
// abstract class is used instead of interface because RoomDatabase is a class not an interface
abstract class ContactDatabase : RoomDatabase() {

    // abstract tells Room to generate the body of this function automatically
    // This is the only way the rest of the app accesses the database operations
    // The ViewModel will call this to get the ContactDao instance
    abstract fun contactDao(): ContactDao

    // companion object holds the Singleton pattern implementation
    // Members here belong to the class itself not to any instance
    // This is Kotlin's equivalent of Java's static members
    // getDatabase() lives here because it must be called before any instance exists
    companion object {

// INSTANCE holds the one and only database connection for the entire app
// Starts as null — no database has been created yet
// Private means only this companion object can set its value
// @Volatile ensures every thread always reads this value from main memory
// Without @Volatile a thread could read a stale null from its CPU cache

        // and try to create a second database instance — breaking the Singleton
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        // The single entry point to the database for the entire app
        // Every part of the app calls ContactDatabase.getDatabase(context)
        // Nobody ever instantiates ContactDatabase directly
        // context is needed by Room to create and locate the database file on disk
        fun getDatabase(context: Context): ContactDatabase {

            // If INSTANCE already exists return it immediately — fast path
            // The ?: operator means "if null execute the right side"
            // This avoids entering the synchronized block on every call
            return INSTANCE ?: synchronized(this) {

                // synchronized(this) is the Singleton lock
                // Only one thread can execute this block at a time
                // Even if two threads both saw INSTANCE == null simultaneously
                // only one enters this block — the other waits
                // When the waiting thread enters it finds INSTANCE already set

                val instance = Room.databaseBuilder(
                    // applicationContext prevents memory leaks
                    // Using an Activity context here would keep the Activity
                    // in memory even after it is destroyed
                    context.applicationContext,

                    // ContactDatabase::class.java is a Java class reference
                    // The .java converts the Kotlin class reference to Java
                    // Room's databaseBuilder was written in Java and requires this format
                    ContactDatabase::class.java,

                    // The actual filename of the database file stored on the device
                    // Visible in Android Studio under Device Explorer → data/data/your.package/databases/
                    "contacts_database"
                ).build()

                // Store the new instance — all future calls return this directly
                // and skip the synchronized block entirely
                INSTANCE = instance

                // Return the newly created instance
                instance
            }
        }
    }
}