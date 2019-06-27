#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

import tornado.web
import py_eureka_client.eureka_client as eureka_client
from tornado.options import define, options

define("port", default=8505, help="run on the given port", type=int)
app_name = 'python'
your_rest_server_port = 8505


class IndexHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('[GET] python tornado...')

def eurekaclient():

    tornado.options.parse_command_line()
    #注册eureka服务
    eureka_client.init(eureka_server="http://127.0.0.1:8502/eureka/",
                                       app_name=app_name,
                                       instance_port=your_rest_server_port,
                                       # 调用其他服务时的高可用策略，可选，默认为随机
                                       ha_strategy=eureka_client.HA_STRATEGY_RANDOM,
                       data_center_name='Netflix'
                       )
    app = tornado.web.Application(handlers=[(r"/", IndexHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
    print("eureka exec")

if __name__ == "__main__":
    eurekaclient()