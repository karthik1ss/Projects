__author__ = 'Karthik'


#import callSenna


with open('../../code/common/output.txt', 'w') as f:
    for line in reversed(open("../../code/common/commentary.txt").readlines()):
        #print line.rstrip()
        f.write(line)


