#!/usr/bin/env python3
# -*- coding: UTF-8 -*-


import asyncio
import tornado.web
from wasp_eureka import EurekaClient
from tornado.options import define, options

define("port", default=7171, help="run on the given port", type=int)

class IndexHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('[GET] python tornado...')

app_name = 'linjk-python-eureka-client'

ip = '192.168.1.109'
my_eureka_url = 'http://192.168.1.109:8761'

loop = asyncio.get_event_loop()    # 创建事件循环

eureka = EurekaClient(app_name=app_name, port=options.port, ip_addr=ip,
                      hostname="localhost", eureka_url=my_eureka_url, loop=loop)

async def main():
    result = await eureka.register()
    print("[Register Rureka] result: %s" % result)

    tornado.options.parse_command_line()
    app = tornado.web.Application(
        handlers=[(r'/', IndexHandler)],
        debug=True
    )
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

    while True:
        await asyncio.sleep(60)
        await eureka.renew()

if __name__ == "__main__":
    loop.run_until_complete(main())