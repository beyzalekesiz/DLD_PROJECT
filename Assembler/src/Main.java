import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {


    private final String[] instructionSet = {"SUB", "SUBI", "ADD", "ADDI", "AND", "ANDI", "OR" ,"ORI",
                                             "XOR", "XORI", "LD", "ST", "JUMP", "PUSH", "POP", "BE", "BNE"};
    public static void main(String[] args) {
    	
     String line = null;
     try {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        
        while((line = reader.readLine()) != null) {
        	String instruction = null;
        	String opcode = null;
        	StringTokenizer st = new StringTokenizer(line);
        	instruction = st.nextToken();
				 switch(instruction) {
				 case "SUB":
					 opcode = "0000";
					 break;
				 case "SUBI":
					 opcode = "0000";
					 break;
				 case "ADD":
					 opcode = "0001";
					 break;
				 case "ADDI":
					 opcode = "0001";
					 break;
				 case "AND":
					 opcode = "0010";
					 break;
				 case "ANDI":
					 opcode = "0010";
					 break;
				 case "OR":
					 opcode = "0011";
					 break;
				 case "ORI":
					 opcode = "0011";
					 break;
				 case "XOR":
					 opcode = "0100";
					 break;
				 case "XORI":
					 opcode = "0100";
					 break;
				 case "LD":
					 opcode = "0101";
					 break;
				 case "ST":
					 opcode = "0110";
					 break; 
				 case "JUMP":
					 opcode = "0111";
					 break;
				 case "PUSH":
					 opcode = "1000";
					 break;
				 case "POP":
					 opcode = "1001";
					 break;
				 case "BE":
					 opcode = "1010";
					 break;
				 case "BNE":
					 opcode = "1011";
					 break;
				 }
			 }
		
			 
			    
			    
        
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

