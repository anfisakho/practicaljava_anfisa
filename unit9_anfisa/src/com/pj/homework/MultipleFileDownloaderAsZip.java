package com.pj.homework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * Multiple files downloader as one archive
 * list URLs to files as command line arguments
 *
 */

public class MultipleFileDownloaderAsZip  {

  public static void main(String[] args) throws IOException  {

    FileOutputStream fout = new FileOutputStream (new File("myArchive.zip")); 
    ZipOutputStream zout = new ZipOutputStream(fout);
    
    for (String filename : args) {
     
      URL remoteFile=new URL(filename);      
      URLConnection urlConnFileStream=remoteFile.openConnection();
      String[] tokens = filename.split("/"); // get file name from URL
      InputStream in=urlConnFileStream.getInputStream();
      ZipEntry ze = new ZipEntry(tokens[tokens.length - 1]);
      System.out.println("Downloading " + tokens[tokens.length - 1]); 
      
      try{
        zout.putNextEntry(ze);
        double i=0;
        double j=0;
        for (int c = in.read(); c != -1 ; c = in.read()) {
            zout.write(c);
            i++;
            if ( (i % 100000) == 0 ) {
              j++;
              if (j==63) {
                System.out.println(".");
                j=0;
              }
              System.out.print(".");
            }
        }
        
        System.out.println("\nFinished download of " + filename);
       
      } catch (Exception e) { 
        e.printStackTrace();
      }  finally {
        in.close();
      } 
      
    }
    System.out.println("Done.");
    zout.close();
  }
}