package io.github.ggface.boring.adapter.presentation.adapter.legacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class RendererView<M extends RendererModel, VH extends RecyclerView.ViewHolder> {

    private final int mViewType;

    public RendererView(final int viewType) {
        mViewType = viewType;
    }

    public void bindView(@NonNull final M item, @NonNull final VH holder, int position, final List payloads) {
    }

    public abstract void bindView(@NonNull M model, @NonNull VH holder, int position);

    @NonNull
    public abstract VH createViewHolder(@NonNull ViewGroup parent);

    @LayoutRes
    public int getType() {
        return mViewType;
    }

    @NonNull
    protected View inflate(final @NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getType(), parent, false);
    }
}