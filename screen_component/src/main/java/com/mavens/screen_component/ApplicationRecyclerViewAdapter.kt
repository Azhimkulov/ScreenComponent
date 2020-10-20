package com.mavens.screen_component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

/**
 * Created by azamat  on 2020-05-23.
 */
open class ApplicationRecyclerViewAdapter<T> : RecyclerView.Adapter<ApplicationRecyclerViewAdapter.ViewHolder>() {

    private val itemList: MutableList<T> = mutableListOf()


    lateinit var cellInfo: CellInfo
    var onItemClickUnit: ((view: View, model: T, position: Int) -> Unit)? = null

    companion object {
        private var adapterId:Int = 0
        private var modelId:Int = 0

        fun initialize(adapterId:Int, modelId:Int) {
            this.adapterId = adapterId
            this.modelId = modelId
        }

        fun <T> getInstance(layoutId: Int): ApplicationRecyclerViewAdapter<T> {
            val applicationRecyclerViewAdapter = ApplicationRecyclerViewAdapter<T>()
            applicationRecyclerViewAdapter.cellInfo = CellInfo(layoutId)
            return applicationRecyclerViewAdapter
        }
    }

    init {
        if (adapterId == 0 && modelId == 0) {
            throw Exception("Required to call \'initialize\' method in Application")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemViewBinding?.setVariable(adapterId, this)
        return viewHolder
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = itemList[position]
        holder.itemViewBinding?.setVariable(modelId, model)
    }

    override fun getItemViewType(position: Int): Int {
        return cellInfo.layoutId
    }

    class CellInfo(val layoutId: Int)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemViewBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    fun onItemClick(view: View, model: T) {
        onItemClickUnit?.invoke(view, model, itemList.indexOf(model))
    }

    fun renderNewArray(list: List<T>) {
        itemList.clear()
        notifyNewItems(list)
    }

    fun notifyNewItems(list: List<T>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun emptyItems() {
        itemList.clear()
        notifyDataSetChanged()
    }
}