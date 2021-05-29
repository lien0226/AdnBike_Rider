package com.arife.adnbikerider.mvp.v;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.arife.adnbikerider.AppData.Adapters.ViewPagerAdapter;
import com.arife.adnbikerider.AppData.Fragments.FragmentGroups;
import com.arife.adnbikerider.AppData.Fragments.FragmentMyRoutes;
import com.arife.adnbikerider.AppData.Fragments.FragmentPost;
import com.arife.adnbikerider.AppData.Fragments.FragmentProfile;
import com.arife.adnbikerider.AppData.Fragments.PendingMove;
import com.arife.adnbikerider.AppData.Sesion;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Image.ProcessImg;
import com.google.android.material.tabs.TabLayout;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements PendingMove {
    private TabLayout tabLayoutHome;
    private ViewPager viewPagerHome;
    private ViewPagerAdapter pagerAdapter;
    private FragmentProfile fragmentProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tabLayoutHome = findViewById(R.id.tab_layout_home);
        this.viewPagerHome = findViewById(R.id.view_pager_home);
        this.pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Sesion.getInstance().readSesion().getName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.fragmentProfile = new FragmentProfile(this);
        this.viewPagerHome.setOffscreenPageLimit(3);
        this.pagerAdapter.AddFragment(new FragmentGroups(this),"");
        this.pagerAdapter.AddFragment(new FragmentMyRoutes(),"");
        this.pagerAdapter.AddFragment(new FragmentPost(),"");
        this.pagerAdapter.AddFragment(this.fragmentProfile, "");

        this.viewPagerHome.setAdapter(this.pagerAdapter);
        this.tabLayoutHome.setupWithViewPager(this.viewPagerHome);

        this.tabLayoutHome.getTabAt(0).setIcon(R.drawable.ic_network);
        this.tabLayoutHome.getTabAt(1).setIcon(R.drawable.ic_placeholder);
        this.tabLayoutHome.getTabAt(2).setIcon(R.drawable.ic_social_media);
        this.tabLayoutHome.getTabAt(3).setIcon(R.drawable.ic_person);
    }

    @Override
    public void PendingMoveAction() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
    private ProcessImg processImg;

    public void ChargeImage(){
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
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Seleccione"),10);
                }else{
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri patch = data.getData();
            if (patch != null) {
                processImg = new ProcessImg(this, this, null);
                processImg.starCrop(patch);
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Log.e("Ucrop","data ucrop");
            Uri imgUriResultCrop = UCrop.getOutput(data);
            if (imgUriResultCrop != null) {
                // imageView.setImageURI(imgUriResultCrop);
                // no sale ni mrd no te digo que la laptop esta kk
                //efe el frio
                //estas como liz ya V: tiene frio la laptop dice por eso se pone lenta
                //si pe:v
                try {
                  this.fragmentProfile.OnImageProcess(MediaStore.Images.Media.getBitmap(getContentResolver(), imgUriResultCrop));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toasty.error(this, e.getMessage(),Toasty.LENGTH_SHORT).show();
                    Log.e("exceptio", e.getMessage());
                }
            }
        }else{
            Log.e("error", "not found");
        }
    }
}
