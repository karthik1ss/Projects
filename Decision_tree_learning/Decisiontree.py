#!/usr/bin/python
__author__ = 'Karthik'

import sys
import Dataset
import Heuristics
import collections,copy
from random import randint

accuracy = None
cnt_nonleaf_nodes = 0
max_gain = 0
heuristic = None

### class for constructing decision tree

class BTreeNode:
    left, right, data, order = None, None, None, None
    subset = []

    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data
        self.order = None
        self.subset = {}


class Stack:
    def __init__(self):
        self.items = []

    def is_empty(self):
        return self.items == []

    def push(self,item):
        self.items.append(item)

    def pop(self):
        return self.items.pop()


# get count of positive and negative examples in the class list

def get_count(list):

    neg_cnt , pos_cnt = 0, 0

    if list:
        for i in list:
            if i == '0':
                neg_cnt += 1

            elif i == '1':
                pos_cnt +=  1

    print neg_cnt , ":",pos_cnt
    return (pos_cnt, neg_cnt)


# get the maximum of count of positives and negatives in the list

def get_max_val(list):

    neg_cnt , pos_cnt = 0, 0

    if list:
        for i in list:
            if i == '0':
                neg_cnt += 1

            elif i == '1':
                pos_cnt +=  1

    if neg_cnt > pos_cnt:
        return 0
    elif neg_cnt < pos_cnt:
        return 1
    else:
        return 0


## decision tree construction recursively - ID3 algorithm from Tom Mitchel book

def GrowTree(dataset, attributes):
    global cnt_nonleaf_nodes,heuristic
    max_gain_attr =  None
    max_gain = 0.0
    gain = 0.0

    # print "Attributes ", attributes

    if not attributes:
        common_val = get_max_val(dataset.get("Class"))
        root = BTreeNode(str(common_val))
        root.left = None
        root.right = None

    else:

        if dataset.has_key('NA'):
            return BTreeNode(str(dataset.get('NA')))

        else:

            class_list = dataset.get("Class")
            # print class_list

            tmp_negcnt, tmp_poscnt = get_count(class_list)

            if tmp_poscnt == 0:
                print "class: all negative examples"
                # print class_list
                return BTreeNode('0')

            if tmp_negcnt == 0:
                print "class: all positive examples"
                # print class_list
                return BTreeNode('1')


            for val in attributes:

                neg_dict, pos_dict = Dataset.split_dataset(dataset, val)
                # print "Neg dict class" , neg_dict.get("Class")
                # print "Pos dict class" , pos_dict.get("Class")

                if heuristic == 0:
                    entropy_set = Heuristics.Entropy_Set(class_list)
                elif heuristic == 1:
                    variance_set = Heuristics.Variance_Impurity_Set(class_list)

                # print "Entropy set for ", val ,entropy_set

                member_list = dataset.get(val)
                if heuristic == 0:
                    entropy_member = Heuristics.Entropy_Members(dataset,neg_dict,pos_dict,member_list)
                elif heuristic == 1:
                    variance_member = Heuristics.Variance_Impurity_Members(dataset,neg_dict,pos_dict,member_list)
                # print "Entropy member for ",val,entropy_member

                if heuristic == 0:
                    gain = Heuristics.gain(entropy_set, entropy_member)
                elif heuristic == 1:
                    gain = Heuristics.gain(variance_set, variance_member)
                print "gain for ",val ,gain


                if bool([a for a in neg_dict.values() if a == []]):
                    print "Sub values empty - zero dataset"
                    common_val = get_max_val(dataset.get("Class"))
                    neg_dict = {}
                    neg_dict.update({'NA':common_val})

                elif bool([a for a in pos_dict.values() if a == []]):
                    print "Sub values empty - one dataset"
                    common_val = get_max_val(dataset.get("Class"))
                    pos_dict = {}
                    pos_dict.update({'NA':common_val})


                if gain >= max_gain:
                    max_gain = gain
                    max_gain_attr = val
                    root_zero_dataset = neg_dict
                    # print "inside max gain cal zeros ",val, neg_dict.get("Class")
                    root_one_dataset = pos_dict
                    # print "inside max gain cal ones ",val, pos_dict.get("Class")

                neg_dict = {}
                pos_dict = {}
                # print


            print "Maximum Information Gain: ",max_gain
            print "Node selected: " , max_gain_attr
            print "Zero Dataset: ", root_zero_dataset.get("Class")
            print "One Dataset: ", root_one_dataset.get("Class")

        root = BTreeNode(max_gain_attr)
        cnt_nonleaf_nodes += 1
        root.order = cnt_nonleaf_nodes
        root.subset = dataset

        if max_gain_attr in attributes:
            attributes.remove(max_gain_attr)

        if root != None:

            # if root.left:
                root.left = GrowTree(root_zero_dataset,attributes)
            # if root.right:
                root.right = GrowTree(root_one_dataset,attributes)


    return root


# print decision tree

def print_tree(root):

    print "Printing Decision tree"

    if root == None:
        return

    else:

        stck = Stack()
        level = 0
        temp = root

        while True:

            while temp != None:
                # print root
                for i in range(level):
                    print "|",
                print temp.data,

                stck.push((temp,level))

                # leaf node in left subtree
                if(temp.left.data == '0' or temp.left.data == '1'):
                    print "= 0 :",temp.left.data
                    break
                else:
                    print "= 0 :"
                    level+=1
                    temp = temp.left

            if stck.is_empty():
                break
            (temp,level) = stck.pop()
            for i in range(level):
                print "|",
            print temp.data,

            # leaf node in right subtree
            if(temp.right.data == '0' or temp.right.data == '1'):
                print "= 1 :",temp.right.data
                temp = None
            else:
                print "= 1 :"
                level+=1
                temp = temp.right



# def print_tree_mod(root):
#
#     if root:
#         print root.data
#
#     if root.left:
#         root.left = print_tree_mod(root.left)
#
#     if root.right:
#         root.right = print_tree_mod(root.right)

# calculating accuracy of given dataset - test set or validation set

def accuracy_tree(root,set):
    predict_val,cnt = None,0
    actual_cnt = len(set.get("Class"))
    tmpdict = collections.OrderedDict()

    testkeys = set.keys()

    for i in range(actual_cnt):

        for val in testkeys:
            node_values = set.get(val)
            tmpdict[val] = node_values[i]

        # print tmpdict

        predict_val = accuracy_row(root,tmpdict)

        # print "Predicted val of class" , predict_val

        if predict_val == tmpdict.get("Class"):
            cnt+=1


    return float(format(cnt/float(actual_cnt),'.4f'))


def accuracy_row(root,testrow):

    if testrow.has_key(root.data):
        itr = testrow.get(root.data)
        if itr == '0':
            # print "0 branch"
            return accuracy_row(root.left,testrow)
        elif itr == '1':
            # print "1 branch"
            return accuracy_row(root.right,testrow)

    else:
        return root.data

# copy entire decision tree to another tree

def copy_tree(node):

    new_node = copy.deepcopy(node)
    return new_node

# replace non leaf nodes in tree with leaf nodes based on majority class of the subset data  - pruning

def replace_node(root,number):

    if root != None:
        if root.order == number:
            cmn_val = get_max_val(root.subset.get("Class"))
            root.data = cmn_val
            root.left = None
            root.right = None

        replace_node(root.left,number)
        replace_node(root.right,number)

# numbering non leaf nodes in the tree

def tree_numbering(root):

    global cnt_nonleaf_nodes

    if root:
        cnt_nonleaf_nodes+= 1
        root.order = cnt_nonleaf_nodes

    if root.left:
        tree_numbering(root.left)

    if root.right:
        tree_numbering(root.right)


# post pruning algorithm to prune the decision treee

def post_pruning(root,l,k,validation_set):
    tmpnode = None
    global cnt_nonleaf_nodes

    accuracy = accuracy_tree(root,validation_set)

    accuracy_best = accuracy

    for i in range(1,l+1):

        tmpnode = copy_tree(root)

        m = randint(1,k)

        for j in range(1,m+1):
            p = randint(1,cnt_nonleaf_nodes)
            replace_node(tmpnode,p)
            cnt_nonleaf_nodes = 0
            tree_numbering(tmpnode)
            # print "Renumbered count", cnt_nonleaf_nodes


        accuracy_temp = accuracy_tree(tmpnode,validation_set)

        if accuracy_temp > accuracy_best:
            accuracy_best = accuracy_temp

    return accuracy_best

# main method

if __name__ == "__main__":
    global heuristic
    # print sys.argv

    attributes = []
    l = int(float(sys.argv[1]))
    k = int(float(sys.argv[2]))
    trainset = sys.argv[3]
    testset = sys.argv[4]
    validset = sys.argv[5]
    toprint = sys.argv[6]
    heuristic = int(float(sys.argv[7]))

    # print "Training Set"
    train_set = Dataset.load_dataset(trainset)
    for itm in train_set.keys():
        if itm != "Class":
            attributes.append(itm)

    node = GrowTree(train_set, attributes)

    # print "Test Set"
    test_set = Dataset.load_dataset(testset)
    accuracy = accuracy_tree(node,test_set)

    print "Test set Accuracy percentage" , accuracy

    vaildation_set = Dataset.load_dataset(validset)

    print "Post Pruning accuracy " , post_pruning(node,l,k,vaildation_set)

    if toprint == 'yes':
        print_tree(node)


