__author__ = 'Karthik'

import collections

## load the dataset into dictionaries for further processing

def load_dataset(file):

    attribute_list = []
    list_xb = []
    list_xc = []
    list_xd = []
    list_xe = []
    list_xf = []
    list_xg = []
    list_xh = []
    list_xi = []
    list_xj = []
    list_xk = []
    list_xl = []
    list_xm = []
    list_xn = []
    list_xo = []
    list_xp = []
    list_xq = []
    list_xr = []
    list_xs = []
    list_xt = []
    list_xu = []
    list_class = []
    attr = None
    attributes_dict  = collections.OrderedDict()

    training_set_file = open(file, 'r')
    # print training_set_file

    for i, line in enumerate(training_set_file):

        # print line.strip()

        if i == 0:

            attribute_list = line.strip().split(',')
            # print attribute_list

        else:

            line_values = line.strip().split(',')

            for attr in attribute_list:

                if attr == "XB":
                    list_xb.append(line_values[0])
                    attributes_dict[attr] = list_xb

                elif attr == "XC":
                    list_xc.append(line_values[1])
                    attributes_dict[attr] = list_xc

                elif attr == "XD":
                    list_xd.append(line_values[2])
                    attributes_dict[attr] = list_xd

                elif attr == "XE":
                    list_xe.append(line_values[3])
                    attributes_dict[attr] = list_xe

                elif attr == "XF":
                    list_xf.append(line_values[4])
                    attributes_dict[attr] = list_xf

                elif attr == "XG":
                    list_xg.append(line_values[5])
                    attributes_dict[attr] = list_xg

                elif attr == "XH":
                    list_xh.append(line_values[6])
                    attributes_dict[attr] = list_xh

                elif attr == "XI":
                    list_xi.append(line_values[7])
                    attributes_dict[attr] = list_xi

                elif attr == "XJ":
                    list_xj.append(line_values[8])
                    attributes_dict[attr] = list_xj

                elif attr == "XK":
                    list_xk.append(line_values[9])
                    attributes_dict[attr] = list_xk

                elif attr == "XL":
                    list_xl.append(line_values[10])
                    attributes_dict[attr] = list_xl

                elif attr == "XM":
                    list_xm.append(line_values[11])
                    attributes_dict[attr] = list_xm

                elif attr == "XN":
                    list_xn.append(line_values[12])
                    attributes_dict[attr] = list_xn

                elif attr == "XO":
                    list_xo.append(line_values[13])
                    attributes_dict[attr] = list_xo

                elif attr == "XP":
                    list_xp.append(line_values[14])
                    attributes_dict[attr] = list_xp

                elif attr == "XQ":
                    list_xq.append(line_values[15])
                    attributes_dict[attr] = list_xq

                elif attr == "XR":
                    list_xr.append(line_values[16])
                    attributes_dict[attr] = list_xr

                elif attr == "XS":
                    list_xs.append(line_values[17])
                    attributes_dict[attr] = list_xs

                elif attr == "XT":
                    list_xt.append(line_values[18])
                    attributes_dict[attr] = list_xt

                elif attr == "XU":
                    list_xu.append(line_values[19])
                    attributes_dict[attr] = list_xu

                elif attr == "Class":
                    list_class.append(line_values[20])
                    attributes_dict[attr] = list_class



    return attributes_dict


# split the dataset into positive and negative examples for the particular attribute

def split_dataset(dataset, node):

    # print "Splitting Dataset"

    pos_dict = collections.OrderedDict()
    neg_dict = collections.OrderedDict()

    zero_list, one_list, tmp_zero_list, tmp_one_list = [],[],[],[]

    # print dataset.keys()

    for items in dataset.keys():

        node_values = dataset.get(node)
        # print node_values
        for val in node_values:

            if val == '0':
                # print node_values.index('0')
                zero_list = [i for i,x in enumerate(node_values) if x == '0']

            elif val == '1':
                one_list = [i for i,x in enumerate(node_values) if x == '1']

        # print zero_list
        # print one_list

        temp_values = dataset.get(items)

        if items!= node:

            for idx in zero_list:
                tmp_zero_list.append(temp_values[idx])

            for indx in one_list:
                tmp_one_list.append(temp_values[indx])

            # print tmp_zero_list
            # print tmp_one_list

            neg_dict[items] =  tmp_zero_list
            pos_dict[items] = tmp_one_list

        tmp_one_list = []
        tmp_zero_list = []



    return (neg_dict, pos_dict)

