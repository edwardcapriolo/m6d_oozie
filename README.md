Oozie "Plan B" Actions
=============

Oozie has specific actions for streaming and other such tasks, but at
the time adding your own actions involved patching and building your own oozie. The oozie "Plan B"/Bootleg actions are not actually actions. They are classes designed to be used with the JavaMain action.

com.m6d.oozie.HiveServiceBAction
-----

The build in oozie action for hive is layered ontop of the hive CLI. This can 
be problematic since the CLI was not designed to be used programatically. Most
of these problems are addressed by the hive thrift service. The HiveServiceBAction allows oozie to submit jobs to hive thrift service. It is used like this:

    <action name="create-node">
        <java>
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <configuration>
                <property>
                    <name>mapred.job.queue.name</name>
                    <value>${queueName}</value>
                </property>
            </configuration>
            <main-class>com.m6d.oozie.HiveServiceBAction</main-class>
            <arg>rhiveservice.hadoop.pvt</arg>
            <arg>10000</arg>
            <arg>CREATE TABLE IF NOT EXISTS zz_zz_abc (a int, b int)</arg>
        </java>
        <ok to="query_node"/>
        <error to="fail"/>
    </action>

The action accepts three or more arguments. The first argument is the host, the second is the port, and all remaining arguments are treated as individual commands sent to hive thirft service for execution.
