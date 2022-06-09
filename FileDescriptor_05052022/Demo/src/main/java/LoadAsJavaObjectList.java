import com.opencsv.CSVWriter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.System.out;
//test comment
public class LoadAsJavaObjectList {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();

        int ct = 0;
        Connection c;
        Statement stmt;
        ArrayList<Consumer> alc = new ArrayList<>();
        //reading nielsen consumer yaml file
        try (InputStream in = LoadAsJavaObjectList.class.getResourceAsStream("/nielsen.yml")) {
            Consumers Consumers = yaml.loadAs(in, Consumers.class);
            for (Consumer Consumer : Consumers.getConsumers()) {
                alc.add(Consumer);
            }
        //array list of file descriptor objects
        Collections.sort(alc, Comparator.comparing(Consumer::getOrder));
        System.out.println(alc);
        //number of attributes for a specific consumer
        ct = alc.size();
        System.out.println("Number of columns as mentioned in yml file = "+ct);

        //connecting to dummy db (local postgres)
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demo","postgres","Ajroy222");
            System.out.println("Connection successful");
            stmt = c.createStatement();

            //nielsen_processed_data is the dummy data source in postgres local db
            ResultSet rs = stmt.executeQuery("select * from nielsen_processed_data");
            ResultSetMetaData metadata = rs.getMetaData();
            System.out.println("Number of columns: " + metadata.getColumnCount());
            String line1[] = {metadata.getColumnName(1), metadata.getColumnName(2),metadata.getColumnName(3)};
            System.out.println(line1);

            //getting header details from consumer objects
            String line2[] = {Consumers.getConsumers().get(0).getName(), Consumers.getConsumers().get(1).getName(), Consumers.getConsumers().get(2).getName()};

            //generating files
            //todo - have a dynamic file storing mechanism
            FileWriter myWriter;
            myWriter = new FileWriter("C:\\Users\\ttc6493\\Downloads\\FileDescriptor_05052022\\Demo\\src\\main\\output_files\\output_nielsen.txt");

            ResultSet rowcount = stmt.executeQuery("select count(*) from nielsen_processed_data");
            rowcount.next();
            int rowct = rowcount.getInt(1);
            LocalDate date = LocalDate.now();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            System.out.println("Current Date: "+formattedDate);

            myWriter.write(rowct+"|"+formattedDate+"|");
            myWriter.write("\n");
            while(rs.next()) {
                for (int i=0; i<ct; i++){
                    myWriter.write(rs.getString(alc.get(i).getName()));
                    myWriter.write("|");
                }
                    myWriter.write("\n");
            }
                myWriter.close();
                System.out.println("Pipe delimited Txt file created.");
        }catch(Exception e){
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            }
        }
    }
}