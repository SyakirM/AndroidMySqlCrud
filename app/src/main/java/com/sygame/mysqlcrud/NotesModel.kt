package com.sygame.mysqlcrud

import java.io.Serializable

data class NotesModel (var notes: ArrayList<Data>) {
    data class Data(var id: String?, var note: String?): Serializable
}