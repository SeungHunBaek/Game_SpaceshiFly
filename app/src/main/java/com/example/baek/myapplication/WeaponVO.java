package com.example.baek.myapplication;

import java.io.Serializable;

public class WeaponVO implements Serializable {
	
	private String weaponNum;
	private String weaponName;
	private int weaponPrice;
	private int weaponAttack;
	private int haveWeapon; // 보유하지 않음 : 0 / 보유 중 : 1 / 보유 중이면서 착용 중 : 2
	
	public WeaponVO() {
		// TODO Auto-generated constructor stub
	}

	public WeaponVO(String weaponNum, String weaponName, int weaponPrice, int weaponAttack, int haveWeapon) {
		super();
		this.weaponNum = weaponNum;
		this.weaponName = weaponName;
		this.weaponPrice = weaponPrice;
		this.weaponAttack = weaponAttack;
		this.haveWeapon = haveWeapon;
	}

	public String getWeaponNum() {
		return weaponNum;
	}

	public void setWeaponNum(String weaponNum) {
		this.weaponNum = weaponNum;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getWeaponPrice() {
		return weaponPrice;
	}

	public void setWeaponPrice(int weaponPrice) {
		this.weaponPrice = weaponPrice;
	}

	public int getWeaponAttack() {
		return weaponAttack;
	}

	public void setWeaponAttack(int weaponAttack) {
		this.weaponAttack = weaponAttack;
	}

	public int getHaveWeapon() {
		return haveWeapon;
	}

	public void setHaveWeapon(int haveWeapon) {
		this.haveWeapon = haveWeapon;
	}

	@Override
	public String toString() {
		return "WeaponVO [weaponNum=" + weaponNum + ", weaponName=" + weaponName + ", weaponPrice=" + weaponPrice
				+ ", weaponAttack=" + weaponAttack + ", haveWeapon=" + haveWeapon + "]";
	}

}
