package com.example.baek.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import bayaba.engine.lib.GameInfo;

public class MainActivity extends Activity{
	private GLView play;
	private GameMain gMain;
	public GameInfo gInfo;
    public int coin =0;
    public int distance =0;

    int restart = 0; //restart Check

    static boolean gameOver = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Intent intent = getIntent();

        restart = intent.getExtras().getInt("restart");

        gInfo = new GameInfo(480, 800);
        gInfo.ScreenXsize = getResources().getDisplayMetrics().widthPixels;
        gInfo.ScreenYsize = getResources().getDisplayMetrics().heightPixels;
        gInfo.SetScale();

       // if(gameOver){
//            Log.d("gameover","gameover3");
//            Intent resultIntent = new Intent(getApplicationContext(),MainViewActivity.class);
//            resultIntent.putExtra("MainGame","shutDownMainGame");
//            Log.d("gameover","gameover4");
//            setResult(RESULT_OK,resultIntent);
//            finish();
//            Log.d("gameover","gameover6");
//            onDestroy();
//            Log.d("gameover","gameover7");
      //  }else{

            //gMain = new GameMain(this, gInfo);
            gMain = new GameMain(this, gInfo,(PlayerVO)intent.getSerializableExtra("playerVO"));
            play = new GLView(this, gMain);
            play.setRenderer(new SurfaceClass(gMain));
            setContentView(play);

      //  }

//        gMain = new GameMain(this, gInfo);
//        play = new GLView(this, gMain);
//        play.setRenderer(new SurfaceClass(gMain));
//
//        gMain.status = gMain.PAUSE;
//        gMain.status = gMain.RUN;
//         setContentView(play);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
     //   play.requestRender();
        play.onPause();
        //gMain.pauseThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        play.onResume();
       // gMain.resumeThread();
    }

    @Override
    protected void onStart() {
        super.onStart();
        play.requestRender();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        play.onResume();
    }

    @Override
    public void onBackPressed(){
     //   super.onBackPressed();
//        try {
//    //        play.wait();
//        } catch (InterruptedException e) {
//          //  e.printStackTrace();
//        }
            //super.onPause();
            //play.onPause();
        Log.d("backPressed","클릭");
        try {
           // synchronized (gMain.mGL) {
                onPause();
           //    gMain.pauseThread();
         //   }
        } catch (Exception e) {
            Log.d("backPressed","InterruptedException");
        }finally {

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            Log.d("backPressed","클릭1");
            dialog.setTitle("알림").setMessage("게임을 계속하시겠습니까?").setPositiveButton("계속하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //play.requestRender();
                    synchronized (gMain.mGL) {
                        onResume();

                   //     play.on
                        //onRestart();
                        //play.getRenderMode();
                        // gMain.resumeThread();
                        //play.setRenderer(new SurfaceClass(gMain));
                        // play.getPreserveEGLContextOnPause();
                        //play.requestRender();
                        //play.onResume();
                    }
                }
            }).setNeutralButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(123);
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).create().show();

        }
    }
    public void gPause(){
        Log.d("restart",restart+"");
        if(restart == 2){
            Log.d("restart",restart+"");
            Intent restartIntent = new Intent(getApplicationContext(), MainViewActivity.class);
            restartIntent.putExtra("MainGame","shutDownMainGame");
            restartIntent.putExtra("coin",coin);
            restartIntent.putExtra("distance",distance);
            setResult(RESULT_OK,restartIntent);
            finish();

        }else{
            Intent resultIntent = new Intent(getApplicationContext(),FirstMenu_Activity.class);
            resultIntent.putExtra("MainGame","shutDownMainGame");
            Log.d("gameResult :",""+coin+":"+distance);
            resultIntent.putExtra("coin",coin);
            resultIntent.putExtra("distance",distance);
            setResult(RESULT_OK,resultIntent);
            finish();
        }
      //  Log.d("gameover","gameover6");
      //  onStop();
     //   Log.d("gameover","gameover7");
      //  play.onPause();
//        Toast.makeText(getApplicationContext(),"22222222",Toast.LENGTH_SHORT).show();
//        synchronized (gMain){
//            gMain.pauseThread();
//        }
    }
//        new AlertDialog.Builder(MainActivity.this).setTitle("종료").setMessage("종료할까요").setPositiveButton("예", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                setResult(RESULT_OK);
//                System.exit(123);
//
//            }
//        }).setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }).show();



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "Quit");
//        menu.add(0, 2, 0, "Pause");
//        menu.add(0, 3, 0, "Resume");
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case 1:    // 종료
//                System.exit(0);
//                break;
//            case 2:    // 일시 정지
//                gMain.status = gMain.PAUSE;     // class의 public 변수 값 참조
//                break;
//            case 3:    // 계속 진행
//                gMain.status = gMain.RUN;
//        }
//        return true;
//    }

}