package com.david.bajela.a6211project.Data

import android.content.Context
import androidx.lifecycle.LiveData
import com.david.bajela.a6211project.IO.DBRoomDatabase
import com.david.bajela.a6211project.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Repo {

    lateinit var user: User
    lateinit var users: MutableMap<String, User>
    lateinit var notes: MutableList<Note>
    lateinit var note: Note
    lateinit var db: DBRoomDatabase

    val timestampformat = "HH:mm:ss dd/MM/yyyy"

    var nextValidNoteID: Int = 100000
    //todo change to load form database

    // TODO: 18/03/2021 delete
    fun populateUsers(L: LiveData<List<User>>) {
        L.value?.forEach { x -> users.put(x.email, x) }
    }

    suspend fun loadNotes() {

        notes = db.NoteDAO().getNotes(user.email) as MutableList<Note>

    }

    /*
    var id:Int,
    var Owner: String,
    var title: String,
    var note: String?,
    var icon: Bitmap,
    var audiomessage: AudioRecord?,
    var image: Bitmap?,
    var video: MediaStore.Video,
    var lastUpdate: Timestamp

    */

    fun loadtestnotes(C: Context) {
        val testNotes: MutableList<Note> = mutableListOf()
        val f = "Title "
        var s = "Message "
        for (i in 1..10) {
            s += "" + Math.random() * 100000 * 1000000
            val n = Note(
                testNotes.size,
                user.email,
                f + i,
                s + 1,
                R.drawable.ic_horn,
                //null,
                //null,
                null,
                System.currentTimeMillis()
            )
            testNotes.add(n)
            notes = testNotes

            CoroutineScope(Dispatchers.IO).launch {
                db.NoteDAO().insert(notes)
            }
        }
    }

    fun updateTimestamp() {
        note.lastUpdate = System.currentTimeMillis()
    }

    suspend fun setUP(context: Context) {
        //todo change to load form database
        notes = mutableListOf()
        users = mutableMapOf()
        db = DBRoomDatabase.getDB(context)
        db.UserDAO().geAllUsers().forEach { u -> users[u.email] = u }
    }

    fun userExist(email: String) = users.containsKey(email)

    fun correctPass(email: String, pass: String) = users[email]?.password == pass

    fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String
    ) {
        val u = User(firstname, lastname, email, password)
        users[u.email] = u
        CoroutineScope(Dispatchers.IO).launch {
            db.UserDAO().insert(u)
        }

//        users[u.email] = u

    }

    fun selectNote(pos: Int) {
        note = notes[pos]
    }

    fun deleteAllnote() {
        notes.removeAll(notes)
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDAO().deletAllUserNotes(user.email)
        }


    }

    fun deleteSelectedNote(n: Note) {
        notes.remove(n)
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDAO().delete(n)
        }


    }

    fun deleteSelectedNote(pos: Int) {
        val n = notes[pos]
        deleteSelectedNote(n)
    }

    fun createNewNote() {
        //todo redefine default icont

        note = Note(
            null,
            user.email,
            "new note",
            "",
            R.drawable.ic_horn,
            //null,
            null,
            //null,
            System.currentTimeMillis()
        )
        notes.add(note)
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDAO().insert(note)
        }
    }


    fun login(c: Context, email: String, pass: String) {
        user = users[email]!!
        CoroutineScope(Dispatchers.IO).launch {
            loadNotes()
        }
    }


    fun updateUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = users.values.toList()
            db.UserDAO().update(list)
        }
    }

    fun updateNote() {
        CoroutineScope(Dispatchers.IO).launch {
            db.NoteDAO().update(notes)
        }
    }

    fun setImage(s: String) {
        note.image = s
    }

    fun updateDatabase() {
        updateUsers()
        updateNote()
    }
}