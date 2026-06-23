package com.app.uts.universe.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.uts.universe.model.Event
import com.app.uts.universe.model.Riwayat
import com.app.uts.universe.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "eventkampus.db", null, 3) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE user (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT,
                username TEXT,
                password TEXT,
                role TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE event (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama_event TEXT,
                kategori TEXT,
                tanggal TEXT,
                lokasi TEXT,
                deskripsi TEXT,
                benefit TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE riwayat (
                id_riwayat INTEGER PRIMARY KEY AUTOINCREMENT,
                username_mahasiswa TEXT,
                id_event INTEGER,
                nama_event TEXT,
                tanggal_daftar TEXT
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO user (nama, username, password, role)
            VALUES (
                'Administrator',
                'admin',
                'admin123',
                'admin'
            )
        """.trimIndent())
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < 3) {
            try {
                db.execSQL("ALTER TABLE event ADD COLUMN benefit TEXT")
            } catch (e: Exception) {
                db.execSQL("DROP TABLE IF EXISTS user")
                db.execSQL("DROP TABLE IF EXISTS event")
                db.execSQL("DROP TABLE IF EXISTS riwayat")
                onCreate(db)
            }
        }
    }

    fun checkLogin(username: String, password: String): String? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT role FROM user WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var role: String? = null
        if (cursor.moveToFirst()) {
            role = cursor.getString(0)
        }
        cursor.close()
        return role
    }

    fun insertEvent(
        nama: String,
        kategori: String,
        tanggal: String,
        lokasi: String,
        deskripsi: String,
        benefit: String = ""
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put("nama_event", nama)
        values.put("kategori", kategori)
        values.put("tanggal", tanggal)
        values.put("lokasi", lokasi)
        values.put("deskripsi", deskripsi)
        values.put("benefit", benefit)

        val result = db.insert("event", null, values)
        return result != -1L
    }

    fun getAllEvent(): ArrayList<Event> {
        val listEvent = ArrayList<Event>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM event", null)

        if (cursor.moveToFirst()) {
            do {
                val event = Event(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    if (!cursor.isNull(6)) cursor.getString(6) else ""
                )
                listEvent.add(event)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listEvent
    }

    fun updateEvent(
        id: Int,
        nama: String,
        kategori: String,
        tanggal: String,
        lokasi: String,
        deskripsi: String,
        benefit: String = ""
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put("nama_event", nama)
        values.put("kategori", kategori)
        values.put("tanggal", tanggal)
        values.put("lokasi", lokasi)
        values.put("deskripsi", deskripsi)
        values.put("benefit", benefit)

        val result = db.update("event", values, "id=?", arrayOf(id.toString()))
        return result > 0
    }

    fun deleteEvent(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("event", "id=?", arrayOf(id.toString()))
        return result > 0
    }


    fun getAllMahasiswa(): ArrayList<User> {
        val listUser = ArrayList<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT id, nama, username FROM user WHERE role = ?", arrayOf("mahasiswa"))
        if (cursor.moveToFirst()) {
            do {
                listUser.add(User(
                    cursor.getInt(0),
                    cursor.getString(1) ?: "",
                    cursor.getString(2) ?: ""
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listUser
    }


    fun insertRiwayat(
        usernameMahasiswa: String,
        idEvent: Int,
        namaEvent: String,
        tanggalDaftar: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put("username_mahasiswa", usernameMahasiswa)
        values.put("id_event", idEvent)
        values.put("nama_event", namaEvent)
        values.put("tanggal_daftar", tanggalDaftar)

        val result = db.insert("riwayat", null, values)
        return result != -1L
    }

    fun getRiwayatMahasiswa(usernameMahasiswa: String): ArrayList<Riwayat> {
        val listRiwayat = ArrayList<Riwayat>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM riwayat WHERE username_mahasiswa = ?",
            arrayOf(usernameMahasiswa)
        )

        if (cursor.moveToFirst()) {
            do {
                val riwayat = Riwayat(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4)
                )
                listRiwayat.add(riwayat)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listRiwayat
    }
}