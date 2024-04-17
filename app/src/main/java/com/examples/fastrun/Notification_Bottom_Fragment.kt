package com.examples.fastrun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.fastrun.adaptar.NotificationAdapter
import com.examples.fastrun.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Notification_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notifications = listOf(
            "Your order has been Canceled Successfully",
            "Order has been taken by the barista",
            "Congrats Your Order Placed"
        )
        val notificationImage = listOf(
            R.drawable.sademoji,
            R.drawable.truck,
            R.drawable.confirm
        )
        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImage)
        )
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}