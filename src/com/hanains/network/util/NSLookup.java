package com.hanains.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		//Sanner 객체 생성
		Scanner scanner = new Scanner( System.in );
		
		while( true ) {
			
			// 키보드 입력 받기
			System.out.print( ">" );
			String host = scanner.nextLine();
			
			// 입력 문자열이 "exit" 이면,  루프 탈출
			if( "exit".equals( host ) ) {
				break;
			}
	
			// IP Address 가져오기
			try {
				InetAddress[] inetAddresses = InetAddress.getAllByName( host );
				for( int i = 0; i < inetAddresses.length; i++ ) {
					String hostAddress = inetAddresses[ i ].getHostAddress();
					System.out.println( hostAddress );
				}
			} catch( UnknownHostException ex ) {
				System.out.println( "알수 없는 호스트 이름 - " + host );
			}
		}
		
		// Scanner(키보드 닫기)
		scanner.close();
	}
}