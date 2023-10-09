package com.example.lgroupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;



public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    int[] images;

    public SliderAdapter(int[] images) {
        this.images=images;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_container, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        viewHolder.imageViewBackground.setImageResource(images[position]);

//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImageUrl())
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);
//
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return images.length;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imageViewS);
            this.itemView = itemView;
        }
    }

}


