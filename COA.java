package rotor96Crypto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class COA {
	public static void main(String[] args) throws IOException {
		String ciphertext="(Vl;^gcbNKv7;[z<aw@HVpH4yBZB0F4X81bR\\#xk/\\FVLOS?47[:-Cx29AW9,ZC(Vj1~j7'P ]_+[Xcc%|85nzOK'8bqXJqG*b2$n5N~Ev@U;z<Wui*EGx4al)uBY#/88YJk\\Ab%RqVhU#n87u_7tUxmUg6D?$C0$Q,#IQ7~mwk~]x90BKwaF>9pzlqU=(6dEM!v5\\oK2tj8EqaGgEG!&?Z,B-$0VMJ*3>M=Ay9|%TUo%q8_j{CoJgcI?Sm3a0Jl[0j.N=$Y7~MjcH5ofD7BAXlK.t_anu*Mb#~Aw_$2vU0*Xs7c)Wb:m<&kp@pM8BJCp";

	    int totalRequiredCiphersmin=0;
	
	   

	   
	        
 /////////// scanning the passwords //////////////////
        
        BufferedReader bufReader = null;
		try {
			bufReader = new BufferedReader(new FileReader("/Users/sujan/Downloads/passwords.txt"));///mention your path
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        List<String> keys = new ArrayList<>(); 
       

        String line = bufReader.readLine();
        while (line != null) {
        	keys.add(line);
        line = bufReader.readLine(); } 
        bufReader.close();
       int passwordlistsize= keys.size();
       
 /////////////////scanning done////////////////
        
        boolean containsWord =true;
        String[] stopwords = {" about ", " actually ", " almost ", " also ", " although ", " always ", " am ", " an ", " and ", 
        						" any ", " are ", " as ", " at ", " be ", " became ", " become ", " but ", " by ", " can ", " could ", 
        						" did ", " do ", " does ", " each ", " either ", " else ", " for ", " from ", " had ", " has ", " have ",
        						" hence ", " how ", " I ", " if ", " in ", " is ", " it ", " its ", " just ", " may ", " maybe ", " me ", 
        						" might ", " mine ", " must ", " my ", " mine ", " must ", " my ", " neither ", " nor ", " not ", " of ",
        						" oh ", " ok ", " when ", " where ", " whereas ", " wherever ", " whenever ", " whether ", " which ",
        						" while ", " who ", " whom ", " whoever ", " whose ", " why ", " will ", " with ", " within ", " without ",
        						" would ", " yes ", " yet ", " you ", " your "};
        
        //////testing other plaintext to make our system more sclable
        
//        String ciphertext1="1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9 and 10 are numbers";
//        String ciphertext3="pneumonoultramicroscopicsilicovolcanoconiosis is the biggest word in the English sentence.";
//        String ciphertext4= "Sujan: story of a daring guy. :)";
//        String ciphertext5= "!,@,£,$,%,^,&,*,(,),_ and + are the non-alphanumeric symbols";

//        String encryptedmsg= Rotor96Crypto.encdec(2,"shadow",ciphertext1);
        
        ////////// testing other ciphertext
        
        
        Set<String> finalkeylist=new HashSet<>();
        
    
  
       
        System.out.println("Loading........................... cheking for best result");
        
        ////// Starting the time
        
        long startTime = System.currentTimeMillis();
        
        for (String key : keys) {
        	if (ciphertext.length()>45) {
            for (int j =1; j<50 ;j++) { // 50 because the biggest word in the english sentense is of 45 lenght long . so we have to encounter space at 46th character
            String decryptedmessage = Rotor96Crypto.encdec(2,key,ciphertext.substring(0,j));//converting to lowercase to keep intergrity with the stopwords
            
            String lowercaseDecryptedmessage=decryptedmessage.toLowerCase();
            
            
            if(lowercaseDecryptedmessage.contains(" ")) { //if contains space, then check for stop words.
            	
            if (containsWord = Arrays.stream(stopwords)
        	        .anyMatch(word -> lowercaseDecryptedmessage.contains(word))){
            	finalkeylist.add(key);
            	totalRequiredCiphersmin=j;
            	break;
            }
            
            else {
            	continue;
            	
            }
            
           
            }
            else {//if it doesn't have space till 45th character then definitely it is not a English sentence
                if(j==46) {
                	break;
                }
            continue;
            }
        }
        	}
        	else {
        		for (int j =1; j<ciphertext.length()+1;j++) {
                	
                    String decryptedmessage = Rotor96Crypto.encdec(2,key,ciphertext.substring(0,j));
                    String lowercaseDecryptedmessage=decryptedmessage.toLowerCase();
                    
                    
                    if(lowercaseDecryptedmessage.contains(" ")) {
           
                    if (containsWord = Arrays.stream(stopwords)
                	        .anyMatch(word -> lowercaseDecryptedmessage.contains(word))){
                    	 System.out.println(lowercaseDecryptedmessage);
                    	finalkeylist.add(key);
                    	totalRequiredCiphersmin=j;
                    	break;
                    }
                    
                    else {
                    	continue;
                    	
                    }
                    
                   
                    }
                    else {
                        if(j==46) {
                        	break;
                        }
                    continue;
                    }
                }
        	}
        
        }
        long endTime = System.currentTimeMillis();
        //// stopping the time
        
        long duration = (endTime - startTime)/1000;
        System.out.println("Loop took " + duration + " seconds.");

        
        System.out.println("*---------*---------*---------* Theoritical calculation *----------*-----------*----------*-----------*----------*-----------*");
        double entropy = Math.log(passwordlistsize) / Math.log(2);
        System.out.println("Entropy: " + entropy);
        double R=4.7;
        double r= 1.5;
        System.out.println("Absolute Rate: " + R);
        System.out.println("Actual Rate: " + r);
        int unicitydistance=(int) (entropy/(R-r));
        System.out.println("Unicity Distance: " + unicitydistance);
        System.out.println("Total ciphertext letters needed to decode the message unambiguously ( Theoritically ): " + unicitydistance);
        System.out.println("Total ciphertext letters needed to decode the message unambiguously ( practical approach ): "+(totalRequiredCiphersmin-1));
        
        
       System.out.println("final key list"+ finalkeylist);
       for (String item:finalkeylist) { // just to make sure that there will be only one key in the list.
       
        	String Ans= Rotor96Crypto.encdec(2, item,ciphertext);
         
			String words[] = Ans.split(" ");
			Boolean Isenglish =false;
			for (String word : words) {
				if (isvalidEnglishSentence(word)==true){
					Isenglish =true;
				
				}
			}
			if (Isenglish == true) {
		        System.out.println("*---------*---------*---------* Final Answer *----------*-----------*----------*-----------*----------*-----------*");

				System.out.println("Key found: " + item);
				System.out.println("Plaintext: "+Ans);	
			}
       }
        
	
	}
	 public static boolean isvalidEnglishSentence(String sentence) {
	        sentence = sentence.toLowerCase(); // convert sentence to lowercase
	        int[] Freq = new int[26]; // frequency of each English letter
	       
	        for (int i = 0; i < sentence.length(); i++) { // update the letter frequency array by iterating through the sentence.
	            char character = sentence.charAt(i);
	            if (character >= 'a' && character <= 'z') {
	                Freq[character - 'a']++;
	            }
	        }
	        int englishLetter= 0;// calculate the total number of English letters in the sentence
	        for (int freq : Freq) {
	            englishLetter =englishLetter+freq;
	        }
	        double englishLetterPercentage = (double) englishLetter / sentence.length();// determine how many words are in English in the statement
	        return englishLetterPercentage >= 0.5;
	    }
	

}
