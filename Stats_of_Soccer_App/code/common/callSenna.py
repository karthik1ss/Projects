'''
Created on Apr 9, 2014

@author: Harish Babu Arunachalam
'''
import os
import subprocess


#commandline method to invoke senna through commandline
def commandLineCall():
    print "output"
    inputFile = open("input.txt")
    #proc = subprocess.Popen("cmd.exe",stdin=subprocess.PIPE)
    cmd = ['cmd','/k' ,'senna -path c:\\senna-v3.0\\']
    #cmd = ['cmd','/k' ,'echo 123']
    #proc = subprocess.Popen(cmd,stdin=subprocess.PIPE,stdout=subprocess.PIPE)
    proc = subprocess.Popen(cmd,stdin=inputFile,stdout="output.txt")
    #proc.wait()
    #os.system("start /WAIT cmd /c senna -path C:\\senna-v3.0\\ < C:\\senna-v3.0\\input.txt > output.txt")
    #os.system("start /WAIT cmd /k dir")
