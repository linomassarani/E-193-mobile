package org.sc.cbm.e193.praia.insercao.wizard;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.sc.cbm.e193.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PictureAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public PictureAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_cbmsc_logo)
                .showImageForEmptyUri(R.drawable.ic_cbmsc_logo)
                .showImageOnFail(R.drawable.ic_cbmsc_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        data = new ArrayList<String>();
//        data.add("drawable://" + R.drawable.sample_0);
//        data.add("drawable://" + R.drawable.sample_1);
//        data.add("drawable://" + R.drawable.sample_2);
//        data.add("drawable://" + R.drawable.sample_3);
//        data.add("drawable://" + R.drawable.sample_4);
//        data.add("drawable://" + R.drawable.sample_5);
//        data.add("drawable://" + R.drawable.sample_6);
//        data.add("drawable://" + R.drawable.sample_7);
//        data.add("drawable://" + R.drawable.sample_1);
//        data.add("drawable://" + R.drawable.sample_2);
//        data.add("drawable://" + R.drawable.sample_3);
//        data.add("drawable://" + R.drawable.sample_4);
//        data.add("drawable://" + R.drawable.sample_5);

    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    public void removeItem(int position) {
        data.remove(position);
    }

    public void removeItem(String path) {
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).matches(path)) {
                data.remove(i);
                return;
            }
        }
    }

    public void addItem(String path) {
        String prefix = path.contains("file://") ? "" : "file://";
        // inhibit duplicated path on list
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).matches(prefix + path)) {
                return;
            }
        }

        data.add(prefix + path);
    }

    /**
     * Add items
     * @param paths string containing paths separated by commas
     */
    public void addItems(String paths) {
        List<String> pathsList = Arrays.asList(paths.split("\\s*,\\s*"));

        for (String path : pathsList)
            data.add(path);
    }

    /**
     * Get picture's paths string
     *
     * @return ex: "file:///storage/1, file:///storage/2, file:///storage/3", without quotes <br> if list is empty returns null
     */
    public String getPathsCommaSeparated() {
        String pathsList = new String();

        if (data.size() == 0) {
            pathsList = null;
        } else {
            for (int i = 0; i < data.size() - 1; i++) {
                pathsList = pathsList + data.get(i) + ", ";
            }
            pathsList = pathsList + data.get(data.size() - 1);

        }
        System.out.println("Pathlist: " + pathsList);
        return pathsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_grid_picture, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance()
                .displayImage(data.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                        removeItem(imageUri);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                });
        return view;
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}