package io.github.ggface.boring.adapter.presentation.adapter.ktx

import androidx.recyclerview.widget.DiffUtil

/**
 * Базовый [DiffUtil.Callback].
 *
 * @author Ivan Novikov on 2023-04-14.
 */
abstract class BoringDiffCallback<BM : BoringModel> : DiffUtil.Callback() {

    private val mOldItems = mutableListOf<BM>()
    private val mNewItems = mutableListOf<BM>()

     fun setItems(oldItems: List<BM>, newItems: List<BM>) {
        mOldItems.clear()
        mOldItems.addAll(oldItems)
        mNewItems.clear()
        mNewItems.addAll(newItems)
    }

    override fun getOldListSize(): Int {
        return mOldItems.size
    }

    override fun getNewListSize(): Int {
        return mNewItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(
            mOldItems[oldItemPosition],
            mNewItems[newItemPosition]
        )
    }

    abstract fun areItemsTheSame(oldItem: BoringModel, newItem: BoringModel): Boolean

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(mOldItems[oldItemPosition], mNewItems[newItemPosition])
    }

    abstract fun areContentsTheSame(oldItem: BoringModel, newItem: BoringModel): Boolean

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return getChangePayload(mOldItems[oldItemPosition], mNewItems[newItemPosition])
            ?: return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    open fun getChangePayload(oldItem: BM, newItem: BM): Any? = null
}