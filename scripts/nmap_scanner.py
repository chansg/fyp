#!/usr/bin/python3

import socket
import nmap
import sys

scanner = nmap.PortScanner()

print("Nmap Automation Tool")

ip_addr = socket.gethostbyname(sys.argv[1])
lower_port = sys.argv[2]
upper_port = sys.argv[3]

scanner.scan(ip_addr, lower_port+"-"+upper_port)

# todo human readability and service versions
for host in scanner.all_hosts():
    print('Host : %s (%s)' % (host, scanner[host].hostname()))
    print('State : %s' % scanner[host].state())
    for proto in scanner[host].all_protocols():
        print('----------')
        print('Protocol : %s' % proto)

        lport = scanner[host][proto].keys()
        # lport.sort()
        sorted(lport)
        for port in lport:
            print ('port : %s\tstate : %s' % (port, scanner[host][proto][port]['state']))
