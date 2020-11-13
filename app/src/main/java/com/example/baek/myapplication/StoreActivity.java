package com.example.baek.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EunYoung on 2016-12-30.
 */

public class StoreActivity extends AppCompatActivity {

    @BindView(R.id.textView_money)  TextView textView_money;
    @BindView(R.id.img_btn_save)    ImageView img_btn_save;
    @BindView(R.id.img_item_btn_b)    ImageView img_item_btn_b;
    @BindView(R.id.img_item_btn_c)    ImageView img_item_btn_c;
    @BindView(R.id.img_item_btn_d)    ImageView img_item_btn_d;
    @BindView(R.id.bullet_b)    ImageView bullet_b;
    @BindView(R.id.bullet_c)    ImageView bullet_c;
    @BindView(R.id.bullet_d)    ImageView bullet_d;
    @BindView(R.id.img_btn_bullet_b)    ImageView img_btn_bullet_b;
    @BindView(R.id.img_btn_bullet_c)    ImageView img_btn_bullet_c;
    @BindView(R.id.img_btn_bullet_d)    ImageView img_btn_bullet_d;

    private PlayerVO userVO;
    private WeaponVO weaponVO;
    private BitmapDrawable used;
    private BitmapDrawable use;


    int status_bullet_a = 0;
    int status_bullet_b = 0;
    int status_bullet_c = 0;
    int status_bullet_d = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        userVO = (PlayerVO)intent.getSerializableExtra("playerVO");
        weaponVO = new WeaponVO();
        Log.d("StoreActivity",""+userVO.getUsedWeapon().getWeaponName());

        checkWeapon(userVO.getUsedWeapon().getWeaponName());

    }

    @OnClick({R.id.img_btn_save,
            R.id.img_item_btn_b, R.id.img_item_btn_c,
            R.id.img_btn_bullet_b, R.id.img_btn_bullet_c,
            R.id.img_btn_bullet_d})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_save: //저장한 Intent를 돌려줌.
                Intent result = new Intent(getApplicationContext(), FirstMenu_Activity.class);
                Log.d("tttt",userVO.getUsedWeapon().getWeaponName()+"");
                if(status_bullet_b == 0 && status_bullet_c == 0 && status_bullet_d == 0){
                    weaponVO.setWeaponName("a");
                    userVO.setUsedWeapon(weaponVO);
                    result.putExtra("playerVO", userVO);
                }else {
                    result.putExtra("playerVO", userVO);
                }

                setResult(RESULT_OK , result);
                finish();
                break;

            case R.id.img_item_btn_b:
                break;
            case R.id.img_item_btn_c:
                break;
            case R.id.img_btn_bullet_b:
                if (status_bullet_b == 0){
                    img_btn_bullet_b.setImageDrawable(used);
                    img_btn_bullet_c.setImageDrawable(use);
                    img_btn_bullet_d.setImageDrawable(use);
                    status_bullet_b = 1;
                    status_bullet_c = 0;
                    status_bullet_d = 0;
                    weaponVO.setWeaponName("b");
                    userVO.setUsedWeapon(weaponVO);
                }else if(status_bullet_b == 1){
                    img_btn_bullet_b.setImageDrawable(use);
                    status_bullet_b = 0;

                }

                Log.d("StoreActivity",""+userVO.getUsedWeapon().getWeaponName());
                break;
            case R.id.img_btn_bullet_c:
                if (status_bullet_c == 0){
                    img_btn_bullet_c.setImageDrawable(used);
                    img_btn_bullet_b.setImageDrawable(use);
                    img_btn_bullet_d.setImageDrawable(use);
                    status_bullet_c = 1;
                    status_bullet_b = 0;
                    status_bullet_d = 0;
                    weaponVO.setWeaponName("c");
                    userVO.setUsedWeapon(weaponVO);
                }else if(status_bullet_c == 1){
                    img_btn_bullet_c.setImageDrawable(use);
                    status_bullet_c = 0;

                }
                break;
            case R.id.img_btn_bullet_d:
                if (status_bullet_d == 0){
                    img_btn_bullet_d.setImageDrawable(used);
                    img_btn_bullet_c.setImageDrawable(use);
                    img_btn_bullet_b.setImageDrawable(use);
                    status_bullet_d = 1;
                    status_bullet_c = 0;
                    status_bullet_b = 0;
                    weaponVO.setWeaponName("d");
                    userVO.setUsedWeapon(weaponVO);
                }else if(status_bullet_d == 1){
                    img_btn_bullet_d.setImageDrawable(use);
                    status_bullet_d = 0;

                }
                break;
        }
    }

    public void checkWeapon(String weaponName){
        Resources res = getResources();
        used = (BitmapDrawable)res.getDrawable(R.drawable.used);
        use = (BitmapDrawable)res.getDrawable(R.drawable.use);
        switch (weaponName){
            case "a":
                img_btn_bullet_b.setImageDrawable(use);
                img_btn_bullet_c.setImageDrawable(use);
                img_btn_bullet_d.setImageDrawable(use);
                status_bullet_a =1;
                break;
            case "b":
                img_btn_bullet_b.setImageDrawable(used);
                status_bullet_b = 1;
                break;
            case "c":
                img_btn_bullet_c.setImageDrawable(used);
                status_bullet_c = 1;
                break;
            case "d":
                img_btn_bullet_d.setImageDrawable(used);
                status_bullet_d = 1;
                break;
        }

    }
}
