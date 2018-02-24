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
package com.rslakra.java.net.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/*
 * Created on May 24, 2005
 */
public class SSLClient {

	public static void main(String[] args) {
		PrintStream out = System.out;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			SSLSocket client = (SSLSocket) f.createSocket("10.0.61.151", 1601);
			printSocketInfo(client);
			client.startHandshake();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String msg = "Welcome to Client:\n";
			while ((msg = r.readLine()) != null) {
				out.println(msg);
				msg = in.readLine();
				w.write(msg, 0, msg.length());
				w.newLine();
				w.flush();
			}
			w.close();
			r.close();
			client.close();
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}

	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: " + s.getClass());
		System.out.println("Remote address = " + s.getInetAddress());
		System.out.println("Remote port = " + s.getPort());
		System.out.println("Local socket address = " + s.getLocalSocketAddress());
		System.out.println("Local address = " + s.getLocalAddress());
		System.out.println("Local port = " + s.getLocalPort());
		System.out.println("Need client authentication = " + s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		System.out.println("Cipher suite = " + ss.getCipherSuite());
		System.out.println("Protocol = " + ss.getProtocol());
	}
}
