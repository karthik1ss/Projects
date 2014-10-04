'''
Created on Apr 9, 2014

@author: Harish Babu Arunachalam, Karthik
'''
import os,platform
import subprocess


#commandline method to invoke senna through commandline
# def commandLineCall():
#     print "output"
#     #proc = subprocess.Popen("cmd.exe",stdin=subprocess.PIPE)
#     cmd = ['cmd','/k' ,'C:/senna-v3.0/senna <input.txt> output.txt']
#     #cmd = ['cmd','/k' ,'echo 123']
#     #proc = subprocess.Popen(cmd,stdin=subprocess.PIPE,stdout=subprocess.PIPE)
#     #proc = subprocess.Popen(cmd,stdin=subprocess.PIPE,stdout=subprocess.PIPE)
#     #proc.wait()
#     os.system("start /WAIT cmd /k C:/senna-v3.0/senna <input.txt> output.txt")


# for OSX

#os.chdir("/Users/Karthik/Desktop/Computer Science/Natural Language Processing/Project/Bitbucket/libraries/senna")

# function to invoke senna giving input file split commentary file for each minute and output is senna processed output
# Senna's output is filtered to recognize NER mentioning option -ner while executing senna

def senna(inputfile, outputfile):

    #code added by Harish for windows
    if(platform.system() == 'Windows'):
        cmd = ['senna', '-ner']
    else:
        cmd = ['./senna', '-ner']
        
    os.chdir("../../libraries/senna")
    p = subprocess.Popen(cmd, stdin=inputfile, stdout=outputfile)
    p.wait()
    outputfile.flush()
    print 'process completed'
