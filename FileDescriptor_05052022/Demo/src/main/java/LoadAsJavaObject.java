//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//import org.yaml.snakeyaml.Yaml;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//
//public class LoadAsJavaObject {
//
//
//    static String format;
//    static ArrayList<String> data;
//
//
//    public static void main(String[] args) throws IOException, CsvException {
//        Consumer Consumer;
//
//        Yaml yaml = new Yaml();
//        try (InputStream in = LoadAsJavaObject.class
//                .getResourceAsStream("/consumer.yml")) {
//            Consumer = yaml.loadAs(in, Consumer.class);
////             //format= Consumer.getFormat();
////            System.out.println(Consumer);
////
////            CSVReader reader = new CSVReader(new FileReader("C:\\Users\\ttc6493\\Downloads\\FileDescriptor_05052022\\Demo\\src\\main\\resources\\testfile.csv"));
////            //Reading the contents of the csv file
////            List list = reader.readAll();
////            //Getting the Iterator object
////            Iterator it = list.iterator();
////            while(it.hasNext()) {
////                String[] str = (String[]) it.next();
////                System.out.println(Arrays.toString(str));
////            }
////
//////            if(format.equals("csv")) {
//////                Path out = Paths.get("output.csv");
//////                Files.write(out, data, Charset.defaultCharset());}
//////            else if(format.equals("txt")) {
//////                Path out = Paths.get("output.txt");
//////                Files.write(out, data, Charset.defaultCharset());
//////
//////            }
////
////        }
//
//        }
//    }
//}
