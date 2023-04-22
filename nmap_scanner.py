#!/usr/bin/python3

import socket
import nmap
import sys

scanner = nmap.PortScanner()

print("Nmap Automation Tool")

ip_addr = socket.gethostbyname(sys.argv[1])
lower_port = sys.argv[2]
upper_port = sys.argv[3]

print(scanner.scan(ip_addr, lower_port+"-"+upper_port))

# todo human readability and service versions
# for host in nmap.all_hosts():
#     print('Host : %s (%s)' % (host, nmap[host].hostname()))
#     print('State : %s' % nmap[host].state())
#     for proto in nmap[host].all_protocols():
#         print('----------')
#         print('Protocol : %s' % proto)
#
#         lport = nmap[host][proto].keys()
#         lport.sort()
#         for port in lport:
#             print ('port : %s\tstate : %s' % (port, nmap[host][proto][port]['state']))
