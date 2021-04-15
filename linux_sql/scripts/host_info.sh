#save hostname to a variable
hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`
#note: `xargs` is a trick to remove leading and trailing white spaces
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

#hardware
hostname=$hostname
cpu_number=$cpu_number
cpu_architecture=$(echo "$lscpu_out" |egrep "^Architecture:"|awk '{print $2}' |xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model [a-z]+:" | cut -d ":" -f 2 ) | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU [A-Z]+[a-z]" | awk '{print $3}'| xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 [a-z]+:" | awk '{print $3}'| xargs)
total_mem=$ (echo `cat /proc/meminfo`| awk '{print $2}'| xargs)
timestamp= $(date -u '+%Y-%m-%d %H:%M:%S')

