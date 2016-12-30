package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;

import crawler.Crawler;
import var.Varibles;

public class Mp2Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is Hashmap Implementation");
		File tf=new File("F:\\mp1\\indexes.txt");
		File pathName=new File("F:\\mp1\\name.txt");
		String sk="",di="",name=readPath(pathName);
		float start=0,end=0;
		HashMap<String,ArrayList<String>> hash=new HashMap<String,ArrayList<String>>();
		Scanner s=new Scanner(System.in);
		int choice=0;
		if(name!=null){
			System.out.println("Path index is "+name);
			System.out.print("Do you want to index another directory press 1 if so if not 2:");
			choice=s.nextInt();	
			
		}else{
			System.out.println("File Index this directory:");
			choice=1;
		}
		Varibles.map=new HashMap<String,ArrayList<String>>();
		Varibles.plist=new ArrayList<String>();
		try{
			if(choice==1){
				System.out.print("File Path:");
				di=s.next();
				File dir=new File(di);
				 start=System.nanoTime();
				 Crawler c1=new Crawler(di,"Ralph");
				 Crawler c2=new Crawler(di,"Inso");
				 Crawler c3=new Crawler(di,"Leah");
				 //Crawler c4=new Crawler(di,"Yna");
				 //Crawler c5=new Crawler(di,"Nik");
				 
				 Thread t1=new Thread(c1);
				 Thread t2=new Thread(c2);
				 Thread t3=new Thread(c3);
				 //Thread t4=new Thread(c4);
				 //Thread t5=new Thread(c5);
				 
				// Timer timer = new Timer();
				 
				 t1.start();
				 t2.sleep(100);
				 t2.start();
				 //timer.schedule(t2, 2000);
				 t3.sleep(300);
				 t3.start();
				 
				 //t4.sleep(500);
				 //t4.start();
				 
				 //t5.sleep(700);
				 //t5.start();
				 
				 t1.join();
				 t2.join();
				 //t3.join();
				 //t4.join();
				 //t5.join();
				 
				 
				//crawl(dir,hash);
				 Varibles.plist=null;
				 hash=Varibles.map;
				saveFile(tf,hash);
				 end=System.nanoTime();
				 writePath(pathName,di);
				 
				}else{
					hash=readFile(tf);
				}
			System.out.println("Toatal Words index "+hash.size());
			//System.out.println("Toatal Files index "+Varibles.plist.size());
			System.out.println("Finished indexing time:"+( ( (end-start)/1000000)/1000 )+" seconds");
			if (hash.isEmpty()){
				System.out.println("Empty");
			}
			while(true){
			System.out.print("Enter Search key:");
			sk=s.next();
			System.out.println("This are the files::");
			System.out.println(search(sk,hash));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}//end of main
	public static String search(String key,HashMap<String,ArrayList<String>> hash){
		String temp="";
		float start=System.nanoTime(),end=0;
		
		//System.out.println();
		int fc=0;
		if(hash.containsKey(key.toLowerCase())){
			end=System.nanoTime();
			for(String s:hash.get(key.toLowerCase())){
				temp+=s+"\n";
				fc++;
			}
			
		}else {
			return "Empty result set";
		}
		//end=System.nanoTime();
		temp+="Number of files:"+fc+"\n";
		temp+="Time taken to search:"+( (  (end-start)/1000000)/1000)+" seconds";
		return temp;
	}//end of search
	public static HashMap<String,ArrayList<String>> readFile(File file2){
		
		HashMap<String,ArrayList<String>>y = null;
		try {
			FileInputStream f1 = new FileInputStream(file2);  
			ObjectInputStream s1 = new ObjectInputStream(f1);  
			y= (HashMap<String,ArrayList<String>>)s1.readObject();
			s1.close();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return y;
	}//end of readfile
	public static void saveFile(File file,HashMap<String,ArrayList<String>>x){
		FileOutputStream f;
		try {
			f = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f);          
			s.writeObject(x);
			s.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}//end of save file
	public static void writePath(File file,String name){
		 try {
	            // Assume default encoding.
	            FileWriter fileWriter =
	                new FileWriter(file);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);

	            // Note that write() does not automatically
	            // append a newline character.
	            bufferedWriter.write(name);
	          
	            // Always close files.
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	          
	            // Or we could just do this:
	            // ex.printStackTrace();
	        }
	}//end of writefile
	public static String readPath(File file){
		BufferedReader br;
		String str="";
		try{
			br=new BufferedReader(new FileReader(file));
			
			while((str=br.readLine())!=null){
				return str;
				
				
			}
		}catch(Exception ex){
			System.out.println(ex);
			System.exit(0);
		}
		return str;
	}//end of read path

}
