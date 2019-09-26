package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Test {
	  public static void main(String[] args) throws Exception, IOException {
				
		  System.out.println("C++");
			String command1 = "g++ D://hyunsoo/1111.cpp -o temp.exe";
			String command2 = "temp.exe";
			String[] command = { command1, command2};
			String output = null;

			Process pc = null;
			 output = "3";
			try {
				pc = Runtime.getRuntime().exec(command[0]);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pc.waitFor();
				pc.destroy();
			}
			try {
				pc = Runtime.getRuntime().exec(command[1]);
				
				OutputStream os = pc.getOutputStream();
				InputStream is = pc.getInputStream();

				os.write("1\n".getBytes());
				os.write("2\n".getBytes());
				os.flush();
				os.close();			
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String s = null;
				while ((s = reader.readLine()) != null) {
					System.out.println("program output is : " + s);
					
				}
			} catch (IOException e) {
				System.out.println("Compile Error");
				e.printStackTrace();
			} finally {
				pc.waitFor();
				pc.destroy();
			}
		}
	  }

