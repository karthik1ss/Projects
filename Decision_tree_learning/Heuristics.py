
from __future__ import division
__author__ = 'Karthik'

import math

### All heurisitcs related functions


# # get count of positive and negative examples in the class list

def get_count(list):

    neg_cnt , pos_cnt = 0, 0

    if list:
        for i in list:
            if i == '0':
                neg_cnt += 1

            elif i == '1':
                pos_cnt +=  1

    return (pos_cnt, neg_cnt)


# calculate gain for the attributes

def gain(set, member):

    gain = set - member
    gain = float(format(gain,".4f"))
    return gain

# calculate entropy for the set

def Entropy_Set(list):

    entropy_class = 0
    # print "Class List" , list

    if list:

        pos_cnt, neg_cnt = get_count(list)

        if neg_cnt == 0:
            # print "All are positive examples"
            return 0

        elif pos_cnt == 0:
            # print "All are negative examples"
            return 0

        neg_ex = neg_cnt/(pos_cnt+neg_cnt)
        pos_ex = pos_cnt/(pos_cnt+neg_cnt)

        neg_ex = float(format(neg_ex,".4f"))
        pos_ex = float(format(pos_ex,".4f"))

        # print "Entropy Class neg/pos samples ", neg_ex, ":", pos_ex

        entropy_class = - neg_ex*math.log(neg_ex,2) - pos_ex*math.log(pos_ex,2)

        entropy_class = float(format(entropy_class,".4f"))

    return entropy_class



# calculate entropy for the individual attributes

def Entropy_Members(dataset, dataset_zero, dataset_one, list):

    entropy_member = None
    # print "Entropy"
    # print "Member List" , list

    pos_cnt, neg_cnt = get_count(list)

    neg_ex = neg_cnt/(pos_cnt+neg_cnt)
    pos_ex = pos_cnt/(pos_cnt+neg_cnt)

    neg_ex = float(format(neg_ex,".4f"))
    pos_ex = float(format(pos_ex,".4f"))

    # print "Entropy Member neg/pos samples" , neg_ex, ":", pos_ex

    entropy_zero = Entropy_Set(dataset_zero.get("Class"))
    entropy_one = Entropy_Set(dataset_one.get("Class"))

    entropy_member =  neg_ex*entropy_zero + pos_ex*entropy_one

    entropy_member = float(format(entropy_member,".4f"))

    return entropy_member


# calculate variance impurity for the set

def Variance_Impurity_Set(list):

    variance_impurity = 0
    # print "Class List" , list
    class_length = len(list)

    if list:

        pos_cnt, neg_cnt = get_count(list)

        if neg_cnt == 0:
            # print "All are positive examples"
            return 0

        elif pos_cnt == 0:
            # print "All are negative examples"
            return 0

        variance_impurity = neg_cnt*pos_cnt/float(class_length*class_length)

        variance_impurity = float(format(variance_impurity,".4f"))

    return variance_impurity

# calculate variance impurity for the individual members

def Variance_Impurity_Members(dataset, dataset_zero, dataset_one, list):

    variance_member = None
    # print "Variance"
    # print "Member List" , list

    pos_cnt, neg_cnt = get_count(list)

    neg_ex = neg_cnt/(pos_cnt+neg_cnt)
    pos_ex = pos_cnt/(pos_cnt+neg_cnt)

    neg_ex = float(format(neg_ex,".4f"))
    pos_ex = float(format(pos_ex,".4f"))

    # print "Entropy Member neg/pos samples" , neg_ex, ":", pos_ex

    variance_zero = Variance_Impurity_Set(dataset_zero.get("Class"))
    variance_one = Variance_Impurity_Set(dataset_one.get("Class"))

    variance_member =  neg_ex*variance_zero + pos_ex*variance_one

    variance_member = float(format(variance_member,".4f"))

    return variance_member






