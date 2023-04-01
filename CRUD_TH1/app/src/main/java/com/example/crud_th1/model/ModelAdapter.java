package com.example.crud_th1.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_th1.R;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {

    private Context context;
    private List<Model> listBackup;
    private List<Model> mlist;

    private ModelItemListener mModelItem;

    public ModelAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
        listBackup = new ArrayList<>();
    }

    public List<Model> getListBackup() {
        return listBackup;
    }

    public void filterList(List<Model> filterList) {
        mlist = filterList;
        notifyDataSetChanged();
    }

    public void setClickListener(ModelItemListener mModelItem) {
        this.mModelItem = mModelItem;
    }

    public void add(Model model) {
        listBackup.add(model);
        mlist.add(model);
        notifyDataSetChanged();
    }

    public void update(int position, Model model) {
        listBackup.set(position, model);
        mlist.set(position, model);
        notifyDataSetChanged();
    }

    public Model getItem(int position) {
        return mlist.get(position);
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ModelViewHolder(view);
    }

    // Bind object into view holder
    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        Model model = mlist.get(position);
        if(model == null) {
            return;
        }
        holder.img.setImageResource(model.getImg());
        holder.tvName.setText(model.getName());
        holder.tvDescribe.setText(model.getDescribe());
        holder.tvPrice.setText(model.getPrice() + "");

        // Click remove
        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xac nhan xoa");
                builder.setMessage("Ban co chac muon xoa" + model.getName() + "?");
                builder.setIcon(R.drawable.remove);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listBackup.remove(position);
                        mlist.remove(position);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView tvName, tvDescribe, tvPrice;
        private Button btRemove;

        public ModelViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            tvName = view.findViewById(R.id.txtName);
            tvDescribe = view.findViewById(R.id.txtDescribe);
            tvPrice = view.findViewById(R.id.txtPrice);
            btRemove = view.findViewById(R.id.btRemove);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mModelItem != null) {
                mModelItem.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ModelItemListener {
        void onItemClick(View view, int position);
    }
}
