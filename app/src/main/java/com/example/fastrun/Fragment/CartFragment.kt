package com.example.fastrun.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastrun.CongratsBottomSheet
import com.example.fastrun.PayOutActivity
import com.example.fastrun.R
import com.example.fastrun.adaptar.CartAdapter
import com.example.fastrun.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container, false)

        val cartFoodName = listOf("Coffee", "Tea", "Frappe" , "Item", "Item")
        val cartItemPrice = listOf("$5", "$6", "$7", "$10", "$10")
        val cartImage = listOf(
            R.drawable.coffee,
            R.drawable.tea,
            R.drawable.frappe,
            R.drawable.coffee,
            R.drawable.tea
        )
        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    companion object {

    }
}