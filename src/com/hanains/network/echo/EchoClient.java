package com.hanains.network.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {
	
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 6060;
	
	public static void main( String[] args ) {
		
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		Socket socket = null;
		
		Scanner scanner = new Scanner( System.in );
		
		try {
			// 소켓 생성
			socket = new Socket();
			
			// 서버 연결
			socket.connect( new InetSocketAddress( SERVER_IP, SERVER_PORT ) );
			consolLog( "서버연결 성공" );
			
			// IOStream 받아오기
			bufferedReader = new BufferedReader ( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
			printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
			
			while( true ) {
				
				// 키보드 입력 받기
				System.out.print( ">>" );
				String message = scanner.nextLine();
				
				// "exit"이면 프로그램 종료
				if( "exit".equals( message ) ) {
					break;
				}
				
				// 소켓  쓰기/읽기
				printWriter.println( message );
				printWriter.flush();
				message = bufferedReader.readLine();
				System.out.println( "<<" + message );
			}
			
		} catch( Exception ex ) {
			consolLog( "에러:" + ex );
		} finally {
			try {
				scanner.close();
				
				if( bufferedReader != null ) {
					bufferedReader.close();
				}
				
				if( printWriter != null ) {
					printWriter.close();
				}
				
				if( socket != null && socket.isClosed() == false ) {
						socket.close();
				}
				
			} catch( Exception ex ) {
				ex.printStackTrace();
			}
		}		
	}
	
	public static void consolLog( String message ) {
		System.out.println(  "[클라이언트] " + message );
	}
} 
