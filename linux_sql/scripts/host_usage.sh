#!/bin/bash

#Variables for CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#validate number of arguments
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  echo "Usage: ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi


memory_free= $(vmstat -t | sed -n 3p | awk '{print $4}'|xargs)
cpu_idle=$(vmstat -t | awk '{print $15}'| tail -1 | xargs)
cpu_kernel=$(vmstat -t | sed -n 3p | awk '{print $14}'|xargs)
disk_io=$(vmstat -d | sed -n 3p | awk '{print $10}'|xargs)
disk_available=$(df -BM | sed -n 2p | awk '{print $4}' | grep -o -E '[0-9]+'|xrgs)
timestamp= $(date -u '+%Y-%m-%d %H:%M:%S')

export PGPASSWORD="$psql_password"


stmt="INSERT INTO PUBLIC.host_usage (timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available)
      VALUES ('$timestamp',(SELECT id FROM host_info WHERE hostname='$hostname'),$memory_free,$cpu_idle,$cpu_kernel,$disk_io,$disk_available);"


psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$stmt"

exit $?
