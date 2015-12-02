package com.hanains.network.thread;

import java.util.ArrayList;
import java.util.List;

public class DigitThread extends Thread {

	
	@Override
	public void run() {
		for( int i = 0; i < 10; i++ ) {
			System.out.print( i );
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}