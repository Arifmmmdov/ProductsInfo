package com.kaplan57.azerimedtask.repository

import android.content.Context
import android.os.Looper
import android.util.Log
import com.kaplan57.azerimedtask.listener.DataCallBackListener
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity
import com.kaplan57.azerimedtask.local_db.roomdatabase.PhonesAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.logging.Handler


object PhonesRepository {

    fun getAllData(
        mContext: Context,
        onDataEmptyListener: DataCallBackListener?,
        onDataExistsListener: DataCallBackListener,
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("aaaaa", "getAllData: ")
            val result = PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().getAll()
            Log.d("aaaaa", "getAllData: $result")

            withContext(Dispatchers.Main) {
                if (result.isEmpty())
                    onDataEmptyListener?.let {
                        it.invoke(result)
                    }
                else
                    onDataExistsListener.invoke(result)
            }

        }

    }


    fun setAllData(mContext: Context, list: List<PhonesEntity>) {
        GlobalScope.launch {
            for (a in list)
                PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().insertData(a)
        }
    }

    fun getResearchedData(
        mContext: Context,
        text: String,
        onDataCallBackListener: DataCallBackListener,
    ) {
        GlobalScope.launch {
            val result =
                PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().getResearchedData(text)
            Log.d("aaaaa", "getResearchedData: $result")
            withContext(Dispatchers.Main) {
                onDataCallBackListener.invoke(result)
            }
        }

    }

    fun deleteAll(mContext: Context) {
        GlobalScope.launch {
            PhonesAppDatabase.getDatabaseInstance(mContext).phonesDao().deleteAll()
        }
    }

}