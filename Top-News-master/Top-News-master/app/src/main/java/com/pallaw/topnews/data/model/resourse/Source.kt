package com.pallaw.topnews.data.model.resourse

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
@Entity(tableName = "source")
data class Source(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String = "",

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = ""
)