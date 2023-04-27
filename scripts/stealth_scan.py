#!/usr/bin/python3
import sys
import os
import subprocess


ip = sys.argv[1]

try:
    # os.system(f"cmd /k nmap {ip} -sS")
    subprocess.run(f'nmap {ip} -sS', shell=True)
except:
    print('Could not execute script, check nmap env path (scripts/stealth_scan.py)')