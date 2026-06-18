package com.app.uts.universe.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.uts.universe.model.Event
import com.app.uts.universe.model.Riwayat

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "eventkampus.db", null, 3) { // Naikkan versi ke 3 untuk menambah kolom benefit

    override fun onCreate(db: SQLiteDatabase) {

        // Tabel User
        db.execSQL("""
            CREATE TABLE user (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT,
                username TEXT,
                password TEXT,
                role TEXT
            )
        """.trimIndent())

        // Tabel Event (ditambahkan kolom `benefit` sebagai TEXT, delimiter: newline)
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

        // --- TAMBAHAN BARU: Tabel Riwayat ---
        db.execSQL("""
            CREATE TABLE riwayat (
                id_riwayat INTEGER PRIMARY KEY AUTOINCREMENT,
                username_mahasiswa TEXT,
                id_event INTEGER,
                nama_event TEXT,
                tanggal_daftar TEXT
            )
        """.trimIndent())

        // Akun admin bawaan
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
        // Jika upgrade dari versi lama, tambahkan kolom `benefit` tanpa menghapus data
        if (oldVersion < 3) {
            try {
                db.execSQL("ALTER TABLE event ADD COLUMN benefit TEXT")
            } catch (e: Exception) {
                // jika ALTER TABLE gagal (mis. kolom sudah ada), fallback ke drop+create
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
                    // benefit may be null if old row; guard with try/catch
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

    // ==========================================
    // TAMBAHAN FUNGSI UNTUK MAHASISWA (RIWAYAT)
    // ==========================================

    // 1. Fungsi Create (Daftar Event)
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

    // 2. Fungsi Read (Ambil Data Riwayat sesuai username yang login)
    fun getRiwayatMahasiswa(usernameMahasiswa: String): ArrayList<Riwayat> {
        val listRiwayat = ArrayList<Riwayat>()
        val db = readableDatabase

        // Menggunakan rawQuery (CMD) dengan kondisi WHERE username = ?
        val cursor = db.rawQuery(
            "SELECT * FROM riwayat WHERE username_mahasiswa = ?",
            arrayOf(usernameMahasiswa)
        )

        if (cursor.moveToFirst()) {
            do {
                val riwayat = Riwayat(
                    cursor.getInt(0),     // id_riwayat
                    cursor.getString(1),  // username_mahasiswa
                    cursor.getInt(2),     // id_event
                    cursor.getString(3),  // nama_event
                    cursor.getString(4)   // tanggal_daftar
                )
                listRiwayat.add(riwayat)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listRiwayat
    }
}