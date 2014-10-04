__author__ = 'Karthik'

import re
import os
import globals


global match_id

# initialization of global variables

def init():

    global match_id
    match_id = globals.match_id


global directory


files = []

# function to split the commentary lines based on minutes of the match and create each minute file containing commentary
# data of that particular minute in MatchRepo/<Match_id>/Sennainput

def split_file():

    global directory
    print "Pre-checking if files already exist in directory"

    precheck_fileexists()

    file = open('../../code/MatchRepo/commentary.txt', 'r')

    # split commentary to 90 text files

    for line in file.readlines():
        m = re.match('(^\d+)',line)
        if m:
            t = m.group()
            exp = directory + '/minute_{0}.txt'.format(t)
            print t

            if exp not in files:
                files.append(exp)

            for s in files:

                if re.search(r'.*minute_{0}.*'.format(t),s):
                    open(s,'a').write(line)

    print files
    file.close()
    store_input_files()


# store all minute split files in another file to give input to senna

def store_input_files():

    sennaFile = open('../../code/common/infilesList.txt','w')
    for s in files:
        sennaFile.write(s+"\n")
    sennaFile.close()


# Precheck for automatically creating folders and delete files if already exists

def precheck_fileexists():

    global directory
    global match_id

    dir = '../../code/MatchRepo/Match_{0}'.format(match_id)
    print match_id
    print dir

    if not os.path.exists(dir):
        print "Creating directory"
        os.makedirs(dir)


    directory = dir + "/Sennainput"
    if not os.path.exists(directory):
        print "Creating directory"
        os.makedirs(directory)

    if os.path.isfile('../../code/common/infilesList.txt'):
        with open('../../code/common/infilesList.txt','r') as f:
            filer = [word.strip() for word in f]

        for it in filer:

            if os.path.exists(it):
                if os.path.isfile(it):
                    print "Exists: Deleting files"
                    os.remove(it)