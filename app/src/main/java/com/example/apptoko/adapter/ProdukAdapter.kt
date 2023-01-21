package com.example.apptoko.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptoko.LoginActivity
import com.example.apptoko.R
import com.example.apptoko.api.BaseRetrofit
import com.example.apptoko.response.produk.Produk
import java.text.NumberFormat
import java.util.*

class ProdukAdapter(val listproduk: List<Produk>):RecyclerView.Adapter<ProdukAdapter.ViewHolder> () {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = listproduk[position]
        holder.txtNamaProduk.text = produk.nama

        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        holder.txtHarga.text = numberFormat.format(produk.harga.toInt()).toString()

        val token = LoginActivity.sessionManager.getString("TOKEN")

    }

    override fun getItemCount(): Int {
        return listproduk.size
    }

    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView) {
        val txtNamaProduk = itemView.findViewById<TextView>(R.id.txtNamaProduk)
        val txtHarga = itemView.findViewById<TextView>(R.id.txtHarga)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)


    }
}