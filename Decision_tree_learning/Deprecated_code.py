__author__ = 'Karthik'


###################### outdated code ######################

    # def max_infogain_calculate(dataset, tracker):
#
#     main_list, tmp_list , rtrv_list, maxgain_list = [],[],[],[]
#     neg_dict , pos_dict = {}, {}
#     max_gain = 0
#
#
#     for val in tracker:
#
#         if val != "Class":
#
#             tmp_gain, tmp_node, neg_dict, pos_dict = Heuristics.Information_gain(dataset,val)
#             tmp_list.insert(0,tmp_node)
#             tmp_list.insert(1,tmp_gain)
#             tmp_list.insert(2,neg_dict)
#             tmp_list.insert(3,pos_dict)
#
#             main_list.append(tmp_list)
#             tmp_list = []
#
#     # print "Main List" , main_list
#
#     for itm in main_list:
#         maxgain_list.append(itm[1])
#
#     max_gain = sorted(maxgain_list)[len(sorted(maxgain_list))-1]
#
#     print "Max Information Gain", max_gain
#
#     for itms in main_list:
#         if itms[1] == max_gain:
#             # print itms
#             rtrv_list = itms
#
#     return rtrv_list

    # def GrowTree(file):
#
#     max_gain_root, max_gain_child, level = 0,0,0
#
#     att_dict = Dataset.load_dataset(file)
#
#     track_keys = att_dict.keys()
#     # print track_keys
#
#     root_list = max_infogain_calculate(att_dict,track_keys)
#     max_gain_root = root_list[1]
#
#     print "Maximum Information Gain: ",max_gain_root
#
#     if max_gain_root == 0:
#         return 0
#     elif max_gain_root == 1:
#         return 1
#     else:
#
#         root_node = root_list[0]
#         print "Root Node ", root_node
#
#         root = BTreeNode(root_node)
#         # print root_node
#
#         stack = Stack()
#
#         stack.push(root_list[3])
#         stack.push(root_list[2])
#
#         if root_node in track_keys:
#             track_keys.remove(root_node)
#
#         temp = root
#
#         while not stack.is_empty():
#
#
#             while (temp != None):
#
#                 print "Decision Tree construction at level", level
#
#                 print "Track Keys", track_keys
#
#                 tmp_dict = stack.pop()
#
#                 # print "Stack contents ", tmp_dict
#
#                 tmp_list = max_infogain_calculate(tmp_dict,track_keys)
#
#
#                 if len(track_keys) == 1 and track_keys.pop() == "Class":
#                     return root
#
#                 else:
#
#                     # print "Child Max Gain ", tmp_list
#
#                     tmp_max_gain = tmp_list[1]
#
#                     if tmp_max_gain == 0:
#                         temp.left = BTreeNode('0')
#                         temp = temp.right
#                         # break
#
#                     elif tmp_max_gain == 1:
#                         temp.right = BTreeNode('1')
#                         temp = temp.left
#                         # break
#
#                     tmp_node = tmp_list[0]
#
#                     child = BTreeNode(tmp_node)
#                     temp = child
#
#                     stack.push(tmp_list[3])
#                     stack.push(tmp_list[2])
#
#                     if tmp_node in track_keys:
#                         track_keys.remove(tmp_node)
#
#                     level+= 1
#
#
#     return root



    # root_node =  GrowTree('data_sets1/verysmall.csv')
    # print_tree(root_node)


    # att_dict = Dataset.load_dataset('data_sets1/sampleset.csv')
    # print att_dict.keys()
    # Dataset.split_dataset(att_dict,"XB")
    #
    # Dataset.split_dataset(att_dict,"XC")
    #
    # Dataset.split_dataset(att_dict,"XD")
    #
    # Dataset.split_dataset(att_dict,"XE")
    #
    # Dataset.split_dataset(att_dict,"XF")
    #
    # Dataset.split_dataset(att_dict,"XG")
    #
    # Dataset.split_dataset(att_dict,"XH")
    #
    # Dataset.split_dataset(att_dict,"XI")
    #
    # Dataset.split_dataset(att_dict,"XJ")
    #
    # Dataset.split_dataset(att_dict,"XK")
    #
    # Dataset.split_dataset(att_dict,"XL")
    #
    # Dataset.split_dataset(att_dict,"XM")
    #
    # Dataset.split_dataset(att_dict,"XN")
    #
    # Dataset.split_dataset(att_dict,"XO")
    #
    # Dataset.split_dataset(att_dict,"XP")
    #
    # Dataset.split_dataset(att_dict,"XQ")
    #
    # Dataset.split_dataset(att_dict,"XR")
    #
    # Dataset.split_dataset(att_dict,"XS")
    #
    # Dataset.split_dataset(att_dict,"XT")
    #
    # Dataset.split_dataset(att_dict,"XU")




