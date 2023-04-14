package io.github.ggface.boring.adapter.presentation.adapter.ktx

/**
 * @author Ivan Novikov on 2023-04-14.
 */
class BoringSimpleDiffCallback : BoringDiffCallback<BoringModel>() {

    override fun areItemsTheSame(oldItem: BoringModel, newItem: BoringModel): Boolean {
        return oldItem.getId() == newItem.getId()
    }

    override fun areContentsTheSame(oldItem: BoringModel, newItem: BoringModel): Boolean {
        return oldItem == newItem
    }
}