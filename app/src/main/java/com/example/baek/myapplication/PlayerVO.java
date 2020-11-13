package com.example.baek.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerVO  implements Serializable {
	
	private String userCode;
	private WeaponVO usedWeapon;
	private ArrayList<WeaponVO> haveWeapons;
	private int haveCoin;
	
	public PlayerVO() {
		// TODO Auto-generated constructor stub
	}
	
	public PlayerVO(String userCode, WeaponVO usedWeapon, ArrayList<WeaponVO> haveWeapons, int haveCoin) {
		super();
		this.userCode = userCode;
		this.usedWeapon = usedWeapon;
		this.haveWeapons = haveWeapons;
		this.haveCoin = haveCoin;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public WeaponVO getUsedWeapon() {
		return usedWeapon;
	}

	public void setUsedWeapon(WeaponVO usedWeapon) {
		this.usedWeapon = usedWeapon;
	}

	public ArrayList<WeaponVO> getHaveWeapons() {
		return haveWeapons;
	}

	public void setHaveWeapons(ArrayList<WeaponVO> haveWeapons) {
		this.haveWeapons = haveWeapons;
	}

	public int getHaveCoin() {
		return haveCoin;
	}

	public void setHaveCoin(int haveCoin) {
		this.haveCoin = haveCoin;
	}

	@Override
	public String toString() {
		return "PlayerVO [userCode=" + userCode + ", usedWeapon=" + usedWeapon + ", haveWeapons=" + haveWeapons
				+ ", haveCoin=" + haveCoin + "]";
	}

}
