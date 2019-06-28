#!/usr/bin/env python
# -*- coding: UTF-8 -*-

from urllib import request
import ssl
import time

#  nohup python3 -u TashVisitCsdn.py test > nohup 2>&1 注意-u是直接输出到文件不经过缓冲
## timeDistance = sys.argv[0] str

arrayUrls = {"https://blog.csdn.net/zhujohnle/article/details/91878414",#集成七牛云
             "https://blog.csdn.net/zhujohnle/article/details/90229702",#jvm
             "https://blog.csdn.net/zhujohnle/article/details/89883218",#git
             "https://blog.csdn.net/zhujohnle/article/details/89675877",#goweb 编程
             "https://blog.csdn.net/zhujohnle/article/details/87865726",
             "https://blog.csdn.net/zhujohnle/article/details/93871591", #搭建私服
             "https://blog.csdn.net/zhujohnle/article/details/85101058", #ios pod
              "https://blog.csdn.net/zhujohnle/article/details/84786200"# android implements
             }

if __name__ == "__main__":
    context = ssl._create_unverified_context()
    while True:
        for key in arrayUrls:
            req = request.Request(key);
            req.add_header('User-Agent', 'Mozilla/6.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/8.0 Mobile/10A5376e Safari/8536.25')
            response = request.urlopen(req,context = ssl._create_unverified_context())
            print(response)
            time.sleep(60)
