package com.arife.adnbikerider.AppData.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.GETPERFILUSER.InteractorPerfilUser;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PerfilUserPresenter;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PerfilUserView;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PresenterPerfilUser;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class FragmentProfile extends Fragment implements PerfilUserView, View.OnClickListener {

    private View view;
    private PerfilUserPresenter perfilUserPresenter;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private EditText prfNames, prfLast, prfTlf, prfDir;
    private Button btnEdit, btnSave;
    private PerfilModel perfilModel;
    private TextView profileName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.imageView = view.findViewById(R.id.Img_Profile_base);
        this.circleImageView = view.findViewById(R.id.Img_Circle_Profile);
        this.prfNames = view.findViewById(R.id.Prf_Names);
        this.prfLast = view.findViewById(R.id.Prf_Last);
        this.prfTlf = view.findViewById(R.id.Prf_Telefono);
        this.prfDir = view.findViewById(R.id.Prf_Direccion);
        this.btnEdit = view.findViewById(R.id.btnEditPerfil);
        this.btnSave = view.findViewById(R.id.btnSavePerfil);
        this.profileName = view.findViewById(R.id.tv_Name_Profile);

        this.circleImageView.setOnClickListener(this);

        this.perfilUserPresenter = new PresenterPerfilUser(this, new InteractorPerfilUser());
        this.perfilUserPresenter.OnPerfilUser(this.ChargueData());

        return view;
    }

    @Override
    public void OnSuccesPerfil(PerfilModel perfilModel) {
        this.prfNames.setText(perfilModel.getNombres());
        this.prfLast.setText(perfilModel.getApellidos());
        this.prfTlf.setText(perfilModel.getCelular());
        this.prfDir.setText(perfilModel.getDireccion());
        this.profileName.setText(" "+perfilModel.getApellidos()+" "+perfilModel.getNombres());

    }

    @Override
    public void OnErrorPerfil(String error) {
        Toasty.error(this.getActivity().getApplicationContext(), error, Toasty.LENGTH_SHORT).show();
    }

    public RestModel ChargueData(){
        RestModel restModel = new RestModel();
        restModel.setContext(this.getActivity().getApplicationContext());
        restModel.setLink(Charge.getInstance().getGetPerfil());
        return restModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Img_Profile_base:

                break;
        }
    }
}