package com.intern.cruda

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.firebase.storage.FirebaseStorage

class AddDialog : AppCompatDialogFragment() {

    lateinit var rName: EditText
    lateinit var rTeam: EditText
    lateinit var txt_desc: TextView
    lateinit var rCar: EditText
    lateinit var img_r: ImageView
    lateinit var btnAdd: Button
    lateinit var listener: AddDialogListener
    var imgurl = ""
    lateinit var filepath: Uri


    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val img = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver,
                    uri
                )
            )
            img_r.setImageBitmap(img)
        }
        filepath = uri
        Toast.makeText(context, "Img uploaded to cloud storage $uri", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity, R.style.MyCustomTheme)
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
            getContent.launch("image/*")

        }


        btnAdd.setOnClickListener {
            if (rName.text.isEmpty()) rName.error = "Please enter name"
            else {

                val racer = Racer(
                    rName.text.toString(),
                    rTeam.text.toString(),
                    rCar.text.toString(),
                    imgUpload()
                )
                listener.saveRacer(racer)
                this.dismiss()
            }
        }

        return builder.create()
    }

    private fun imgUpload(): String { // Defining the child of storageReference
        val ref = FirebaseStorage.getInstance().reference.child("images/$rName-$rTeam")
        // adding listeners on upload or failure of image
        ref.putFile(filepath).addOnSuccessListener {
            // Image uploaded successfully
            Toast.makeText(
                context,
                "Image Uploaded!!",
                Toast.LENGTH_SHORT
            ).show()
            imgurl = ref.path
        }.addOnFailureListener {
            // Error, Image not uploaded
            Toast.makeText(
                context,
                "Failed $it",
                Toast.LENGTH_SHORT
            ).show()
        }
        return imgurl
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