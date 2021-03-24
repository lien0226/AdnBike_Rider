package com.arife.adnbikerider.AppData.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.mvc.m.GroupModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListGroupAdapter extends RecyclerView.Adapter<ListGroupAdapter.MyViewHolder> {

    private List<GroupModel> listGroup;
    private List<GroupModel> listGroupAll;
    private Context context;

    public ListGroupAdapter(List<GroupModel> listGroup, Context context) {
        this.listGroup = listGroup;
        this.context = context;
        this.listGroupAll = new ArrayList<>(listGroup);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_group,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView groupName, descriptionGroup;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            descriptionGroup = itemView.findViewById(R.id.description_group);
            imageView = itemView.findViewById(R.id.img_group);

        }
    }

}
