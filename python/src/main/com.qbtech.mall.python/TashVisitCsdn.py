#!/usr/bin/env python3
# -*- coding: UTF-8 -*-
from random import choice
from urllib import request
import ssl
import time
import datetime
from mail.ZMaill import mail


#  nohup python3 -u TashVisitCsdn.py test > nohup 2>&1  & 注意-u是直接输出到文件不经过缓冲
## timeDistance = sys.argv[0] str

arrayUrls = [
             "https://blog.csdn.net/zhujohnle/article/details/95394140",#并发编程回顾
             "https://blog.csdn.net/zhujohnle/article/details/95639096",#【Android】版本更替
             "https://blog.csdn.net/zhujohnle/article/details/96483445",#markdown文件导出pdf带目录
             "https://blog.csdn.net/zhujohnle/article/details/96467891",#android通知准时送达
             "https://blog.csdn.net/zhujohnle/article/details/96435447",#go基础语言
             "https://blog.csdn.net/zhujohnle/article/details/91878414",#集成七牛云
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
             "https://blog.csdn.net/zhujohnle/article/details/94361525",#python3 安装
             "https://blog.csdn.net/zhujohnle/article/details/95639096", #android 版本整理
             "https://blog.csdn.net/zhujohnle/article/details/93464994", #https 部署
             "https://blog.csdn.net/zhujohnle/article/details/93199400" #google 工具
             ]


arraysAgent = [
    "User-Agent:Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)", #ie
    "User-Agent:Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",#safari 5.1 – Windows
    "User-Agent:Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",#safari 5.1 – Windows
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",#Firefox 4.0.1 – MAC
     "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",#360浏览器
     # iphone
    "User-Agent:Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
    "User-Agent:Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5"
]

if __name__ == "__main__":
    context = ssl._create_unverified_context()
    while True:
        for key in arrayUrls:
            req = request.Request(key)
            agent = choice(arraysAgent);
            req.add_header('User-Agent', agent)
            try:
                response = request.urlopen(req,context = ssl._create_unverified_context())
                print("url:"+key)
                print("agent:"+agent)
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
                time.sleep(150)
            elif hourInt>=8 and hourInt<=20:
                #三分钟 一篇刷20次
                time.sleep(65)
            else:
                #10分钟
                time.sleep(120)

