package org.headroyce.declanm2022;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static File fileOutput; //the output file
    private static File fileInput; // the input file
    private static String input; //the name of the input file
    private static String output; // the name of the output file(if any)

    public static void main(String[] args) {

        input = args[args.length-1];
        HashMap<String,Integer> FreqTable = Reader(input);
        Huffman huffman = new Huffman();
        HashMap<String,String> Dictionary = huffman.RunHuffman(FreqTable);

        //Find output file in arguments
        boolean isFileOutput = false;
        boolean isOutput = false;
        for(String arg: args){
            if(isOutput == true){
                output = arg;
                isOutput = false;
            }
            if(arg.equals("-o")){
                isOutput = true;
                isFileOutput = true;
            }
        }

        //Output
        if(isFileOutput){
            //Output to file
            Writer(Dictionary,output);
        }else{
            //Output to terminal
            System.out.println("---------- Huffman Dictionary ----------");
            Set set = Dictionary.keySet();
            Iterator it = set.iterator();
            for (Iterator iter = it; iter.hasNext(); ) {
                String value = (String) iter.next();
                System.out.print(value);
                for(int i = value.length(); i < 16; i++){
                    System.out.print(" ");
                }
                System.out.println(Dictionary.get(value));
            }
        }
    }

    //Reads input file and creates the frequency table
    private static HashMap<String,Integer> Reader(String input){
        HashMap FreqTable = new HashMap<String,Integer>();
        try{
            fileInput = new File(input);
            Scanner reader = new Scanner(fileInput);
            System.out.println(input);
            while(reader.hasNextLine()){
                //grab each line of the input file
                String data = reader.nextLine();
                Character character;
                for(int i = 0; i < data.length(); i++){
                    //grab every character of every line
                    character = data.charAt(i);

                    //checks to see if tab is used
                    if(character.equals("\t".charAt(0))){
                        if(FreqTable.containsKey("(tab)")){
                            //increase the count
                            Integer value = (Integer) FreqTable.get("(tab)");
                            int val = value.intValue()+1;
                            FreqTable.put("(tab)", val);
                        }else{
                            //add "(tab)"
                            FreqTable.put("(tab)", 1);
                        }
                    }else{
                        //space is "(space)" because " " does not work well
                        if(character.equals(" ".charAt(0))){
                            if(FreqTable.containsKey("(space)")){
                                //increase the count
                                Integer value = (Integer) FreqTable.get("(space)");
                                int val = value.intValue()+1;
                                FreqTable.put("(space)", val);
                            }else{
                                //add "(space)"
                                FreqTable.put("(space)", 1);
                            }
                        }else{
                            if(FreqTable.containsKey(character.toString())){
                                //increase count
                                Integer value = (Integer) FreqTable.get(character.toString());
                                int val = value.intValue()+1;
                                FreqTable.put(character.toString(),val);

                            }else{
                                //add character
                                FreqTable.put(character.toString(), 1);
                            }
                        }
                    }



                }
                //at the end of every line the line ends
                if(FreqTable.containsKey("(enter)")){
                    Integer value = (Integer) FreqTable.get("(enter)");
                    int val = value.intValue()+1;
                    FreqTable.put("(enter)", val);
                }else{
                    FreqTable.put("(enter)", 1);
                }
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("An Error Occurred");
        }
        return FreqTable;
    }

    //write into the output file
    private static void Writer(HashMap<String,String> dictionary, String file){

        try{
            fileOutput = new File(file);
            if(fileOutput.createNewFile()){
                System.out.println("File Created: "+fileOutput.getName());
            }else{
                System.out.println(file+" Already Exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FileWriter myWriter = new FileWriter(fileOutput);
            myWriter.write("---------- Huffman Dictionary ----------\n");
            Set set = dictionary.keySet();
            Iterator it = set.iterator();
            for (Iterator iter = it; iter.hasNext(); ) {
                String value = (String) iter.next();
                //write the value
                myWriter.write(value);
                //give an equal space for every value
                for(int i = value.length(); i < 16; i++){
                    myWriter.write(" ");
                }
                //write the binary code and end the line
                myWriter.write(dictionary.get(value)+"\n");
            }
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }
}