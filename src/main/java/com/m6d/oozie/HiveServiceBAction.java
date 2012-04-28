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
    ServiceHive service = new ServiceHive(args[0]
            ,Integer.parseInt(args[1])
    );
    for (int i=2;i<args.length;i++){
      String arg = args[i];
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
