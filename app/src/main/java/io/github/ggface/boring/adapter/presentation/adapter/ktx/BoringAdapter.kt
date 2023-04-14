package io.github.ggface.boring.adapter.presentation.adapter.ktx

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ivan Novikov on 2023-04-14.
 */
open class BoringAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val modelsList = mutableListOf<BoringModel>()
    private val mBoringViewsList = SparseArray<BoringView<*, *>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val renderer = mBoringViewsList[viewType]
        if (renderer != null) {
            return renderer.createViewHolder(parent)
        }
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    fun registerRenderer(renderer: BoringView<*, *>) {
        val type = renderer.type
        if (mBoringViewsList[type] == null) {
            mBoringViewsList.put(type, renderer)
        } else {
            val className = renderer.javaClass.simpleName
            throw RuntimeException("$className already exist with this type: $type")
        }
    }

    fun registerAllRenderer(boringViews: List<BoringView<*, *>>) {
        for (rendererView in boringViews) {
            registerRenderer(rendererView)
        }
    }

    fun registerAllRenderer(vararg boringViews: BoringView<*, *>) {
        for (rendererView in boringViews) {
            registerRenderer(rendererView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val item = modelsList[position]
            val renderer = mBoringViewsList[item.getType()]
            if (renderer != null) {
                renderer.bindView(item, holder, position, payloads)
            } else {
                throw UnsupportedViewHolderException(holder)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: BoringModel = modelsList[position]
        val renderer = mBoringViewsList[item.getType()]
        if (renderer != null) {
            renderer.bindView(item, holder, position)
        } else {
            throw UnsupportedViewHolderException(holder)
        }
    }

    override fun getItemViewType(position: Int) = modelsList[position].getType()

    override fun getItemCount() = modelsList.size

    fun setItems(
        items: List<BoringModel>,
        diffCallback: BoringDiffCallback<*>
    ) {
        diffCallback.setItems(modelsList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        modelsList.clear()
        modelsList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItems(items: List<BoringModel>) {
        modelsList.clear()
        modelsList.addAll(items)
        notifyDataSetChanged()
    }

    private class UnsupportedViewHolderException(
        holderName: RecyclerView.ViewHolder
    ) : RuntimeException("Not supported View Holder: " + holderName.javaClass.simpleName)
}