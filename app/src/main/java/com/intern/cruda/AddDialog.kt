package com.intern.cruda

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.imageview.ShapeableImageView

class AddDialog : AppCompatDialogFragment() {

    lateinit var rName: TextView
    lateinit var rTeam: TextView
    lateinit var rCar: TextView
    lateinit var img_r: ShapeableImageView
    lateinit var btnAdd: Button
    lateinit var listener: AddDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_add_dialog, null)

        builder.setView(view)
            .setTitle("Add new Racer")
            .setCancelable(true)

        rName = view.findViewById(R.id.et_racer_name)
        rTeam = view.findViewById(R.id.et_racer_team)
        rCar = view.findViewById(R.id.et_racer_car)
        btnAdd = view.findViewById(R.id.btn_add)


        img_r.setOnClickListener{

        }
        btnAdd.setOnClickListener {
            if (rName.text.isEmpty()) rName.error = "Please enter name"
            else {
                val racer = Racer(
                    rName.text.toString(),
                    rTeam.text.toString(),
                    rCar.text.toString()
                )
                listener.saveRacer(racer)
                this.dismiss()
            }
        }

        return builder.create()
    }

    interface AddDialogListener {
        fun saveRacer(racer: Racer)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement AddDialogListener")
            )
        }
    }

}