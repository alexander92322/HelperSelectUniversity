package com.example.touniversity2;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touniversity2.databinding.ItemsBinding;

import java.util.ArrayList;
import java.util.List;


public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityAdapterViewHolder> {
    private List<University> universityList = new ArrayList<>();
    private OnUniversityClickListener onUniversityClickListener;

    public UniversityAdapter(List<University> items) {
        universityList=items;
    }

    public void setOnUniversityClickListener(OnUniversityClickListener onUniversityClickListener) {
        this.onUniversityClickListener = onUniversityClickListener;
    }

    interface OnUniversityClickListener {
        void onUniversityClick(University university);
    }
    @NonNull
    @Override
    public UniversityAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);

        return new UniversityAdapterViewHolder(ItemsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityAdapterViewHolder holder, int position) {
        University university = universityList.get(position);
        holder.binding.title.setText("Университет: "+university.getCalled_university());
        holder.binding.program.setText("Программа:"+'\n'+university.getCalled_program());
        holder.binding.city.setText("Город: "+university.getCity());
        switch (university.getCalled_university()) {
            case "МГУ":
                holder.binding.image.setImageResource(R.drawable.mgu);
                break;
            case "МГТУ":
                holder.binding.image.setImageResource(R.drawable.mgtu);
                break;
            case "МИФИ":
                holder.binding.image.setImageResource(R.drawable.mifi);
            case "МФТИ":
                holder.binding.image.setImageResource(R.drawable.mfti);
                break;
            case "ИТМО":
                holder.binding.image.setImageResource(R.drawable.itmo);
                break;
            case "НГУ":
                holder.binding.image.setImageResource(R.drawable.ngu);
                break;
            case "НГТУ":
                holder.binding.image.setImageResource(R.drawable.ngtu);
                break;
            case "УРФУ":
                holder.binding.image.setImageResource(R.drawable.urfu);
                break;
            case "СПБГУ":
                holder.binding.image.setImageResource(R.drawable.spbgu);
            case "ВШЭ":
                holder.binding.image.setImageResource(R.drawable.vshe);
                break;
            case "ТГУ":
                holder.binding.image.setImageResource(R.drawable.tgu);
                break;
            default:
                holder.binding.image.setImageResource(R.drawable.other);
        }



        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUniversityClickListener != null) {
                    onUniversityClickListener.onUniversityClick(university);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }


    static class UniversityAdapterViewHolder extends RecyclerView.ViewHolder{
        private final ItemsBinding binding;
        public UniversityAdapterViewHolder(ItemsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
