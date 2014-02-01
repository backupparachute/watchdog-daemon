# Ubuntu Install #

## Tomcat ##

- Security manager problems requires editing of `/etc/init.d/tomcat5.5` and change the `TOMCAT5_SECURITY=yes` to `no`

## MYSQL ##

- edit `/etc/mysql/my.conf` and add `lower_case_table_names=1`