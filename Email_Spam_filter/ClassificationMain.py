__author__ = 'Karthik'


#######################################
# Main program to run Email Spam Filtering . Uses Naive Bayes and MCAP Logistic Regression learning algorithms
#######################################

from os import listdir
from os import path
import re
import NaiveBayes
import LogisticRegression
import sys

vocab = set()
hamset, spamset = [],[]
ham_dict, spam_dict = {}, {}
Nh, Ns, N = 0,0,0
ham_cond_prob, spam_cond_prob = {},{}
Nlist = []
accuracy = 0
count = 0

#######################################
##  function to load the dataset and extract doc file names into two lists - hamset , spamset ###
#######################################


def loadset(folder):

    hampattern = re.compile(r'.*.ham.txt')
    spampattern = re.compile(r'.*.spam.txt')

    for files in listdir(folder):
        file_path = path.join(folder,files)
        if path.isfile(file_path):
            if hampattern.search(file_path):
                hamset.append(file_path)

            if spampattern.search(file_path):
                spamset.append(file_path)


        else:
            loadset(file_path)


#######################################
# function to calculate total no of documents in each set ham(not spam) and spam
#######################################

def calculateTotal():
    global Nh, Ns,N

    Nh = len(hamset)
    Ns = len(spamset)
    N = Nh + Ns

    Nlist.append(Nh)
    Nlist.append(Ns)

    print "Ham Files", Nh
    print "Spam files", Ns
    print "Total files", N


#######################################
#  function to calculate vocabulary or corpus of the whole text
# #######################################

def loadVocabulary(map):

    for files in map:

        file =  open(files,'r')
        for line in file:

            words = line.strip().split(' ')

            for items in words:
                vocab.add(items)


#######################################
#  function to calculate count of each words in a class - ham/spam
#######################################

def loadMaps(map):
    dict = {}


    for files in map:

        file =  open(files,'r')
        for line in file:

            words = line.strip().split(' ')

            for items in words:
                if items in vocab:

                    if dict.get(items) == None:
                        dict[items] = 0
                    else:
                        dict[items] += 1

    return dict


#######################################
# funciton to calculate count of each word in document
#######################################

def loadLRTable(map):

    dict, indict = {},{}
    for files in map:
        file = open(files,'r')

        for line in file:
            words = line.strip().split(' ')

            for items in words:
                if items in vocab:

                    if indict.get(items) == None:
                        indict[items] = 0
                    else:
                        indict[items] += 1

        dict[files] = indict

    return dict


#######################################
#  removing stop words like to,or,and ... from corpus
#######################################

def filterwords():
    stop = []

    file = open('./stopwords.txt','r')
    for line in file:

        stop.append(line.strip())

    print stop

    for items in stop:
        if items in vocab:
            # print "removing", items
            vocab.remove(items)


#######################################
#  accuracy calculation for the Naive Bayes learning algorithm on test set
#######################################

def accuracyNB(map):
    global accuracy,count
    global Nh, Ns
    decision = 0


    for items in map:

        decision = NaiveBayes.applyNB(vocab, Nlist,items,ham_cond_prob,spam_cond_prob)
        pattern = re.compile(decision)

        if pattern.search(items):
            count += 1


#######################################
#  accuracy calculation for the Logisitic Regression algorithm on test set
#######################################

def accuracyLR(map,dict):

    global accuracy,count
    decision = 0
    error = 0

    for items in map:

        decision = LogisticRegression.applyLR(vocab,items,dict)
        pattern = re.compile(decision)

        if pattern.search(items):
            count += 1


#######################################
# # Main program
#######################################

if __name__ == "__main__":

    global Nh,Ns,N
    Y_ham = 0
    Y_spam = 1

    method = sys.argv[1]
    fltr = sys.argv[2]

    loadset("./train/")

    print hamset
    print spamset

    calculateTotal()
    loadVocabulary(hamset)
    loadVocabulary(spamset)

    if fltr == "yes":
        filterwords()

    if method == "nb":

        ham_dict = loadMaps(hamset)
        spam_dict = loadMaps(spamset)

        print "Vocabulary", vocab
        print "Ham Dict", ham_dict
        print "Spam Dict", spam_dict

        ham_cond_prob, spam_cond_prob = NaiveBayes.trainNB(vocab, ham_dict, spam_dict)
        print "Ham Cond Prob Dict", ham_cond_prob
        print "Spam Cond Prob Dict", spam_cond_prob

        hamset, spamset = [],[]

        loadset("./test/")

        calculateTotal()

        accuracyNB(hamset)
        accuracyNB(spamset)

        accuracy = format(count/float(N),'.5f')

        print "count", count

        print "Accuracy test set Naive Bayesian learning",accuracy


    elif method == 'lr':

        n = float(sys.argv[3])
        lamda = float(sys.argv[4])
        k = int(float(sys.argv[5]))

        print "Vocabulary", vocab

        ham_dict = loadLRTable(hamset)
        spam_dict = loadLRTable(spamset)

        # print "Ham LR Table ", ham_dict
        # print "Spam LR Table ", spam_dict

        print "training weights ..."


        LogisticRegression.trainLR(vocab,hamset,ham_dict,Y_ham,n,lamda,k)
        LogisticRegression.trainLR(vocab,spamset,spam_dict,Y_spam,n,lamda,k)

        hamset, spamset = [],[]
        ham_dict, spam_dict = {}, {}

        print "Testing ..."

        loadset("./test/")

        print hamset
        print spamset

        calculateTotal()

        ham_dict = loadLRTable(hamset)
        spam_dict = loadLRTable(spamset)

        # print "Ham LR Table ", ham_dict
        # print "Spam LR Table ", spam_dict

        print "Calculating accuracy"

        accuracyLR(hamset,ham_dict)
        accuracyLR(spamset,spam_dict)

        accuracy = format(count/float(N),'.5f')

        # print "count", count

        print "Accuracy test set Logistic Regression",accuracy
        print "vocab words", len(vocab)




