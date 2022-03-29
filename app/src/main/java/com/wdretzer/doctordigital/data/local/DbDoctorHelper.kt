package com.wdretzer.doctordigital.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.wdretzer.doctordigital.data.entity.DoctorEntry
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_NAME
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_PHOTO

class DbDoctorHelper(context: Context) {
    private val db = DbHelper(context, sqlCreationEntries, sqlDeleteEntries)

    fun insert(values: ContentValues.() -> Unit): Long {
        return db.writableDatabase.insert(
            DoctorEntry.TABLE_NAME,
            null,
            ContentValues().apply(values)
        )
    }

    fun update(id: Long, values: ContentValues.() -> Unit) {
        db.writableDatabase.update(
            DoctorEntry.TABLE_NAME,
            ContentValues().apply(values),
            "WHERE id = ?",
            arrayOf(id.toString())
        )
    }

    fun delete(id: Long) {
        db.writableDatabase.delete(DoctorEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    fun listAllWithAbacate() {
        val projection = arrayOf(BaseColumns._ID, COLUMN_NAME_NAME, COLUMN_NAME_PHOTO)

        val selection = "$COLUMN_NAME_NAME = ?"
        val selectionValue = arrayOf("Abacate")

        val order = "${BaseColumns._ID} DESC"

        val cursor = db.readableDatabase.query(
            DoctorEntry.TABLE_NAME,
            projection,
            selection,
            selectionValue,
            null,
            null,
            order
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                val photo = getString(getColumnIndexOrThrow(COLUMN_NAME_PHOTO))

                println("$id | $name | $photo")

            }
        }
        cursor.close()
    }


    companion object {
        private const val sqlCreationEntries = "CREATE TABLE ${DoctorEntry.TABLE_NAME}(" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${DoctorEntry.COLUMN_NAME_NAME} TEXT," +
                "${DoctorEntry.COLUMN_NAME_PHOTO} TEXT," +
                "${DoctorEntry.COLUMN_NAME_SPECIALIZATION} TEXT," +
                "${DoctorEntry.COLUMN_NAME_CLASSIFICATION} TEXT," +
                "${DoctorEntry.COLUMN_NAME_EXPERIENCE} TEXT," +
                "${DoctorEntry.COLUMN_NAME_PATIENT_STORIES} TEXT," +
                "${DoctorEntry.COLUMN_NAME_VIEWS} TEXT," +
                "${DoctorEntry.COLUMN_NAME_API_ID} TEXT)"

        private const val sqlDeleteEntries = "DROP TABLE IF EXISTS ${DoctorEntry.TABLE_NAME}"

    }
}
