package com.arife.adnbikerider.AppData.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arife.adnbikerider.AppData.Adapters.OnImageResponse;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.Utilitarios.Image.ProcessImg;
import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.GETPERFILUSER.InteractorPerfilUser;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PerfilUserPresenter;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PerfilUserView;
import com.arife.adnbikerider.mvp.GETPERFILUSER.PresenterPerfilUser;
import com.arife.adnbikerider.mvp.SAVEPERFILUSER.InteractorSaveUser;
import com.arife.adnbikerider.mvp.SAVEPERFILUSER.PresenterSaveUser;
import com.arife.adnbikerider.mvp.SAVEPERFILUSER.SaveUserPresenter;
import com.arife.adnbikerider.mvp.SAVEPERFILUSER.SaveUserView;
import com.arife.adnbikerider.mvp.v.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Base_img_prf;
import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class FragmentProfile extends Fragment implements PerfilUserView, SaveUserView, View.OnClickListener {

    private View view;
    private PerfilUserPresenter perfilUserPresenter;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private EditText prfNames, prfLast, prfTlf, prfDir;
    private Button btnEdit, btnSave;
    private TextView profileName;
    private Bitmap bitmap;
    private MainActivity mainActivity;
    private SaveUserPresenter saveUserPresenter;
    private PerfilModel perfilModel;
    private ProcessImg processImg;

    public FragmentProfile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

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

        this.imageView.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
        this.btnEdit.setOnClickListener(this);

        this.OnEditText(false);

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
        this.profileName.setText(""+perfilModel.getApellidos()+" "+perfilModel.getNombres());
        if (!perfilModel.getImagen().isEmpty()){
            OnImageResponse onImageResponse = new OnImageResponse() {
                @Override
                public void OnImage(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    circleImageView.setImageBitmap(bitmap);
                }

                @Override
                public void OnErrorImage(String error) {
                    imageView.setImageResource(R.drawable.img_base);
                    circleImageView.setImageResource(R.drawable.img_base);
                }
            };
            processImg = new ProcessImg(mainActivity.getApplicationContext(), this.mainActivity, onImageResponse);
            processImg.ObtenerImg(Base_img_prf+perfilModel.getImagen());
        }else{
            imageView.setImageResource(R.drawable.img_base);
            circleImageView.setImageResource(R.drawable.img_base);
        }

    }

    @Override
    public void OnErrorPerfil(String error) {
        Toasty.error(this.getActivity().getApplicationContext(), "sin datos de perfil", Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Img_Profile_base:
                //Toasty.success(this.getActivity().getApplicationContext(), "img_base",Toasty.LENGTH_SHORT).show();
                this.mainActivity.ChargeImage();
                break;
            case R.id.btnSavePerfil:
                /*String name[]  = (prfNames.getText().toString().split(" "));
                String pnombre="";
                String snombre="";
                int a;
                int b;
                Log.e("coun Name", String.valueOf(name.length));
                //this.perfilModel = new PerfilModel();
                if (name.length>1){
                    a = (int) Math.ceil((float) (name.length)/2);
                    Log.e("a",String.valueOf(a));
                    b = (int) (name.length-a);
                    Log.e("b", String.valueOf(b));
                    for (int i=0;i<a;i++){
                        pnombre += name[i]+" ";
                    }
                    for (int j=a; j<name.length; j++){
                        snombre += name[j]+" ";
                    }
                    //this.perfilModel.setNombres(pnombre);
                    Log.e("Nombres","nombre1: "+pnombre+" nombre2:"+snombre);
                    Toasty.success(getActivity().getApplicationContext(), "nombre1: "+pnombre+" nombre2:"+snombre, Toasty.LENGTH_SHORT).show();
                }*/

                processImg = new ProcessImg(getActivity().getApplicationContext(),getActivity(), null );
                this.perfilModel = new PerfilModel();
                this.perfilModel.setId("0");
                this.perfilModel.setNombres(prfNames.getText().toString());
                this.perfilModel.setApellidos(prfLast.getText().toString());
                this.perfilModel.setDireccion(prfDir.getText().toString());
                this.perfilModel.setCelular(prfTlf.getText().toString());
                this.perfilModel.setImagen(processImg.ImgToString(bitmap));
                saveUserPresenter = new PresenterSaveUser(this, new InteractorSaveUser());
                saveUserPresenter.OnSaveUser(this.ChargueDataSP());
                break;
            case R.id.btnEditPerfil:
                this.OnEditText(true);
                break;
        }
    }

    public void OnImageProcess(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
        this.bitmap = bitmap;
    }

    @Override
    public void OnSuccesSaveUser(String msg) {
        Toasty.success(getActivity().getApplicationContext(), "Perfil actualizado", Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void OnErrorUser(String error) {
        Toasty.error(getActivity().getApplicationContext(), error,Toasty.LENGTH_SHORT).show();
    }

    public RestModel ChargueData(){
        RestModel restModel = new RestModel();
        restModel.setContext(this.getActivity().getApplicationContext());
        restModel.setLink(Charge.getInstance().getGetPerfil());
        return restModel;
    }

    public RestModel ChargueDataSP(){
        RestModel restModel = new RestModel();
        restModel.setContext(this.getActivity().getApplicationContext());
        restModel.setParameters(Charge.getInstance().genSavePerfil(perfilModel));
        restModel.setLink(Link_Base+"Reg_Perfil");
        return restModel;
    }

    private void OnEditText(boolean state){
        this.prfNames.setEnabled(state);
        this.prfLast.setEnabled(state);
        this.prfTlf.setEnabled(state);
        this.prfDir.setEnabled(state);
    }
}