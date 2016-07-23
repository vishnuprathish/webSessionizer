# webSessionizer
web sessionizer using hadoop

Usage:
//untar
tar -xvf 2015_07_22_mktplace_shop_web_log_sample.log.gz

chmod +x ./start.sh
./start.sh /2015_07_22_mktplace_shop_web_log_sample.log /outputpath

Sample output for the mapreduce job: (IP IP, average duration of sesssion, longest session in millis, sessions array)

 1.186.101.79	1.186.101.79,3,0 min 59 sec,146934,[0](Wed Jul 22 10:45:55 UTC 2015 Wed Jul 22 10:48:22 UTC 2015 9) [1](Wed Jul 22 11:00:44 UTC 2015 Wed Jul 22 11:01:15 UTC 2015 7) [2](Wed Jul 22 11:04:32 UTC 2015 Wed Jul 22 11:04:33 UTC 2015 3)
 1.186.103.240	1.186.103.240,1,0 min 11 sec,11611,[0](Wed Jul 22 17:41:09 UTC 2015 Wed Jul 22 17:41:20 UTC 2015 4)
 1.186.103.78	1.186.103.78,1,0 min 9 sec,9001,[0](Wed Jul 22 10:47:42 UTC 2015 Wed Jul 22 10:47:51 UTC 2015 3)
 1.186.108.213	1.186.108.213,2,0 min 0 sec,0,[0](Wed Jul 22 16:21:11 UTC 2015 Wed Jul 22 16:21:11 UTC 2015 1) [1](Wed Jul 22 16:23:04 UTC 2015 Wed Jul 22 16:23:04 UTC 2015 0)
 1.186.108.230	1.186.108.230,1,0 min 18 sec,18815,[0](Wed Jul 22 16:11:27 UTC 2015 Wed Jul 22 16:11:46 UTC 2015 4)

 Sample output for the Pig script: (Top 50 userIps sorted by longest session(most enganged users))
  (52.74.219.71,559207)
  (106.186.23.95,559119)
  (119.81.61.166,558236)
  (125.19.44.66,557810)
  (54.251.151.39,557727)
  (103.29.159.186,557716)
  (188.40.135.194,556944)

