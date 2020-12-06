package com.example.controle.util

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.controle.R
import com.example.controle.ui.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_ajuda.*

/**
 * A simple [Fragment] subclass.
 */

var escolha = 0
class AjudaFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).supportActionBar?.setTitle("Ajuda")
        return inflater.inflate(R.layout.fragment_ajuda, container, false)
    }

    companion object{
        fun setNumber(numero:Int){
            escolha = numero
        }
    }

    fun getPDF():String{
        var str = ""
        when(escolha){
            0 -> str = "01.pdf"
            1 -> str = "02.pdf"
            2 -> str = "03.pdf"
            3 -> str = "04.pdf"
            4 -> str = "05.pdf"
            5 -> str = "06.pdf"
            6 -> str = "07.pdf"
        }
        return str
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Dexter.withActivity(activity)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object:BaseMultiplePermissionsListener(){


                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    super.onPermissionsChecked(report)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    super.onPermissionRationaleShouldBeShown(permissions, token)
                }


            })


        pdf_view.fromAsset(getPDF())
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onDraw { canvas, pageWidth, pageHeight, displayedPage ->  
                
            }.onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->  
                
            }
            .onPageChange { page, pageCount ->  
                
            }.onPageScroll { page, positionOffset ->
//                Toast.makeText(context,"Error while opening page"+page,Toast.LENGTH_SHORT).show()
//                Log.d("Error",""+positionOffset)
            }
            .onTap { false }
            .onRender { nbPages, pageWidth, pageHeight ->
                pdf_view.fitToWidth()
            }
            .enableAnnotationRendering(true)
            .invalidPageColor(Color.RED)
            .load()


    }

}
