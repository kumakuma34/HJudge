package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.python.util.PythonInterpreter;

public class CompileExam {

	private static PythonInterpreter interpreter;
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//python("3");
		System.out.println();
		//String[] s = {"ㅓㅇ","어"};
		//cLan("3",s);
		cPlusplus("D://test/4/8/17/1111.cpp", "D://test/4/8/17", "D://test/4/8/17/result.txt","test");
		System.out.println();
		//cPlusplus("3");
		System.out.println();
		//java("3");
	}

	public static void python(String ans) throws IOException, InterruptedException {
		System.out.println("python");
		String command = "python histogram.py";
		 interpreter = new PythonInterpreter();
	        interpreter.execfile("histogram.py");
	        interpreter.exec("histogram(result)");
        
        
        
        Process pc = null;
		
		try {
			pc = Runtime.getRuntime().exec(command);
			
			OutputStream os = pc.getOutputStream();
			InputStream is = pc.getInputStream();

			os.write("D://test/4/8/17/result".getBytes());
			os.flush();
			os.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while ((s = reader.readLine()) != null) {
				System.out.println("program output is : " + s);
				if (s.equals(ans)) {
					System.out.println("pass");
				} else {
					System.out.println("fail");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
	}
	
	public static void cLan(String codepath, String Path, String resultPath, String student) throws IOException, InterruptedException {
		System.out.println("C");
		String command1 = "gcc "+codepath+" -o "+Path+"/"+student+".exe";
		System.out.println("command1 : "+command1);
		String command2 = Path+"/"+student+".exe";
		String[] command = { command1, command2};
		String output = null;
        String input = null;
        String outputPath = Path + "/output.txt";
        String inputPath = Path + "/input.txt";
		Process pc = null;
		 try{
			  File file = new File(outputPath);
			  File file2 = new File(inputPath);
	          //파일 객체 생성
			  FileReader filereader = new FileReader(file);
			  FileReader filereader2 = new FileReader(file2);
	          //입력 버퍼 생성
	          BufferedReader bufReader = new BufferedReader(filereader);
	          BufferedReader bufReader2 = new BufferedReader(filereader2);

	          String line = bufReader.readLine();
	          String line2 = bufReader2.readLine();
	 		 output = line;
	 		 input = line2;
	          System.out.println("input : " + input);

	 		 //System.out.println("output is : "+output);
	          bufReader.close();
	      }catch (FileNotFoundException e) {
	          // TODO: handle exception
	      }catch(IOException e){
	          System.out.println(e);
	      }
		try {
			pc = Runtime.getRuntime().exec(command[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
		System.out.println("output : " + output);
		try {
			pc = Runtime.getRuntime().exec(command[1]);
			
			OutputStream os = pc.getOutputStream();
			InputStream is = pc.getInputStream();

			os.write(input.getBytes());
			//os.write("6\n".getBytes());
			os.flush();
			os.close();			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while ((s = reader.readLine()) != null) {
				System.out.println("program output is : " + s);
				if (s.equals(output)) {
					System.out.println("pass");
					resultWrite(true, resultPath);

				} else {
					System.out.println("fail");
					resultWrite(false, resultPath);

				}
			}
		} catch (IOException e) {
			System.out.println("Compile Error");
			resultWrite(false, resultPath);
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
	}
	//C언어 채점 코드
public static void cLan(String ans, String path[]) throws IOException, InterruptedException {
		System.out.println("C language");
		String[] command = { "gcc D:/qgqg264/2/11/problem1/1111111.C -o examc.exe", "examc.exe" };
		Process pc = null;
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
				if (s.equals(ans)) {
					System.out.println("pass");
				} else {
					System.out.println("fail");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
	}

	public static void cPlusplus(String codepath, String Path, String resultPath, String student) throws IOException, InterruptedException {
		System.out.println("C++");
		String command1 = "g++ "+codepath+" -o "+Path+"/"+student+".exe";
		String command2 = Path+"/"+student+".exe";
		String[] command = { command1, command2};
		String output = null;
        String input = null;
        String outputPath = Path + "/output.txt";
        String inputPath = Path + "/input.txt";
		Process pc = null;
		 try{
			  File file = new File(outputPath);
			  File file2 = new File(inputPath);
	          //파일 객체 생성
			  FileReader filereader = new FileReader(file);
			  FileReader filereader2 = new FileReader(file2);
	          //입력 버퍼 생성
	          BufferedReader bufReader = new BufferedReader(filereader);
	          BufferedReader bufReader2 = new BufferedReader(filereader2);

	          String line = bufReader.readLine();
	          String line2 = bufReader2.readLine();

	 		 output = line;
	 		 input = line2;
	 		 //System.out.println("output is : "+output);
	          bufReader.close();
	      }catch (FileNotFoundException e) {
	          // TODO: handle exception
	      }catch(IOException e){
	          System.out.println(e);
	      }
		try {
			pc = Runtime.getRuntime().exec(command[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
		System.out.println("output : " + output);
		try {
			pc = Runtime.getRuntime().exec(command[1]);
			
			OutputStream os = pc.getOutputStream();
			InputStream is = pc.getInputStream();

			os.write(input.getBytes());
			//os.write("6\n".getBytes());
			os.flush();
			os.close();			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while ((s = reader.readLine()) != null) {
				System.out.println("program output is : " + s);
				if (s.equals(output)) {
					System.out.println("pass");
					resultWrite(true, resultPath);

				} else {
					System.out.println("fail");
					resultWrite(false, resultPath);

				}
			}
		} catch (IOException e) {
			System.out.println("Compile Error");
			resultWrite(false, resultPath);
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
	}
	//C++언어 채점 코드
	public static void java(String codepath, String Path, String resultPath, String student) throws IOException, InterruptedException {
		System.out.println("java");
		String[] command = { "javac "+ codepath +"", "java "+Path};
		String output = null;
        String input = null;
        String outputPath = Path + "/output.txt";
        String inputPath = Path + "/input.txt";
		Process pc = null;
		 try{
			  File file = new File(outputPath);
			  File file2 = new File(inputPath);
	          //파일 객체 생성
			  FileReader filereader = new FileReader(file);
			  FileReader filereader2 = new FileReader(file2);
	          //입력 버퍼 생성
	          BufferedReader bufReader = new BufferedReader(filereader);
	          BufferedReader bufReader2 = new BufferedReader(filereader2);

	          String line = bufReader.readLine();
	          String line2 = bufReader2.readLine();
	 		 output = line;
	 		 input = line2;
	          System.out.println("input : " + input);

	 		 //System.out.println("output is : "+output);
	          bufReader.close();
	      }catch (FileNotFoundException e) {
	          // TODO: handle exception
	      }catch(IOException e){
	          System.out.println(e);
	      }
		try {
			pc = Runtime.getRuntime().exec(command[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
		System.out.println("output : " + output);
		try {
			pc = Runtime.getRuntime().exec(command[1]);
			
			OutputStream os = pc.getOutputStream();
			InputStream is = pc.getInputStream();

			os.write(input.getBytes());
			//os.write("6\n".getBytes());
			os.flush();
			os.close();			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while ((s = reader.readLine()) != null) {
				System.out.println("program output is : " + s);
				if (s.equals(output)) {
					System.out.println("pass");
					resultWrite(true, resultPath);

				} else {
					System.out.println("fail");
					resultWrite(false, resultPath);

				}
			}
		} catch (IOException e) {
			System.out.println("Compile Error");
			resultWrite(false, resultPath);
			e.printStackTrace();
		} finally {
			pc.waitFor();
			pc.destroy();
		}
	}
	//자바 언어 채점 코드
public static void resultWrite(boolean result, String path)
	{
		System.out.println(path);
	     File file = new File(path);
		  FileWriter writer = null;
		  try {
			  writer = new FileWriter(file, true);
			  if(result == true)
			  {
				  writer.write("correct");
				  writer.write("\r\n");
			  }
			  else
			  {
				  writer.write("fail");
				  writer.write("\r\n");
			  }
			  writer.flush();
			  System.out.println("DONE");

		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}//채점 결과를 text File에 쓰는 코드
