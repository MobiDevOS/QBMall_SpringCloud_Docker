from email.mime.text import MIMEText
import smtplib
from email.utils import formataddr



def sendMail(*,content):
    msg = MIMEText(content, 'plain', 'utf-8')
    from_addr = '545948101@qq.com'
    to_addr = '545948101@qq.com'
    smtp_server = 'smtp.qq.com'


    # 初始化smtp对象，传入服务器地址与端口号
    server = smtplib.SMTP(smtp_server, 465)
    # 设置调试模式可以让我们看到发送邮件过程中的信息
    server.set_debuglevel(1)
    # 登陆MUA，使用账户与授权码登陆
    server.login(from_addr, 'iutnbsqbskaibbhd')
    msg['From'] = from_addr
    msg['To'] = to_addr
    msg['Subject'] = '阿里云脚本警报'
    server.sendmail(from_addr, to_addr, msg.as_string())

def mail(*,content,subject=""):
    my_sender='545948101@qq.com'    # 发件人邮箱账号
    my_pass = 'iutnbsqbskaibbhd'              # 发件人邮箱密码
    my_user='545948101@qq.com'      # 收件人邮箱账号，我这边发送给自己

    ret=True
    try:
        msg=MIMEText(content,'plain','utf-8')
        msg['From']=formataddr(["aliyun-csdn",my_sender])  # 括号里的对应发件人邮箱昵称、发件人邮箱账号
        msg['To']=formataddr(["zhujohnle",my_user])              # 括号里的对应收件人邮箱昵称、收件人邮箱账号
        msg['Subject'] = subject                # 邮件的主题，也可以说是标题

        server=smtplib.SMTP_SSL("smtp.qq.com", 465)  # 发件人邮箱中的SMTP服务器，端口是25
        server.login(my_sender, my_pass)  # 括号中对应的是发件人邮箱账号、邮箱密码
        server.sendmail(my_sender,[my_user,],msg.as_string())  # 括号中对应的是发件人邮箱账号、收件人邮箱账号、发送邮件
        server.quit()  # 关闭连接
    except Exception:  # 如果 try 中的语句没有执行，则会执行下面的 ret=False
        ret = False
    return ret




if __name__ == '__main__':
    ret = mail(content="test",subject="csdn脚本异常")
    if ret:
        print("邮件发送成功")
    else:
        print("邮件发送失败")