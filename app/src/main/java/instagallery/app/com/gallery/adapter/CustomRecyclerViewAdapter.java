package instagallery.app.com.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import instagallery.app.com.gallery.Model.Data;
import rx.Observable;
import rx.subjects.PublishSubject;

public abstract class CustomRecyclerViewAdapter extends RecyclerView
        .Adapter {

    public ArrayList<Data> images;

    public PublishSubject<Object[]> mViewClickSubject = PublishSubject.create();

    public Observable<Object[]> getViewClickedObservable() {
        return mViewClickSubject.asObservable();
    }

    public class CustomRecycleViewHolder extends RecyclerView.ViewHolder {
        public CustomRecycleViewHolder(View itemView) {
            super(itemView);
        }

    }
}