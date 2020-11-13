package com.example.baek.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EunYoung on 2017-01-11.
 */

public class MainViewActivity extends AppCompatActivity {

    public static final int CODE_MAIN = 0;
    public static final int CODE_GAME = 10;
    public static final int CODE_GAME_RESULT = 20;
    public static final int CODE_WEAPON_BUY = 30;
    public static final int CODE_WEAPON_CHANGE = 40;

    ImageView btn_restart;
    ImageView btn_menu;

    public PlayerVO playerVO;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.img_result)
    ImageView imgResult;
    @BindView(R.id.textView_resultCoin)
    TextView textViewResultCoin;
    @BindView(R.id.textView_resultDistance)
    TextView textViewResultDistance;
    @BindView(R.id.btn_menu)
    ImageView btnMenu;
    @BindView(R.id.btn_restart)
    ImageView btnRestart;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //String androidId = Settings.Secure.ANDROID_ID;
//        String androidId = "12345678";
//        setContentView(R.layout.activity_main);
//        contectServer = new ContectServer(androidId);
//        playerVO = new PlayerVO(androidId, null, null, 0 );

        btn_menu = (ImageView) this.findViewById(R.id.btn_menu);
        btn_restart = (ImageView) this.findViewById(R.id.btn_restart);

        btn_menu.setOnClickListener(new ClickImage());
        btn_restart.setOnClickListener(new ClickImage());

        //TODO : 에뮬레이터에서 고유번호 가져올 수 없음
//        Thread serverThread = new Thread() {
//            @Override
//            public void run() {
//                contectServer = new ContectServer("12345678");
//            }
//        };
//        serverThread.start();


        btn_menu.setOnClickListener(new ClickImage());
        btn_restart.setOnClickListener(new ClickImage());

        Intent resultIntent = getIntent();
        textViewResultCoin.setText(resultIntent.getExtras().getInt("coin")+"");
        textViewResultDistance.setText(resultIntent.getExtras().getInt("distance")+"");
    }



    class ClickImage implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id) {
                case R.id.btn_menu:

                    Intent sendIntent = new Intent(getApplicationContext(), FirstMenu_Activity.class);
                    setResult(RESULT_OK, sendIntent);
                    finish();

//                    Intent gameIntent = new Intent(MainViewActivity.this, MainActivity.class);
//                    gameIntent.putExtra("playerVO", contectServer.loadPlayer(playerVO));
                    //startActivityForResult(gameIntent, CODE_MAIN);
                    //   finish();
                    break;

                case R.id.btn_restart:
                    Intent reGame = getIntent();
                    Intent restart = new Intent(MainViewActivity.this, MainActivity.class);
                    restart.putExtra("playerVO", (PlayerVO) reGame.getSerializableExtra("playerVO"));
                    restart.putExtra("restart", 2);
                    startActivityForResult(restart, CODE_MAIN);
//                    startActivity(restart);
                    //    finish();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_MAIN) {
            //data.getExtras()
            if (resultCode == RESULT_OK) {
                textViewResultCoin.setText(data.getExtras().getInt("coin")+"");
                textViewResultDistance.setText(data.getExtras().getInt("distance")+"");

                Log.d("gameResult MainGame : ", "" + data.getExtras().getString("shutDownMainGame"));

                Log.d("gameResult coin : ", "" + data.getExtras().getInt("coin"));
                Log.d("gameResult distance : ", "" + data.getExtras().getInt("distance"));

//                playerVO.setHaveCoin(playerVO.getHaveCoin()+data.getExtras().getInt("coin")+(data.getExtras().getInt("distance") /10));
//                contectServer.savePlayer(contectServer.loadPlayer(playerVO),CODE_GAME_RESULT );
                Log.d("restart", "종료");

            } else {

                Log.d("restart", "종료");

            }
        } else {
            Log.d("restart", "종료");
        }
    }

}
