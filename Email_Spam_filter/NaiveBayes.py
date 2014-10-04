__author__ = 'Karthik'

import math

#######################################
#  function to train set using Naive Bayes learning technique
#######################################

def trainNB(vocab, hamdict, spamdict):

    cond_prob_ham , cond_prob_spam = {}, {}
    denom_hamcount, denom_spamcount = 0,0

    print "Training"

    V = len(vocab)

    for ix in hamdict.values():
        denom_hamcount += ix

    for iv in spamdict.values():
        denom_spamcount += iv

    ### calculating conditional probabilities for each word given class

    for items in vocab:

        if items in hamdict.keys():
            prob_ham = float(format((hamdict.get(items) + 1)/float(denom_hamcount + V),'.5f'))
        else:
            prob_ham = float(format(1/float(denom_hamcount + V),'.5f'))

        cond_prob_ham[items] = prob_ham


    for items in vocab:

        if items in spamdict.keys():
            prob_spam = float(format((spamdict.get(items) + 1)/float(denom_spamcount + V),'.5f'))
        else:
            prob_spam = float(format(1/float(denom_spamcount + V),'.5f'))

        cond_prob_spam[items] = prob_spam


    return (cond_prob_ham, cond_prob_spam)


# function to test doc (new emails ) and predict its spam/not spam by applying Naive bayes training
# previously calculated probabilities

def applyNB(vocab, Nlist, doc, ham_condprob, spam_condprob):
    ham_score, spam_score = 0,0

    V = len(vocab)

    N = Nlist[0] + Nlist[1]

    priorham = float(format(Nlist[0]/float(N),'.5f'))
    priorspam = float(format(Nlist[1]/float(N),'.5f'))

    ham_score = float(format(math.log(priorham,2),'.5f'))
    spam_score = float(format(math.log(priorspam,2),'.5f'))

    file = open(doc,'r')
    for line in file:
        words = line.strip().split(' ')

        for items in words:
            if items in vocab:
                ham_score += float(format(math.log(ham_condprob.get(items)),'.5f'))
                spam_score += float(format(math.log(spam_condprob.get(items)),'.5f'))


    ham_score = float(format(ham_score,'.5f'))
    spam_score = float(format(spam_score,'.5f'))

    if ham_score >= spam_score:
        return "ham"
    else:
        return "spam"


