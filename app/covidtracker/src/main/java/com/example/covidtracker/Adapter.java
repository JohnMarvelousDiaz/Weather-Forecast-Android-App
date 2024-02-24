package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    int x = 1;
    Context context;
    List<ModelClass> countrylist;

    public Adapter(Context m, Context context, List<ModelClass> countrylist) {
        this.x = x;
        this.context = context;
        this.countrylist = countrylist;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_items, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelClass modelClass = countrylist.get(position);
        if(x == 1)
        {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getCases())));
        }
        else if(x == 2)
        {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getRecovered())));
        }
        else if(x == 3)
        {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getDeaths())));
        }
        else
        {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getActive())));
        }

        holder.country.setText(modelClass.getCountry());
    }

    @Override
    public int getItemCount() {
        return countrylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cases, country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cases = itemView.findViewById(R.id.countrycase);
            country = itemView.findViewById(R.id.countryname);
        }
    }

    public void filter(String charText)
    {
        if(charText.equals("cases"))
        {
            x = 1;
        }
        else if(charText.equals("recovered"))
        {
            x = 2;
        }
        else if(charText.equals("deaths"))
        {
            x = 3;
        }
        else{
            x = 4;
        }

        notifyDataSetChanged();

    }
}
