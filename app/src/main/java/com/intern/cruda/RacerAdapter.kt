package com.intern.cruda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class RacerAdapter(
    options: FirebaseRecyclerOptions<Racer>
) :
    FirebaseRecyclerAdapter<Racer, RacerAdapter.RViewHolder>(options) {


    class RViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtname: TextView = view.findViewById(R.id.txt_name)
        var txtteam: TextView = view.findViewById(R.id.txt_team)
        var txtcar: TextView = view.findViewById(R.id.txt_car)
        var imgR: ShapeableImageView = view.findViewById(R.id.img_r)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardlayout_racer, parent, false)
        return RViewHolder(view)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int, racer: Racer) {
        holder.txtname.text = racer.rname
        holder.txtteam.text = racer.team
        holder.txtcar.text = racer.car

        if (racer.iurl.isNotBlank()) Picasso.get().load(racer.iurl).centerCrop().resize(100, 100)
            .error(R.drawable.ic_action_add)
            .into(holder.imgR)
        else holder.imgR.setImageResource(R.drawable.fia_logo)
    }
}
