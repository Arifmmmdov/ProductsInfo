package com.kaplan57.azerimedtask.repository

import android.content.Context
import android.os.Looper
import android.util.Log
import com.kaplan57.azerimedtask.listener.DataCallBackListener
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity
import com.kaplan57.azerimedtask.local_db.roomdatabase.PhonesAppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.logging.Handler


object PhonesRepository {

    fun getAllData(mContext: Context, onDataEmptyListener: DataCallBackListener?, onDataExistsListener:DataCallBackListener ) {
        GlobalScope.launch {
            Log.d("aaaaa", "getAllData: ")
            val result = PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().getAll()
            Log.d("aaaaa", "getAllData: $result")


            android.os.Handler(Looper.getMainLooper()).post {
                if (result.isEmpty())
                    onDataEmptyListener?.let {
                        it.invoke(result)
                    }
                else
                    onDataExistsListener.invoke(result)
            }

        }

    }


    suspend fun setAllData(mContext: Context, list: List<PhonesEntity>) {
        for (a in list)
            PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().insertData(a)
    }

    fun getResearchedData(mContext: Context,text:String, onDataCallBackListener:DataCallBackListener ){
        GlobalScope.launch {
            val result = PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().getResearchedData(text)

            onDataCallBackListener.invoke(result)
        }
    }

    fun deleteAll(mContext: Context){
        GlobalScope.launch{
            PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().deleteAll()
        }
    }

}