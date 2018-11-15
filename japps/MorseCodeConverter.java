package jApps;

/**
    @param author Seniru
    Give a string input to convert.
        ie. Input: Sololearn
**/


public class MorseCodeConverter {

     static String[] chars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
     static String[] morse = {"•-","-•••","-•-•","-••","•","••-•","--•","••••","••","•---","-••-","•-••","--","-•","---","•--•","--•-","•-•","•••","-","••-","•••-","•--","-••--","-•--","--••","•----","••---","•••--","••••-","•••••","-••••","--•••","---••","----•","-----"};
     
    public static String convert(String input) {
    input = input.toLowerCase().replaceAll("[^a-zA-Z0-9/l]","  ");
    for(int i=0;i<=chars.length-1;i++) {
      input =  input.replaceAll(chars[i]," "+morse[i]);
    }
    return input.replaceAll("•",".");
    }


}
