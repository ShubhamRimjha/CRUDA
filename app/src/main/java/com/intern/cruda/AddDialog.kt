package com.intern.cruda

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment

class AddDialog(rname: String) : AppCompatDialogFragment(){

    lateinit var rName: EditText
    lateinit var rTeam: EditText
    lateinit var txt_desc: TextView
    lateinit var rCar: EditText
    lateinit var img_r: ImageView
    lateinit var btnAdd: Button
    lateinit var listener: AddDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity, R.style.MyCustomTheme)
        var imgurl = ""
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_add_dialog, null)

        builder.setView(view)
            .setTitle("Add new Racer")
            .setCancelable(true)

        rName = view.findViewById(R.id.et_racer_name)
        rTeam = view.findViewById(R.id.et_racer_team)
        rCar = view.findViewById(R.id.et_racer_car)
        btnAdd = view.findViewById(R.id.btn_add)
        img_r = view.findViewById(R.id.iv_dialog_pic)
        txt_desc = view.findViewById(R.id.txt_desc)

        img_r.setOnClickListener {

            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1)

            Toast.makeText(context, "Img uploaded to cloud storage", Toast.LENGTH_SHORT).show()
        }

        btnAdd.setOnClickListener {
            if (rName.text.isEmpty()) rName.error = "Please enter name"
            else {
                val racer = Racer(
                    rName.text.toString(),
                    rTeam.text.toString(),
                    rCar.text.toString(),
                    imgurl
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