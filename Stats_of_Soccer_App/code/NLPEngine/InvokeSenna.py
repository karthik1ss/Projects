__author__ = 'Karthik'


import callSenna
import re
import os
import globals


global match_id

# initialization of global variables

def init():
    global match_id
    match_id = globals.match_id

outfiles = []


# Execute senna with this function by passing input files and output files are created MatchRepo/<Match_id>/Sennaoutput

def invoke_senna():

    create_directory()

    with open('../../code/common/infilesList.txt','r') as f:
        files = [word.strip() for word in f]


    # calling senna 90 times

    for item in files:
        print "Processing Senna Output for file",item
        inputfile = open(item)
        rep = item.replace('Sennainput','Sennaoutput')
        op = rep.replace('.txt','_result.txt')
        outfiles.append(op)
        outputfile = open(op, 'w')
        callSenna.senna(inputfile,outputfile)
        inputfile.close()
        outputfile.close()

    store_senna_files()


def create_directory():
    global match_id

    dir = '../../code/MatchRepo/Match_{0}'.format(match_id)

    if not os.path.exists(dir):
        print "Creating directory"
        os.makedirs(dir)


    directory = dir + "/Sennaoutput"
    if not os.path.exists(directory):
        print "Creating directory"
        os.makedirs(directory)


# store all minute split files of senna processed output in another file

def store_senna_files():

    sennaFile = open('../../code/common/outfilesList.txt','w')
    for s in outfiles:
        sennaFile.write(s+"\n")
    sennaFile.close()