package io.github.ggface.boring.adapter.presentation.adapter.legacy;

public final class SimpleDiffCallback extends RendererRecyclerAdapter.DiffCallback<RendererModel> {

    @Override
    public boolean areItemsTheSame(RendererModel oldItem, RendererModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(RendererModel oldItem, RendererModel newItem) {
        return oldItem.equals(newItem);
    }
}