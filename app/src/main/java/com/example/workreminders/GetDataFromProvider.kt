package com.example.workreminders
import android.content.Context
import android.net.Uri


// uri bài quản lý công việc: content://com.example.workmanagement.MyContentProvider/works

data class Work(val id: Int, val workName: String, val workTime: String)

fun getDataFromProvider(context: Context): List<Work> {
    val workList = mutableListOf<Work>()
    val projection = arrayOf("id", "work_name", "work_time")

    val uriLink = "content://com.example.workmanagement.MyContentProvider/works"
    val uri = Uri.parse(uriLink)

    context.contentResolver.query(uri, projection, null, null, null)
        .use{ cursor ->         // use{} sẽ tự close cho cursor
            while (cursor!!.moveToNext()){
                // các cột truy vấn phải khớp với tên trong db
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val wname = cursor.getString(cursor.getColumnIndexOrThrow("work_name"))
                val wtime = cursor.getString(cursor.getColumnIndexOrThrow("work_time"))
                val work = Work(id, wname, wtime)
                workList.add(work)
            }
    }
    return workList
}
