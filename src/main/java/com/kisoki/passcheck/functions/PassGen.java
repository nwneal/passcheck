/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kisoki.passcheck.functions;

import java.util.Arrays;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

public class PassGen {
    // setup collection pool of chars for random password generation.
    private static char[][] availableChars = {
        {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}, // length = 26
        {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}, // length = 26
        {'0','1','2','3','4','5','6','7','8','9'}, // length = 10
        {'!','@','#','$','%','^','&','*',':','~','?'} // length = 11
    };
    
    // set main function for random password generation.
    public static String randomPassword(String doNotInclude, int length, boolean useCaps, boolean useNums, boolean useChars) {
        if (length < 8) length = 8;  // set min pass length.
        else if (length > 26) length = 26; // set max pass length.
        char[] password = new char[length]; // initialize password array.
        int pl = setPoolLength(useCaps,useNums,useChars); // set length of the password collection pool based on options. 
        char[] pool = setPool(pl); // set the password collection pool.
        
        for (int i = 0; i < length; i++) {
            int rn = 0;
            do {
                rn = randInt(0, pl-1);
            } while (checkChar(doNotInclude, pool[rn]));
            password[i] = pool[rn];
        }
              
        return Arrays.toString(password); // return joined char array.
    }
    
    private static boolean checkChar(String dni, char c) {
        boolean check = false;
        
        if (!dni.equals("")) {
            char[] dnichar = dni.toCharArray();
            for (int i = 0; i < dnichar.length; i++) {
                if (c == dnichar[i]) {
                    check = true;
                }
            }
        }
        
        return check;
    }
    
    private static int setPoolLength(boolean useCaps, boolean useNums, boolean useChars) {
        int pl = 26;
        
        if (useCaps) pl+=26;
        if (useNums) pl+=10;
        if (useChars) pl+=11;
        
        return pl;
    }
    
    private static char[] setPool (int pl) {
        char[] pool = new char[pl];
        int num = pl - 26;
        
        switch (num) {
            case 0: // lower-case only
                pool = availableChars[0];
                break;
            
            case 10: // lower-case & numbers 
                pool = (char[])ArrayUtils.addAll(availableChars[0],availableChars[2]);
                break;
                
            case 11: // lower-case & special chars
                pool = (char[])ArrayUtils.addAll(availableChars[0],availableChars[3]);
                break;
                
            case 21: // lower-case, numbers & special chars
                pool = (char[])ArrayUtils.addAll(ArrayUtils.addAll(availableChars[0],availableChars[2]),availableChars[3]);
                break;
                    
            case 26: // lower-case & upper-case
                pool = (char[])ArrayUtils.addAll(availableChars[0],availableChars[1]);
                break;
                
            case 36: // lower-case, upper-case & numbers
                pool = (char[])ArrayUtils.addAll(ArrayUtils.addAll(availableChars[0],availableChars[1]),availableChars[2]);
                break;
                
            case 37: // lower-case, upper-case & special chars
                pool = (char[])ArrayUtils.addAll(ArrayUtils.addAll(availableChars[0],availableChars[1]),availableChars[3]);
                break;
                
            case 47: // all
                pool = (char[])ArrayUtils.addAll(ArrayUtils.addAll(availableChars[0],availableChars[1]),ArrayUtils.addAll(availableChars[2],availableChars[3]));
                break;
        }
        
        return shufflePool(pool); // shuffle pool and return
    }
    
    private static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    
    private static char[] shufflePool(char[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }        
        return a;
    }

    private static void swap(char[] a, int i, int change) {
        char helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
    
}
