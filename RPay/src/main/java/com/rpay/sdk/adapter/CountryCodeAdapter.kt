package com.rpay.sdk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.rpay.sdk.databinding.CountryListItemBinding
import com.rpay.sdk.model.CountryListResponse

class CountryCodeAdapter(val context: Context, private val countryList: ArrayList<CountryListResponse.CountryList>): BaseAdapter() {

    override fun getCount(): Int {
        return countryList.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = CountryListItemBinding.inflate(inflater)

        var countryCode = countryList[p0].country_code
        if (countryCode == "+1868") {
            countryCode = countryCode.substring(0, countryCode.length - 3)
        }
        view.countryCodeTextView.text = countryCode
        Glide.with(context).load(countryList[p0].country_flag).into(view.countryImageView)

        return view.root
    }
}