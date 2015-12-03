package com.hanains.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoServerReceiveThread extends Thread {
	
	private Socket socket;
	
	public EchoServerReceiveThread( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		
		try {
			
			//1. 리모트 호스트 정보 출력
			InetSocketAddress inetSocketAddress = ( InetSocketAddress ) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			EchoServer.consolLog( "연결됨 from " + remoteHostAddress + ":" + remoteHostPort );

			//2. IOStream 받아오기
			bufferedReader = new BufferedReader ( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
			printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
		
			//3. 데이터 읽기
			while( true ) {
				String data = bufferedReader.readLine();
				if( data == null ) {
					EchoServer.consolLog( "클라이언트로 부터 연결 끊김" );
					break;
				}
				
				EchoServer.consolLog( "수신 데이터:" + data );
				
				//4. 데이터 보내기
				printWriter.println( data );
				printWriter.flush();
			}
		} catch( IOException ex ) {
			EchoServer.consolLog( "에러:" + ex );
		} finally {
			try {
				//5. 자원정리
				if( bufferedReader != null ) {
					bufferedReader.close();
				}
				if( printWriter != null ) {
					printWriter.close();
				}
				if( socket.isClosed() == false ) {
					socket.close();
				}
			} catch( IOException ex ) {
				EchoServer.consolLog( "에러:" + ex );
			}
		}
	}
}