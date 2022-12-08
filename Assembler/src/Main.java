import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    private final String[] instructionSet = {"SUB", "SUBI", "ADD", "ADDI", "AND", "ANDI", "OR" ,"ORI",
                                             "XOR", "XORI", "LD", "ST", "JUMP", "PUSH", "POP", "BE", "BNE"};
    public static void main(String[] args) {
     try {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    } catch (
    IOException e) {
        e.printStackTrace();
    }
}

}
