import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private final String[] instructionSet = {"SUB", "SUBI", "ADD", "ADDI", "AND", "ANDI", "OR" ,"ORI",
                                             "XOR", "XORI", "LD", "ST", "JUMP", "PUSH", "POP", "BE", "BNE"};
    public static void main(String[] args) {
     try {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
         Main main = new Main();
         while((line = reader.readLine()) != null) {
             System.out.println(Arrays.toString(main.parseInstruction(line)));
        }
        reader.close();
    } catch (
    IOException e) {
        e.printStackTrace();
    }
}
    public String[] parseInstruction(String instruction){
     String parsed[] = instruction.split(" ");   //parsing opcode
     String operand[] = parsed[1].split(",");    //parsing operands
     return parsed;
     //System.out.println(Arrays.toString(opcode));
    }

    public String opcodeConverter (String instruction){
        switch () {
            case
        }
    }
}
