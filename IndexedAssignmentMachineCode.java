import java.io.FileOutputStream;
import java.io.IOException;
public class GenerateMachineCodeIndexedAssignment {
    public static void main(String[] args) throws IOException {
        // Create a byte array to store the machine code
        byte[] machineCode = new byte[6];
        
        // Set the opcode for the MIPS instruction in the byte array
        machineCode[0] = (byte) 0x2b;
 
        // Set the source register in the byte array
        machineCode[1] = (byte) 0x04;
 
        // Set the base register in the byte array
        machineCode[2] = (byte) 0x00;
    
        // Set the offset in the byte array
        machineCode[3] = (byte) 0x00;
        machineCode[4] = (byte) 0x00;
        machineCode[5] = (byte) 0x18;
 
        // Write the machine code to a file
        try (FileOutputStream fos = new FileOutputStream("machine_code.bin")) {
            fos.write(machineCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
