# Group hosts by CPU number and sort by their memory size in descending order
select cpu_number,id as host_id,total_mem from host_info group by cpu_number,id order by total_mem desc; 

# Average used memory in percentage over 5 mins interval for each host.
select distinct h.host_id, h1.hostname,date_trunc('hour' ,h.timestamp)+extract(minute from h.timestamp):: int/5 * interval '5 min' as timestamp,
avg((h1.total_mem-h.memory_free/1000)*100/h1.total_mem) from host_info h1 inner join host_usage h on h.host_id=h1.id
group by h.timestamp,h.host_id,h1.hostname order by h.host_id,h1.hostname;


# Server failure detection in 5 min timespan
select host_id, timestamp,count(host_id) as count_data from(select id as host_id, date_trunc('hour', h."timestamp") + date_part('minute', h."timestamp"):: int / 5 * interval '5 min' as timestamp
 from host_info i JOIN host_usage h on i.id = h.host_id ) as inner_query group by host_id, timestamp having count(host_id) < 3 order by timestamp asc;





