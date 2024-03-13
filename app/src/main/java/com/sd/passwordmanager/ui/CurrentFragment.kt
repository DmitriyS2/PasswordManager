package com.sd.passwordmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sd.passwordmanager.databinding.FragmentCurrentBinding

class CurrentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding:FragmentCurrentBinding = FragmentCurrentBinding.inflate(inflater, container, false)

        Glide.with(binding.image)
            .load("https://www.hh.ru/favicon.ico") //работает
            //   .load("http://www.google.com/s2/favicons?domain=youtube.com")//не работает
//            .placeholder(R.drawable.download_64)
//            .error(R.drawable.error_64)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .timeout(10_000)
            .into(binding.image)

        return binding.root
    }
}