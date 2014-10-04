__author__ = 'Karthik'

import math
weights = {}
w0 = 0.0001
file_sum = {}


# function to calculate summed count*weights for all words in doc

def calculate_findsums(dict,set):
    temp_sum = 0

    for files in set:

        file = open(files,'r')


        if files in dict.keys():

            for line in file:
                words = line.strip().split(' ')

                for items in words:

                    if dict.get(files).get(items):
                        temp_sum += weights.get(items)*dict.get(files).get(items)
                    else:
                        temp_sum += 0

                    temp_sum = float(format(temp_sum,'.6f'))
                    file_sum[files] = temp_sum

        temp_sum = 0

    print "file_sum",file_sum


# calculate summed weight for all documents in a class

def calculate_weightsums(item,dict, set,Y):

    global w0,file_sum
    weight_sum = 0

    t = 0

    for files in set:

        file = open(files,'r')


        if files in dict.keys():

            if dict.get(files).get(item):

                if Y == 0:
                    t = float(format(math.exp(w0 + file_sum.get(files))/float(1 + math.exp(w0 + file_sum.get(files))),'.6f'))
                    # t = (w0 + file_sum) - math.log(math.exp(w0 + file_sum) + 1)
                    x = t
                elif Y == 1:
                    x = 1 - t

                weight_sum += dict.get(files).get(item)*(Y - x)



    weight_sum = float(format(weight_sum,'.6f'))


    return weight_sum


# function to calculate weights of each words in corpus . training weights by applying linear logistic regression

def trainLR(vocab,set,dict,Y,n,lamda,k):
    global w0


    # n = 1
    # lamda = 0.01
    # thrshld = 0.0001
    weight_sum = 0

    for items in vocab:
        weights[items] = 0.0001

    print weights

    # while (new_wi - wi) < thrshld:
    for i in range(k):

        calculate_findsums(dict,set)
        # print "file sum" ,file_sum

        for items in vocab:

            wi = weights.get(items)
            weight_sum = calculate_weightsums(items,dict,set,Y)
            new_wi = wi + n*weight_sum - n*lamda*wi
            new_wi = float(format(new_wi,'.8f'))
            print "updating weight: ", wi, " with ", new_wi

            weights[items] = new_wi

        file_sum = {}



    print weights


# predict document belongs to class - spam/not spam by using already calculated trained weights

def applyLR(vocab, doc, dict):

    global w0
    file_sum = 0

    if doc in dict.keys():

        file = open(doc,'r')
        for line in file:
            words = line.strip().split(' ')

            for items in words:

                if items in vocab:

                    # print "weight", weights.get(items)
                    # print "counts", dict.get(doc).get(items)

                    file_sum += weights.get(items)*dict.get(doc).get(items)

    file_sum = float(format(file_sum,'.8f'))

    # print "file sum" , file_sum

    val = w0 + file_sum
    # print "val", val

    if val > 0:
        return "ham"
    else:
        return "spam"










