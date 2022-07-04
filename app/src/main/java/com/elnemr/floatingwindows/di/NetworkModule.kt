package com.elnemr.floatingwindows.di

import android.content.Context
import androidx.room.Room
import com.elnemr.floatingwindows.db.NoteDao
import com.elnemr.floatingwindows.db.NoteDatabase
import com.elnemr.floatingwindows.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, Constants.DATABASE_NAME)
            // solves the problem with the concurrent changes to the same database.
//            .enableMultiInstanceInvalidation()
            .build()

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()
}