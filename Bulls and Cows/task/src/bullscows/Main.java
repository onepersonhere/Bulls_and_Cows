package bullscows;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static void inputHandler(String code){
        // match digit and index = bull++
        // match digit only = cow++
        // if all digit = bull, ends

        // take 4 digit input
        // predefined 4 digit code
        // grade answer that is input
        String secretcode = code;
        int length = secretcode.length();
        char[] c = new char[length];

        for(int i = 0; i < length; i++){
            c[i] = secretcode.charAt(i);
        }
        int turn = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Turn " + turn + ":");
        String str = scanner.nextLine();
        char[] s = new char[length];
        for(int i = 0; i < length; i++){
            s[i] = str.charAt(i);
        }

        int bull = 0; int cow = 0;
        while(bull < length) {

            bull = 0; cow = 0;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (s[j] == c[i]) {
                        if (s[i] == c[i]) {
                            bull++;
                            break;
                        } else {
                            cow++;
                            break;
                        }
                    }
                }
            }

            if(bull == 0 && cow == 0){
                System.out.println("Grade: None.");
            }else if(bull == 0){
                System.out.println("Grade: " + cow + " cow(s).");
            }else if(cow == 0){
                System.out.println("Grade: " + bull + " bull(s).");
            }else{
                System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s).");
            }
            turn++;

            if(bull != length){
                bull = 0; cow = 0;
                System.out.println("Turn " + turn + ":");
                str = scanner.nextLine();
                s = new char[length];
                for(int i = 0; i < length; i++){
                    s[i] = str.charAt(i);
                }
            }else{
                break;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    static long RNG(int length){
        if(length > 10){
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            System.exit(0);
        }

        long pseudoRandomNumber = System.nanoTime();
        String str = Long.toString(pseudoRandomNumber);
        int[] secretnum = new int[length];
        int enoughunique = 0;
        boolean b = true;

        while(b) {
            //System.out.println(pseudoRandomNumber);

            for(int i = 0; i < length; i++){
                //System.out.println("i is " + i);
                for(int j = 0; j < str.length(); j++){
                    int num = Character.getNumericValue(str.charAt(str.length() - j - 1));
                    //System.out.print(num + " ");

                    if(i == 0 && num != 0){
                        secretnum[0] = num;
                        enoughunique++; //System.out.println("EU: " + enoughunique);
                        break;
                    }
                    if (i > 0){
                        boolean unique = true;
                        for(int k = 0; k < enoughunique; k++){
                            if(num == secretnum[k]){
                                unique = false;
                            }
                        }
                        if(unique){
                            enoughunique++; //System.out.println("EU: " + enoughunique);
                            secretnum[i] = num;
                            break;
                        }
                    }
                }
                //System.out.println(Arrays.toString(secretnum));
            }

            if(enoughunique == length){
                b = false;
                break;
            }else {
                //System.out.println("new time");
                pseudoRandomNumber = System.nanoTime();
                str = Long.toString(pseudoRandomNumber);
                secretnum = new int[length];
                enoughunique = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i : secretnum){
            sb.append(i);
        }
        long secretNum = Long.parseLong(sb.toString());
        //System.out.println("The random secret number is " + secretNum);
        return secretNum;
    }

    static String newRNG(int length, int symbols){
        //48 - 57 | 97 - 122
        if(length > 36){
            System.out.println("Error: can't generate a secret number with a length of 36 because there aren't enough unique digits.");
            System.exit(0);
        }
        int max = 48 + symbols; int min = 48; int range = max - min + 1;

        //symbols = num of possible symbols aka changes the max
        char[] arr = new char[length];
        for(int i = 0; i < length; i++){
            int rand = (int)(Math.random() * range) + min;
            if(rand > 57){
                rand += 40;
            }
            char ch = (char)rand; //System.out.println(ch);
            boolean unique = true;
            for(int j = 0; j < i; j++){
                if(ch == arr[j]){
                    unique = false;
                    break;
                }
            }
            if(unique){
                arr[i] = ch;
            }else{
                i--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(char i : arr){
            sb.append(i);
        }
        String secret = sb.toString();
        return secret;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        if(!scanner.hasNextInt()){
            String s = scanner.nextLine();
            System.out.println("Error: \"" + s + "\" isn't a valid number.");
            System.exit(0);
        }
        int length = scanner.nextInt();
        if(length < 1){
            System.out.println("Error: can't generate a secret number with a length of 0.");
            System.exit(0);
        }
        
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scanner.nextInt();

        if(symbols < length){
            System.out.println("Error: it's not possible to generate a code with a length of "
                    + length + " with " + symbols + " unique symbols");
            System.exit(0);
        }
        if(symbols > 36){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
        String secret = newRNG(length, symbols); System.out.println(secret);
        System.out.print("The secret is prepared: ");
        for(int i = 0; i < length; i++){
            System.out.print('*');
        }
        //48 - 57 | 97 - 122
        symbols += 48;
        char symbol = (char)symbols;
        if(symbols <= 57) {
            System.out.print(" (0-" + symbol + ").\n");
        }else{
            symbols += 38;
            symbol = (char)symbols;
            System.out.print(" (0-9, a-" + symbol + ").\n");
        }
        System.out.println("Okay, let's start a game!");
        inputHandler(secret);
    }
}
