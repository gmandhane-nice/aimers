package com.nice.util;

import com.nice.pojos.Voter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BulkDataGenerator {

  void generateBulkData()
      throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
    List<Voter> vl = new ArrayList<>();
    for(int i=0;i<100;i++) {
      Voter v = new Voter();
      v.setVoterId(UUID.randomUUID().toString());
      v.setCandidateName("name" +i);
      v.setConstituencyName("constituency" +i);
//      if (i<100000){
//        v.setConstituencyName("constituency" + i);
//      } else if(i>100000 && i<=200000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>300000 && i<=400000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>400000 && i<=500000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>500000 && i<=600000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>600000 && i<=700000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>700000 && i<=800000) {
//        v.setConstituencyName("constituency" + i);
//      } else if(i>800000 && i<=900000) {
//        v.setConstituencyName("constituency" + i);
//      } else {
//        v.setConstituencyName("constituency other");
//      }
      v.setPollingStation(UUID.randomUUID().toString());
      vl.add(v);
    }

    FileWriter writer = new
        FileWriter("c:\\Gourav\\bulkdata.csv");

    ColumnPositionMappingStrategy mappingStrategy=
        new ColumnPositionMappingStrategy();

    mappingStrategy.setType(Voter.class);

    String[] columns = new String[]
        { "voterId", "constituencyName", "pollingStation", "candidateName" };
    mappingStrategy.setColumnMapping(columns);

    // Creating StatefulBeanToCsv object
    StatefulBeanToCsvBuilder<Voter> builder=
        new StatefulBeanToCsvBuilder(writer);
    StatefulBeanToCsv beanWriter =
        builder.withMappingStrategy(mappingStrategy).build();

    // Write list to StatefulBeanToCsv object
    beanWriter.write(vl);

    // closing the writer object
    writer.close();

  }

  public static void main(String[] args)
      throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
    new BulkDataGenerator().generateBulkData();
  }

}
