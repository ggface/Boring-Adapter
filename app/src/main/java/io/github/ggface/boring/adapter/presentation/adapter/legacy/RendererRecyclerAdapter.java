package io.github.ggface.boring.adapter.presentation.adapter.legacy;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RendererRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final ArrayList<RendererModel> mItems = new ArrayList<>();
    @NonNull
    private final SparseArray<RendererView> mRenderers = new SparseArray<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final RendererView renderer = mRenderers.get(viewType);
        if (renderer != null) {
            return renderer.createViewHolder(parent);
        }

        throw new RuntimeException("Not supported Item View Type: " + viewType);
    }

    public void registerRenderer(@NonNull final RendererView renderer) {
        final int type = renderer.getType();

        if (mRenderers.get(type) == null) {
            mRenderers.put(type, renderer);
        } else {
            String className = renderer.getClass().getSimpleName();
            throw new RuntimeException(className + " already exist with this type: " + type);
        }
    }

    public void registerAllRenderer(@NonNull final List<RendererView> rendererViews) {
        for (RendererView rendererView : rendererViews) {
            registerRenderer(rendererView);
        }
    }

    public void registerAllRenderer(@NonNull final RendererView... rendererViews) {
        for (RendererView rendererView : rendererViews) {
            registerRenderer(rendererView);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position, @Nullable final List payloads) {
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final RendererModel item = getItem(position);
            final RendererView renderer = mRenderers.get(item.getType());

            if (renderer != null) {
                renderer.bindView(item, holder, position, payloads);
            } else {
                throw new UnsupportedViewHolderException(holder);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final RendererModel item = getItem(position);

        final RendererView renderer = mRenderers.get(item.getType());
        if (renderer != null) {
            renderer.bindView(item, holder, position);
        } else {
            throw new UnsupportedViewHolderException(holder);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        final RendererModel item = getItem(position);
        return item.getType();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T extends RendererModel> T getItem(final int position) {
        return (T) mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @SuppressWarnings("unchecked")
    public void setItems(@NonNull final List<? extends RendererModel> items,
                         @NonNull final DiffCallback diffCallback) {
        diffCallback.setItems(mItems, items);

        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mItems.clear();
        mItems.addAll(items);

        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    public List<RendererModel> getItems() {
        return mItems;
    }

    public void setItems(@NonNull final List<? extends RendererModel> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public abstract static class DiffCallback<BM extends RendererModel> extends DiffUtil.Callback {
        private final List<BM> mOldItems = new ArrayList<>();
        private final List<BM> mNewItems = new ArrayList<>();

        void setItems(@NonNull final List<BM> oldItems, @NonNull final List<BM> newItems) {
            mOldItems.clear();
            mOldItems.addAll(oldItems);

            mNewItems.clear();
            mNewItems.addAll(newItems);
        }

        @Override
        public int getOldListSize() {
            return mOldItems.size();
        }

        @Override
        public int getNewListSize() {
            return mNewItems.size();
        }

        @Override
        public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
            return areItemsTheSame(
                    mOldItems.get(oldItemPosition),
                    mNewItems.get(newItemPosition)
            );
        }

        public abstract boolean areItemsTheSame(final BM oldItem, final BM newItem);

        @Override
        public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
            return areContentsTheSame(mOldItems.get(oldItemPosition), mNewItems.get(newItemPosition));
        }

        public abstract boolean areContentsTheSame(final BM oldItem, final BM newItem);

        @Nullable
        @Override
        public Object getChangePayload(final int oldItemPosition, final int newItemPosition) {
            final Object changePayload = getChangePayload(mOldItems.get(oldItemPosition), mNewItems.get(newItemPosition));
            if (changePayload == null) {
                return super.getChangePayload(oldItemPosition, newItemPosition);
            }
            return changePayload;
        }

        @Nullable
        public Object getChangePayload(final BM oldItem, final BM newItem) {
            return null;
        }
    }

    private final static class UnsupportedViewHolderException extends RuntimeException {
        UnsupportedViewHolderException(@NonNull final RecyclerView.ViewHolder holderName) {
            super("Not supported View Holder: " + holderName.getClass().getSimpleName());
        }
    }
}