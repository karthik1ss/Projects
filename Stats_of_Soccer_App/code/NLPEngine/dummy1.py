# coding=utf-8

__author__ = 'Karthik'



# import globals
import unicodedata


str = unicode("Martín Demichelis",'utf-8')


title = u"Klüft skräms inför på fédéral électoral große"
# print title.encode('ascii','ignore')

# print str.encode('ascii','ignore')

print unicodedata.normalize('NFKD', str).encode('ascii','ignore')
# print globals.match_id

#def fun():
    #print "Inside dummy1" , globals.match_id