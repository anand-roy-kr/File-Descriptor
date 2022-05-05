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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.System.out;

public class LoadAsJavaObjectList {

    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();

        int ct = 0;
        Connection c;
        Statement stmt;
        Collection<Consumer> fds = new ArrayList<Consumer>();

        //reading nielsen consumer yaml file
        try (InputStream in = LoadAsJavaObjectList.class.getResourceAsStream("/nielsen.yml")) {
            Consumers Consumers = yaml.loadAs(in, Consumers.class);
            for (Consumer Consumer : Consumers.getConsumers()) {
                fds.add(Consumer);
                //System.out.println(Consumer);
            }
            //collection of file descriptor objects
            System.out.println(fds);
            //number of attributes for a specific consumer
            ct = fds.size();
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
//            String line1[] = {metadata.getColumnName(1), metadata.getColumnName(2),metadata.getColumnName(3)};

            //getting header details from consumer objects
            String line2[] = {Consumers.getConsumers().get(0).getName(), Consumers.getConsumers().get(1).getName(), Consumers.getConsumers().get(2).getName()};

            CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\ttc6493\\Downloads\\FileDescriptor_05052022\\Demo\\src\\main\\output_files\\output_nielsen.csv"));
            //writer.writeNext(line2);
            String data[] = new String[3];

            //populating files
            FileWriter myWriter;
            myWriter = new FileWriter("C:\\Users\\ttc6493\\Downloads\\FileDescriptor_05052022\\Demo\\src\\main\\output_files\\output_nielsen.txt");

            ResultSet rowcount = stmt.executeQuery("select count(*) from nielsen_processed_data");
            rowcount.next();
            int rowct = rowcount.getInt(1);
            LocalDate date = LocalDate.now();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            System.out.println("Current Date: "+formattedDate);

            myWriter.write(rowct+"| "+formattedDate+"|");
            myWriter.write("\n");
            while(rs.next()) {
                for (Consumer fd : fds) {
                    switch (fd.getOrder()) {
                        case 1:
                            myWriter.write(rs.getString(line2[0]));
                            myWriter.write("|  ");
                            data[0] = rs.getString(line2[0]);
                            break;

                        case 2:
                            myWriter.write(rs.getString(line2[1]));
                            myWriter.write("|  ");
                            data[1] = rs.getString(line2[1]);
                            break;

                        case 3:
                            myWriter.write(rs.getString(line2[2]));
                            myWriter.write("|  ");
                            data[2] = rs.getString(line2[2]);
                            break;
                    }
                    }
                    myWriter.write("\n");
                    writer.writeNext(data);
            }
                myWriter.close();
                writer.flush();
                System.out.println("Pipe delimited Txt file and csv file created.");





        }catch(Exception e){
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            }



        }
    }
}



