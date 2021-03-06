package com.intern.cruda

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.firebase.storage.FirebaseStorage
import com.transitionseverywhere.TransitionManager


class AddDialog : AppCompatDialogFragment() {

    lateinit var rName: EditText
    lateinit var rTeam: EditText
    lateinit var txt_desc: TextView
    lateinit var rCar: EditText
    lateinit var img_r: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var btnAdd: Button
    lateinit var listener: AddDialogListener

    var imgurl = "gs://cruda-cbb45.appspot.com/place_holder.png"
    var imgUploadflag = false


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val img = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        uri
                    )
                )
                img_r.setImageBitmap(img)
                imgUploadflag = true
            }
            imgUpload(uri)
            Toast.makeText(context, "Img selected $uri", Toast.LENGTH_SHORT).show()
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
        progressBar = view.findViewById(R.id.pb_img_upload)

        img_r.setOnClickListener {
            getContent.launch("image/*")
            TransitionManager.beginDelayedTransition(view as ViewGroup)
            txt_desc.setVisibility(View.GONE)

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

                Toast.makeText(
                    context,
                    "New Driver Added!",
                    Toast.LENGTH_SHORT
                ).show()

            }
            this.dismiss()
        }

        return builder.create()
    }

    private fun imgUpload(uri: Uri): String {

        val ref =
            FirebaseStorage.getInstance().reference.child("images")
                .child("${rName.text}_${rTeam.text}")
        // adding listeners on upload or failure of image
        ref.putFile(uri)
            .addOnProgressListener {
                var progress = 100 * it.bytesTransferred / it.totalByteCount
                progressBar.progress = progress.toInt()
            }
            .addOnSuccessListener {
                // Image uploaded successfully
//                Toast.makeText(
//                    context, "Image Uploaded!!", Toast.LENGTH_SHORT
//                ).show()

                imgurl = ref.downloadUrl.toString()

            }.addOnFailureListener {
                // Error, Image not uploaded
                Toast.makeText(
                    context, "Failed $it", Toast.LENGTH_SHORT
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