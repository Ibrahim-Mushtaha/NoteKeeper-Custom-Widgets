package com.jwhh.notekeeper.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.jwhh.notekeeper.BR
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.adapter.GenericAdapter
import com.jwhh.notekeeper.data.db.DataManager
import com.jwhh.notekeeper.data.model.CourseInfo
import com.jwhh.notekeeper.data.model.NoteInfo
import com.jwhh.notekeeper.utils.ClickEnum
import com.jwhh.notekeeper.utils.NOTE_POSITION
import com.jwhh.notekeeper.utils.OnListItemViewClickListener
import com.jwhh.notekeeper.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.app_bar_items.*
import kotlinx.android.synthetic.main.content_items.*

class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    OnListItemViewClickListener<NoteInfo> {

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val noteAdapter by lazy {
        GenericAdapter(R.layout.item_note,BR.Note,this)
    }

    private val courseRecyclerAdapter by lazy {
        GenericAdapter(R.layout.item_course,BR.Course,object :
            OnListItemViewClickListener<CourseInfo>{
            override fun onClickItem(itemViewModel: CourseInfo, type: ClickEnum, position: Int) {
                when(type){
                    ClickEnum.ONE ->{
                        recyclerItems.showSnackBar(itemViewModel.title)
                    }
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayNotes()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.notifyDataSetChanged()
    }

    private fun displayNotes() {
        noteAdapter.data = DataManager.notes
        recyclerItems.layoutManager = noteLayoutManager
        recyclerItems.adapter = noteAdapter
        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        courseRecyclerAdapter.data =  DataManager.courses.values.toList()
        recyclerItems.layoutManager = courseLayoutManager
        recyclerItems.adapter = courseRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                recyclerItems.showSnackBar("Display settings")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_share -> {
                recyclerItems.showSnackBar("Don't you think you've shared enough")
            }
            R.id.nav_send -> {
                recyclerItems.showSnackBar("Send")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onClickItem(itemViewModel: NoteInfo, type: ClickEnum,position:Int) {
        when(type){
            ClickEnum.ONE ->{
                val intent = Intent(this, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, position)
                startActivity(intent)
            }
        }
    }

}
