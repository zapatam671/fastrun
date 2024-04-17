package com.examples.fastrun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.fastrun.adaptar.MenuAdapter
import com.examples.fastrun.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMenuBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }


        val menuFoodName = listOf("Coffee", "Tea", "Frappe" , "Item", "Item")
        val menuItemPrice = listOf("$5", "$6", "$7", "$10", "$10")
        val menuImage = listOf(
            R.drawable.coffee,
            R.drawable.tea,
            R.drawable.frappe,
            R.drawable.coffee,
            R.drawable.tea
        )
        val adapter = MenuAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuImage),requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter


        return  binding.root
    }

    companion object {

    }
}