#!/usr/bin/python
__author__ = 'Karthik'

# import sys
import Dataset
import Heuristics


class BTreeNode:
    left, right, data = None, None, None

    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data


class Stack:
    def __init__(self):
        self.items = []

    def is_empty(self):
        return self.items == []

    def push(self,item):
        self.items.append(item)

    def pop(self):
        return self.items.pop()


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



def GrowTree(dataset, attributes, level):

    print "Constructing Decision Tree" , level
    max_gain, max_gain_attr, level = 0, None, 0

    print "Attributes ", attributes

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

            tmp_negcnt, tmp_poscnt = get_count(class_list)

            if tmp_poscnt == 0:
                print "class: all negative examples"
                return BTreeNode('0')

            if tmp_negcnt == 0:
                print "class: all positive examples"
                return BTreeNode('1')


            for val in attributes:

                neg_dict, pos_dict = Dataset.split_dataset(dataset, val)

                variance_set = Heuristics.Variance_Impurity_Set(class_list)

                print "Variance Impurity set for ", val ,variance_set

                member_list = dataset.get(val)
                variance_member = Heuristics.Variance_Impurity_Members(dataset,neg_dict,pos_dict,member_list)
                print "Variance Impurity member for ",val,variance_member

                var_gain = Heuristics.gain(variance_set, variance_member)
                print "Variance Impurity gain for ",val ,var_gain

                print "Bool value - zeros" , bool([a for a in neg_dict.values() if a == []])
                print "Bool value - ones" , bool([a for a in pos_dict.values() if a == []])

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


                if var_gain > max_gain:
                    max_gain = var_gain
                    max_gain_attr = val
                    root_zero_dataset = neg_dict
                    root_one_dataset = pos_dict

                else:
                    max_gain = var_gain
                    max_gain_attr = val
                    root_zero_dataset = neg_dict
                    root_one_dataset = pos_dict



            print "Maximum Information Gain: ",max_gain
            print "Node selected" , max_gain_attr
            print "Zero Dataset", root_zero_dataset
            print "One Dataset", root_one_dataset

        root = BTreeNode(max_gain_attr)

        if max_gain_attr in attributes:
            attributes.remove(max_gain_attr)

        if root != None:
            root.left = GrowTree(root_zero_dataset,attributes,level)
            root.right = GrowTree(root_one_dataset,attributes,level)


    level+= 1


    return root



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



if __name__ == "__main__":

    attributes = []

    att_dict = Dataset.load_dataset('data_sets1/verysmall.csv')
    for itm in att_dict.keys():
        if itm != "Class":
            attributes.append(itm)

    print attributes

    node = GrowTree(att_dict, attributes, 0)
    print_tree(node)



