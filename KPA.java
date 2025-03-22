package rotor96Crypto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class KPA {
    public static void main(String[] args) throws IOException {
        String ciphertext = ",x+Vp(FhreypzL?544R$$oB\\PESQi\"m_SA;?%9:';2HN4/>5/U|&=<x08oJ!g>QNhg>|>+ |?Ok}}!SgbXmMICok~tyi\"";
        String plaintextbegining = "Th";
        String FinalAns=null;
        String Finalkey=null;
        
        /////////// scanning the passwords //////////////////
        
        BufferedReader bufReader = null;
		try {
			bufReader = new BufferedReader(new FileReader("/Users/sujan/Downloads/passwords.txt"));/// give file path here
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        List<String> keys = new ArrayList<>(); 
        String line = bufReader.readLine();
        while (line != null) {
        	keys.add(line);
        line = bufReader.readLine(); } 
        bufReader.close();
        
        
        /////////////////////////////// calling the decryption class//////////////////////
        
       
        List<String> finalkeylist= new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (String key : keys) {
            String enc = Rotor96Crypto.encdec(2,key,ciphertext.substring(0,2));
            if (enc.startsWith(plaintextbegining)) {
                finalkeylist.add(key);
//               
            }

        }
        System.out.println("List of key found : "+finalkeylist);

		System.out.println("*---------*---------*---------*----------*----------*----------*-----------*----------*-----------*");
        for(String items: finalkeylist) {
        	
        	String Ans= Rotor96Crypto.encdec(2, items,ciphertext);

            System.out.println("Key found: " + items);

        	
			String words[] = Ans.split(" ");
			Boolean Isenglish =false;
			for (String word : words) {
				if (EnglishwordValidator.wordvalidate(word)==true){
					Isenglish =true;
				
				}
			}
			if (Isenglish == true) {
				System.out.println(Ans);	
				FinalAns=Ans;
				Finalkey =items;
				System.out.println("This is a english sentence, Correct Key. Hurray!!!!");
				System.out.println("*---------*---------*---------*----------*----------*----------*-----------*----------*-----------*");
			}
			else {
				System.out.println(Ans);
				
				System.out.println("This is not a english sentence, key might be wrong. Check for other key!!!!");
				System.out.println("*---------*---------*---------*----------*----------*----------*-----------*----------*-----------*");
			}
        	
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Loop took " + duration + " milliseconds.");
        System.out.println("*---------*---------*---------* Final Answer *----------*-----------*----------*-----------*----------*-----------*");
        System.out.println("List of key found : "+finalkeylist);
        System.out.println("Correct key : "+Finalkey);
        System.out.println("PLaintext : "+FinalAns);
        System.out.println("PLaintext : "+FinalAns.length());
    }

    public class EnglishwordValidator {
	    public static boolean wordvalidate(String sentence) {
	    	// defining the pattern of the valid English word using regex
	    	// Then no english word has capital letter in between 
	    	// Then checking for valid alphabets
	    	// later hyphen should always surrounded by letters
	    	// at last all punctuation marks should be at the last of the word.
	        String regex = "^[a-z]+(?:-[a-z]+)*(?: [a-z]+(?:-[a-z]+)*)*[.?!]$";
	        
	        return Pattern.matches(regex, sentence);
	    }
	}
    	}






