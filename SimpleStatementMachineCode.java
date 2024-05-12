import java.io.FileOutputStream;
import java.io.IOException;
public class SimpleStatementMachineCode {
    public static void main(String[] args) throws IOException {
        //Create a byte array to store the machine code
        byte[] machineCode = new byte[4];

        //Set the opcode for the MIPS instruction in the byte array
        machineCode[0] = (byte) 0x0;

        //Set the source register for the first operand in the byte array
        machineCode[1] = (byte) 0x0;

        //Set the source register for the second operand in the byte array
        machineCode[2] = (byte) 0x80;

        //Set the destination register in the byte array
        machineCode[3] = (byte) 0x21;

        //Write the machine code to a file
        try (FileOutputStream fos = new FileOutputStream("machine_code.bin")){
            fos.write(machineCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

