/*
 * Copyright [2011] [Edward Capriolo]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 * 
 */
package com.m6d.oozie;
import java.io.*;
import java.util.Properties;
/*
 *  Use this to capture output of any process 
 *     <java>
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <configuration>
                <property>
                    <name>mapred.job.queue.name</name>
                    <value>${queueName}</value>
                </property>
            </configuration>
            <main-class>test.RunShellProp</main-class>
            <arg>/bin/echo</arg>
            <arg>x=5</arg>
            <capture-output />
        </java>

 */
public class RunShellProps {

	private static final String OOZIE_ACTION_OUTPUT_PROPERTIES = "oozie.action.output.properties";

	public static void main(String args[]) throws Exception {
		String oozieProp = System.getProperty(OOZIE_ACTION_OUTPUT_PROPERTIES);
		if (oozieProp != null) {
			File propFile = new File(oozieProp);
			Properties props = new Properties();

			String line;
			Process p = Runtime.getRuntime().exec(args);
			BufferedReader input = new BufferedReader(new InputStreamReader(p
					.getInputStream()));

			while ((line = input.readLine()) != null) {
				String[] parts = line.split("=");
				if (parts.length != 2) {
					throw new RuntimeException("line must have name=value was "
							+ line);
				}
				props.setProperty(parts[0], parts[1]);
			}
			input.close();
			OutputStream os = new FileOutputStream(propFile);
			props.store(os, "");
			os.close();

			BufferedReader error = new BufferedReader(new InputStreamReader(p
					.getErrorStream()));
			String errorLine;
			while ((errorLine = error.readLine()) != null) {
				System.err.println(errorLine);
			}
			error.close();
			p.waitFor();
			int ret = p.exitValue();
			if (ret != 0) {
				throw new RuntimeException("process returned " + ret);
			}
		} else {
			throw new RuntimeException(OOZIE_ACTION_OUTPUT_PROPERTIES
					+ " System property not defined");
		}
	}

}
