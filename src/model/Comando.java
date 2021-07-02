package model;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



/*
 * @ autor dessa luan marinate 
 */

public class Comando  {
	
	@SuppressWarnings("resource")
	public  static void copiaFile(File source, File destination) throws IOException {
		if (destination.exists())
		destination.delete(); 

		    FileChannel sourceChannel = null;
		    FileChannel destinationChannel = null;

		    try {
		        sourceChannel = new FileInputStream(source).getChannel();
		        destinationChannel = new FileOutputStream(destination).getChannel();
		        sourceChannel.transferTo(0, sourceChannel.size(),
		                destinationChannel);
		    } finally {
		        if (sourceChannel != null && sourceChannel.isOpen())
		            sourceChannel.close();
		        if (destinationChannel != null && destinationChannel.isOpen())
		            destinationChannel.close();
		   }
		}
	
	public static boolean copiaArquivos( File origem, File destino ){
        try{
            if( origem.isDirectory() ){
                if( !destino.exists() ){
                    destino.mkdir();
                }
                String[] children = origem.list();

                for (int i=0; i<children.length; i++){
                	copiaArquivos( new File( origem, children[i] ), new File( destino, children[i] ) );
                }
            } 
            else{

                InputStream in = new FileInputStream( origem );
                OutputStream out = new FileOutputStream( destino );
              
                byte[] buf = new byte[1024];
                int len;
                while( (len = in.read( buf ) ) > 0 ) {
                    out.write( buf, 0, len );
                }
                in.close();
                out.close();
            }
        }
        catch( IOException ioex ){

            ioex.printStackTrace();
            return false;
        }

        return true;
    }
	
	public static  void copiarUmArquivo(File file, String destino){
	    try {
	        FileInputStream fileIn = new FileInputStream(file);//file e passado usando JFileChooser
	        FileOutputStream fileOut = new FileOutputStream(destino+file.getName());//destino uso padrao
	        BufferedInputStream in = new BufferedInputStream(fileIn);
	        BufferedOutputStream out = new BufferedOutputStream(fileOut);

	        byte[] buffer = new byte[10240];
	        int len = 0;

	        while((len = in.read(buffer)) > 0) {
	            out.write(buffer, 0 , len);
	            System.out.println("#");
	        }

	    in.close();
	    out.close();;
	    }
	    catch(FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    catch(IOException io)
	    {
	        io.printStackTrace();
	    }
	}
	
	
	public static void compactarParaZip(String caminho) throws IOException {
		try {
		    FileOutputStream fos = new FileOutputStream(caminho+".zip");
		          
		    ZipOutputStream zipOut = new ZipOutputStream( fos );
		      
		    File pasta = new File(caminho);
		    for(File arq : pasta.listFiles() ){
		        zipOut.putNextEntry( new ZipEntry( arq.getName().toString() ) );           
		        @SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream( arq );
		                  
		        int content;
		        while ((content = fis.read()) != -1) {
		            zipOut.write( content );
		        }
		                  
		        zipOut.closeEntry();
		                  
		    }
		          
		    zipOut.close();
		          
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	
	public static void criarPastaComCaminho(String caminho,String nome) {
		  File diretorio = new File(caminho+"\\"+nome);
		  diretorio.mkdir();
	}
	  
		public static void limparPasta (File f) {
	        if (f.isDirectory()) {
	            File[] files = f.listFiles();
	            for (int i = 0; i < files.length; ++i) {
	            	limparPasta(files[i]);
	            }
	        }
	        f.delete();
	}
		public static void delPasta(String nome) {
			File f = new File(nome);
			if ((f.exists()) && (f.isDirectory()))
				f.delete();
		}
		public static void delArquivo(String caminho) {
			File file = new File(caminho);
			if(file.exists()){
				file.delete();
			}
		}

	public static void main(String[] args) {
	
		
	}

}
