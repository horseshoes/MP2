package crawler;
import var.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Crawler implements Runnable {
	private String path;
	private static File dir;
	private String name;
	
	public Crawler(String path,String name){
		//super();
		this.path=path;
		this.name=name;
		this.dir=new File(path);
		
		
	}
	@Override
	public void run() {
		try{
		crawl(dir);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void crawl(File dir){
		
		File [] files=dir.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				crawl(f);
			}else{
				
				
				if(f.getName().endsWith(".txt"))
					
					if(!checkList(f.getAbsolutePath())){
						addList(f.getAbsolutePath());
						index(f.getAbsolutePath());
						System.out.println(this.name+":"+f.getName()+ " path:"+f.getAbsolutePath());
						
					}
			}
		}
	}//end of crawl
	public  void index(String path){
		BufferedReader br;
		try{
			br=new BufferedReader(new FileReader(path));
			String str ="";
			int c;
			while((c=br.read())!=-2 ){
				
				if( (c==45) || (c==39) || ( (c>=65) && (c<=90) ) || ( (c>=97)) && (c<=122) ){
					str+=(char)c;
					//System.out.println("Paht:"+path+" word:"+str);
				
				}else{
					//System.out.println("Paht:"+path+" word:"+str);
					if(str.length()>=1){
						if(checkMap(str.toLowerCase())){
							
							if(!isContain(str.toLowerCase(),path)){//if hash already contains path
								update(str.toLowerCase(),path);
							}
						}else{
								add(str.toLowerCase(),path);
						}
						str=null;
						str="";
					}	
				}//end of else
				if(c==-1){
					if(str.length()>=1){
						if(checkMap(str.toLowerCase())){
							
							if(!isContain(str.toLowerCase(),path)){//if hash already contains path
								update(str.toLowerCase(),path);
							}
						}else{
								add(str.toLowerCase(),path);
						}
						str=null;
						str="";
					}
					break;
				}
			
			}
			//qThread.sleep(5000);
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(0);
		}
		
	}//end of index
	public  synchronized  boolean checkMap(String key){
		if(Varibles.map.containsKey(key))
			return true;
		else
			return false;
	}
	public  synchronized boolean isContain(String key,String path){
		if(Varibles.map.get(key).contains(path))
			return true;
		else
			return false;
	}
	public  synchronized  void add(String key,String path){
		ArrayList<String>temp=new ArrayList<String>();
		temp.add(path);
		Varibles.map.put(key,temp);
		temp=null;
	}
	public  synchronized void update(String key,String path){
		Varibles.map.get(key).add(path);
	}
	//list of directories
	public  synchronized  boolean checkList(String key){
		if(Varibles.plist.contains(key))
			return true;
		else
			return false;
	}
	public  synchronized  void addList(String path){
		Varibles.plist.add(path);
	}	
	
	
	

}
