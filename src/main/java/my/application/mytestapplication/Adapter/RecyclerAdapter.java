package my.application.mytestapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

import my.application.mytestapplication.CustomFilter;
import my.application.mytestapplication.Model.AddItem;
import my.application.mytestapplication.R;


public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> implements Filterable {
    private Context mContext;
    public List<AddItem> AddItems,filterList;
    private CustomFilter filter;
    private OnItemClickListener mListener;

    public RecyclerAdapter(Context context, List<AddItem> uploads) {
        mContext = context;
        AddItems = uploads;
        filterList=uploads;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.model, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        AddItem currentItem = AddItems.get(position);
        holder.nameTextView.setText(currentItem.getItemname());
        //holder.nameTextView2.setText(currentItem.getItemname());
        holder.locTxt.setText(currentItem.getLocation());
        holder.status.setText(currentItem.getStatus());
        holder.pnum.setText(currentItem.getPnum());
        holder.dateTextView.setText(currentItem.getDate());
        holder.descriptionTextView.setText(currentItem.getDescription());
        Picasso.get()
                .load(currentItem.getImage())
                .placeholder(R.drawable.add_image)
                .fit()
                .centerCrop()
                .into(holder.imgView);

}


    @Override
    public int getItemCount() {
        return AddItems.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView nameTextView, locTxt, status, pnum, dateTextView, descriptionTextView;
        public ImageView imgView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTxt);
            locTxt = itemView.findViewById(R.id.locTxt);
            status = itemView.findViewById(R.id.statusTxt);
            pnum = itemView.findViewById(R.id.pnumTxt);
            pnum.setMovementMethod(LinkMovementMethod.getInstance());
            dateTextView = itemView.findViewById(R.id.dateTxt);
            descriptionTextView = itemView.findViewById(R.id.descTxt);
            imgView = itemView.findViewById(R.id.myimageView);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add(Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onShowItemClick(int position);

        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
