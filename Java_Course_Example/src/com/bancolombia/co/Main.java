package com.bancolombia.co;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World");

        char[] input = {'h','e','l','l','o'};

        String inputS = "Hello";
        System.out.println("input: "+reverseString(input));
        System.out.println("Reverse: "+reverse(inputS));
    }



    public static String reverse(String array){
        StringBuilder st = new StringBuilder(array);
        return st.reverse().toString();
    }


    public static char [] reverseString(char[] s){

        int size= s.length;
        char[] reverse = new char[size];
        for (int i=0;i<size;i++ ){
            reverse[i]=s[size-i-1];
        }
        System.out.println("Array first: "+s.toString());
        System.out.println("Array reverse: "+reverse.toString());
        return reverse.toString().toCharArray();
    }

}
