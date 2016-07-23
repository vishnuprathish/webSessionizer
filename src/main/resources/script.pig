data = load '$out/part-r-00000' using PigStorage(',') as (userIpDouble:chararray, sessioncount:int, avgsessiontime:chararray, longestsessiontime:long, sessions:chararray);
tuples = FOREACH data GENERATE STRSPLIT(userIpDouble, ' ').$0 as userIp, longestsessiontime;
grp_data = ORDER tuples BY longestsessiontime DESC;
top50 = LIMIT grp_data 50;
dump top50;