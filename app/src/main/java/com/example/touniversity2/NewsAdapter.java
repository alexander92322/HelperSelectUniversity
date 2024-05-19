package com.example.touniversity2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements Filterable {

    private List<UniversityNews> universityNewsList;
    private List<UniversityNews> universityNewsListFull;
    private Context context;

    public NewsAdapter(Context context, List<UniversityNews> universityNewsList) {
        this.context = context;
        this.universityNewsList = universityNewsList;
        this.universityNewsListFull=new ArrayList<>(universityNewsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UniversityNews universityNews = universityNewsList.get(position);
        holder.textTitle.setText(universityNews.getTitle());
        holder.textDescription.setText(universityNews.getDescription());


        // Используем Picasso для загрузки изображения из URL и установки его в ImageView
        Picasso.get().load(universityNews.getImageUrl()).into(holder.imageView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем интент для открытия ссылки в браузере
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(universityNews.getUrl())); // Устанавливаем URL из объекта ВУЗа
                v.getContext().startActivity(intent); // Запускаем интент
            }
        });
    }

    @Override
    public int getItemCount() {
        return universityNewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle;
        TextView textDescription;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            button = itemView.findViewById(R.id.button);

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
            List<UniversityNews> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(universityNewsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (UniversityNews item : universityNewsListFull) {
                    // Проверяем условие, по которому будет происходить фильтрация
                    // если название университета содержит введенный текст
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
            universityNewsList.clear();
            universityNewsList.addAll((List<UniversityNews>) results.values);
            notifyDataSetChanged();
        }
    };
}