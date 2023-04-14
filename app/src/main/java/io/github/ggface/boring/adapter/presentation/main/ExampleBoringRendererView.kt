package io.github.ggface.boring.adapter.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ggface.boring.adapter.R
import io.github.ggface.boring.adapter.databinding.ListItemLegacyExampleBinding
import io.github.ggface.boring.adapter.presentation.adapter.ktx.BoringModel
import io.github.ggface.boring.adapter.presentation.adapter.ktx.BoringView
import io.github.ggface.boring.adapter.presentation.adapter.legacy.RendererView

/**
 * [RendererView] для отображения идентификатора.
 *
 * @param onClickListener слушатель клика
 *
 * @author Ivan Novikov on 2023-04-14.
 */
class ExampleBoringRendererView(
    private val onClickListener: (productId: String) -> Unit
) : BoringView<ExampleBoringRendererView.Model, ExampleBoringRendererView.ViewHolder>(Model.TYPE) {

    override fun bindView(model: Model, holder: ViewHolder, position: Int) = holder.bindView(model)

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(
        ListItemLegacyExampleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClickListener
    )

    /**
     * @property productId идентификатор продукта
     */
    data class Model(
        val productId: String
    ) : BoringModel {

        override fun getId() = productId

        override fun getType() = TYPE

        companion object {
            const val TYPE = R.layout.list_item_legacy_example
        }
    }

    /**
     * @param binding [ListItemLegacyExampleBinding]
     * @param onClickListener слушатель клика
     */
    class ViewHolder(
        private val binding: ListItemLegacyExampleBinding,
        private val onClickListener: (productId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var productId: String

        init {
            itemView.setOnClickListener {
                if (adapterPosition > RecyclerView.NO_POSITION) {
                    onClickListener(productId)
                }
            }
        }

        fun bindView(model: Model) = with(binding) {
            productId = model.productId
            binding.idTextView.text = "id=${model.productId}"
        }
    }
}