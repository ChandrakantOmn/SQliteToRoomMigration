package com.omniwyse.migration

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by AB on 7/12/2017.
 */

@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var uId : Long? = 0
    @ColumnInfo(name = "uContact")
    var uContact: String? = null
    @ColumnInfo(name = "uName")
    var uName: String? = null

    constructor(uId: Long?) {
        this.uId = uId
    }

    @Ignore
    constructor(id: Long, name: String, number: String) {
        this.uId = id
        this.uName = name
        this.uContact = number
    }

    constructor(wonderman: String, s: String) {
        this.uName = wonderman
        this.uContact = s
    }


}
