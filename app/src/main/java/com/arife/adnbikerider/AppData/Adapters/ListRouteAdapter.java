package com.arife.adnbikerider.AppData.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvc.m.UnionModel;
import com.arife.adnbikerider.mvp.m.interactor.Route.UnionRouteInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRouteInteractor;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRoutePresenter;
import com.arife.adnbikerider.mvp.m.interfaz.UnionRoute.UnionRouteView;
import com.arife.adnbikerider.mvp.p.Route.UnionRoutePresenterImpl;
import com.arife.adnbikerider.mvp.v.MainActivity;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class ListRouteAdapter extends RecyclerView.Adapter<ListRouteAdapter.MyViewHolder>{

    private List<RouteModel> listRoute;
    private Context context;
    private UnionRoutePresenter unionRoutePresenter;
    private UnionRouteView unionRouteView;
    private UnionModel unionModel;

    public ListRouteAdapter(List<RouteModel> listRoute, Context context) {
        this.listRoute = listRoute;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_route,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final RouteModel routeModel = this.listRoute.get(position);
        holder.IdRuta.setText(String.valueOf(routeModel.getId()));
        holder.NameRoute.setText(routeModel.getNameRuta());
        holder.DescRoute.setText(routeModel.getDescRuta());
        holder.Button.setText(routeModel.getEstado());
        holder.Button.setOnClickListener(view ->{
            Log.e("click","click");
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setMessage("¿Quieres unirte a esta ruta?").setTitle("Alerta");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toasty.success(context, "Has seleccionado si", Toasty.LENGTH_SHORT).show();

                    unionModel = new UnionModel();
                    unionModel.setOpcion("N");
                    unionModel.setId(0);
                    unionModel.setIdRuta(routeModel.getId());

                    unionRouteView = new UnionRouteView() {
                        @Override
                        public void OnSuccesUnion(String msg) {
                            Toasty.success(context, "unido");
                            holder.Button.setText("Iniciar");
                        }

                        @Override
                        public void OnErrorUnion(String error) {
                            Toasty.success(context, error);
                        }
                    };

                    unionRoutePresenter = new UnionRoutePresenterImpl(unionRouteView, new UnionRouteInteractorImpl());
                    unionRoutePresenter.OnRegisterUnion(ChargueData("Reg_Union_Group"));
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toasty.success(context, "Has seleccionado no", Toasty.LENGTH_SHORT).show();
                }
            });
            builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public RestModel ChargueData(String command){
        RestModel restModel = new RestModel();
        restModel.setContext(context);
        restModel.setParameters(Charge.getInstance().genUnion(this.unionModel));
        restModel.setLink(Link_Base+command);
        return restModel;
    }

    @Override
    public int getItemCount() {
        return this.listRoute.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private EditText IdRuta;
        private TextView NameRoute, DescRoute;
        private Button Button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IdRuta = itemView.findViewById(R.id.IdRuta);
            NameRoute = itemView.findViewById(R.id.nameRoute);
            DescRoute = itemView.findViewById(R.id.descRoute);
            Button = itemView.findViewById(R.id.button);
        }
    }
}
