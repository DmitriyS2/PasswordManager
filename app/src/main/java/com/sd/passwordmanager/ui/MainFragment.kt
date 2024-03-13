package com.sd.passwordmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sd.passwordmanager.adapter.ItemAdapter
import com.sd.passwordmanager.adapter.Listener
import com.sd.passwordmanager.databinding.FragmentMainBinding
import com.sd.passwordmanager.dto.ItemPassword

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding:FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        val adapter = ItemAdapter(object :Listener{
            override fun removeItem(item: ItemPassword) {
                TODO("Not yet implemented")
            }

            override fun editItem(item: ItemPassword) {
                TODO("Not yet implemented")
            }

            override fun showPassword(item: ItemPassword) {
                TODO("Not yet implemented")
            }

            override fun hidePassword(item: ItemPassword) {
                TODO("Not yet implemented")
            }

        })

        binding.rwMain.layoutManager = LinearLayoutManager(activity)
        binding.rwMain.adapter = adapter

        binding.fab.setOnClickListener{
            it.animTouch()
        }

        return binding.root
    }
}