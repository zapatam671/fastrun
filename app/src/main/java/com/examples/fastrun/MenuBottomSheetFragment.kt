package com.examples.fastrun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastrun.databinding.FragmentMenuBottomSheetBinding
import com.examples.fastrun.adaptar.MenuAdapter
import com.examples.fastrun.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

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
        retrieveMenuItems()

//        val menuFoodName = listOf("Coffee", "Tea", "Frappe" , "Item", "Item")
//        val menuItemPrice = listOf("$5", "$6", "$7", "$10", "$10")
//        val menuImage = listOf(
//            R.drawable.coffee,
//            R.drawable.tea,
//            R.drawable.frappe,
//            R.drawable.coffee,
//            R.drawable.tea
//        )




        return  binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef :DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it)}
                }
                Log.d("ITEMS", "onDataChange: Data Received")
                // once data receive , set to adapter
                setAdapter()
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun setAdapter() {
        if (menuItems.isNotEmpty()){
            val adapter = MenuAdapter(menuItems,requireContext())
            binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecyclerView.adapter = adapter
            Log.d("Items", "setAdapter: data set")
        }else{
            Log.d("Items", "setAdapter: data NOT set")
        }

    }

    companion object {
    }

}