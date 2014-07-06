package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;

public class FnyMSG {
    private ImageIcon image;
    private String text;
    private static int[][] levels;
    private static ArrayList<ArrayList<FnyMSG>> messages=null;  
    
    public FnyMSG(String img, String message){
        this.image=new ImageIcon(getClass().getResource(img));
        this.text=message;    
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
       
    private static void initMessages(String file){
        InputStream input= FnyMSG.class.getResourceAsStream(file);
        Scanner scan= new Scanner(input,"UTF-8");  
        int lv=Integer.valueOf(scan.nextLine());
        levels= new int[lv][2];
        messages= new ArrayList<>(lv);
        
        int lvl =-1;
        while(scan.hasNext()){
            String line=scan.nextLine();
            if(line.startsWith("*")){
                lvl++;
                String[] l= line.substring(1).split("-");
                levels[lvl][0]=Integer.valueOf(l[0]);
                levels[lvl][1]=Integer.valueOf(l[1]);
                messages.add(new ArrayList<FnyMSG>());                                
                continue;
            }
            String[] v= line.split(";");
            String t=v[0];
            String m=v[1];
            
            messages.get(lvl).add(new FnyMSG(m,t));
        }        
    }
       
    public static FnyMSG getFunnyMessage(int credits){
        Random rand= new Random();
        if(messages==null){
            initMessages("/view/MSG/messages.txt");
        }
        int i=0;
        int level=-1;
        for(int[] arr: levels){
            if(credits>=arr[0]&&credits<=arr[1]){
                level=i;
                break;
            }
            i++;
        }        
        return level<0?null:messages.get(level).get(rand.nextInt(messages.get(level).size()));
    }

    public static void main(String[] args){
        initMessages("/view/MSG/messages.txt");
    }
    
}
