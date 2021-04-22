#Variables for CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#validate number of arguments
if [ "$#" -ne 5 ]; then
  echo "5 parameters not passed"
  echo "Usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

# save hostname to a variable
hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`
meminfo_out=$(cat /proc/meminfo)
#note: `xargs` is a trick to remove leading and trailing white spaces
cpu_number=$(echo "$lscpu_out"|egrep "^CPU\(s\):"|awk '{print $2}'|xargs)

#hardware
hostname=$hostname
cpu_number=$cpu_number
cpu_architecture=$(echo "$lscpu_out"|egrep "^Architecture:"|awk '{print $2}'|xargs)
cpu_model=$(echo "$lscpu_out"|egrep "^Model name:"|awk '{$1=$2=""; print $0}'|xargs)
#cpu_model=$(echo "$lscpu_out"|egrep "^Model [a-z]+:"|cut -d ":" -f 2 )|xargs)
cpu_mhz=$(echo "$lscpu_out"|egrep "^CPU [A-Z]+[a-z]"|awk '{print $3}'|xargs)
l2_cache=$(echo "$lscpu_out"|egrep "^L2 [a-z]+:"|awk '{print $3}'|xargs|grep -o '[0-9]\+')
total_mem=$(echo "$meminfo_out"|egrep "MemTotal:"|awk '{print $2}'|xargs)
#total_mem=$(echo `cat /proc/meminfo`|awk '{print $2}'|xargs)
timestamp=$(date "+%Y-%m-%d %H:%M:%S")

#insert statement
stmt="INSERT INTO host_info (hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,total_mem,timestamp)
  VALUES ('$hostname',$cpu_number,'$cpu_architecture','$cpu_model',$cpu_mhz,$l2_cache,$total_mem,'$timestamp');"

#Execution
export PGPASSWORD=$psql_password
#connect to instance
psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$stmt"
exit 0
