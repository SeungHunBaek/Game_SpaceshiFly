package com.example.baek.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.baek.myapplication.MainViewActivity.CODE_GAME_RESULT;
import static com.example.baek.myapplication.MainViewActivity.CODE_MAIN;
import static com.example.baek.myapplication.MainViewActivity.CODE_WEAPON_BUY;
import static com.example.baek.myapplication.MainViewActivity.CODE_WEAPON_CHANGE;

/**
 * Created by EunYoung on 2017-01-11.
 */

public class ContectServer {

    /*
    loading한 PlayerVO의 정보를 저장해놓고 다른 클래스에서 참조하도록 함
     mainPlayer = loadPlayer(player);
     mainPlayer = savePlayer(player);
    */
    static PlayerVO mainPlayer;

    //TODO : server 값 설정해줄 것
    String serverIp = "203.233.199.219";
    String portNum = "8089";

    String urlStringData = "http://"+serverIp+":"+portNum+"/AndroidProject_server/";
    String urlString = "";
    // cf) http://localhost:7878/AndroidProject_server/


    public ContectServer(String androidId) {
        Log.i("EY_androidId : ",androidId);

//        PlayerVO player = new PlayerVO();
//        player.setUserCode(androidId);
//        mainPlayer = this.loadPlayer(player);

    }

    public PlayerVO loadPlayer(PlayerVO player){
        String getServerPlayer = "";
        PlayerVO dataPlayer = new PlayerVO();

        urlString = urlStringData + "loadingGame";

        //player의 userCode를 JSON 타입으로 parsing함
        JSONObject jsonPlayer = new JSONObject();

        try {
            jsonPlayer.put("userCode", player.getUserCode());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("EY : ", jsonPlayer.toString());

        //parsing한 데이터를 웹으로 전송하고 player데이터를 받아옴
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(300 * 1000);
            conn.setReadTimeout(300 * 1000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(jsonPlayer.toString());
            writer.flush();
            writer.close();

            int responseCode = conn.getResponseCode();

            Log.i("EY_responseCode : ", responseCode+"");

            //server에서 JSON데이터를 읽어옴
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuffer buffer = new StringBuffer();
            String data = "";

            while((data = reader.readLine()) != null){
                buffer.append(data);
            }

            Log.i("EY_buffer : ", buffer.toString());

            //JSON데이터를 PlayerVO 객체로 변환
            try {
                JSONObject getJson = new JSONObject(buffer.toString());

                JSONObject getJsonPlayer = new JSONObject(getJson.get("player").toString());

                Log.i("EY_getJsonPlayer", getJsonPlayer.toString());
                Log.i("EY_usedWeapon", getJsonPlayer.get("usedWeapon").toString());

                JSONObject getJsonUsedWeapon = new JSONObject(getJsonPlayer.get("usedWeapon").toString());
                JSONArray getJsonHaveWeapons = new JSONArray(getJsonPlayer.getJSONArray("haveWeapons").toString());

                Log.i("EY_getJsonUsedWeapon", getJsonUsedWeapon.toString());
                Log.i("EY_getJsonHaveWeapons", getJsonHaveWeapons.toString());

                dataPlayer.setUserCode(getJsonPlayer.get("userCode").toString());
                dataPlayer.setHaveCoin(Integer.parseInt(getJsonPlayer.get("haveCoin").toString()));

                WeaponVO getUsedWeapon = new WeaponVO();
                getUsedWeapon.setWeaponNum(getJsonUsedWeapon.get("weaponNum").toString());
                getUsedWeapon.setWeaponName(getJsonUsedWeapon.get("weaponName").toString());
                getUsedWeapon.setWeaponAttack(Integer.parseInt(getJsonUsedWeapon.get("weaponPrice").toString()));
                getUsedWeapon.setWeaponPrice(Integer.parseInt(getJsonUsedWeapon.get("weaponAttack").toString()));
                getUsedWeapon.setHaveWeapon(Integer.parseInt(getJsonUsedWeapon.get("haveWeapon").toString()));
                dataPlayer.setUsedWeapon(getUsedWeapon);

                ArrayList<WeaponVO> getHaveWeapons = new ArrayList<>();
                for(int i = 0; i < getJsonHaveWeapons.length(); i++){
                    WeaponVO weapon = new WeaponVO();
                    JSONObject jsonWeapon = getJsonHaveWeapons.getJSONObject(i);
                    weapon.setWeaponNum(jsonWeapon.get("weaponNum").toString());
                    weapon.setWeaponName(jsonWeapon.get("weaponName").toString());
                    weapon.setWeaponAttack(Integer.parseInt(jsonWeapon.get("weaponPrice").toString()));
                    weapon.setWeaponPrice(Integer.parseInt(jsonWeapon.get("weaponAttack").toString()));
                    weapon.setHaveWeapon(Integer.parseInt(jsonWeapon.get("haveWeapon").toString()));
                    getHaveWeapons.add(weapon);
                }
                dataPlayer.setHaveWeapons(getHaveWeapons);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("EY_parsingPlayer : ", dataPlayer.toString());
            if(reader != null){
                reader.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return dataPlayer;
    }


    public PlayerVO savePlayer(PlayerVO player, int stateCode){

        //받은 player의 객체를 JSON으로 parsing
        JSONObject jsonPlayer = new JSONObject();
        JSONObject jsonUsedWeapon = new JSONObject();
        JSONArray jsonHaveWeapons = new JSONArray();

        try {
            jsonPlayer.put("userCode", player.getUserCode());
            jsonPlayer.put("haveCoin", player.getHaveCoin());

            jsonUsedWeapon.put("weaponNum", player.getUsedWeapon().getWeaponNum());
            jsonUsedWeapon.put("weaponName", player.getUsedWeapon().getWeaponName());
            jsonUsedWeapon.put("weaponPrice", player.getUsedWeapon().getWeaponPrice());
            jsonUsedWeapon.put("weaponAttack", player.getUsedWeapon().getWeaponAttack());
            jsonUsedWeapon.put("haveWeapon", player.getUsedWeapon().getHaveWeapon());

            jsonPlayer.put("usedWeapon", jsonUsedWeapon);

            for(int i = 0 ; i <player.getHaveWeapons().size(); i++){
                JSONObject jsonWeapon = new JSONObject();
                jsonWeapon.put("weaponNum", player.getHaveWeapons().get(i).getWeaponNum());
                jsonWeapon.put("weaponName", player.getHaveWeapons().get(i).getWeaponName());
                jsonWeapon.put("weaponPrice", player.getHaveWeapons().get(i).getWeaponPrice());
                jsonWeapon.put("weaponAttack", player.getHaveWeapons().get(i).getWeaponAttack());
                jsonWeapon.put("haveWeapon", player.getHaveWeapons().get(i).getHaveWeapon());

                jsonHaveWeapons.put(jsonWeapon);
            }

            jsonPlayer.put("haveWeapons", jsonHaveWeapons);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("EY : ", jsonPlayer.toString());

        //parsing한 JSON을 웹으로 전송
        switch (stateCode){
            case CODE_MAIN :
                break;

            case CODE_GAME_RESULT : //게임이 종료되었을 때 : coin 저장
                urlString = urlStringData + "endGame";
                break;
            case CODE_WEAPON_BUY :
                urlString = urlStringData + "buyWeapon";
                break;
            case CODE_WEAPON_CHANGE :
                urlString = urlStringData + "changeWeapon";
                break;
        }

        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(300 * 1000);
            conn.setReadTimeout(300 * 1000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(jsonPlayer.toString());
            writer.flush();

            if(writer != null) {
                writer.close();
            }

            int responseCode = conn.getResponseCode();

            Log.i("EY_responseCode : ", responseCode+"");
            Log.i("EY_responseCodeOK : ", HttpURLConnection.HTTP_OK+"");

            //JSON 데이터를 전송 후, 최신의 PlayerVO 객체를 가져옴
            if(responseCode == HttpURLConnection.HTTP_OK){

                Log.i("EY : ", "데이터 전송 후, 최신 PlayerVO 가져옴");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer buffer = new StringBuffer();
                String data = "";

                while((data = reader.readLine()) != null){
                    buffer.append(data);
                }

                Log.i("EY_buffer : ", buffer.toString());

                //JSON데이터를 PlayerVO 객체로 변환
                PlayerVO dataPlayer = new PlayerVO();
                try {
                    JSONObject getJson = new JSONObject(buffer.toString());

                    JSONObject getJsonPlayer = new JSONObject(getJson.get("player").toString());

                    Log.i("EY_getJsonPlayer", getJsonPlayer.toString());
                    Log.i("EY_usedWeapon", getJsonPlayer.get("usedWeapon").toString());

                    JSONObject getJsonUsedWeapon = new JSONObject(getJsonPlayer.get("usedWeapon").toString());
                    JSONArray getJsonHaveWeapons = new JSONArray(getJsonPlayer.getJSONArray("haveWeapons").toString());

                    Log.i("EY_getJsonUsedWeapon", getJsonUsedWeapon.toString());
                    Log.i("EY_getJsonHaveWeapons", getJsonHaveWeapons.toString());

                    dataPlayer.setUserCode(getJsonPlayer.get("userCode").toString());
                    dataPlayer.setHaveCoin(Integer.parseInt(getJsonPlayer.get("haveCoin").toString()));

                    WeaponVO getUsedWeapon = new WeaponVO();
                    getUsedWeapon.setWeaponNum(getJsonUsedWeapon.get("weaponNum").toString());
                    getUsedWeapon.setWeaponName(getJsonUsedWeapon.get("weaponName").toString());
                    getUsedWeapon.setWeaponAttack(Integer.parseInt(getJsonUsedWeapon.get("weaponPrice").toString()));
                    getUsedWeapon.setWeaponPrice(Integer.parseInt(getJsonUsedWeapon.get("weaponAttack").toString()));
                    getUsedWeapon.setHaveWeapon(Integer.parseInt(getJsonUsedWeapon.get("haveWeapon").toString()));
                    dataPlayer.setUsedWeapon(getUsedWeapon);

                    ArrayList<WeaponVO> getHaveWeapons = new ArrayList<>();
                    for(int i = 0; i < getJsonHaveWeapons.length(); i++){
                        WeaponVO weapon = new WeaponVO();
                        JSONObject jsonWeapon = getJsonHaveWeapons.getJSONObject(i);
                        weapon.setWeaponNum(jsonWeapon.get("weaponNum").toString());
                        weapon.setWeaponName(jsonWeapon.get("weaponName").toString());
                        weapon.setWeaponAttack(Integer.parseInt(jsonWeapon.get("weaponPrice").toString()));
                        weapon.setWeaponPrice(Integer.parseInt(jsonWeapon.get("weaponAttack").toString()));
                        weapon.setHaveWeapon(Integer.parseInt(jsonWeapon.get("haveWeapon").toString()));
                        getHaveWeapons.add(weapon);
                    }
                    dataPlayer.setHaveWeapons(getHaveWeapons);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("EY_parsingPlayer : ", dataPlayer.toString());
                player = dataPlayer;

                if(reader != null)    {
                    reader.close();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }
}
