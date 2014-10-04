__author__ = 'Karthik'

############################################################################################################

# Program to generate bigram unsmoothing , Add-one smoothing and Good-Turing Discount model to say which of the sentences
# S1: The company chairman said he will increase the profit next year .
# S2: The president said he believes the last year profit were good .

# is more probable.

############################################################################################################


from nltk.tokenize import word_tokenize
from nltk.corpus import PlaintextCorpusReader
from nltk.probability import FreqDist
from nltk.util import bigrams
from math import log

corpus_root = './training'
wordlists = PlaintextCorpusReader(corpus_root, '.*')
print wordlists.fileids()
trainingdata = []
trainingdata = wordlists.words('trainingset.txt')

print trainingdata

for i,line in enumerate(open('testset.txt','rU')):
    if i ==0:
        test1 = line.strip()
    else:
        test2 = line.strip()

print test1,"\n"
print test2,"\n"

token1 = word_tokenize(test1)
token2 = word_tokenize(test2)
print "Tokens for input test data ",test1
print token1,"\n"
print "Tokens for input test data ",test2
print token2,"\n"

fdist = FreqDist(trainingdata)

print "Computing uni-gram counts for the input test data \n"

vocabulary = fdist.N()
print "Total no of tokens in the corpus","\n"
print vocabulary,"\n"

uniquewords = len(fdist)
print "Total no of unique word types in the corpus \n"
print uniquewords,"\n"

nobigrams = len(bigrams(trainingdata))
print "Total no of bigrams in corpus \n"
print nobigrams,"\n"

def calculateNc(count):

    cnt = 0
    for no in fdist.values():
        if count == no:
            cnt = cnt + 1
    return cnt


# function to calculate unigram counts
def generateunigramcnt(token):

    dict = {}
    for word in token:
        fdist.inc(word,0)
        dict[word] = fdist.get(word)
    return dict

unigramlist1 = generateunigramcnt(token1)
unigramlist2 = generateunigramcnt(token2)

print "Unigram count for input test data ",test1,"\n"
print unigramlist1,"\n"
print "Unigram count for input test data ",test2,"\n"
print unigramlist2,"\n"


print "Computing uni-gram probability for the input test data \n"


# function to calculate unigram probabilities
def computeunigramprob(dict):

    prob = {}
    for elem in dict:
        prob[elem] = float(dict.get(elem))/vocabulary
    return prob

print "Unigram probabilites for input test data ",test1,"\n"
unigramprob1 = computeunigramprob(unigramlist1)
print unigramprob1,"\n"

print "Unigram probabilites for input test data ",test2,"\n"
unigramprob2 = computeunigramprob(unigramlist2)
print unigramprob2,"\n"


print "Computing bi-gram counts for the given test data in the training set \n"

freqcntbigramcorpa = FreqDist(bigrams(trainingdata))

print "bigrams for input test data ",test1,"\n"
bigramtest1 = bigrams(token1)
print bigramtest1,"\n"

print "bigrams for input test data ",test2,"\n"
bigramtest2 = bigrams(token2)
print bigramtest2,"\n"


# function for printing table
def printable(token,dict):
        print "\t\t","\t".join(token)

        for word in token:
            print word,"\t",
            for sample in token:
                    print "\t",dict[(word,sample)],

            print "\n"


# function to calculate probabilities of the input test data
def calculateprob(token,unigram,bigram):

    problist = []
    result = 1.0
    for i,word in enumerate(token):

        if i ==0 :
            temp = word
            problist.append('%.5f' % float(unigram[word]))
        else:
            #print temp ,"->",word,
            problist.append('%.5f' % float(bigram[(temp,word)]))
            temp=word

    print problist
    for prob in problist:
        result = result*float(prob)

    return result


############################################################################################################

####################################  functions to generate bi-gram models #################################

############################################################################################################

# function for bigram model without smoothing.

print "Generating bigram models \n"

def bigrammodel_Unsmoothing():

    # function for generating bigram freq counts of the bigrams of the input test data in the corpus
    def generatebigramcnt_unsmoothing(token):

        bigramfreqcnt = {}
        for word in token:
            for elem in token:
                if freqcntbigramcorpa.get((word, elem)):
                    bigramfreqcnt[(word,elem)] = freqcntbigramcorpa.get((word,elem))
                else:
                    bigramfreqcnt[(word,elem)] = 0
        return bigramfreqcnt

    print "bigram counts for input test data ",test1,"\n"
    bigramfreqcntlist1 = generatebigramcnt_unsmoothing(token1)
    print bigramfreqcntlist1,"\n"

    print "bigram counts for input test data ",test2,"\n"
    bigramfreqcntlist2 = generatebigramcnt_unsmoothing(token2)
    print bigramfreqcntlist2,"\n"


    print "Constructing Bi-gram Frequency counts table for the input test data ",test1,"\n"
    printable(token1,bigramfreqcntlist1)

    print "Constructing Bi-gram Frequency counts table for the input test data ",test2,"\n"
    printable(token2,bigramfreqcntlist2)


    # function for calculating conditional probabilities of all possible bigrams of input test data
    def computebigramprob_unsmoothing(token,unigramfreq,bigramfreq):

        bigramprob = {}

        for word in token:
            for elem in token:
                bigramprob[(word,elem)] = format(float(bigramfreq[(word,elem)])/float(unigramfreq[word]),".7f")

        return bigramprob


    print "Calculating bi-gram probabilities for the input test data ",test1,"\n"
    bigramproblist1 = computebigramprob_unsmoothing(token1,unigramlist1,bigramfreqcntlist1)
    print bigramproblist1,"\n"

    print "Calculating bi-gram probabilities for the input test data ",test2,"\n"
    bigramproblist2 = computebigramprob_unsmoothing(token2,unigramlist2,bigramfreqcntlist2)
    print bigramproblist2,"\n"


    print "Constructing Bi-gram probabilities table for the input test data ",test1,"\n"
    printable(token1,bigramproblist1)

    print "Constructing Bi-gram probabilities table for the input test data ",test2,"\n"
    printable(token2,bigramproblist2)

    print "Unsmoothing Probability Calculations .. \n"
    print "Unsmoothing Probability for ",test1,"is:  %s" % calculateprob(token1,unigramprob1,bigramproblist1),"\n"
    print "Unsmoothing Probability for ",test2,"is:  %s" % calculateprob(token2,unigramprob2,bigramproblist2),"\n"




# function for bigram model add one smoothing.

def bigrammodel_addonesmoothing():

    # function for generating bigram freq counts of the bigrams of the input test data in the corpus
    def generatebigramcnt_addonesmoothing(token):

        bigramfreqcnt = {}
        for word in token:
            for elem in token:
                if freqcntbigramcorpa.get((word, elem)):
                    bigramfreqcnt[(word,elem)] = freqcntbigramcorpa.get((word,elem)) + 1
                else:
                    bigramfreqcnt[(word,elem)] = 1
        return bigramfreqcnt

    print "bigram counts for input test data ",test1,"\n"
    bigramfreqcntlist1 = generatebigramcnt_addonesmoothing(token1)
    print bigramfreqcntlist1,"\n"

    print "bigram counts for input test data ",test2,"\n"
    bigramfreqcntlist2 = generatebigramcnt_addonesmoothing(token2)
    print bigramfreqcntlist2,"\n"

    print "Constructing Bi-gram Frequency counts table for the input test data ",test1,"\n"
    printable(token1,bigramfreqcntlist1)

    print "Constructing Bi-gram Frequency counts table for the input test data ",test2,"\n"
    printable(token2,bigramfreqcntlist2)


    # function for calculating conditional probabilities of all possible bigrams of input test data
    def computebigramprob_addonesmoothing(token,unigramfreq,bigramfreq):

        bigramprob = {}

        for word in token:
            for elem in token:
                bigramprob[(word,elem)] = format(float(bigramfreq[(word,elem)])/(float(unigramfreq[word]) + uniquewords),".7f")

        return bigramprob


    print "Calculating bi-gram probabilities for the input test data ",test1,"\n"
    bigramproblist1 = computebigramprob_addonesmoothing(token1,unigramlist1,bigramfreqcntlist1)
    print bigramproblist1,"\n"

    print "Calculating bi-gram probabilities for the input test data ",test2,"\n"
    bigramproblist2 = computebigramprob_addonesmoothing(token2,unigramlist2,bigramfreqcntlist2)
    print bigramproblist2,"\n"


    print "Constructing Bi-gram probabilities table for the input test data ",test1,"\n\n"
    printable(token1,bigramproblist1)

    print "Constructing Bi-gram probabilities table for the input test data ",test2,"\n\n"
    printable(token2,bigramproblist2)



    # function for generating reconstituted bigram freq counts of the bigrams of the input test data in the corpus
    def generatebigram_reconstitutedcnt_addonesmoothing(token,unigramfreq):

        bigramfreq_reconstcnt = {}
        for word in token:
            for elem in token:
                if freqcntbigramcorpa.get((word, elem)):
                    bigramfreq_reconstcnt[(word,elem)] = format(((freqcntbigramcorpa.get((word,elem)) + 1)*float(unigramfreq[word]))/(float(unigramfreq[word]) + uniquewords),".7f")
                else:
                    bigramfreq_reconstcnt[(word,elem)] = format(float(unigramfreq[word])/(float(unigramfreq[word]) + uniquewords),".7f")

        return bigramfreq_reconstcnt

    print "Reconstituted bigram counts for input test data ",test1,"\n\n"
    bigramfreq_reconstcntlist1 = generatebigram_reconstitutedcnt_addonesmoothing(token1,unigramlist1)
    print bigramfreq_reconstcntlist1,"\n"

    print "Reconstituted bigram counts for input test data ",test2,"\n\n"
    bigramfreq_reconstcntlist2 = generatebigram_reconstitutedcnt_addonesmoothing(token2,unigramlist2)
    print bigramfreq_reconstcntlist2,"\n"



    print "Constructing Reconstituted Bi-gram Frequency counts table for the input test data ",test1,"\n"
    printable(token1,bigramfreq_reconstcntlist1)

    print "Constructing Reconstituted Bi-gram Frequency counts table for the input test data ",test2,"\n"
    printable(token2,bigramfreq_reconstcntlist2)



    print "Add-one Smoothing Probability Calculations .. \n"

    prob1 = calculateprob(token1,unigramprob1,bigramproblist1)
    prob2 = calculateprob(token2,unigramprob2,bigramproblist2)

    print "Add-one smoothing Probability for ",test1,"is:  %s" % prob1,"\n"
    print "Add-one smoothing Probability for ",test2,"is:  %s" % prob2,"\n"

    if prob1 > prob2:
        print "Sentence:\n %s \nis more probable than \nSentence:\n %s" % (test1,test2),"\n"
    else:
        print "Sentence:\n %s \nis more probable than \nSentence:\n %s" % (test2,test1),"\n"




# function for bigram model Good-Turing discounting.

def bigrammodel_Goodturingdiscount():

    # function for generating Good turing bigram freq counts of the bigrams of the input test data in the corpus
    # if Nc = 0 replace Nc with 2^(a+blog(c)) . I have used a = 1 and b = 1 since a & b values are unknown
    def generatebigramcnt_Goodturingdiscount(token):

        bigramfreqgtcnt = {}
        for word in token:
            for elem in token:
                temp = freqcntbigramcorpa.get((word, elem))
                if temp:
                    if calculateNc(temp) == 0:
                        denomtr = pow(2,(1+log(temp,2)))

                    elif calculateNc(temp+1) == 0:
                        numtr = pow(2,(1+log(temp+1,2)))

                    else:
                        numtr = calculateNc(temp+1)
                        denomtr = calculateNc(temp)

                    bigramfreqgtcnt[(word,elem)] = format(numtr*(temp+1)/float(denomtr),".7f")
                else:
                    bigramfreqgtcnt[(word,elem)] = format(calculateNc(1)/float(nobigrams),".7f")
        return bigramfreqgtcnt

    print "bigram counts for input test data ",test1,"\n"
    bigram_gtcntlist1 = generatebigramcnt_Goodturingdiscount(token1)
    print bigram_gtcntlist1,"\n"

    print "bigram counts for input test data ",test2,"\n"
    bigram_gtcntlist2 = generatebigramcnt_Goodturingdiscount(token2)
    print bigram_gtcntlist2,"\n"


    print "Constructing Bi-gram Frequency counts table for the input test data ",test1,"\n"
    printable(token1,bigram_gtcntlist1)

    print "Constructing Bi-gram Frequency counts table for the input test data ",test2,"\n"
    printable(token2,bigram_gtcntlist2)


    def computebigramprob_Goodturingdiscount(token,bigramfreq):

        bigramprob = {}

        for word in token:
            for elem in token:
                bigramprob[(word,elem)] = format(float(bigramfreq[(word,elem)])/float(vocabulary),".7f")

        return bigramprob


    print "Calculating bi-gram probabilities for the input test data ",test1,"\n"
    bigramproblist1 = computebigramprob_Goodturingdiscount(token1,bigram_gtcntlist1)
    print bigramproblist1,"\n"

    print "Calculating bi-gram probabilities for the input test data ",test2,"\n"
    bigramproblist2 = computebigramprob_Goodturingdiscount(token2,bigram_gtcntlist2)
    print bigramproblist2,"\n"


    print "Constructing Bi-gram probabilities table for the input test data ",test1,"\n\n"
    printable(token1,bigramproblist1)

    print "Constructing Bi-gram probabilities table for the input test data ",test2,"\n\n"
    printable(token2,bigramproblist2)

    print "Good-Turing Smoothing Probability Calculations .. \n"

    prob1 = calculateprob(token1,unigramprob1,bigramproblist1)
    prob2 = calculateprob(token2,unigramprob2,bigramproblist2)

    print "Good-Turing smoothing Probability for ",test1,"is:  %s" % prob1,"\n"
    print "Good-Turing smoothing Probability for ",test2,"is:  %s" % prob2,"\n"

    if prob1 > prob2:
        print "Sentence:\n %s \nis more probable than \nSentence:\n %s" % (test1,test2),"\n"
    else:
        print "Sentence:\n %s \nis more probable than \nSentence:\n %s" % (test2,test1),"\n"



print "Calculating probabilities of the input test data \n"

print "bigram model without smoothing.\n"
bigrammodel_Unsmoothing()

print "bigram model with add-one smoothing.\n"
bigrammodel_addonesmoothing()

print "bigram model with Good-Turing discounting.\n"
bigrammodel_Goodturingdiscount()






