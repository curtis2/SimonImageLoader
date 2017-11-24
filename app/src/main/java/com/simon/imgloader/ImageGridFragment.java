package com.simon.imgloader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simon.imgloader.loader.ImageLoader;
import com.simon.imgloader.provide.Images;

/**
 *
 */
public class ImageGridFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mGridLayoutManager;
    private int mImageThumbSpacing;
    private DisplayMetrics displayMetrics;
    private int screenWidth;
    private int screenHeight;
    private int itemHeight;

    public ImageGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth=displayMetrics.widthPixels;
        screenHeight=displayMetrics.heightPixels;
        mImageThumbSpacing =getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        itemHeight =(screenWidth-mImageThumbSpacing*3)/3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        recyclerView= (RecyclerView) inflater.inflate(R.layout.fragment_image_grid, container, false);
        mGridLayoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter=new MyAdapter(Images.imageThumbUrls);
        recyclerView.setAdapter(mAdapter);
        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

  private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
      private String[] images;
      public MyAdapter(@Nullable String[] images) {
          if(images==null){
              images=new String[]{};
          }
          this.images = images;
      }

      public class ViewHolder extends RecyclerView.ViewHolder{
          public ImageView imageView;
          public ViewHolder(View itemView) {
              super(itemView);
              this.imageView= (ImageView) itemView;
          }
      }

      @Override
      public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          //怎么自定义view添加
          final ImageView imageView=  new ImageView(getActivity());
//        final ImageView imageView= (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.image_grid_item, parent, false);
          ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
          imageView.setLayoutParams(layoutParams);
          ViewHolder viewHolder=new ViewHolder(imageView);
          return viewHolder;
      }

      @Override
      public void onBindViewHolder(ViewHolder holder, int position) {
          ImageLoader.getmInstance(getActivity()).loadBitmap(images[position],holder.imageView);
      }

      @Override
      public int getItemCount() {
          return images.length;
      }
  }



}
