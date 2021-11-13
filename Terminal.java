/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projectos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author aramg
 * 
 * 
 */


class Parser{
  public String commandName;
  public String[] args;
    
    
    public boolean parse(String input){
        
  
        this.args = input.split(" ");
        this.commandName = this.args[0];
        for (int i=1;i<this.args.length;i++){
           
            this.args[i-1] = this.args[i];
          
            if(i==this.args.length-1){
                this.args[i]="";
            }
            
        }
               
       
      return true;
        
    }
    public String getCommandName(){
        
        return this.commandName;
        
    }
    public String[] getArgs(){
        return this.args;
    }
    
}


public class Terminal {
    
     Parser p = new Parser();
     String []message;
     String path;
     
     public  String[] echo(Parser p){
        return p.getArgs();
     }
     
     public void pwd(){
        this.path = System.getProperty("user.dir");
         System.out.println(this.path);
         
     }
     
     public void ls(){
       
       File directory = new File(".");  //current directory
       File []fileList = directory.listFiles();
         for (File fileList1 : fileList) {
             System.out.println(fileList1);
         }

     }
     
     public void lsReverse(){
         File directory2 = new File(".");  //current directory
         File []fileList = directory2.listFiles();
            for (int i=fileList.length-1; i>=0;i--){
                System.out.println(fileList[i]);
            }
         }
     
     
    public static void cat(String[] args)
{
    for (int i = 0; i < args.length - 1; i++)
    {
        try {
            FileReader fileReader = new FileReader(args[i]);
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while((line = in.readLine())!= null){
                System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(args[0]+", file not found.");
        }
        catch (IOException ex) {
            System.out.println(args[0]+", input/output error.");
        }
    }
}
    
    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Terminal t = new Terminal();
        Parser pars = new Parser();
        boolean stop = false;
        
        
        Scanner in = new Scanner(System.in);
        
       while (stop==false){
            String input = in.nextLine();
            pars.parse(input);
            if("echo".equals(pars.getCommandName())){              //echo command    
                String [] echoReturn=t.echo(pars);
            for (String echoReturn1 : echoReturn) {
                System.out.print(echoReturn1 + " ");
            }
                
            }
            else if ("pwd".equals(pars.getCommandName())){        //pwd command
                t.pwd();
            }
            else if("cat".equals(pars.getCommandName())){         //cat command
                t.cat(pars.getArgs());
            }
            else if("ls".equals(pars.getCommandName())){          //ls command
                t.ls();
                String[]argss = pars.args;
                
                   if ("-r".equals(argss[0])){                    //ls -r command
                       t.lsReverse();
                       
                   }
                
                
                
            }
 
          
            
            
            
        
        
 }
        
  }
}

   