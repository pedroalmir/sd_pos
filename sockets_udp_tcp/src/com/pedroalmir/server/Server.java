package com.pedroalmir.server;

import java.io.IOException;
import java.util.Date;

public abstract class Server {
	/** Server start time */
	protected Date upTime;
	/** Number of requests received by the server */
	protected int reqNum;
	/** Flag used to close server */
	protected boolean closeFlag;
	
	/**
	 * @param upTime
	 * @param reqNum
	 */
	public Server() {
		this.reqNum = 0;
		this.closeFlag = false;
		this.upTime = new Date();
	}
	
	/**
	 * Start server 
	 */
	public abstract void start(int port) throws IOException;
	
	/**
	 * Stop server 
	 */
	public void stop() {
		this.closeFlag = true;
		System.out.println("Closing the server! See you!");
	}
	
	/**
	 * @return server uptime
	 */
	public String getUpTime(){
		/* Diff in milliseconds */
		long diff = new Date().getTime() - this.upTime.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return String.format("%f days, %f hours, %f minutes and %f seconds.", diffDays, diffHours, diffMinutes, diffSeconds);
	}
	
	/**
	 * 
	 */
	public void plusOneReqNum(){
		this.reqNum++;
	}
	
	/**
	 * @return the reqNum
	 */
	public int getReqNum() {
		return reqNum;
	}
	
	/**
	 * @param reqNum the reqNum to set
	 */
	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}
}
