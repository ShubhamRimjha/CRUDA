package com.intern.cruda

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import com.squareup.picasso.Picasso
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog


class RacerAdapter(
    options: FirebaseRecyclerOptions<Racer>
) :
    FirebaseRecyclerAdapter<Racer, RacerAdapter.RViewHolder>(options) {

    lateinit var uName: EditText
    lateinit var uTeam: EditText
    lateinit var uCar: EditText
    lateinit var btnEdit: Button

    class RViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtname: TextView = view.findViewById(R.id.txt_name)
        var txtteam: TextView = view.findViewById(R.id.txt_team)
        var txtcar: TextView = view.findViewById(R.id.txt_car)
        var imgR: ShapeableImageView = view.findViewById(R.id.img_r)
        var editBtn: Button = view.findViewById(R.id.btn_edit)
        var deleteBtn: Button = view.findViewById(R.id.btn_delete)
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

        holder.editBtn.setOnClickListener {
            val bDialog = DialogPlus.newDialog(holder.imgR.context)
                .setContentHolder(ViewHolder(R.layout.layout_edit_dialog))
                .setExpanded(
                    true,
                    800
                ) // This will enable the expand feature, (similar to android L share dialog)
                .create()
            var hodlr = bDialog.holderView

            uName = hodlr.findViewById(R.id.et_racer_name_edit)
            uTeam = hodlr.findViewById(R.id.et_racer_team_edit)
            uCar = hodlr.findViewById(R.id.et_racer_car_edit)
            btnEdit = hodlr.findViewById(R.id.btn_dialog_edit)

            uName.setText(racer.rname)
            uTeam.setText(racer.team)
            uCar.setText(racer.car)

            btnEdit.setOnClickListener {
                val racerUpdated =
                    Racer(uName.text.toString(), uTeam.text.toString(), uCar.text.toString())

                FirebaseDatabase.getInstance().reference.child("Racers")
                    .child(getRef(position).key.toString()).setValue(racerUpdated)

            }

            bDialog.show()
        }


        holder.deleteBtn.setOnClickListener {

            val bDialog = BottomSheetMaterialDialog.Builder(holder.imgR.context as Activity)
                .setTitle("Delete?")
                .setMessage("Are you sure want to delete this file?")
                .setCancelable(false)
                .setPositiveButton("Delete") { dialogInterface, _ ->

                    FirebaseDatabase.getInstance().reference.child("Racers")
                        .child(getRef(position).key.toString()).removeValue()

                    Toast.makeText(
                        holder.imgR.context,
                        "Deleted!",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialogInterface, _ ->
                    Toast.makeText(
                        holder.imgR.context,
                        "Cancelled!",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialogInterface.dismiss()
                }.build()

            // Show Dialog

            // Show Dialog
            bDialog.show()


        }
    }
}
