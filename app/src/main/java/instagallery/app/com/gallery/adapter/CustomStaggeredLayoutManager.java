package instagallery.app.com.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public class CustomStaggeredLayoutManager extends StaggeredGridLayoutManager {

    private boolean isScrollEnabled = true;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens");
        }
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

            public CustomStaggeredLayoutManager(int spanCount, int orientation) {
                super(spanCount,orientation);
            }

         }