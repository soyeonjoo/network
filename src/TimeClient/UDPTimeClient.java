package TimeClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPTimeClient {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 7070;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		
		DatagramSocket datagramSocket = null;
		
		try {
			// 1. UDP 클라이언트 소켓 생성
			datagramSocket = new DatagramSocket();
			
			// 2. 요청 packet 보내기
			byte[] data = "".getBytes();
			DatagramPacket sendPacket = new DatagramPacket(
					data,
					data.length,
					new InetSocketAddress( SERVER_IP, SERVER_PORT ) );
			datagramSocket.send( sendPacket );

			// 3.시간 데이터 받기
			DatagramPacket receivePacket = 
				new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
			datagramSocket.receive( receivePacket );

			// 4.시간 출력
			String date = new String(
				receivePacket.getData(),
				0,
				receivePacket.getLength(),
				"UTF-8" );
			log( date );
			
		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			//5.자원정리
			if( datagramSocket != null ) {
				datagramSocket.close();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[udp-time-client] " + log);
	}
}
