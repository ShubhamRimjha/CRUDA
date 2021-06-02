package com.intern.cruda.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.FirebaseDatabase
import com.intern.cruda.AddDialog
import com.intern.cruda.R
import com.intern.cruda.Racer
import com.intern.cruda.RacerAdapter
import java.util.*


class MainActivity : AppCompatActivity(), AddDialog.AddDialogListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RacerAdapter
    private lateinit var picbtn: ShapeableImageView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        picbtn = findViewById(R.id.me_img_smol)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Racers"

        recyclerView = findViewById(R.id.rv_main)

        fab.setOnClickListener {
            fab.animate().withEndAction { openAddDialog() }.start()
        }

        picbtn.setOnClickListener {
            val AboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    picbtn,
                    picbtn.transitionName
                )
            // pass your bundle data
            startActivity(AboutIntent, options.toBundle())
            overridePendingTransition(R.anim.anim_in, R.anim.screen_out)
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