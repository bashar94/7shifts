
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bashar
 */
public class Test {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Input the String");
        String userInput = scan.nextLine();
        
        System.out.print("Result: ");
        System.out.println(add(userInput));
        
    }

    private static int add(String userInput) {
        String input = userInput.trim();
        
        if(input.length() < 1) return 0;                       //return zero if nothing found
        
        String delimiter = ",";                                //primary delimiter assign
        
//checking custom delimiter with arbitrary length
        String regex =  "(?<=\\/{2})(.*?)(?=\\\\n)";           //regex for custom delimiter check
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
    
        if (matcher.find()){
            delimiter = matcher.group();                       //find the custom delimiter
            input = input.replace("//"+delimiter+"\\n", "");   //removing custom delimiter part from string
        }
        
//New Line removing
        input = input.replaceAll("\\\\n", "");

//spliting by delimiter(any kind of) to get all number from string into array
        String[] allNumbers = input.split(makeDelimiter(delimiter));      
        
        int add = 0;
        
        try {                                               //try-catch for negative numbers
            String negativeNumbers = "";
            
            for(String number : allNumbers){
                //converting string to integer
                int temp = Integer.parseInt(number);
                
                if(temp < 0){                               //checking if the number is negative
                    negativeNumbers += temp+", ";
                }else if(temp <= 1000){                     //taking numbers smaller than 1000
                    add += temp;
                }
            }
            if(negativeNumbers.length() > 0){               //if there are negative numbers throw exception
                throw new NegativeNumberException("Negatives Not Allowed\n"+negativeNumbers.substring(0, negativeNumbers.length() - 2));
            }
             
        } catch (NegativeNumberException e) {
            System.out.println(e);       
        }
        
        return add;
    }


    private static String makeDelimiter(String delimiter) { //making regex for the delimiter to split the string
        if(delimiter.equals(",")) return delimiter;
        
        String temp = new String();
        String escapes = ".^$*+=?!()[]{}<>|\\";
        for (int i = 0; i < delimiter.length(); i++){
            String ch = Character.toString(delimiter.charAt(i));
            if(escapes.contains(ch)){
                temp += "\\"+ch;
            }else{
                temp += ch;
            }  
        }
        return temp.replaceAll(",", "\\|");
    }
    
}

class NegativeNumberException extends Exception {           //Negative Number exception class
    public NegativeNumberException(String errorMessage) {
        super(errorMessage);
    }
}
