#!/usr/bin/python3
import sys
import os
import subprocess


ip = sys.argv[1]

try:
    # os.system(f"cmd /k nmap {ip} -sS")
    subprocess.run(f'nmap {ip} -T4 -A -p1-80', shell=True)
except:
    print('Could not execute script, check nmap env path (scripts/aggressive_scan.py)')