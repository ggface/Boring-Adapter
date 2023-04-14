package io.github.ggface.boring.adapter.presentation.adapter.ktx

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ivan Novikov on 2023-04-14.
 */
abstract class BoringView<M : BoringModel, VH : RecyclerView.ViewHolder>(val type: Int) {

    open fun bindView(item: M, holder: VH, position: Int, payloads: List<Any>) {}

    abstract fun bindView(model: M, holder: VH, position: Int)

    abstract fun createViewHolder(parent: ViewGroup): VH
}