import java.io.*;

class Tinker {
    public static void main(String[] args) {

        String file = "dell_products.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null) {
                String[] token = line.split(cvsSplitBy);
                for(String str : token)
                    System.out.print(str+" ");
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}