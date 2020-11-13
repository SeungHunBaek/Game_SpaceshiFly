package com.example.baek.myapplication;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class GameMain{
	public GL10 mGL = null;
	public Context MainContext;
	public Random MyRand = new Random();
	public GameInfo gInfo;
	public float TouchX, TouchY;

	public PlayerVO userVO = null;

	int distance=0; //거리
	int getCoin=0; //코인

	// TODO: 2017-01-12 0. 주인공 공격력 조절(완료)
	float power =20.0f;//공격력 20.0f, 40.0f, 50.0f

	// TODO: 2017-01-12 1. 몬스터 이동속도 조절(완료)
	float monsterSpeed =  2.0f; //2.0f, 3.0f, 4.0f

	// TODO: 2017-01-12 2. 몬스터 생성속도 조절(완료)
	int createMonsterTimer = 5000; //5000ms. 4000ms, 2000ms

	// TODO: 2017-01-12 3. 몬스터 에너지 조절(완료)
	float plusEnergy = 1.0f;

	// TODO: 2017-01-13 4. 돌맹이 생성속도 조절(진행중)
	int createWarningTimer = 15000;

	public static final int RUN =1;
	public static final int PAUSE=2;
	public int status = RUN;

	private Font font = new Font();
	public static boolean gameover = false;
	boolean user = false;

	Sprite backSpr = new Sprite();
	Sprite heroSpr = new Sprite();
	Sprite monSpr = new Sprite();
	Sprite dustSpr = new Sprite();
	Sprite coinSpr = new Sprite();

	Sprite back_p = new Sprite();
	Sprite bullet_spr_a = new Sprite();
	Sprite bullet_spr_b = new Sprite();
	Sprite bullet_spr_c = new Sprite();
	Sprite bullet_spr_d = new Sprite();

	Sprite warningSpr = new Sprite();
	Sprite troubleSpr = new Sprite();

	GameObject backObj = new GameObject();
	ArrayList<GameObject> game = new ArrayList<>();//배경
	ArrayList<GameObject> bullet = new ArrayList<>();
	ArrayList<GameObject> monster = new ArrayList<>();
	ArrayList<GameObject> effect = new ArrayList<>();
	ArrayList<GameObject> coin = new ArrayList<>();
	ArrayList<GameObject> warning = new ArrayList<>();
	ArrayList<GameObject> trouble = new ArrayList<>();

 	GameObject myHero = new GameObject();
	ButtonObject selectButton = new ButtonObject();
	GameObject menu = new GameObject();

	public GameMain(Context context, GameInfo info)  	{
		MainContext = context;
		gInfo = info;
	}
	public GameMain(Context context, GameInfo info, PlayerVO playVO)  	{
		userVO = playVO;
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()	{
		backSpr.Antialiasing = false;
//		backSpr.LoadSprite(mGL,MainContext,"back.spr");
		backSpr.LoadSprite(mGL,MainContext,"back_space.spr");

		//monSpr.LoadSprite(mGL,MainContext,"monster.spr");
		monSpr.LoadSprite(mGL,MainContext,"monster_b.spr");
		//dustSpr.LoadSprite(mGL,MainContext,"dust.spr");
		dustSpr.LoadSprite(mGL,MainContext,"explosions.spr");
		coinSpr.LoadSprite(mGL,MainContext,"coin.spr");

		//기본 공격력 내장.
		//heroSpr.LoadSprite(mGL,MainContext,"hero.spr");

		//이미지 변경 hero
//		heroSpr.LoadSprite(mGL,MainContext,"hero_a.spr");
		heroSpr.LoadSprite(mGL,MainContext,"user_f.spr");
		//1단
		bullet_spr_a.LoadSprite(mGL,MainContext,"bullet_a.spr");
		//2단
		bullet_spr_b.LoadSprite(mGL,MainContext,"bullet_b.spr");
		//3단
		bullet_spr_c.LoadSprite(mGL,MainContext,"bullet_c.spr");
		//궁극
		bullet_spr_d.LoadSprite(mGL,MainContext,"bullet_d.spr");
		//경고표시
		warningSpr.LoadSprite(mGL,MainContext,"warning.spr");
		//트러블 생성.
		troubleSpr.LoadSprite(mGL,MainContext,"trouble.spr");

		back_p.LoadSprite(mGL,MainContext,"back_p.spr");
		selectButton.SetButton(back_p, ButtonType.TYPE_ONE_CLICK,0,220,380,0);

		for (int i = 0; i < 3; i++){
			GameObject temp = new GameObject();
 			temp.SetObject(backSpr,0,0,240,400 - (i * 800),0,0);
			game.add(temp);
		}
		myHero.SetObject(heroSpr,0,0,240,700,0,8);
		makeMonster();
	}

	public void pushButton(boolean push ){
		if(push){
			myHero.x = TouchX;
		}
	}
	public void selectButton(Boolean push){ //test용 버튼이 일시정지 및 종료
		selectButton.CheckButton(gInfo,push,TouchX,TouchY);
	}

	void makeBullet(){
		if (System.currentTimeMillis() - myHero.timer >= 100 ){// 0.1초마다 미사일을 만듦.

			GameObject temp = new GameObject();
			if(userVO.getUsedWeapon().getWeaponName().equals("a")){//1단 미사일
				temp.SetObject(heroSpr,0,0, myHero.x, myHero.y - 50 ,1,0);//주인공이랑 같이 배치되어있는 경우
				temp.SetObject(bullet_spr_a,0,0, myHero.x, myHero.y - 50 ,0,0);//주인공이랑 다르게 spr되어있는 경우
 				power =10f;
			}else if(userVO.getUsedWeapon().getWeaponName().equals("b")){//2단 미사일
				temp.SetObject(bullet_spr_b,0,0, myHero.x, myHero.y - 50 ,0,0);
 				power =30f;
			}else if(userVO.getUsedWeapon().getWeaponName().equals("c")){//3단 미사일
				temp.SetObject(bullet_spr_c,0,0, myHero.x, myHero.y - 50 ,0,0);
				power =30f;
			}else if(userVO.getUsedWeapon().getWeaponName().equals("d")){//4단 미사일
				temp.SetObject(bullet_spr_d,0,0, myHero.x, myHero.y - 50 ,0,0);
				power =70f;
			}else {
				temp.SetObject(heroSpr,0,0, myHero.x, myHero.y - 50 ,1,0);//주인공이랑 같이 배치되어있는 경우
				temp.SetObject(bullet_spr_a,0,0, myHero.x, myHero.y - 50 ,0,0);//주인공이랑 다르게 spr되어있는 경우
 				power =10f;
			}

		//	if(userVO.getUsedWeapon().getWeaponName().equals("b")){
			//temp.SetObject(bullet_spr_b,0,0, myHero.x, myHero.y - 50 ,0,0);
			//}
			//temp.SetObject(bullet_spr_c,0,0, myHero.x, myHero.y - 50 ,0,0);
			//temp.SetObject(bullet_spr_d,0,0, myHero.x, myHero.y - 50 ,0,0);

			bullet.add(temp);
			myHero.timer = System.currentTimeMillis();
		}
	}
//  backUp method
//	void makeBullet(){
//		if (System.currentTimeMillis() - myHero.timer >= 100 ){
//
//			GameObject temp = new GameObject();
//			temp.SetObject(heroSpr,0,0, myHero.x, myHero.y - 50 ,1,0);
//			bullet.add(temp);
//			myHero.timer = System.currentTimeMillis();
//		}
//	}

	long MonsterTimer = 0;
	void makeMonster(){
		if (System.currentTimeMillis() - MonsterTimer >= createMonsterTimer){ //5초마다 몬스터 생성.
			for ( int i = 0; i<5; i++) {
				GameObject temp = new GameObject();
				temp.SetObject(monSpr, 0, 0, 50 + (i * 100 ), -30, 0, 0);
				monster.add(temp);
			}
			MonsterTimer = System.currentTimeMillis();
		}
	}

	public GameObject CheckDamege(GameObject target){// 사각형 충돌 확인.
		for (int i = 0; i< bullet.size(); i++ ){
			bullet.get(i).PutSprite(gInfo);
			if (gInfo.CrashCheck(target, bullet.get(i),10,0)){ //좌표로부터 10정도 차이가 나면 충돌
				return  bullet.get(i);
			}
		}
		return null;
	}

	public GameObject getMoney(GameObject money) { //돈을 먹은경우.

		for (int i = 0; i < coin.size(); i++) { //
			//	Coin.get(i).PutSprite(gInfo);
			if (gInfo.CrashCheck(myHero,coin.get(i),10,10)){
				Log.i("bsh","get money" + getCoin);
				return coin.get(i);
			}
		}
		return null;
	}

	public boolean checkUser(){//몬스터랑 충돌한 경우.
		for (int i =0; i< monster.size(); i++){
			monster.get(i).PutSprite(gInfo);
			if (gInfo.CrashCheck(myHero, monster.get(i),-45,0)){ //좌표로부터 45정도 차이가 나면 충돌
				return  true;
			}
		}
		return false;
	}
	public boolean checkUser_trouble(){
		for (int i = 0; i < trouble.size(); i ++){
			trouble.get(i).PutSprite(gInfo);
			if (gInfo.CrashCheck(myHero,trouble.get(i),-40,0)){
				return true;
			}
		}
		return false;
	}


	public void makeEffect(GameObject target){
		for (int i = 0; i< 20; i++){
			GameObject temp = new GameObject();
			temp.SetObject(dustSpr, 0,0,target.x, target.y ,0 ,0);

			float scale = 0.3f + ((float)MyRand.nextInt(9)/10);
			temp.SetZoom(gInfo,scale,scale);
			temp.direct = MyRand.nextInt(360);
			effect.add(temp);
		}
//		GameObject temp = new GameObject();
//		temp.SetObject(coinSpr,1,0,target.x, target.y ,0 ,0);
//		temp.power = 1000;
//		Effect.add(temp);
	}
	public void makeMoney(GameObject money){
		GameObject temp = new GameObject();
		temp.SetObject(coinSpr,1,0,money.x, money.y ,0 ,0);
		temp.power = 1000;

		coin.add(temp);
	}

	public void levelControll(){//주행거리별 난이도 조절
		if (1000 <= distance  &&  distance <=1999){
			monsterSpeed = 1.5f; //몬스터 내려오는 속도 + 돌덩이 내려오는 속도
			createMonsterTimer = 5000;//몬스터 생성속도 + 돌덩이 생성되는 속도(보류중)
			plusEnergy = 1.0f;// 몬스터 에너지 증가
		}else if(2000 <= distance  &&  distance <=2999){
			monsterSpeed = 2.5f;
			createMonsterTimer = 4500;
			plusEnergy = 0.4f;
		}else if(3000 <= distance  &&  distance <=3999){
			monsterSpeed = 4.0f;//시연용
			createMonsterTimer = 2000;//시연용
			plusEnergy = 0.1f;//시연용
// 			monsterSpeed = 3.0f;
//			createMonsterTimer = 4000;
//			plusEnergy = 0.2f;
		}else if(4000 <= distance  &&  distance <=4999){
			monsterSpeed = 3.5f;
			createMonsterTimer = 3800;
			plusEnergy = 0.1f;
		}else if(5000 <= distance  &&  distance <=5999){
			monsterSpeed = 4.0f;
			createMonsterTimer = 3000;
			plusEnergy = 0.1f;
		}else if(6000 <= distance  &&  distance <=6999){
			monsterSpeed = 4.4f;
			createMonsterTimer = 3000;
			plusEnergy = 0.1f;
		}
	}




	private void dialogSimple(){

		for (int i = 0 ; i < 100; i ++){
//			Log.d("StopGame", i+"StopGame" );
		}
	}

//		AlertDialog.Builder alert = new AlertDialog.Builder(MainContext);
//		alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();     //닫기
//			}
//		});
//		alert.setMessage("테스트 메세지");
//		alert.show();

	public boolean gameStop(){
		Log.d("gameStop","일시정지 ");
		boolean result = false;
		font.BeginFont(gInfo);
		Log.d("gameStop","일시정지1 ");

		selectButton.DrawSprite(mGL,0,gInfo,font);
		Log.d("gameStop","일시정지2 ");
		if(selectButton.click == ButtonType.STATE_CLK_BUTTON){
			selectButton.ResetButton();
		    Log.d("gameStop","일시정지3 ");
		}
		font.EndFont(gInfo);
		mGL.notify();

		return result;
	}



	long warningTimer = 0;
	long removeWarningTimer = 0;
	ArrayList<Float> x = null;
	public void warning(){//경고 메세지 생성
		if(System.currentTimeMillis() - warningTimer >=3000 ){//3초마다 경고 생성.
			float randomX =0.0f;
			int tempX = 0;
			x = new ArrayList<>();
			for (int i = -1; i <2; i++){
				GameObject temp = new GameObject();
				randomX = myHero.x+(i* (float)Math.random()*300 +80);
				x.add(randomX);
				temp.SetObject(warningSpr,0,0, randomX,60.0f,0,0);
				warning.add(temp);
			}
			makeTrouble(x);

//		if(System.currentTimeMillis() - warningTimer >=(3000 + 2000)){ //경고가 생성된후 2초 뒤에 운석생성.
//				Log.d("test","test2");
//		}
			removeWarningTimer = System.currentTimeMillis();
			warningTimer = System.currentTimeMillis();
		}
	}
	public void removeWarning(int i){
		if(System.currentTimeMillis() - removeWarningTimer >=1000 ){//1000ms 뒤에 경고삭제
				Log.d("운석","5000ms 뒤에 경고삭제");
				warning.get(i).dead = true;
				//warning.get(1).dead = true;
				//warning.get(2).dead = true;

				warning.remove(i);
				waitingTime = System.currentTimeMillis();
		}
	}

	long waitingTime = 0;
	public void makeTrouble(ArrayList<Float> randomX){
		if(System.currentTimeMillis() - waitingTime >=1000){
			for (int i = 0; i <randomX.size(); i++){
				GameObject temp_a = new GameObject();
				temp_a.SetObject(troubleSpr,0,0,randomX.get(i),-0.0f,0,0);
				trouble.add(temp_a);
			}
			waitingTime = System.currentTimeMillis();
		}
	}


	public void DoGame(){
		synchronized ( mGL )		{
			System.gc();

			font.BeginFont(gInfo);
			user = checkUser();//monster랑 충돌한 경우.
			if (checkUser() || checkUser_trouble() ){
				Log.i("bsh","맞음");
			//	selectButton.DrawSprite(mGL,0,gInfo,font);
				gameover = true;
			}

			for (int i = 0; i< game.size(); i++ ){
				game.get(i).y++;
				game.get(i).DrawSprite(gInfo);
//				if (user ){
//					Log.i("bsh","쳐맞음");
//					selectButton.DrawSprite(mGL,0,gInfo,font);
//					gameover = true;
//				}
				if (game.get(i).y1 >= gInfo.ScreenY){//화면 밖으로 벗어났는가 확인.
					game.get(i).y -= 1920; //벗어났으면 다시 위로 올림. 화면 사이즈의 3배수가 자연스러운듯
				}
				System.gc();//gc call
			}

			if(gameover){
				//selectButton.DrawSprite(mGL,0,gInfo,font);
				try {
					((MainActivity)MainContext).coin = getCoin;
					((MainActivity)MainContext).distance = distance;
					//mGL.wait();
					//MainContext.wait();
					Log.d("gameover","gameover1");
					((MainActivity)MainContext).gameOver = true;
					gameover = false;
					mGL.wait(500);
					((MainActivity)MainContext).gPause();
					Log.d("gameover","gameover2");
					//pauseThread();
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
				//	selectButton.DrawSprite(mGL,0,gInfo,font);
				}
				dialogSimple();
			}

//			if (distance >= 1000){//주행거리가 길어지면
//				monsterSpeed = 5.0f;
//				createMonsterTimer = 1000;
//			}

			for (int i = 0; i < warning.size(); i++){
				warning.get(i).DrawSprite(gInfo);
		//		warning.get(i).AddFrameLoop(0.);

				removeWarning(i);
//				if(System.currentTimeMillis()- removeWarning >=2000){//2000ms 뒤에 운석생성
//					Log.d("운석","2000ms 뒤에 운석생성");
//					trouble.get(i).y += 8.0f;
//					trouble.get(i).DrawSprite(gInfo);
//					// TODO: 2017-01-13 삭제 및 메모리처리.
//					if (trouble.get(i).y2  < 0 ){
//						Log.d("운석","화면 밖으로 나가면 운석삭제");
//						trouble.get(i).dead = true;
//						trouble.clear();
//					}
//				}
//				if(System.currentTimeMillis() - removeWarning >=4000 ){//5000ms 뒤에 경고삭제
//					Log.d("운석","5000ms 뒤에 경고삭제");
//					warning.get(0).dead = true;
//					warning.get(1).dead = true;
//					warning.get(2).dead = true;
//
//					warning.clear();
//					removeWarning = System.currentTimeMillis();
//				}
			}
			for (int i = 0; i < trouble.size(); i++){
				trouble.get(i).y += (monsterSpeed + 5.0f);
				trouble.get(i).DrawSprite(gInfo);

				if(trouble.get(i).y1 >  gInfo.ScreenY + 100){
					trouble.get(i).dead = true;
					trouble.remove(i--);
				}
			}

			levelControll();
			for (int i = 0; i<monster.size(); i++){
				// TODO: 2017-01-12 난이도별 속도처리
				monster.get(i).y += monsterSpeed; //monsterSpeed 몬스터가 내려오는 속도
				monster.get(i).DrawSprite(gInfo);
				monster.get(i).AddFrameLoop(0.1f); //프레임속도

				GameObject hit = CheckDamege(monster.get(i));
				if(hit != null){//충돌했다면

					monster.get(i).energy -= (power * plusEnergy); //몬스터 에너지 100.
					if (monster.get(i).energy <= 0 ){
						makeEffect(monster.get(i));
						makeMoney(monster.get(i));
						monster.remove(i--);
					}
					hit.dead = true; //Bullet 삭제
				}
				//if(CheckDamege(Monster.get(i) == null);
				System.gc();//gc call
			}
			for (int i = 0; i<bullet.size(); i++){
					bullet.get(i).DrawSprite(gInfo);
					bullet.get(i).y -=20;
					if(bullet.get(i).dead || bullet.get(i).y2 < 0 ){// 몬스터에게 맞은 경우 || 화면 밖으로 나간경우
						bullet.remove(i--);
					}
					System.gc();//gc call
				}
				//selectButton.DrawSprite(mGL,0,gInfo,font);
				//주행거리 출력
				font.BeginFont(gInfo);
				font.DrawFontCenter(mGL,gInfo,400,15,255,255,255,25,"주행거리");
				font.DrawFontCenter(mGL,gInfo,400,40,255,255,255,25,"" + distance++);

				font.DrawFontCenter(mGL,gInfo,60,15,255,255,255,25,"Coin");
				font.DrawFontCenter(mGL,gInfo,60,40,255,255,255,25,"" + getCoin);

				font.EndFont(gInfo);

				myHero.DrawSprite(gInfo);
				myHero.AddFrameLoop(0.1f);
				for (int i = 0; i < effect.size(); i++){
					if(effect.get(i).type == 0){ //먼지 이펙트.
						effect.get(i).Zoom(gInfo,-0.02f,-0.02f);
						effect.get(i).MovebyAngle(gInfo, effect.get(i).direct,1.2f );
						effect.get(i).DrawSprite(gInfo);
						if (effect.get(i).scalex < 0.1f ){
							effect.remove(i--);
						}
					}//	else if(Effect.get(i).type == 1){ //동전 이펙트.
	//					Effect.get(i).y -= Effect.get(i).power / 100;
	//					Effect.get(i).power -= 30;
	//					Effect.get(i).DrawSprite(gInfo);
	//					}
					System.gc();//gc call
			}

			for (int i = 0; i < coin.size(); i++){// 돈 생성 모듈
				if (coin.get(i).type == 1){
					coin.get(i).y -= coin.get(i).power / 100;
					coin.get(i).power -= 30;
					coin.get(i).DrawSprite(gInfo);
					//코인 먹는 모듈;
					//todo : 누적카운팅. 보여주는 문제. 메모리 관리 .
					GameObject money = getMoney(coin.get(i));
					if(money != null){
//						Log.i("money", "돈 먹음. ");
						money.dead = true; // 돈 사라짐.
						coin.remove(i--);

						getCoin += 7;
					}else if(coin.get(i).y2 < -200 ){ //메모리처리 테스트 2
//						Log.i("money", "돈 삭제. ");
						coin.remove(i);
//					else if(Coin.get(i).y2 < 0 ) { //메모리처리 테스트1
//						money.dead = true;
//						Coin.remove(i--);
						//Log.i("money", "돈 지나감 ");
					}
					//todo : 화면 밖으로 나간 경우 메모리 처리.
//					if(coin.get(i).y1 >  gInfo.ScreenY + 100){
//						coin.remove(i--);
//					}
				}
			}

			//selectButton.DrawSprite(mGL,0,gInfo,font);
			}
			makeBullet();
			makeMonster();
			System.gc();//gc call
			warning();

		}


		public void pauseThread() {
			gameover = true;
			Log.d("gameover","pauseThread1");
			//selectButton.DrawSprite(mGL,0,gInfo,font);

			((MainActivity)MainContext).gameOver = true;

			Log.d("gameover","pauseThread2");
			try {
				Log.d("gameover","pauseThread3");
				//		((MainActivity)MainContext).cust_Pause();
				((MainActivity)MainContext).onBackPressed();
				Log.d("gameover","pauseThread4");
			//	((MainActivity)MainContext).play.onPause();
				Log.d("gameover","pauseThread5");
			} catch ( Exception e) {
				Log.d("InterruptException", "InterruptException()");
				e.printStackTrace();
			}
	//			synchronized (mGL) {
	//				try {
	//					mGL.wait();
	//				} catch (InterruptedException e) {
	//				}
	//			}
		}
		public void resumeThread(){
			gameover = false;
			synchronized ( mGL ){
				try {
					mGL.notify();
				} catch (Exception e) {

				}
			}
		}
	}

