#! /bin/bash
echo "$1" | sudo -S service vsftpd stop
echo "$1" | sudo -S rm /etc/vsftpd.conf
echo "$1" | sudo -S mv ./vsftpd.conf /etc
echo "$1" | sudo -S chown root /etc/vsftpd.conf
echo "$1" | sudo -S chgrp root /etc/vsftpd.conf
echo "$1" | sudo -S service xinetd restart
echo "$1" | sudo -S service vsftpd start
