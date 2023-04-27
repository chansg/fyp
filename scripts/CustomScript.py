#!/usr/bin/python3
import sys
import os
import subprocess


try:
    # os.system(f"cmd /k nmap {ip} -sS")
    subprocess.run(f'ipconfig', shell=True)
except:
    print('Could not execute script, check python env path (scripts/customScript.py)')