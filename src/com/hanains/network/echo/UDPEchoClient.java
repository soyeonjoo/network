package com.hanains.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {

	private static final String HOST_ADDRESS = "127.0.0.1";
	private static final int PORT = 9090;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		
		DatagramSocket datagramSocket = null;
		Scanner scanner = new Scanner( System.in );
			
		try {
			datagramSocket = new DatagramSocket();
			
			while( true ) {
				
				System.out.print( ">>" );
				String data = scanner.nextLine();
				if( "exit".equals( data ) == true ) {
					break;
				}
				
				byte[] dataBytes = data.getBytes();
				
				DatagramPacket sendPacket  = new DatagramPacket( dataBytes, dataBytes.length, new InetSocketAddress( HOST_ADDRESS, PORT ) );
				datagramSocket.send( sendPacket );
				
				// 3. 데이터 받기
				DatagramPacket receivePacket = new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
				datagramSocket.receive( receivePacket );
				
				// 4. 메세지 출력
				String message = new String( receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8" );
				System.out.println( "<<" + message );
			}
			
		} catch( IOException ex ) {
			log( "error:" + ex );
		} finally {
			scanner.close();
			if( datagramSocket != null ) {
				datagramSocket.close();
			}
		}
	}
	
	public static void log( String log ) {
		System.out.println( "[udp-echo-client] " + log );
	}

}
