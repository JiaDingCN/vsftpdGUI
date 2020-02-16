package sample;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class myUtils {
    public static String bashCommand(String command) {
        Process process = null;
        String stringBack = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : processList) {
            stringBack += line;
            stringBack +="\n";
        }
        return stringBack;
    }

    public static Vector<String> bashCommandVectorWithoutComment(String command) {
        Process process = null;
        Vector<String> vectorBack = new Vector<String>();
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : processList) {
            if(line.length()>1 && line.trim().charAt(0)!='#') {
                vectorBack.add(line);
            }
        }
        return vectorBack;
    }
    public static Vector<String> bashCommandVectorWithComment(String command) {
        Process process = null;
        Vector<String> vectorBack = new Vector<String>();
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : processList) {
                vectorBack.add(line);
        }
        return vectorBack;
    }
    public static boolean isNullPath(String returnWhereis){
        return returnWhereis.charAt(returnWhereis.length()-2)==':';
    }
    public static HashMap<String,String> readConfig(){
        HashMap<String,String> resultMap=new HashMap<String, String>();
        Vector<String> strings = bashCommandVectorWithoutComment("cat /etc/vsftpd.conf");
        for (String line : strings){
            String[] splitItem = line.split("=");
            resultMap.put(splitItem[0],splitItem[1]);
        }
        return resultMap;
    }
    public static String runBashResourceFromJar(String name,InputStream in,String addedCommand) throws IOException {
        File tempBash=new File("./"+name);
        copyFile(in,tempBash);
        String s = bashCommand("bash ./" + tempBash.getName() + " " + addedCommand);
        tempBash.delete();
        return s;
    }
    public static String runBashResourceFromJar(String name,InputStream in) throws IOException {
        File tempBash=new File("./"+name);
        copyFile(in,tempBash);
        String s = bashCommand("bash ./" + tempBash.getName() );
        tempBash.delete();
        return s;
    }
    public static void copyFile(InputStream in,File to) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(in,"utf8"));
        String temp=br.readLine();
        if(to.exists()){
            to.delete();
        }
        to.createNewFile();
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to,false),"UTF-8"));
        while(temp!=null){
            out.write(temp+"\n");
            temp=br.readLine();
        }
        in.close();
        br.close();
        out.close();
    }
    @Test
    public void test() {

    }
}
