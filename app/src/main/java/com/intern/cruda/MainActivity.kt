package com.intern.cruda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class MainActivity : AppCompatActivity(), AddDialog.AddDialogListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: RacerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dbRef = FirebaseDatabase.getInstance().getReference("Racers")

        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.rv_main)

        fab.setOnClickListener { openAddDialog() }


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
        var dbRef = FirebaseDatabase.getInstance().getReference("Racers")
        dbRef.child(racer.rname.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            .setValue(racer)
    }

    private fun openAddDialog() {
        val dialog = AddDialog()
        dialog.enterTransition =
        dialog.show(supportFragmentManager, "display dialog")
    }
}