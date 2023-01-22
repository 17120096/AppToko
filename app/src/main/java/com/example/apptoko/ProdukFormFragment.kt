package com.example.apptoko

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apptoko.api.BaseRetrofit
import com.example.apptoko.response.produk.Produk
import com.example.apptoko.response.produk.ProdukResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProdukFormFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_produk_form, container, false)

        val btnProsesproduk =  view.findViewById<Button>(R.id.btnProsesproduk)

        val txtNamaProduk = view.findViewById<TextView>(R.id.txtNamaProduk)
        val txtHarga = view.findViewById<TextView>(R.id.txtHarga)
        val txtStok = view.findViewById<TextView>(R.id.txtStok)

        val status = arguments?.getString("status")
        val produk = arguments?.getParcelable<Produk>("produk")

        Log.d("produkForm", produk.toString())

        if(status=="edit"){
            txtNamaProduk.setText(produk?.nama.toString())
            txtHarga.setText(produk?.harga.toString())
            txtStok.setText(produk?.stok.toString())
        }

        btnProsesproduk.setOnClickListener{
            val txtNamaProduk =  view.findViewById<EditText>(R.id.txtNamaProduk)
            val txtHarga =  view.findViewById<EditText>(R.id.txtHarga)
            val txtStok =  view.findViewById<EditText>(R.id.txtStok)

            val token = LoginActivity.sessionManager.getString("TOKEN")
            val adminId = LoginActivity.sessionManager.getString("ADMIN_ID")

            if(status=="edit"){
                api.putProduk(token.toString(), produk?.id.toString().toInt(), adminId.toString().toInt(), txtNamaProduk.text.toString(), txtHarga.text.toString().toInt(), txtStok.text.toString().toInt()).enqueue(object :
                    Callback<ProdukResponsePost> {
                    override fun onResponse(
                        call: Call<ProdukResponsePost>,
                        response: Response<ProdukResponsePost>
                    ) {
                        Log.d("ResponData", response.body()!!.data.toString())
                        Toast.makeText(activity?.applicationContext, "Data "+ response.body()!!.data.produk.nama.toString() +" di edit", Toast.LENGTH_LONG).show()

                        findNavController().navigate(R.id.produkFragment)
                    }

                    override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                        Log.e("Error", t.toString())
                    }
                })
            } else {
                api.postProduk(token.toString(), adminId.toString().toInt(), txtNamaProduk.text.toString(), txtHarga.text.toString().toInt(), txtStok.text.toString().toInt()).enqueue(object :
                    Callback<ProdukResponsePost> {
                    override fun onResponse(
                        call: Call<ProdukResponsePost>,
                        response: Response<ProdukResponsePost>
                    ) {
                        Log.d("Data", response.toString())
                        Toast.makeText(activity?.applicationContext, "Data diinput", Toast.LENGTH_LONG).show()

                        findNavController().navigate(R.id.produkFragment)
                    }

                    override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                        Log.e("Error", t.toString())
                    }
                })
            }
        }
        return view
    }

}