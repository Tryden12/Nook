package com.tryden.nook.di

import android.app.Application
import androidx.room.Room
import com.tryden.nook.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module /** Turns this into a Dagger module. **/
@InstallIn(SingletonComponent::class) /** Auto generated class **/
object AppModule {

    @Provides /** Tells Dagger this is an instruction function. **/
    @Singleton /** Tells Dagger to only create one instance of the db. **/
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "nook-database")
        // tells Room what it should do when we update our db schema,
        // but didn't provide a proper migration strategy
        // In this case, it will drop the table(s) and create new one(s).
        .fallbackToDestructiveMigration()
        .build()


    /** Later, when we need a Dao, Dagger will look where we can get such a Dao.
     *  @Provides tells dagger this is how we can provide such a Dao.
     *  @param db can easily be found by Dagger because of the instruction function above.
     */
    @Provides
    fun providePriorityItemEntityDao(db: AppDatabase) = db.priorityItemEntityDao()

    @Provides
    fun provideChecklistItemEntityDao(db: AppDatabase) = db.checklistItemEntityDao()

    @Provides
    fun provideFolderItemEntityDao(db: AppDatabase) = db.folderItemEntityDao()

    @Provides
    fun provideNoteItemEntityDao(db: AppDatabase) = db.noteItemEntityDao()


    /** Coroutine scope that lives in the application as long as the application lives.
     *  We let Dagger provide this, so we can inject it where ever we need,
     *  and we don't have to worry about it.
     *  @param SupervisorJob prevents the entire coroutine from cancelling if a child within the
     *  coroutine fails.
     *  ** (In a coroutine, if one job (child) fails, the coroutine cancels the other children) **
     *
     *  @ApplicationScope Tells Dagger that this is NOT just any coroutine scope,
     *  it's the application scope!
     *  When we use it inside our AppDatabase().Callback(), it is explicit.
     *
     *  @return CoroutineScope()
     */
//    @ApplicationScope
//    @Provides
//    @Singleton
//    fun applicationScope() = CoroutineScope(SupervisorJob())

}
