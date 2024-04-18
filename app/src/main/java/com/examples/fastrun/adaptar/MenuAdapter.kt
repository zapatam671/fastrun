package com.examples.fastrun.adaptar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastrun.databinding.MenuItemBinding
import com.examples.fastrun.DetailsActivity
import com.examples.fastrun.model.MenuItem

class MenuAdapter(
    private val menuItems:List<MenuItem>,
    private val requireContext: Context
) :RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size
    inner class MenuViewHolder(private val binding: MenuItemBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    openDetailActivity(position)
                }
            }
        }

        private fun openDetailActivity(position: Int) {
val menuItem :MenuItem = menuItems[position]

            // a intent to open details activity and pass data
            val intent = Intent(requireContext ,DetailsActivity::class.java).apply {
                putExtra("MenuItemName",menuItem.foodName)
                putExtra("MenuItemImage",menuItem.foodImageUrl)
                putExtra("MenuItemDescription",menuItem.foodDescription)
                putExtra("MenuItemIngredients",menuItem.foodIngredients)
                putExtra("MenuItemPrice",menuItem.foodPrice)

            }
            // start the detail activity
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text=menuItem.menuFoodName
                menuPrice.text = menuItem.foodPrice
                val Uri = Uri.parse(menuItem.foodImageUrl)
            }
        }

    }

    interface OnClickListener{
        fun onItemClick(position: Int)

    }
}


