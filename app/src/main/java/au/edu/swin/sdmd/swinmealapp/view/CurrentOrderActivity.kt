package au.edu.swin.sdmd.swinmealapp.view

import android.content.Context
import au.edu.swin.sdmd.swinmealapp.adapters.RecyclerCurrentOrderAdapter
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.edu.swin.sdmd.swinmealapp.R
import au.edu.swin.sdmd.swinmealapp.datamodels.CurrentOrderItem
import au.edu.swin.sdmd.swinmealapp.services.OrderServices

class CurrentOrderActivity : AppCompatActivity(), RecyclerCurrentOrderAdapter.OnItemClickListener {
    private val currentOrderList = ArrayList<CurrentOrderItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerCurrentOrderAdapter

    private var orderServices = OrderServices()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_order)

        recyclerView = findViewById(R.id.current_order_recycler_view)
        recyclerAdapter = RecyclerCurrentOrderAdapter(this, currentOrderList, this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadCurrentOrdersFromDatabase()
    }

    // Retrieve data from database
    private  fun loadCurrentOrdersFromDatabase() {

        val sharedPrefs = getSharedPreferences("Order", Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("emailOrder", "") ?: ""

        orderServices.getAllCurrentOrders(userEmail) { data ->
            data?.let {
                if(data.isEmpty()) {
                    findViewById<LinearLayout>(R.id.current_order_empty_indicator_ll).visibility = ViewGroup.VISIBLE
                }

                findViewById<LinearLayout>(R.id.current_order_empty_indicator_ll).visibility = ViewGroup.GONE
                for (i in 0 until data.size) {
                    val currentOrderItem = CurrentOrderItem()

                    currentOrderItem.orderId = data[i].orderId
                    currentOrderItem.takeAwayTime = data[i].takeAwayTime
                    currentOrderItem.paymentStatus = data[i].paymentStatus
                    currentOrderItem.orderItemNames = data[i].orderItemNames
                    currentOrderItem.orderItemQuantities = data[i].orderItemQuantities
                    currentOrderItem.totalItemPrice = data[i].totalItemPrice
                    currentOrderItem.tax = data[i].tax
                    currentOrderItem.subTotal = data[i].subTotal
                    currentOrderList.add(currentOrderItem)
                    currentOrderList.reverse()
                    recyclerAdapter.notifyItemRangeInserted(0, data.size)
                    Log.i("current act", currentOrderItem.toString())
                }
            }
        }
    }

    //confirm to receive order successfully and delete the item in  the list
    override fun confirm(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Food Received")
            .setMessage("Are you done this order?")
            .setPositiveButton("Yes, I have received the food", DialogInterface.OnClickListener { dialogInterface, _ ->
                val sharedPrefs = getSharedPreferences("Order", Context.MODE_PRIVATE)
                val userEmail = sharedPrefs.getString("emailOrder", "") ?: ""
                val id = currentOrderList[position].orderId

                orderServices.confirmOrderDone(userEmail, id)

                currentOrderList.removeAt(position)
                recyclerAdapter.notifyItemRemoved(position)
                recyclerAdapter.notifyItemRangeChanged(position, currentOrderList.size)
                Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show()

                if(currentOrderList.isEmpty()) {
                    findViewById<LinearLayout>(R.id.current_order_empty_indicator_ll).visibility = ViewGroup.VISIBLE
                }

                dialogInterface.dismiss()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
            .create().show()
    }

    //delete the item in  the list
    override fun cancelOrder(position: Int) {

        AlertDialog.Builder(this)
            .setTitle("Order Cancellation")
            .setMessage("Are you sure you want to cancel this order?")
            .setPositiveButton("Yes, Cancel Order", DialogInterface.OnClickListener { dialogInterface, _ ->

                val sharedPrefs = getSharedPreferences("Order", Context.MODE_PRIVATE)
                val userEmail = sharedPrefs.getString("emailOrder", "") ?: ""
                var id = currentOrderList[position].orderId
                Log.i("position", id)
                orderServices.orderCancel(userEmail, id)

                currentOrderList.removeAt(position)
                recyclerAdapter.notifyItemRemoved(position)
                recyclerAdapter.notifyItemRangeChanged(position, currentOrderList.size)
                Toast.makeText(this, "Order Cancelled", Toast.LENGTH_SHORT).show()

                if(currentOrderList.isEmpty()) {
                    findViewById<LinearLayout>(R.id.current_order_empty_indicator_ll).visibility = ViewGroup.VISIBLE
                }

                dialogInterface.dismiss()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
            .create().show()
    }

    fun goBack(view: View) {onBackPressed()}
}