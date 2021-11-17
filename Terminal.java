

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


/**
 * @author aramg
 */


class Parser {
    public String commandName;
    public String[] args;


    public boolean parse(String input) {


        String args[] = input.split(" ");
        this.args = new String[args.length - 1];
        this.commandName = args[0];
        for (int i = 0; i < args.length - 1; i++) {

            this.args[i] = args[i + 1];


        }


        return true;

    }

    public String getCommandName() {

        return this.commandName;

    }

    public String[] getArgs() {
        return this.args;
    }

}


public class Terminal {

    Parser p = new Parser();
    String[] message;

    Path path;
    private String[] args;


    Terminal() {
        this.path = Paths.get(System.getProperty("user.dir"));
    }

    public void echo(String[] args) {                       //echo
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }

    public void pwd() {                                    //pwd

        System.out.println(this.path);


    }

    public void ls() {                                     //ls

        File directory = new File(".");  //current directory
        File[] fileList = directory.listFiles();
        for (File fileList1 : fileList) {
            System.out.println(fileList1);
        }

    }

    public void lsReverse() {                               //ls-r
        File directory = new File(System.getProperty("user.dir"));
        String arr[] = directory.list();
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.println(arr[i]);
        }
    }


    private static File makeFile(String destinationPath) {
        File file = new File(destinationPath);
        System.out.println(destinationPath);
        return file;
    }

    public static boolean touch(String destinationPath) throws Exception {    //touch
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0)
            throw new Exception(String.format("cannot touch '%s': No such file or directory", destinationPath));

        try {
            if (!file.createNewFile()) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean mkdir(String destinationPath) throws Exception {    //mkdir
        File file = makeFile(destinationPath);

        if (destinationPath.length() == 0)
            throw new Exception(String.format("cannot create directory ‘%s’: No such file or directory", destinationPath));
        if (file.exists())
            throw new Exception(String.format("cannot create directory ‘%s’: File exists", destinationPath));

        try {
            if (!file.mkdir()) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean rmdir(String destinationPath) throws Exception {      //rmdir
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.exists())
            throw new Exception(String.format("failed to remove '%s': No such file or directory", destinationPath));
        if (!file.isDirectory())
            throw new Exception(String.format("failed to remove '%s': Not a directory", destinationPath));

        try {
            if (!file.delete())
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static void cat(String[] args) {                                   //cat
        for (int i = 0; i < args.length - 1; i++) {
            try {
                FileReader fileReader = new FileReader(args[i]);
                BufferedReader in = new BufferedReader(fileReader);
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (FileNotFoundException ex) {
                System.out.println(args[0] + ", file not found.");
            } catch (IOException ex) {
                System.out.println(args[0] + ", input/output error.");
            }
        }
    }

    /**
     *
     */
    public void rm(String... args) {                                        //rm
        File myFile = new File(args[0]);
        if (myFile.delete()) {
            System.out.println("Deleted the file: " + myFile.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }


    public static void cp(String fromFile, String toFile) throws IOException {   //cp
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));

        String line = null;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        reader.close();  // Close to unlock.
        writer.close();  // Close to unlock and flush to disk.
    }

    public static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        }
    }


    public void cp_r(String... args) {                  //cp_r
        File s = new File(args[0]);
        File d = new File(args[1]);
        copyFolder(s, d);
    }


    public void cd(String[] args) throws Exception {
        Path newPath;
        if (args.length == 0) {
            newPath = Paths.get(System.getProperty("user.home"));
        } else {
            Path argumentPath = Paths.get(args[0]);
            if (argumentPath.toString().equals("..")) {
                newPath = this.path.getParent();
            } else {
                if (argumentPath.isAbsolute()) {
                    newPath = argumentPath;
                } else {
                    newPath = this.path.resolve(argumentPath);
                }
            }
        }
        this.path = newPath;

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Terminal t = new Terminal();
        Parser pars = new Parser();
        boolean stop = false;


        Scanner in = new Scanner(System.in);


        while (stop == false) {
            String input = in.nextLine();
            pars.parse(input);
            if ("echo".equals(pars.getCommandName())) {              //echo command
                t.echo(pars.getArgs());


            } else if ("pwd".equals(pars.getCommandName())) {        //pwd command
                t.pwd();

            } else if ("cat".equals(pars.getCommandName())) {         //cat command
                t.cat(pars.getArgs());
            } else if ("ls".equals(pars.getCommandName())) {          //ls command
                t.ls();
                String[] argss = pars.args;
            }else if ("ls-r".equals(pars.getCommandName())){
                t.lsReverse();
                String[] argss = pars.args;
            } else if ("rm".equals(pars.getCommandName())) {        //rm command
                t.rm(pars.getArgs());
            } else if ("touch".equals(pars.getCommandName())) {
                t.touch(pars.getArgs()[0]);
            } else if ("mkdir".equals(pars.getCommandName())) {
                t.mkdir(pars.getArgs()[0]);
            } else if ("rmdir".equals((pars.getCommandName()))) {
                t.rmdir(pars.getArgs()[0]);
            } else if ("cp-r".equals(pars.getCommandName())) {
                t.cp_r(pars.getArgs());
            } else if ("cp".equals(pars.getCommandName())) {
                t.cp(pars.getArgs()[0], pars.getArgs()[1]);
            } else if ("cd".equals(pars.getCommandName())) {
                t.cd(pars.getArgs());
            } else if ("exit".equals(pars.getCommandName())) {
                System.exit(0);
            } else {
                System.out.println("INVALID COMMAND: PLEASE ENTER A VALID ONE..");
            }
        }

    }


}

   