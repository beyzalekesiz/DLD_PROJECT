import java.io.*;
import java.util.Scanner;
	
public class main {
	private final String[] instructions = {"SUB","SUBI","ADD","ADDI","AND","ANDI","OR","ORI","XOR","XORI","LD","ST","JUMP","PUSH","POP","BE","BNE"};
	private final int immediateBit=7;
	private final int addressBit=10;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main main = new main();
		main.readAndWriteFile("input.txt");
	}
	private void readAndWriteFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
			String line;
			//File myObj = new File(fileName);
			//Scanner reader = new Scanner(myObj);
			FileWriter fileWriter = new FileWriter("output.txt");
			while((line = reader.readLine()) != null) {
				String data = line;
				fileWriter.append(parser(data)+"\n");
			}
			reader.close();
			fileWriter.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	private String parser(String data) {
		String[] parseOpcode = data.split(" ");
		String[] parseReg = parseOpcode[1].split(",");
		StringBuilder sb = new StringBuilder();
		
		//sb.append(opCodeToBinary(parseOpcode[0]));
		
		//immediate value
		switch(parseOpcode[0]) {
		case "SUB","AND","ADD","OR","XOR":
			sb.append(registerToBinary(parseReg[0]));
		    sb.append(registerToBinary(parseReg[1]));
		    sb.append("0000");
		    sb.append(registerToBinary(parseReg[2]));
		    break;
		case "SUBI","ADDI","ANDI","ORI","XORI":
			sb.append(registerToBinary(parseReg[0]));
		    sb.append(registerToBinary(parseReg[1]));
		    sb.append("1");
		    sb.append(immToBinary(parseReg[2]));
		    break;
		
		case "LD","ST","PUSH","POP":
			sb.append(registerToBinary(parseReg[0]));
			//sb.append(addressToBinary(parseReg[1]));
			break;
		case "JUMP":
			//sb.append(addressToBinary(parseReg[0]));
			break;
		case "BE","BNE":
			sb.append(registerToBinary(parseReg[0]));
		    sb.append(registerToBinary(parseReg[1]));
		    //sb.append(addressToBinary(parseReg[2]));
	    }
		System.out.println(parseOpcode[0]);
		System.out.println(sb.toString());
		return sb.toString();//return convertToHex(sb.toString());
	}
	//private String opCodeToBinary(String value){}
	private String registerToBinary(String value){

        // we have just 16 register so we need just 4 bit for represent the registers
        String[] values = value.split("R");
        int valueInt = Integer.parseInt(values[1]) ;

        if (Integer.toBinaryString(valueInt).length() == 1)
            return "00"+Integer.toBinaryString(valueInt);
        else if (Integer.toBinaryString(valueInt).length() == 2)
            return "0"+Integer.toBinaryString(valueInt);
        else if (Integer.toBinaryString(valueInt).length() == 3)
            return Integer.toBinaryString(valueInt);
        else if(Integer.toBinaryString(valueInt).length()== 4)
        	return Integer.toBinaryString(valueInt);
        else
            return "Register fault";

    }
	//private String addressToBinary(String value){}
	
	private String immToBinary(String value) {
		StringBuilder sb = new StringBuilder();
        //2's complement
        if(value.startsWith("-")){
            // negative number
            String[] splitSign = value.split("-");
            int number = Integer.parseInt(splitSign[1])-1 ;
            if (number > 63 || number<0)
                return "Negative number can not smaller than -64";
            for (int i=0 ; i<immediateBit-Integer.toBinaryString(number).length() ; i++)
                sb.append("0");
            sb.append(Integer.toBinaryString(number));
            StringBuilder tempSb = new StringBuilder();
            for (int i=0 ; i<sb.toString().length() ; i++)
                if (sb.toString().charAt(i)=='0')  tempSb.append("1");
                else if(sb.toString().charAt(i)=='1')   tempSb.append("0");
            return tempSb.toString();
        }else{
            // positive number
            if (Integer.parseInt(value)>63)
                return "Positive number can not bigger than 63";
            for (int i=0 ; i<immediateBit-Integer.toBinaryString(Integer.parseInt(value)).length() ; i++)
                sb.append("0");
            sb.append(Integer.toBinaryString(Integer.parseInt(value)));
            return sb.toString();
        }

	}
	private String convertToHex(String binaryValue){

        return Integer.toHexString(convertToDecimal(binaryValue.substring(0,4))).toUpperCase() +
               Integer.toHexString(convertToDecimal(binaryValue.substring(4,8))).toUpperCase() +
               Integer.toHexString(convertToDecimal(binaryValue.substring(8,12))).toUpperCase() +
               Integer.toHexString(convertToDecimal(binaryValue.substring(12,16))).toUpperCase() +
               Integer.toHexString(convertToDecimal(binaryValue.substring(16,19))).toUpperCase() ;

    }
	private int convertToDecimal(String binaryFour){

        switch (binaryFour){
            case "0000":
            	return 0 ;
            case "0001":
            	return 1 ;
            case "0010":
            	return 2 ;
            case "0011":
            	return 3 ;
            case "0100":
            	return 4 ;
            case "0101":
            	return 5 ;
            case "0110":
            	return 6 ;
            case "0111":
            	return 7 ;
            case "1000":
            	return 8 ;
            case "1001":
            	return 9 ;
            case "1010":
            	return 10 ;
            case "1011":
            	return 11 ;
            case "1100":
            	return 12 ;
            case "1101":
            	return 13 ;
            case "1110":
            	return 14 ;
            case "1111": 
            	return 15 ;
    }

        return 0;
    }

}
