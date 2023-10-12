package com.example.workreminders
import android.content.Context
import android.net.Uri


// uri bài quản lý công việc: content://com.example.workmanagement.MyContentProvider/works

data class Work(val id: Int, val workName: String, val workTime: String)

fun getDataFromProvider(context: Context): List<Work> {
    val workList = mutableListOf<Work>()
    val projection = arrayOf("ID", "Name", "Time")

    val uriLink = "content://com.example.workmanagement.MyContentProvider/works"
    val uri = Uri.parse(uriLink)

    context.contentResolver.query(uri, projection, null, null, null)
        .use{ cursor ->         // use{} sẽ tự close cho cursor
            while (cursor!!.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                val wname = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
                val wtime = cursor.getString(cursor.getColumnIndexOrThrow("Time"))
                val work = Work(id, wname, wtime)
                workList.add(work)
            }
    }
    return workList
}
