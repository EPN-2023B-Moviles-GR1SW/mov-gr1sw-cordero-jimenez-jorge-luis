package com.barryzea.nilopartner.pojo

import com.google.firebase.firestore.Exclude


data class Message(@get:Exclude var id:String ="",
                   var message:String="",
                   var sender:String ="",
                   @get:Exclude var myUid:String="")
{
    @Exclude
    fun isSenderByMe():Boolean = sender == myUid
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
