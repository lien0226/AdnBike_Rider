package com.arife.adnbikerider.AppData.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arife.adnbikerider.AppData.Fragments.FragmentGroups;
import com.arife.adnbikerider.AppData.Fragments.PendingMove;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Image.ProcessImg;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvp.v.CreateGroup;
import com.arife.adnbikerider.mvp.v.GroupRoutes;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Base_img;

public class ListGroupAdapter extends RecyclerView.Adapter<ListGroupAdapter.MyViewHolder> {

    private List<GroupModel> listGroup;
    private Context context;
    private PendingMove pendingMove;
    private Activity activity;
    private ProcessImg processImg;

    public ListGroupAdapter(List<GroupModel> listGroup, Context context , PendingMove pendingMove, Activity activity) {
        this.listGroup = listGroup;
        this.context = context;
        this.pendingMove = pendingMove;
        this.activity = activity;
        //this.listGroupAll = new ArrayList<>(listGroup);
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
        final GroupModel groupModel = this.listGroup.get(position);
        holder.groupName.setText(groupModel.getGroupName());
        holder.descriptionGroup.setText(groupModel.getGroupDescription());
        holder.idGroup.setText(String.valueOf(groupModel.getId()));

        if (groupModel.getImage()!=null){
            OnImageResponse onImageResponse = new OnImageResponse() {
                @Override
                public void OnImage(Bitmap bitmap) {
                    holder.circleImageView.setImageBitmap(bitmap);
                }

                @Override
                public void OnErrorImage(String error) {
                    holder.circleImageView.setImageResource(R.drawable.img_base);
                }
            };
            processImg = new ProcessImg(this.context, this.activity,onImageResponse);
            processImg.ObtenerImg(Base_img+groupModel.getImage());


        }else{
            holder.circleImageView.setImageResource(R.drawable.img_base);

        }
        holder.linearLayout.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("GroupModel", groupModel);
            holder.linearLayout.getContext().startActivity(new Intent(holder.linearLayout.getContext(), GroupRoutes.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));

            this.pendingMove.PendingMoveAction();
            //getAoverridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        });
    }

    @Override
    public int getItemCount() {
        return this.listGroup.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView groupName, descriptionGroup;
        private EditText idGroup;
        private LinearLayout linearLayout;
        private ImageView imageView;
        private CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.LayoutGroup);
            idGroup = itemView.findViewById(R.id.idGroup);
            groupName = itemView.findViewById(R.id.group_name);
            descriptionGroup = itemView.findViewById(R.id.description_group);
            circleImageView = itemView.findViewById(R.id.img_group);

        }
    }

}
