package com.hanains.network.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	private static final int PORT = 7070;
	private static final int BUFFER_SIZE = 2048;
	
	public static void main(String[] args) {

		DatagramSocket datagramSocket = null;
		
		try {
			//1. UDP 서버 소켓 생성
			datagramSocket = new DatagramSocket( PORT );
			
			while( true ) {
				
				// 2. 수신 대기
				log("packet 수신 대기");
				DatagramPacket receivePacket =
					new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
				datagramSocket.receive( receivePacket );

				// 3.수신 데이터 출력
				String message = new String(
					receivePacket.getData(),
					0,
					receivePacket.getLength(),
					"UTF-8" );
				log( "packet 수신:" + message );

				// 4.시간 데이터 보내기
				SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
				String date = format.format( new Date() );
				byte[] data = date.getBytes();

				DatagramPacket sendPacket = new DatagramPacket(
					data, 
					data.length,
					receivePacket.getAddress(), 
					receivePacket.getPort() );
				
				datagramSocket.send( sendPacket );
			}
			
		} catch( IOException ex ) {
			log( "error:" + ex );
		} finally {
			//5.자원정리
			if( datagramSocket != null ) {
				datagramSocket.close();
			}
		}
	}

	public static void log( String log ) {
		System.out.println( "[udp-time-server] " + log );
	}
}
