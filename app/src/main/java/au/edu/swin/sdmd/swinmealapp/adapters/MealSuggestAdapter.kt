package au.edu.swin.sdmd.swinmealapp.adapters

import akathon.cos30017.swin_meal_backend.datamodel.SuggestMeal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.edu.swin.sdmd.swinmealapp.R
import au.edu.swin.sdmd.swinmealapp.datamodels.MenuItem
import com.squareup.picasso.Picasso

class MealSuggestAdapter(
    private val suggestMeals: MutableList<SuggestMeal>,
    private val listener: OnItemClickListener)
    : RecyclerView.Adapter<MealSuggestAdapter.ItemListViewHolder>() {

    interface OnItemClickListener {
        fun onFoodClick1(item: SuggestMeal)
        fun onFoodClick2(item: SuggestMeal)
        fun onPlusBtn1Click(item: SuggestMeal)
        fun onPlusBtn2Click(item: SuggestMeal)
        fun onMinusBtn1Click(item: SuggestMeal)
        fun onMinusBtn2Click(item: SuggestMeal)
    }

    class ItemListViewHolder( view: View) : RecyclerView.ViewHolder(view) {
        val itemFood1Image: ImageView = view.findViewById(R.id.food1_item_image)
        val itemFood1Name: TextView = view.findViewById(R.id.food1_item_name)
        val itemFood1Price: TextView = view.findViewById(R.id.food1_item_price)
        val itemFood1Stars: TextView = view.findViewById(R.id.food1_item_stars)
        val itemFood1ShortDesc: TextView = view.findViewById(R.id.food1_item_shortDesc)
        val itemFood1Quantity: TextView = view.findViewById(R.id.food1_item_quantity)
        val itemFood1Plus: ImageView = view.findViewById(R.id.food1_item_plus)
        val itemFood1Minus: ImageView = view.findViewById(R.id.food1_item_minus)
        val itemFood1CaloriesTv = view.findViewById<TextView>(R.id.food1_item_calories)

        val itemFood2Image: ImageView = view.findViewById(R.id.food2_item_image)
        val itemFood2Name: TextView = view.findViewById(R.id.food2_item_name)
        val itemFood2Price: TextView = view.findViewById(R.id.food2_item_price)
        val itemFood2Stars: TextView = view.findViewById(R.id.food2_item_stars)
        val itemFood2ShortDesc: TextView = view.findViewById(R.id.food2_item_shortDesc)
        val itemFood2Quantity: TextView = view.findViewById(R.id.food2_item_quantity)
        val itemFood2Plus: ImageView = view.findViewById(R.id.food2_item_plus)
        val itemFood2Minus: ImageView = view.findViewById(R.id.food2_item_minus)
        val totalSuggestedCalories = view.findViewById<TextView>(R.id.total_suggested_calories)
        val itemFood2CaloriesTv = view.findViewById<TextView>(R.id.food2_item_calories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.meal_suggest_list_item, parent, false) as View
//        println("view holder created")
        return ItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val suggestMeal = suggestMeals[position]
        holder.itemFood1Name.text = suggestMeal.food1.itemName
        holder.itemFood1Price.text = "$${suggestMeal.food1.itemPrice}"
        holder.itemFood1Stars.text = suggestMeal.food1.itemStars.toString()
        holder.itemFood1ShortDesc.text = suggestMeal.food1.itemShortDesc
        holder.itemFood1Quantity.text = suggestMeal.food1.quantity.toString()
        holder.itemFood1CaloriesTv.text = "${suggestMeal.food1.calories.toString()} kcal"
        Picasso.get().load(suggestMeal.food1.imageUrl).into(holder.itemFood1Image)

        holder.itemFood2Name.text = suggestMeal.food2.itemName
        holder.itemFood2Price.text = "$${suggestMeal.food2.itemPrice}"
        holder.itemFood2Stars.text = suggestMeal.food2.itemStars.toString()
        holder.itemFood2ShortDesc.text = suggestMeal.food2.itemShortDesc
        holder.itemFood2Quantity.text = suggestMeal.food2.quantity.toString()
        holder.itemFood2CaloriesTv.text = "${suggestMeal.food2.calories.toString()} kcal"
        Picasso.get().load(suggestMeal.food2.imageUrl).into(holder.itemFood2Image)
        val textHolder = "Option ${position + 1} - Calories: ${suggestMeal.food1.calories + suggestMeal.food2.calories} "
        holder.totalSuggestedCalories.text = textHolder

        holder.itemFood1Plus.setOnClickListener {
            val number = suggestMeal.food1.quantity
            holder.itemFood1Quantity.text = (number + 1).toString()
            listener.onPlusBtn1Click(suggestMeal)
        }

        holder.itemFood1Minus.setOnClickListener {
            val number = suggestMeal.food1.quantity
            if (number > 0) {
                holder.itemFood1Quantity.text = (number - 1).toString()
                listener.onMinusBtn1Click(suggestMeal)
            }
        }

        holder.itemFood2Plus.setOnClickListener {
            val number = suggestMeal.food2.quantity
            holder.itemFood2Quantity.text = (number + 1).toString()
            listener.onPlusBtn2Click(suggestMeal)
        }

        holder.itemFood2Minus.setOnClickListener {
            val number = suggestMeal.food2.quantity
            if (number > 0) {
                holder.itemFood2Quantity.text = (number - 1).toString()
                listener.onMinusBtn2Click(suggestMeal)
            }
        }

        holder.itemFood1Image.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2Image.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }

        holder.itemFood1Name.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2Name.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }

        holder.itemFood1Price.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2Price.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }

        holder.itemFood1Stars.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2Stars.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }

        holder.itemFood1ShortDesc.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2ShortDesc.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }

        holder.itemFood1CaloriesTv.setOnClickListener {
            listener.onFoodClick1(suggestMeal)
        }

        holder.itemFood2CaloriesTv.setOnClickListener {
            listener.onFoodClick2(suggestMeal)
        }
    }

    override fun getItemCount(): Int = suggestMeals.size
}