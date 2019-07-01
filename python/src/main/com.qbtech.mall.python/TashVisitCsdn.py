#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

from urllib import request
import ssl
import time
import datetime
from mail.ZMaill import mail


#  nohup python3 -u TashVisitCsdn.py test > nohup 2>&1  & 注意-u是直接输出到文件不经过缓冲
## timeDistance = sys.argv[0] str

arrayUrls = ["https://blog.csdn.net/zhujohnle/article/details/91878414",#集成七牛云
             "https://blog.csdn.net/zhujohnle/article/details/90229702",#jvm
             "https://blog.csdn.net/zhujohnle/article/details/89883218",#git
             "https://blog.csdn.net/zhujohnle/article/details/89675877",#goweb 编程
             "https://blog.csdn.net/zhujohnle/article/details/87865726",
             "https://blog.csdn.net/zhujohnle/article/details/93871591", #搭建私服
             "https://blog.csdn.net/zhujohnle/article/details/85101058", #ios pod
             "https://blog.csdn.net/zhujohnle/article/details/84786200",# android implements
             "https://blog.csdn.net/zhujohnle/article/details/90258028",
             "https://blog.csdn.net/zhujohnle/article/details/90519244",
             "https://blog.csdn.net/zhujohnle/article/details/93061825",# 部署springcloud
             "https://blog.csdn.net/zhujohnle/article/details/93161488", # java 后台学习
             "https://blog.csdn.net/zhujohnle/article/details/93616648", # python
             "https://blog.csdn.net/zhujohnle/article/details/94361525"#python3 安装
             ]

if __name__ == "__main__":
    context = ssl._create_unverified_context()
    while True:
        for key in arrayUrls:
            req = request.Request(key)
            req.add_header('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36')
            try:
                response = request.urlopen(req,context = ssl._create_unverified_context())
                print(key)
            except Exception as e:
                ip = ' 主机1 \n'
                exce = str(e)+"\n"
                content = key+ip+exce
                ret = mail(content = content,subject="csdn脚本异常")
                if ret:
                    print("邮件发送成功")
                else:
                    print("邮件发送失败")

                continue


            now = datetime.datetime.now()
            hour = now.strftime('%H')
            hourInt = int(hour)
            #10分钟
            if hourInt>=0 and hourInt<8:
                time.sleep(600)
            elif hourInt>=8 and hourInt<=20:
                #三分钟 一篇刷20次
                time.sleep(260)
            else:
                #10分钟
                time.sleep(480)

