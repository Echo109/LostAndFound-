package my.application.mytestapplication;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import my.application.mytestapplication.Adapter.RecyclerAdapter;
import my.application.mytestapplication.Model.AddItem;

public class CustomFilter extends Filter {

    RecyclerAdapter adapter;
    List<AddItem> filterList;

public CustomFilter(List<AddItem> filterList, RecyclerAdapter adapter)
        {
        this.adapter=adapter;
        this.filterList=filterList;

        }

//FILTERING OCURS
@Override
protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
        //CHANGE TO UPPER
        constraint=constraint.toString().toUpperCase();
        //STORE OUR FILTERED PLAYERS
        List<AddItem> filteredUploads=new ArrayList<>();

        for (int i=0;i<filterList.size();i++)
        {
        //CHECK
        if(filterList.get(i).getItemname().toUpperCase().contains(constraint))
        {
        //ADD PLAYER TO FILTERED PLAYERS
            filteredUploads.add(filterList.get(i));
        }
        }

        results.count=filteredUploads.size();
        results.values=filteredUploads;
        }else
        {
        results.count=filterList.size();
        results.values=filterList;

        }

        return results;
        }

@Override
protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.AddItems= (List<AddItem>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
        }
}

