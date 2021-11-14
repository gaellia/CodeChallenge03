package com.launchpad.codechallenge03.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.launchpad.codechallenge03.models.Animal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

@Database(
    entities = [Animal::class],
    version = 2
)
abstract class DB: RoomDatabase(){
    abstract fun animalDao(): AnimalDao

    companion object{
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, DB::class.java, "codeChallenge03.db")
                .fallbackToDestructiveMigration()
                .addCallback(object: Callback(){
                    override fun onCreate(sqLiteDatabase: SupportSQLiteDatabase) {
                        super.onCreate(sqLiteDatabase)
                        CoroutineScope(Dispatchers.IO).launch {
                            val db: DB by inject(DB::class.java)
                            db.animalDao().upsert(
                                Animal(
                                    id = "${System.currentTimeMillis()}",
                                    name = "Mouse",
                                    description = "A small rodent that lives in open fields, it may decide to live in your home.",
                                    timeStamp = System.currentTimeMillis(),
                                    type = Animal.Type.LAND,
                                    size = Animal.Size.SMALL
                                )
                            )
                        }
                    }
                })
                .build()
    }
}