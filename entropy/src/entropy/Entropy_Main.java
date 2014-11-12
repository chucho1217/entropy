//package entropy;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import javax.xml.crypto.Data;

//import com.csvreader.CsvWriter;



public class Entropy_Main {
	
	///Users/carlosgarzon/Desktop/recitation/calgary/bib
	public static float m_Entropy = 0;
	
	public static void main( String [] args ) throws IOException
	{
		//CsvWriter out = new CsvWriter("calgary.csv");
		FileWriter out = new FileWriter("calgary.csv");
		//FileOutputStream name = new FileOutputStream("calgary.csv");
		//DataOutputStream out = new DataOutputStream(name);
		
		File bib = new File("calgary/bib");
		printToFile(bib, out);
		
		File book1 = new File("calgary/book1");
		printToFile(book1, out);
		
		File book2 = new File("calgary/book2");
		printToFile(book2, out);
		
		File geo = new File("calgary/geo");
		//printToFile(geo, out);
		
		File news = new File("calgary/news");
		printToFile(news, out);
		
		File obj1 = new File("calgary/obj1");
		//printToFile(obj1, out);
		
		File obj2 = new File("calgary/obj2");
		//printToFile(obj2, out);
		
		File paper1 = new File("calgary/paper1");
		printToFile(paper1, out);
		
		File paper2 = new File("calgary/paper2");
		printToFile(paper2, out);
		
		File paper3 = new File("calgary/paper3");
		printToFile(paper3, out);
		
		File paper4 = new File("calgary/paper4");
		printToFile(paper4, out);
		
		File paper5 = new File("calgary/paper5");
		printToFile(paper5, out);
		
		File paper6 = new File("calgary/paper6");
		printToFile(paper6, out);
		
		File pic = new File("calgary/pic");
		//printToFile(pic, out);
		
		File progc = new File("calgary/progc");
		printToFile(progc, out);
		
		File progl = new File("calgary/progl");
		printToFile(progl, out);
		
		File progp = new File("calgary/progp");
		printToFile(progp, out);
		
		File trans = new File("calgary/trans");
		printToFile(trans, out);
		
		out.close();
		
		System.out.println("\n\n");
		
		FileWriter out2 = new FileWriter("canterbury.csv");
		
		File alice29 = new File("canterbury/alice29");
		//printToFile(alice29, out2);
		
		File asyoulik = new File("canterbury/asyoulik");
		//printToFile(asyoulik, out2);
		
		File cp = new File("canterbury/cp");
		//printToFile(cp, out2);
		
		File fields = new File("canterbury/fields");
		//printToFile(fields, out2);
		
		File grammar = new File("canterbury/grammar");
		//printToFile(grammar, out2);
		
		File kennedy = new File("canterbury/ kennedy");
		//printToFile( kennedy, out2);
		
		File lcet10 = new File("canterbury/lcet10");
		//printToFile(lcet10, out2);
		
		File plrabn12 = new File("canterbury/plrabn12");
		//printToFile(plrabn12, out2);
		
		File ptt5 = new File("canterbury/ptt5");
		//printToFile(ptt5, out2);
		
		File sum = new File("canterbury/sum");
		//printToFile(sum, out2);
		
		File xargs = new File("canterbury/xargs");
		//printToFile(xargs, out2);
		
		out2.close();
		
		/*try 
		{
			byte[] b = getBytesFromFile( file );
			m_Entropy = CalcShannon(b, b.length);
			
			System.out.println("Your entropy for this particular files is --> " + m_Entropy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

	}
	
	public static void printToFile(File file, FileWriter out) throws IOException
	{	
		try 
		{
			byte[] b = getBytesFromFile( file );
			m_Entropy = CalcShannon(b, b.length);
			
			out.write((int)m_Entropy);
			//out.append(m_Entropy);
			
			System.out.println("Your entropy for this particular files is --> " + m_Entropy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

   public static byte[] getBytesFromFile(File file) throws IOException 
   {
        InputStream is = new FileInputStream(file);
    
		// Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
   
   public static double log2( double value )
   {
	   return Math.log( value ) / Math.log( 2.0 ) ;
   }
   
   /******************************* 
	* What I'm assuming
	* pBuffer: holds the bytes
	* FreqArray: Holds the frequency with which each character is used
    ***********************************************************************/
   public static int CalcFreq( byte[] pBuffer, int[] FreqArray, int length )
   {    
	 int[] CharArray = new int[256];	//The amount of ASCII characters
     int nCount;
     int nArraylen = 0;
     for( nCount =0; nCount < 256; nCount++ )
     {
       CharArray[nCount]=-1;
     }

     //Iterates through every character in the char array
     for( nCount = 0; nCount < length; nCount++ )
     {
     	//if position has not been initialized
       if( CharArray[(int)pBuffer[nCount]] == -1)
       {
         //Each ASCII character is represented by a position int the array
         //the ASCII character position is getting current array length
         CharArray[(int)pBuffer[nCount]]= nArraylen;
         nArraylen++;
       }
       //The amount of times that particular character is found increases
       FreqArray[ CharArray[(int)pBuffer[nCount]] ]++;
     }
     return nArraylen;
   }
   
   /*
    * What I am assuming 
    * pFreq: the frequency with which each ascii character is used
    * nFreqArraylen: is the return value from CalcFreq
    * nBufflen: the lenght of the byte array
    */
   public static float PerformShannon( int[] pFreq, int nFreqArraylen,int nBufflen)
   {
     float Shannon = 0;
     for( int i=0; i < nFreqArraylen;i++)
     {
       Shannon -= (float)pFreq[i]/(float)nBufflen * (float)log2((float)pFreq[i]/nBufflen);
     }
     return Shannon;
   }
   
   public static float CalcShannon( byte[] buffer, int nBufferLength )
   {
     float Entropy;
     int[] pFreq = new int[nBufferLength];
     int FreqArraylen = 0;
     FreqArraylen = CalcFreq( buffer, pFreq, nBufferLength );
     Entropy = PerformShannon( pFreq, FreqArraylen, nBufferLength );

     return Entropy;

   }
}