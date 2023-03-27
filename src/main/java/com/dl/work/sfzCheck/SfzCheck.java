package com.dl.work.sfzCheck;

public class SfzCheck {
	private static final String sfz = "51190289a.199906201110";
	private static final String xmm = "1109056789a";

	public static int check(String xmm) {
		int count = 0;
		for (int xmmNum = xmm.length() - 4; xmmNum >= 0; xmmNum--) {
//            System.out.println(xmm.substring(length, length + 4));
			for (int sfzNum = sfz.length() - 4; sfzNum >= 0; sfzNum--) {
				if (xmm.substring(xmmNum, xmmNum + 4).equals(sfz.substring(sfzNum, sfzNum + 4))) {
					count++;
				}
//                System.out.println(sfz.substring(i, i + 4));
			}
		}
		if (count != 0) {
			// 校验失败
			return 1;
		} else {
			return 0;
		}
	}

	public static void main(String[] args) {
		int check = check(xmm);
		System.out.println(check);
	}

	public String getSfz() {
		return sfz;
	}
}
