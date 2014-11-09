package entropy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import javax.xml.crypto.Data;



public class Entropy_Main {
	
	///Users/carlosgarzon/Desktop/recitation/calgary/bib
	public static float m_Entropy = 0;
	
	public static void main( String [] args )
	{
		File file = new File("/Users/carlosgarzon/Desktop/recitation/calgary/book2");
		
		try 
		{
			byte[] b = getBytesFromFile( file );
			m_Entropy = CalcShannon(b, b.length);
			
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
