#!/usr/bin/python3

import shlex
import subprocess
import os
import sys


def sendIcmpEcho(target, out_xml):
    out_xml = os.path.join(out_xml,'icmp_echo_host_discovery.xml')
    nmap_cmd = f"C:\\Program Files (x86)\\Nmap\\nmap.exe {target} -n -sn -PE -v"
    sub_args = shlex.split(nmap_cmd)
    subprocess.Popen(sub_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()


def sendIcmpNetmask(target, out_xml):
    out_xml = os.path.join(out_xml,'icmp_netmask_host_discovery.xml')
    nmap_cmd = f"C:\\Program Files (x86)\\Nmap\\nmap.exe {target} -n -sn -PM -vv -oX {out_xml}"
    sub_args = shlex.split(nmap_cmd)
    subprocess.Popen(sub_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()


def sendIcmpTimestamp(target, out_xml):
    out_xml = os.path.join(out_xml,'icmp_timestamp_host_discovery.xml')
    nmap_cmd = f"C:\\Program Files (x86)\\Nmap\\nmap.exe {target} -n -sn -PP -vv -oX {out_xml}"
    sub_args = shlex.split(nmap_cmd)
    subprocess.Popen(sub_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()


def sendTcpSyn(target, out_xml):
    out_xml = os.path.join(out_xml,'tcp_syn_host_discovery.xml')
    nmap_cmd = f"C:\\Program Files (x86)\\Nmap\\nmap.exe {target} -PS21,22,23,25,80,113,443 -PA80,113,443 -n -sn -T4 -vv -oX {out_xml}"
    sub_args = shlex.split(nmap_cmd)
    subprocess.Popen(sub_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()




def main():
    target = '127.0.0.1'

    sendIcmpEcho(target, os.getcwd())
    # sendIcmpNetmask(target, os.getcwd())
    # sendIcmpTimestamp(target, os.getcwd())
    # sendTcpSyn(target, os.getcwd())

if __name__ == '__main__':
    main()