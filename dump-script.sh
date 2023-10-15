#!/bin/bash


MYSQL_HOST="mysql"
MYSQL_USER="root"
MYSQL_PASSWORD="1234"
MYSQL_DATABASE="java" 

mysqldump -h $MYSQL_HOST -u $MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE --routines -r /dump.sql