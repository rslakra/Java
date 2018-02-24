/******************************************************************************
 * Copyright (C) Devamatre Inc 2009-2018. All rights reserved.
 * 
 * This code is licensed to Devamatre under one or more contributor license 
 * agreements. The reproduction, transmission or use of this code, in source 
 * and binary forms, with or without modification, are permitted provided 
 * that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *      
 * Devamatre reserves the right to modify the technical specifications and or 
 * features without any prior notice.
 *****************************************************************************/
package com.rslakra.java.threads;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * UpdateTask.java
 * 
 * The <code>UpdateTask</code> TODO Define Purpose here
 * 
 * 
 * @author Rohtash Singh (rohtash.singh@devamatre.com)
 * @date Aug 1, 2010 3:49:09 PM
 */
public class UpdateTask {

	private static UpdateTask instance;
	private Timer timer;
	private boolean checkUpdates;
	private final Calendar calendar;

	/* singleton pattern. */
	private UpdateTask() {
		checkUpdates = true;
		calendar = Calendar.getInstance();
	}

	/**
	 * @return FileUtils
	 */
	public static UpdateTask getInstance() {
		synchronized (UpdateTask.class) {
			if (instance == null) {
				instance = new UpdateTask();
			}
		}
		return instance;
	}

	/**
	 * Initialized Timer.
	 * 
	 * @param seconds
	 */
	public void init(int seconds) {
		timer = new Timer();
		timer.schedule(new UpdateTimer(), seconds * 1000);
		if (calendar.before(new Date())) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	/**
	 * @author Rohtash Singh (rohtash.lakra@devamatre.com)
	 * @version 2009/04/02
	 */
	private class UpdateTimer extends TimerTask {
		public void run() {
			if (isCheckUpdates()) {
				calendar.add(Calendar.DATE, 1);
				if (calendar.before(new Date())) {
					calendar.add(Calendar.DATE, -1);
				}
				timer.cancel(); // Terminate the thread
			}
		}
	}

	/**
	 * @return true, if update checking allowed.
	 */
	public boolean isCheckUpdates() {
		return checkUpdates;
	}

	/**
	 * To be set checkUpdates.
	 * 
	 * @param checkUpdates
	 */
	public void setCheckUpdates(boolean checkUpdates) {
		this.checkUpdates = checkUpdates;
	}

	public static void main(String args[]) {
		System.out.println("Schedule something to do in 5 seconds.");
		UpdateTask.getInstance().init(5);
		System.out.println("Waiting.");
	}
}
