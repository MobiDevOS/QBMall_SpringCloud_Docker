from mail.ZMaill import mail

if __name__ == '__main__':

    ret = mail(content="test",subject="csdn脚本异常")
    if ret:
        print("邮件发送成功")
    else:
        print("邮件发送失败")