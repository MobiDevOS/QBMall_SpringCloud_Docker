#!/usr/bin/env python3
# -*- coding:utf-8 -*-

import tsocket, time, _thread

def socket_port(ip, port):
    """
    输入IP和端口号，扫描判断端口是否占用
    """
    try:
        if port >=65535:
            print ('端口扫描结束')
        s =tsocket.socket(tsocket.AF_INET, tsocket.SOCK_STREAM)
        result=s.connect_ex((ip, port))
        if result==0:
            lock.acquire()
            print (ip,':',port,'端口已占用')
            lock.release()
    except:
        print ('端口扫描异常')

def ip_scan(ip):
    """
    输入IP，扫描IP的0-65534端口情况
    """
    try:
        print ('开始扫描 %s' % ip)
        start_time=time.time()
        for i in range(0,65534):
            _thread.start_new_thread(socket_port,(ip, int(i)))
        print ('扫描端口完成，总共用时：%.2f' %(time.time()-start_time))
    #       raw_input("Press Enter to Exit")
    except:
        print ('扫描ip出错')

if __name__=='__main__':
    url = 8081
    lock=_thread.allocate_lock()
    ip_scan(url)