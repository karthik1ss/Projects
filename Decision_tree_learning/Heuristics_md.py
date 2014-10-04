
from __future__ import division
__author__ = 'Karthik'

import math
import Dataset


def Information_gain(dataset, node):
    info_gain = None

    print "Information gain heurisitic"

    entropy_set = Entropy_Set(dataset)

    neg_dict, pos_dict = Dataset.split_dataset(dataset, node)

    entropy_members = Entropy_Members(dataset,neg_dict,pos_dict,node)

    if entropy_set == 'F':
        info_gain = 0
        print "Info gain for all negative examples", info_gain
        return (info_gain,'NA',{},{})

    elif entropy_set == 'T':
        info_gain = 1
        print "Info gain for all positive examples", info_gain
        return (info_gain,'NA',{},{})

    else:
        info_gain = entropy_set - entropy_members
        info_gain = float(format(info_gain,".4f"))
        print "Info gain for ", node,": " , info_gain
        return (info_gain, node, neg_dict, pos_dict)




def Entropy_Set(dataset):
    entropy_class = None

    class_list = dataset.get("Class")
    print "Class List" , class_list

    pos_cnt, neg_cnt = get_count(class_list)

    if pos_cnt == 0 and neg_cnt == 0:
        # entropy_class = 0
        print "consider parent dataset and return max of pos/neg examples"

    elif neg_cnt == 0:
        print "All are positive examples"
        return 'T'

    elif pos_cnt == 0:
        print "All are negative examples"
        return 'F'


    else:

        pos_ex = pos_cnt/(pos_cnt+neg_cnt)
        neg_ex = neg_cnt/(pos_cnt+neg_cnt)

        pos_ex = float(format(pos_ex,".4f"))
        neg_ex = float(format(neg_ex,".4f"))

        print "Entropy Class pos/neg samples ", pos_ex, ":", neg_ex

        entropy_class = - pos_ex*math.log(pos_ex,2) - neg_ex*math.log(neg_ex,2)

        entropy_class = float(format(entropy_class,".4f"))

    print "Entropy class" ,entropy_class

    return entropy_class


def get_count(list):

    neg_cnt , pos_cnt = 0, 0

    if list:
        for i in list:
            if i == '0':
                neg_cnt += 1

            elif i == '1':
                pos_cnt +=  1

    # print neg_cnt , ":",pos_cnt
    return (pos_cnt, neg_cnt)


def Entropy_Members(dataset, dataset_zero, dataset_one, node):

    entropy_member = None
    member_list = dataset.get(node)
    print node , "Member List" , member_list

    pos_cnt, neg_cnt = get_count(member_list)

    if pos_cnt == 0 and neg_cnt == 0:
        entropy_member = 0

    else:

        pos_ex = pos_cnt/(pos_cnt+neg_cnt)
        neg_ex = neg_cnt/(pos_cnt+neg_cnt)

        pos_ex = float(format(pos_ex,".4f"))
        neg_ex = float(format(neg_ex,".4f"))

        print "Entropy Member pos/neg samples" , pos_ex, ":", neg_ex

        entropy_zero = Entropy_Set(dataset_zero)
        entropy_one = Entropy_Set(dataset_one)

        entropy_member =  pos_ex*entropy_one + neg_ex*entropy_zero

        entropy_member = float(format(entropy_member,".4f"))

    print "Entropy Member" , entropy_member

    return entropy_member




def Variance_Impurity():

    print "Vaiance Impurity Heuristic"


