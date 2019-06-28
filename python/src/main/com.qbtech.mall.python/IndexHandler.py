#!/usr/bin/env python3
# -*- coding: UTF-8 -*-
import string

import tornado.web
import py_eureka_client.eureka_client as eureka_client
from tornado.options import define, options

#当前服务监听的端口
define("port", default=8505, help="run on the given port", type=int)
#当前在eureka中注册的名称
define("appName", default='python', help="app name in eureka", type=string)


class IndexHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('[GET] python server...')

def eurekaclient():

    tornado.options.parse_command_line()
    #注册eureka服务
    eureka_client.init(eureka_server="http://127.0.0.1:8502/eureka/",
                                       app_name=options.appName,
                                       instance_port=options.port,
                                       # 调用其他服务时的高可用策略，可选，默认为随机
                                       ha_strategy=eureka_client.HA_STRATEGY_RANDOM,
                       data_center_name='MyOwn'
                       )
    app = tornado.web.Application(handlers=[(r"/", IndexHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
    print("eureka exec")

if __name__ == "__main__":
    eurekaclient()