package com.hanains.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {

	private static final int PORT = 9090;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		
		DatagramSocket datagramSocket = null;
		
		try {
			datagramSocket = new DatagramSocket( PORT );
			
			while( true ) {
				log( "packet 수신대기" );
				DatagramPacket receivePacket = new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
				datagramSocket.receive( receivePacket );
			
				//3. 수신 데이터 출력
				String message = new String( receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8" );
				log( "packet 수신:" + message );
				
				// 4. 데이터 보내기
				DatagramPacket sendPacket = new DatagramPacket( receivePacket.getData(), receivePacket.getLength(), receivePacket.getAddress(), receivePacket.getPort() );
				datagramSocket.send( sendPacket );
			}
			
		} catch( IOException ex ) {
			log( "error:" + ex );
		} finally {
			if( datagramSocket != null ) {
				datagramSocket.close();
			}
		}
	}
	
	public static void log( String log ) {
		System.out.println( "[udp-echo-server] " + log );
	}

}
