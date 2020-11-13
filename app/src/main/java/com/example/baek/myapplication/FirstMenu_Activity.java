package com.example.baek.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstMenu_Activity extends AppCompatActivity {

    @BindView(R.id.btn_start)
    ImageButton btnStart;
    @BindView(R.id.btn_store)
    ImageButton btnStore;
    @BindView(R.id.btn_exit)
    ImageButton btnExit;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;


    public static final int CODE_ITEMSHOP = 1;
    public static final int CODE_GAME_RESULT = 2;
    public static final int CODE_GAME_ACTIVITY = 3;

    public static final int CODE_FIRST_MENU = 10;
    public static final int CODE_MAIN = 0;
    public static final int CODE_GAME = 10;
    public static final int CODE_WEAPON_BUY = 30;
    public static final int CODE_WEAPON_CHANGE = 40;

    ContectServer contectServer;
    public static PlayerVO playerVO;
    public static WeaponVO weaponVO = new WeaponVO();
    public static ArrayList<WeaponVO> haveWeapons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_menu_);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String androidId = "12345678";
        //contectServer = new ContectServer(androidId);

        weaponVO.setWeaponName("b");
        playerVO = new PlayerVO(androidId, weaponVO, null, 999999 );
        ButterKnife.bind(this); //자동 findViewById

    }

    @OnClick({R.id.btn_start, R.id.btn_store, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent gameIntent = new Intent(FirstMenu_Activity.this, MainActivity.class);
                gameIntent.putExtra("playerVO", playerVO);
                gameIntent.putExtra("restart",0);
                startActivityForResult(gameIntent, CODE_GAME_ACTIVITY);

                break;

            case R.id.btn_store:
                Intent storeIntent = new Intent(FirstMenu_Activity.this, StoreActivity.class);
                storeIntent.putExtra("playerVO",playerVO);
                startActivityForResult(storeIntent, CODE_ITEMSHOP);

                break;

            case R.id.btn_exit:
                System.exit(18);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_GAME_ACTIVITY){
            if(resultCode == RESULT_OK){
                Log.d("FirstMenu_Activity","정상적인 GameMain 종료");
                Intent resultIntent = new Intent(FirstMenu_Activity.this, MainViewActivity.class);
                Log.d("FirstMenu_Activity","정상적인 GameMain 종료" + data.getExtras().getInt("coin"));
                Log.d("FirstMenu_Activity","정상적인 GameMain 종료" + data.getExtras().getInt("distance"));

                resultIntent.putExtra("coin",data.getExtras().getInt("coin"));
                resultIntent.putExtra("distance", data.getExtras().getInt("distance"));
                resultIntent.putExtra("playerVO",playerVO);

                startActivityForResult(resultIntent, CODE_GAME_RESULT);
            }
        }else if(requestCode == CODE_GAME_RESULT){
            Log.d("FirstMenu_Activity","정상적인 GameResultActivity 종료");
        }else if(requestCode == CODE_ITEMSHOP){
            Log.d("FirstMenu_Activity"," ItemShopActivity 종료");
            playerVO = (PlayerVO) data.getExtras().getSerializable("playerVO");
            Log.d("FirstMenu_Activity"," ItemShopActivity 종료" + playerVO.getUsedWeapon().getWeaponName());
        }
    }
}
