import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

    private final String[] instructions = {"SUB","SUBI","ADD","ADDI","AND","ANDI","OR","ORI","XOR","XORI","LD","ST","JUMP","PUSH","POP","BE","BNE"};
    private final static int immediateBit=7;
    private final static int addressBit=10;
    private final static int PCoffsetBit=8;

    public static void main(String[] args) throws IOException {

        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        fw.write("v2.0 raw\n");
        PrintWriter pw = new PrintWriter(fw);
        //pw.println("v2.0 raw");

        String line = null;
        //set try catch blocks to catch errors while reading a file
        try {
            //use buffered reader to read the input file
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            while((line = reader.readLine()) != null){

                StringTokenizer st = new StringTokenizer(line);
                String instruction = st.nextToken();

                switch(instruction) {
                    case "JUMP":
                        String dAddress = st.nextToken();
                        String output1 = (opCodeToBinary(instruction) +  "000000" + adrrToBinary(dAddress));
                        String out1 = toHex(output1.substring(0,4)) +
                                toHex(output1.substring(4,8))+
                                toHex(output1.substring(8,12))+
                                toHex(output1.substring(12,16))+
                                toHex(output1.substring(16,20));
                        pw.println(out1);
                        break;
                    case "LD":
                    case "ST":
                        String dRegister = st.nextToken(",");
                        String address = st.nextToken();
                        String output2 = (opCodeToBinary(instruction) + RegToBinary(dRegister) + "00" + adrrToBinary(address));
                        String out2 = toHex(output2.substring(0,4)) +
                                toHex(output2.substring(4,8))+
                                toHex(output2.substring(8,12))+
                                toHex(output2.substring(12,16))+
                                toHex(output2.substring(16,20));
                        pw.println(out2);
                        break;
                    case "SUB":
                    case "SUBI":
                    case "ADD":
                    case "ADDI":
                    case "AND":
                    case "ANDI":
                    case "OR":
                    case "ORI":
                    case "XOR":
                    case "XORI":
                        String dregister = st.nextToken(",");
                        String sregister = st.nextToken(",");
                        String immOrReg = st.nextToken();
                        if(immOrReg.startsWith("R")) {
                            String sregister2 = immOrReg;
                            String output3 = (opCodeToBinary(instruction) + RegToBinary(dregister) + RegToBinary(sregister) + "0000" + RegToBinary(sregister2) );
                            String out = toHex(output3.substring(0,4)) +
                                    toHex(output3.substring(4,8))+
                                    toHex(output3.substring(8,12))+
                                    toHex(output3.substring(12,16))+
                                    toHex(output3.substring(16,20));
                            pw.println(out);
                        }
                        else {
                            String immVal = immOrReg;
                            String output4 = (opCodeToBinary(instruction) + RegToBinary(dregister) + RegToBinary(sregister) + "1" + immToBinary(immVal) );
                            String out = toHex(output4.substring(0,4)) +
                                    toHex(output4.substring(4,8))+
                                    toHex(output4.substring(8,12))+
                                    toHex(output4.substring(12,16))+
                                    toHex(output4.substring(16,20));
                            pw.println(out);
                        }

                        break;
                    case "BE":
                    case "BNE":
                        String REG1 = st.nextToken(",");
                        String REG2 = st.nextToken(",");
                        String PCOffset = st.nextToken();
                        String output5 =(opCodeToBinary(instruction) + RegToBinary(REG1) + RegToBinary(REG2) + PCoffsetToBinary(PCOffset));
                        String out5 = toHex(output5.substring(0,4)) +
                                toHex(output5.substring(4,8))+
                                toHex(output5.substring(8,12))+
                                toHex(output5.substring(12,16))+
                                toHex(output5.substring(16,20));
                        pw.println(out5);
                        break;
                    case "PUSH":
                    case "POP":
                        String SR1 = st.nextToken(",");
                        String output6 =(opCodeToBinary(instruction) + RegToBinary(SR1) + "000000000000" );
                        String out6 = toHex(output6.substring(0,4)) +
                                toHex(output6.substring(4,8))+
                                toHex(output6.substring(8,12))+
                                toHex(output6.substring(12,16))+
                                toHex(output6.substring(16,20));
                        pw.println(out6);
                        break;
                }


            }reader.close();
            pw.close();

        }catch(Exception e) {
            e.printStackTrace();
        }


    }


    private static String opCodeToBinary(String value){
        switch(value) {
            case "SUB":
            case "SUBI":
                return "0000";
            case "ADD":
            case "ADDI":
                return "0001";
            case "AND":
            case "ANDI":
                return "0010";
            case "OR":
            case "ORI":
                return "0011";
            case "XOR":
            case "XORI":
                return "0100";
            case "LD":
                return "0101";
            case "ST":
                return "0110";
            case "JUMP":
                return "0111";
            case "PUSH":
                return "1000";
            case "POP":
                return "1001";
            case "BE":
                return "1010";
            case "BNE":
                return "1011";
            default:
                return null;
        }
    }
    public static String RegToBinary (String reg) {

        String[] regs = reg.split("R");
        int regIntValue = Integer.valueOf(regs[1]);

        switch (Integer.toBinaryString(regIntValue).length()) {
            case 1:
                return "000".concat(Integer.toBinaryString( regIntValue));
            case 2:
                return "00".concat(Integer.toBinaryString(regIntValue));
            case 3:
                return "0".concat(Integer.toBinaryString(regIntValue));
            case 4:
                return Integer.toBinaryString(regIntValue);
            default:
                return "Register does not exist.";
        }

    }

    private static String immToBinary(String immValue) {
        StringBuilder sb = new StringBuilder();
        //negative value
        if(immValue.startsWith("-")) {
            int number = (-1) * Integer.parseInt(immValue);
            if(number > 64 || number < 0 ) {
                return "Negative number can not smaller than -64.";
            }
            for(int i=0;i<immediateBit-Integer.toBinaryString(number).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(number));

            StringBuilder tempSb = new StringBuilder();
            for(int i=0;i<sb.toString().length();i++) {
                if(sb.toString().charAt(i)=='0') tempSb.append("1");
                else if(sb.toString().charAt(i)=='1')   tempSb.append("0");
            }
            int num1 = Integer.parseInt(tempSb.toString(), 2);
            int num2 = Integer.parseInt("0001",2);
            int sum = num1 + num2;
            return Integer.toBinaryString(sum).toString();
            //return tempSb.toString();
        }
        else {
            if(Integer.parseInt(immValue) > 63) {
                return "Positive number can nıt bigger than 63.";
            }
            for(int i=0;i<immediateBit-Integer.toBinaryString(Integer.parseInt(immValue)).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(Integer.parseInt(immValue)));
            return sb.toString();
        }
    }

    public static String adrrToBinary(String addr) {

        StringBuilder sb = new StringBuilder();
        //negative value
        if(addr.startsWith("-")) {
            int addrInt = (-1) * Integer.valueOf(addr);
            if(addrInt > 512 || addrInt < 0 ) {
                return "Address value out of bound!";
            }
            for(int i=0;i<addressBit-Integer.toBinaryString(addrInt).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(addrInt));

            StringBuilder tempSb = new StringBuilder();
            for(int i=0;i<sb.toString().length();i++) {
                if(sb.toString().charAt(i)=='0') tempSb.append("1");
                else if(sb.toString().charAt(i)=='1')   tempSb.append("0");
            }
            int num1 = Integer.parseInt(tempSb.toString(), 2);
            int num2 = Integer.parseInt("0001",2);
            int sum = num1 + num2;
            return Integer.toBinaryString(sum).toString();
            //return tempSb.toString();
        }
        else {
            if(Integer.parseInt(addr) > 512) {
                return "Address value out of bound.";
            }
            for(int i=0;i<addressBit-Integer.toBinaryString(Integer.parseInt(addr)).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(Integer.parseInt(addr)));
            return sb.toString();
        }
    }

    public static String PCoffsetToBinary(String PCOffset) {

        StringBuilder sb = new StringBuilder();
        //negative value
        if(PCOffset.startsWith("-")) {
            int PCOffsetInt = (-1) * Integer.valueOf(PCOffset);
            if(PCOffsetInt > 128 || PCOffsetInt < 0 ) {
                return "Relative Address value (PC Offset) out of bound!";
            }
            for(int i=0;i<PCoffsetBit-Integer.toBinaryString(PCOffsetInt).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(PCOffsetInt));

            StringBuilder tempSb = new StringBuilder();
            for(int i=0;i<sb.toString().length();i++) {
                if(sb.toString().charAt(i)=='0') tempSb.append("1");
                else if(sb.toString().charAt(i)=='1')   tempSb.append("0");
            }
            int num1 = Integer.parseInt(tempSb.toString(), 2);
            int num2 = Integer.parseInt("0001",2);
            int sum = num1 + num2;
            return Integer.toBinaryString(sum).toString();
            //return tempSb.toString();
        }
        else {
            if(Integer.parseInt(PCOffset) > 128) {
                return "Relative address value (PC offset) out of bound.";
            }
            for(int i=0;i<PCoffsetBit-Integer.toBinaryString(Integer.parseInt(PCOffset)).length();i++) {
                sb.append("0");
            }
            sb.append(Integer.toBinaryString(Integer.parseInt(PCOffset)));
            return sb.toString();
        }
    }

    public static String toHex(String binaryValue) {

        switch (binaryValue){
            case "0000":
                return "0" ;
            case "0001":
                return "1";
            case "0010":
                return "2";
            case "0011":
                return "3";
            case "0100":
                return "4";
            case "0101":
                return "5";
            case "0110":
                return "6";
            case "0111":
                return "7";
            case "1000":
                return "8";
            case "1001":
                return "9";
            case "1010":
                return "A" ;
            case "1011":
                return "B";
            case "1100":
                return "C";
            case "1101":
                return "D";
            case "1110":
                return "E";
            case "1111":
                return "F";
        }
        return "0";
    }
}
