package com.kaplan57.azerimedtask.local_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "phones_table")
data class PhonesEntity(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @SerializedName("title")
    val phonesName: String,
    @SerializedName("description")
    val phonesDescription: String,
    @SerializedName("thumbnail")
    val phonesImages: String,
)

data class Products(
    @SerializedName("products")
    val list:List<PhonesEntity>
)