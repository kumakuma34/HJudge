/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package api;

import com.google.cloud.vision.v1p3beta1.AnnotateImageRequest;
import com.google.cloud.vision.v1p3beta1.AnnotateImageResponse;
import com.google.cloud.vision.v1p3beta1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1p3beta1.Block;
import com.google.cloud.vision.v1p3beta1.Feature;
import com.google.cloud.vision.v1p3beta1.Feature.Type;
import com.google.cloud.vision.v1p3beta1.Image;
import com.google.cloud.vision.v1p3beta1.ImageAnnotatorClient;
import com.google.cloud.vision.v1p3beta1.ImageContext;
import com.google.cloud.vision.v1p3beta1.ImageSource;
import com.google.cloud.vision.v1p3beta1.LocalizedObjectAnnotation;
import com.google.cloud.vision.v1p3beta1.Page;
import com.google.cloud.vision.v1p3beta1.Paragraph;
import com.google.cloud.vision.v1p3beta1.Symbol;
import com.google.cloud.vision.v1p3beta1.TextAnnotation;
import com.google.cloud.vision.v1p3beta1.Word;
import com.google.protobuf.ByteString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Detect {
    static public String type;

  /**
   * Detects entities, sentiment, and syntax in a document using the Vision API.
   *
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void main(String[] args) throws Exception, IOException {
	  while(true)
	  { 
		  File file = new File("D:\\list4.txt");
		  File mutex = new File("D:\\mutex.txt");
		  FileWriter writer = null;

		  //파일이 존재하면 이거하기 없으면 하지말기
		  try{
	          //파일 객체 생성
			  FileReader filereader = new FileReader(file);
	          //입력 버퍼 생성
	          BufferedReader bufReader = new BufferedReader(filereader);
	          String line = "";
	          while((line = bufReader.readLine()) != null){
	        	  //System.out.println("line : "+line);
	        	  if(line.equals("Stop"))
	        	  {
	        		//  System.out.println("Stop!");
	        		  continue;
	        	  }
	              String path[] = line.split("-");
	              String url = "gs://starlit-complex-235610.appspot.com/";
	              url +=path[0]+"/"+path[1] +"/"+path[2]+"/"+path[3]+"/"+path[4];
	              type = path[5];
	              System.out.println(url);
	              String s[] = {"handwritten-ocr", url};
	        	  argsHelper(s, System.out);
	          }
	          //.readLine()은 끝에 개행문자를 읽지 않는다.            
	          bufReader.close();
	          filereader.close();
	         
			 // writer = new FileWriter(file, false);
			  //writer.write("Stop\r\n");
			 // writer.flush();
	      }catch (FileNotFoundException e) {
	          // TODO: handle exception
	      }catch(IOException e){
	          System.out.println(e);
	      }
	}

			 
		 
	 


     //while(true)
    // {
    	// String s[] = {"handwritten-ocr", "gs://starlit-complex-235610.appspot.com/test/input.PNG"};
   	     //argsHelper(s, System.out);
    // }


  }

  /**
   * Helper that handles the input passed to the program.
   *
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void argsHelper(String[] args, PrintStream out) throws Exception, IOException {
    if (args.length < 1) {
      out.println("Usage:");
      out.printf(
          "\tmvn exec:java -DDetect -Dexec.args=\"<command> <path-to-image>\"\n"
              + "\tmvn exec:java -DDetect -Dexec.args=\"ocr <path-to-file> <path-to-destination>\""
              + "\n"
              + "Commands:\n"
              + "\tobject-localization| handwritten-ocr\n"
              + "Path:\n\tA file path (ex: ./resources/wakeupcat.jpg) or a URI for a Cloud Storage "
              + "resource (gs://...)\n"
              + "Path to File:\n\tA path to the remote file on Cloud Storage (gs://...)\n"
              + "Path to Destination\n\tA path to the remote destination on Cloud Storage for the"
              + " file to be saved. (gs://BUCKET_NAME/PREFIX/)\n");
      return;
    }
    String command = args[0];
    String path = args.length > 1 ? args[1] : "";

    if (command.equals("object-localization")) {
      if (path.startsWith("gs://")) {
        detectLocalizedObjectsGcs(path, out);
      } else {
        detectLocalizedObjects(path, out);
      }
    } else if (command.equals("handwritten-ocr")) {
      if (path.startsWith("gs://")) {
        detectHandwrittenOcrGcs(path, out);
      } else {
        detectHandwrittenOcr(path, out);
      }
    }
  }

  // [START vision_localize_objects_beta]
  /**
   * Detects localized objects in the specified local image.
   *
   * @param filePath The path to the file to perform localized object detection on.
   * @param out A {@link PrintStream} to write detected objects to.
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void detectLocalizedObjects(String filePath, PrintStream out)
      throws Exception, IOException {
    List<AnnotateImageRequest> requests = new ArrayList<>();

    ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

    Image img = Image.newBuilder().setContent(imgBytes).build();
    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder()
            .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
            .setImage(img)
            .build();
    requests.add(request);

    // Perform the request
    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      // Display the results
      for (AnnotateImageResponse res : responses) {
        for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
          out.format("Object name: %s\n", entity.getName());
          out.format("Confidence: %s\n", entity.getScore());
          out.format("Normalized Vertices:\n");
          entity
              .getBoundingPoly()
              .getNormalizedVerticesList()
              .forEach(vertex -> out.format("- (%s, %s)\n", vertex.getX(), vertex.getY()));
        }
      }
    }
  }
  // [END vision_localize_objects_beta]

  // [START vision_localize_objects_gcs_beta]
  /**
   * Detects localized objects in a remote image on Google Cloud Storage.
   *
   * @param gcsPath The path to the remote file on Google Cloud Storage to detect localized objects
   *     on.
   * @param out A {@link PrintStream} to write detected objects to.
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void detectLocalizedObjectsGcs(String gcsPath, PrintStream out)
      throws Exception, IOException {
    List<AnnotateImageRequest> requests = new ArrayList<>();

    ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
    Image img = Image.newBuilder().setSource(imgSource).build();

    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder()
            .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
            .setImage(img)
            .build();
    requests.add(request);

    // Perform the request
    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      client.close();
      // Display the results
      for (AnnotateImageResponse res : responses) {
        for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
          out.format("Object name: %s\n", entity.getName());
          out.format("Confidence: %s\n", entity.getScore());
          out.format("Normalized Vertices:\n");
          entity
              .getBoundingPoly()
              .getNormalizedVerticesList()
              .forEach(vertex -> out.format("- (%s, %s)\n", vertex.getX(), vertex.getY()));
        }
      }
    }
  }
  // [END vision_localize_objects_gcs_beta]

  // [START vision_handwritten_ocr_beta]
  /**
   * Performs handwritten text detection on a local image file.
   *
   * @param filePath The path to the local file to detect handwritten text on.
   * @param out A {@link PrintStream} to write the results to.
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void detectHandwrittenOcr(String filePath, PrintStream out) throws Exception {
    List<AnnotateImageRequest> requests = new ArrayList<>();

    ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

    Image img = Image.newBuilder().setContent(imgBytes).build();
    Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
    // Set the Language Hint codes for handwritten OCR
    ImageContext imageContext =
        ImageContext.newBuilder().addLanguageHints("en-t-i0-handwrit").build();

    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder()
            .addFeatures(feat)
            .setImage(img)
            .setImageContext(imageContext)
            .build();
    requests.add(request);

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      client.close();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          out.printf("Error: %s\n", res.getError().getMessage());
          return;
        }

        // For full list of available annotations, see http://g.co/cloud/vision/docs
        TextAnnotation annotation = res.getFullTextAnnotation();
        for (Page page : annotation.getPagesList()) {
          String pageText = ""; 
          for (Block block : page.getBlocksList()) {
            String blockText = "";
            for (Paragraph para : block.getParagraphsList()) {
              String paraText = "";
              for (Word word : para.getWordsList()) {
                String wordText = "";
                for (Symbol symbol : word.getSymbolsList()) {
                  wordText = wordText + symbol.getText();
                  out.format(
                      "Symbol text: %s (confidence: %f)\n",
                      symbol.getText(), symbol.getConfidence());
                }
                out.format("Word text: %s (confidence: %f)\n\n", wordText, word.getConfidence());
                paraText = String.format("%s %s", paraText, wordText);
              }
              // Output Example using Paragraph:
              out.println("\nParagraph: \n" + paraText);
              out.format("Paragraph Confidence: %f\n", para.getConfidence());
              blockText = blockText + paraText;
            }
            pageText = pageText + blockText;
          }
        }
        out.println("\nComplete annotation:");
        out.println(annotation.getText());
        System.out.printf("calling file output\n");
        String path[] = filePath.split("_");
        String className = path[0];
        String Exam = path[1];
        String Problem = path[2];
        String studentId = path[3];
        //writeOnFile(annotation.getText(),className, Exam, Problem, studentId);
      }
    }
  }
  // [END vision_handwritten_ocr_beta]

  public static void writeOnFile(String s,String userName, String className, String Exam, String Problem, String studentID)
  {
	  System.out.println("writeOnFile Started");
	  String path = "D:/" + userName;
	  File Folder = new File(path);
	  if(!Folder.exists()) {
		  try {
			  Folder.mkdir();
		  }
		  catch(Exception e) {
			  e.getStackTrace();
		  }
	  }
	  //create Folder for User

	  path += "/" + className;
	  File Folder5 = new File(path);
	  System.out.println(path);
	  if(!Folder5.exists()) {
		  try {
			  Folder5.mkdir();
		  }
		  catch(Exception e) {
			  e.getStackTrace();
		  }
	  }
	  //create Folder for Class
	  path += "/" + Exam;
	  File Folder1 = new File(path);
	  System.out.println(path);
	  if(!Folder1.exists()) {
		  try {
			  Folder1.mkdir();
		  }
		  catch(Exception e) {
			  e.getStackTrace();
		  }
	  }
	  //create Folder for exam
	  path += "/" + Problem;
	  File Folder2 = new File(path);
	  if(!Folder2.exists()) {
		  try {
			  Folder2.mkdir();
		  }
		  catch(Exception e) {
			  e.getStackTrace();
		  }
	  }
	//create Folder for Problem
	  System.out.println("Student ID : "+studentID);
	  String temp[] = studentID.split(".jpg");
	  String student = temp[0];
	  String array[] = s.split("\n");
	  //코드를 한줄씩 구분
	  String outputPath = path;
	  String path1 = path + "/" + student+"." + type;
	  System.out.println("Type : " + type);
      String resultPath = path + "/"+student+"_result.txt";
	 
	  System.out.println(path1);
	  System.out.println("output path" + outputPath);
	  File file = new File(path1);
	  FileWriter writer = null;
	  try {
		  writer = new FileWriter(file, true);
		  for(int i = 0 ; i < array.length; i++)
		  {
			  System.out.printf("%d %s\n",i,array[i]);
			  int sz = array[i].length();
			  String last = array[i].substring(sz-1, sz);
			  String first = array[i].substring(0, 1);
			  if(!last.equals(";"))
			  {
				  System.out.println("correction start");
				  System.out.println("last in int: "+(int)last.charAt(0)+"char is : "+last.charAt(0));
		
				  if(first.equals("}"))
				  {
					  writer.write("}");
					  writer.write("\r\n");
				  }//주석처리
				  else if(last.equals("i")||last.equals("j")||last.equals("a")||(int)last.charAt(0)==229)
				  {
					  String temp2 = array[i].substring(0, sz-1);
					  temp2 +=";";
					  System.out.println("temp2:" + temp2);
					  writer.write(temp2);
					  writer.write("\r\n");
				  }else { //정상적으로 #일 경우
					  writer.write(array[i]);				  
					  writer.write("\r\n");
				  }
			  }
			  else//정상
			  {
				  writer.write(array[i]);
				  writer.write("\r\n");
			  }
			  System.out.println("last : "+last);	
			  
		  }
		  //writer.write(s);
		  writer.flush();
		  
		  System.out.println("DONE");

	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  try {
		  System.out.println("outputPath"+outputPath);
		  if(type.equals("c"))
				  CompileExam.cLan(path1, outputPath, resultPath , student);
		  else
			  CompileExam.cPlusplus(path1, outputPath, resultPath , student);
	} catch (IOException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  // [START vision_handwritten_ocr_gcs_beta]
  /**
   * Performs handwritten text detection on a remote image on Google Cloud Storage.
   *
   * @param gcsPath The path to the remote file on Google Cloud Storage to detect handwritten text
   *     on.
   * @param out A {@link PrintStream} to write the results to.
   * @throws Exception on errors while closing the client.
   * @throws IOException on Input/Output errors.
   */
  public static void detectHandwrittenOcrGcs(String gcsPath, PrintStream out) throws Exception {
	   System.out.println("Here!");

	  List<AnnotateImageRequest> requests = new ArrayList<>();

    ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
    Image img = Image.newBuilder().setSource(imgSource).build();
    Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
    // Set the parameters for the image
    ImageContext imageContext =
        ImageContext.newBuilder().addLanguageHints("en-t-i0-handwrit").build();

    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder()
            .addFeatures(feat)
            .setImage(img)
            .setImageContext(imageContext)
            .build();
    requests.add(request);

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      client.close();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          out.printf("Error: %s\n", res.getError().getMessage());
          return;
        }

        // For full list of available annotations, see http://g.co/cloud/vision/docs
        TextAnnotation annotation = res.getFullTextAnnotation();
        for (Page page : annotation.getPagesList()) {
          String pageText = "";
          for (Block block : page.getBlocksList()) {
            String blockText = "";
            for (Paragraph para : block.getParagraphsList()) {
              String paraText = "";
              for (Word word : para.getWordsList()) {
                String wordText = "";
                for (Symbol symbol : word.getSymbolsList()) {
                  wordText = wordText + symbol.getText();
                  //out.format(
                      //"Symbol text: %s (confidence: %f)\n",
                      //symbol.getText(), symbol.getConfidence());
                }
                //out.format("Word text: %s (confidence: %f)\n\n", wordText, word.getConfidence());
                paraText = String.format("%s %s", paraText, wordText);
              }
              // Output Example using Paragraph:
             // out.println("\nParagraph: \n" + paraText);
             // out.format("Paragraph Confidence: %f\n", para.getConfidence());
              blockText = blockText + paraText;
            }
            pageText = pageText + blockText;
          }
        }
        out.println("\nComplete annotation:");
        out.println(annotation.getText());
         String path[] = gcsPath.split("/");
         String userName = path[3];
         String className = path[4];
         String Exam = path[5];
         String Problem = path[6];
         String studentId = path[7];
         for(int i = 0 ; i <path.length; i++)
         {
        	System.out.println(i + " : " +path[i]);
        	 
         }
         //System.out.println(studentId);
         writeOnFile(annotation.getText(),userName,className, Exam, Problem, studentId);
         
        //FileOutputStream output = new FileOutputStream("c:/example.md");
       // output.close();
        out.println(annotation.getText());
        
      }
    }
  }
  // [END vision_handwritten_ocr_gcs_beta]
}