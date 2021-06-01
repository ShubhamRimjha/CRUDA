package com.intern.cruda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class MainActivity : AppCompatActivity(), AddDialog.AddDialogListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RacerAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.isHideOnContentScrollEnabled
        title = "Formula Racers"

        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.rv_main)

        fab.setOnClickListener {
            fab.animate().rotationBy(180F).setDuration(180).withStartAction { openAddDialog() } .start()
        }


        recyclerView.layoutManager = LinearLayoutManager(this)

        val options = FirebaseRecyclerOptions.Builder<Racer>()
            .setQuery(FirebaseDatabase.getInstance().reference.child("Racers"), Racer::class.java)
            .build()
        adapter = RacerAdapter(options)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun saveRacer(racer: Racer) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Racers")

        dbRef.child(racer.rname.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        })
            .setValue(racer)
    }

    private fun openAddDialog() {
        val dialog = AddDialog()
        dialog.show(supportFragmentManager, "display dialog")
    }
}