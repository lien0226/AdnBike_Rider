package com.arife.adnbikerider.mvp.v;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.Utilitarios.Image.ProcessImg;
import com.arife.adnbikerider.mvc.m.CityModel;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvp.m.interactor.City.CityInteractorImpl;
import com.arife.adnbikerider.mvp.m.interactor.Group.GroupInteractorImpl;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.City.CityView;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupPresenter;
import com.arife.adnbikerider.mvp.m.interfaz.Group.GroupView;
import com.arife.adnbikerider.mvp.p.City.CityPresenterImpl;
import com.arife.adnbikerider.mvp.p.Groups.GroupPresenterImpl;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.arife.adnbikerider.Utilitarios.Charge.Link_Base;

public class CreateGroup extends AppCompatActivity implements CityView, GroupView, View.OnClickListener {

    private Spinner spinner;
    private CityPresenter cityPresenter;
    private GroupPresenter groupPresenter;
    private Button btnSaveGroup;
    private GroupModel groupModel;
    private EditText groupName, groupDesc;
    private String cod;
    private RadioGroup tipoGrupo;
    private RadioButton publico;
    private RadioButton privado;
    private ImageView imgVwGroup;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Crear Grupo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.spinner = findViewById(R.id.spinner_city);
        this.btnSaveGroup = findViewById(R.id.btnSaveGroup);
        this.groupName = findViewById(R.id.name_group);
        this.groupDesc = findViewById(R.id.description_group);
        this.tipoGrupo = findViewById(R.id.rgTipoGrupo);
        this.privado = findViewById(R.id.rbPrivado);
        this.publico = findViewById(R.id.rbPublico);
        this.imgVwGroup = findViewById(R.id.ImageGroup);
        this.cod = "";

        this.btnSaveGroup.setOnClickListener(this);
        this.imgVwGroup.setOnClickListener(this);

        this.groupPresenter = new GroupPresenterImpl(this, new GroupInteractorImpl());
        this.cityPresenter = new CityPresenterImpl(this, new CityInteractorImpl());

        RestModel restModel = new RestModel();
        restModel.setContext(this);
        restModel.setLink(Link_Base+"get_ubigeo");

        this.cityPresenter.onGetCity(restModel);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSuccess(ArrayList<CityModel> listCity) {

        List<String> cityString = new ArrayList<>();
        for (CityModel cityModel : listCity){
           cityString.add(cityModel.getFormato());
        }

        this.spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityString));
        Log.e("LISTA DE CIUDADES", listCity.toString());
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0){
                    Toasty.error(getApplicationContext(), "Selecciona una opcion", Toasty.LENGTH_SHORT).show();
                }else{
                    //String sCity = parent.getItemAtPosition(position).toString();
                    Log.e("Ciudad ; ",position+" "+listCity.get(position).getCodigo());
                    cod = listCity.get(position).getCodigo();
                    // Toasty.success(getApplicationContext(), listCity.get(position).toString(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void OnError(String error) {
        Toasty.error(this, error).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveGroup:
                this.groupModel = new GroupModel();
                int idRbSelected = tipoGrupo.getCheckedRadioButtonId();
                if (idRbSelected == privado.getId()) {
                    this.groupModel.setTipo("005011");
                } else if (idRbSelected == publico.getId()) {
                    this.groupModel.setTipo("005010");
                } else {
                    Toasty.error(this, "Selecciona una ciudad");
                }
                this.groupModel.setOpcion("N");
                this.groupModel.setId(0);
                this.groupModel.setGroupName(this.groupName.getText().toString());
                this.groupModel.setGroupDescription(this.groupDesc.getText().toString());
                this.groupModel.setUbigeo(cod);
                this.groupModel.setCodEstado("001001");
                this.groupModel.setImage(this.ImgToString(bitmap));
                this.groupPresenter.onRegisterGroup(this.ChargueData(groupModel));
                break;
            case R.id.ImageGroup:
                    this.ChargeImage();
                    Log.e("click","imageview");
                break;
        }
    }

    @Override
    public void OnSuccesGroup(String msg) {
        Toasty.success(this, msg);
        startActivity(new Intent(CreateGroup.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void OnErrorGroup(String error) {
        Toasty.error(this, error);
    }

    public RestModel ChargueData(GroupModel groupModel){
        RestModel restModel = new RestModel();
        restModel.setContext(getApplicationContext());
        restModel.setParameters(Charge.getInstance().genGroup(groupModel));
        restModel.setLink(Link_Base+"Reg_Group");
        return restModel;
    }

    private void ChargeImage(){
        final CharSequence[] options = {"Tomar foto","Abrir galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Tomar foto")){
                    Toasty.success(getApplicationContext(), "tomar foto", Toasty.LENGTH_SHORT).show();
                }else if(options[which].equals("Abrir galeria")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent,"Seleccione"),10);
                }else{
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }

    private ProcessImg processImg;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10 && resultCode == RESULT_OK) {
            Uri patch = data.getData();
            if (patch != null) {
                processImg = new ProcessImg(this, this,null);
                processImg.starCrop(patch);
                //imgVwGroup.setImageURI(patch);

               /*try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), patch);
                    imgVwGroup.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }else if(requestCode == UCrop.REQUEST_CROP && resultCode==RESULT_OK){
                Uri imgUriResultCrop = UCrop.getOutput(data);
                if (imgUriResultCrop!=null){
                    imgVwGroup.setImageURI(imgUriResultCrop);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUriResultCrop);
                        imgVwGroup.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    private String ImgToString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imageString = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return imageString;

    }
}