import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;

public class Cw1 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static String userName = "kuba";
    private static FileSystem fs;
	private static Boolean hadoop = true;
	static InputStream getInputStream(String fileName) throws IOException
	{
		if(! hadoop)
			return new FileInputStream(fileName);
		
		 Path path = new Path("hdfs://localhost:9000/user/" + userName + "/" + fileName);
		 return fs.open(path);
	}
	
	static OutputStream getOutputStream(String fileName) throws IOException
	{
		if(! hadoop)
			return new FileOutputStream(fileName);
		
		Path path = new Path("hdfs://localhost:9000/user/" + userName + "/" + fileName);
		if(fs.exists(path))
			fs.delete(path, true);
		
		return fs.create(path);
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {	
		if(hadoop)
			fs = FileSystem.get(new Configuration());
		
		if (args[0].startsWith("-p")) {
			OutputStream outStream = getOutputStream(args[args.length - 1]);
			PrintWriter writer = new PrintWriter(outStream);				
			
			for (int i = 1; i < args.length - 1; i++) {						
				InputStream inputStream = getInputStream(args[i]);
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				Boolean eofEveryLine = args[0].equals("-pl");
				
				while((line = reader.readLine()) != null)
				{
					writer.println(line);
					if(eofEveryLine)
						writer.println("///EOF///");		
				}
				writer.println("///EOF///");		
				reader.close();
			}						
			writer.close();
		}

		if (args[0].equals("-cl")) {			
			InputStream inStream = getInputStream(args[1]);
			int size = Integer.parseInt(args[2]);
			int hashSize = Integer.parseInt(args[3]);
			OutputStream outStream = getOutputStream(args[4]);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			HashShingleBuilder builder = new HashShingleBuilder(size, hashSize);
			ShingleProcessor processor = new ShingleProcessor(builder, size);
			String line;
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("///EOF///"))
					continue;
				processor.Process(line);
			}
			reader.close();

			if(! outStream.equals("null"))
			{				
				PrintWriter writer = new PrintWriter(outStream);
				for (String s : processor.Set) {
					writer.println(s);
				}
				writer.close();
			}			

			System.out.println("shingle count: " +processor.Set.size());
		}

		if (args[0].equals("-b")) {
			InputStream shingleListIn = getInputStream(args[1]);
			InputStream inputFile = getInputStream(args[2]);
			int size = Integer.parseInt(args[3]);
			int hashSize = Integer.parseInt(args[4]);			
			OutputStream output = getOutputStream(args[5]);
			
			//reading all shingles
			ArrayList<String> shingles = new ArrayList<String>(15000000);
			BufferedReader shingleListReader = new BufferedReader(
					new InputStreamReader(shingleListIn));
			
			String shing;
			while ((shing = shingleListReader.readLine()) != null) {
				shingles.add(shing);
			}
			shingleListReader.close();
			
			SparseMatrix matrix = new SparseMatrix();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));			
			int num = 0;
			HashShingleBuilder builder = new HashShingleBuilder(size,hashSize);
			ShingleProcessor processor = new ShingleProcessor(builder, size);

			String line;
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("///EOF///"))
				{
					for (String shingle : processor.Set) {
						int pos = Collections.binarySearch(shingles, shingle);
						if (pos >= 0)
							matrix.add(pos, num);
					}
					processor.Set.clear();					
					num++;
					continue;
				}
				processor.Process(line);
			}
			
			matrix.sortAndPrint(output);
		}

		if (args[0].equals("-m")) {
			InputStream input = getInputStream(args[1]);
			OutputStream output = getOutputStream(args[2]);
			MinhashGen gen = new MinhashGen();
			gen.GenereteMinhashes(input, output);
		}

		if (args[0].equals("-lsh"))
		{
			InputStream input = getInputStream(args[1]);
			OutputStream output = getOutputStream(args[2]);
			LocalSensitiveHasher lsh = new LocalSensitiveHasher();
			lsh.Execute(input, output);
		}
	}

}
