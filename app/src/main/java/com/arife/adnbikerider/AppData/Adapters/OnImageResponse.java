package com.arife.adnbikerider.AppData.Adapters;

import android.graphics.Bitmap;

public interface OnImageResponse {
    void OnImage(Bitmap bitmap);
    void OnErrorImage(String error);
    //nel pe chamo asi no se usan las interfaces
    //esta igual que el de ServerRestResponse :v pero ese si instancia su modelompor eso si funca
}
