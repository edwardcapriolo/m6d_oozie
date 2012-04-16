package com.m6d.oozie;

import com.jointhegrid.hive_test.ServiceHive;
import java.util.List;
import java.util.Properties;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.service.HiveServerException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

public class HiveServiceBAction {
  public static final String HIVE_SERVICE_HOST="hiveoozie.service.host";
  public static final String HIVE_SERVICE_PORT="hiveoozie.service.port";

  public void HiveServiceBAction(){

  }

  public void runAction(String [] args) throws TTransportException,
          HiveServerException, TException{
    Properties props = System.getProperties();
    ServiceHive service = new ServiceHive(props.getProperty(HIVE_SERVICE_HOST)
            ,Integer.parseInt(HIVE_SERVICE_PORT));
    for (String arg : args){
      service.client.execute(arg);
      List<String> results = service.client.fetchAll();
      for (String result : results){
        System.out.println(result);
      }
    }
    service.transport.close();
  }

  public static void main (String [] args) throws
          MetaException, TTransportException, HiveServerException, TException {
    HiveServiceBAction bact = new HiveServiceBAction();
    bact.runAction(args);
  }
}
