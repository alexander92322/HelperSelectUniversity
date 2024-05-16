package com.example.touniversity2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touniversity2.databinding.ItemsBinding;

import java.util.ArrayList;
import java.util.List;


public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityAdapterViewHolder> implements Filterable {
    private List<University> universityList = new ArrayList<>();
    private List<University> universityListFull; // Полный список университетов для поиска
    private OnUniversityClickListener onUniversityClickListener;

    public UniversityAdapter(List<University> items) {
        universityList=items;
        notifyDataSetChanged();
        universityListFull = new ArrayList<>(items); // Создаем копию списка для поиска
        notifyDataSetChanged();
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
        holder.binding.learningForm.setText("Форма обучения: " + university.getLearning_form());
        holder.binding.pointf.setText("Баллы на бюджет: " + university.getPoint_tofree());
        holder.binding.pointp.setText("Баллы на платное: " + university.getPoint_topaid());
        holder.binding.dvi.setText("Внутренние испытания: " + university.getDvi());
        switch (university.getCalled_university()) {
            case "МГУ":
                holder.binding.image.setImageResource(R.drawable.mgu);
                break;
            case "МГТУ":
                holder.binding.image.setImageResource(R.drawable.mgtu);
                break;
            case "МФТИ":
                holder.binding.image.setImageResource(R.drawable.mfti);
                break;
            case "Университет ИТМО":
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
                break;
            case "НИУ ВШЭ":
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
        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем интент для открытия ссылки в браузере
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(university.getLink())); // Устанавливаем URL из объекта ВУЗа
                v.getContext().startActivity(intent); // Запускаем интент
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

    // Реализация интерфейса Filterable
    @Override
    public Filter getFilter() {
        return universityFilter;
    }

    private Filter universityFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<University> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(universityListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (University item : universityListFull) {
                    // Проверяем условие, по которому будет происходить фильтрация
                    // если название университета содержит введенный текст
                    if (item.getCalled_university().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            universityList.clear();
            universityList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
