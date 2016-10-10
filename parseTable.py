import csv
def convert(a):
    if 0<=a<3:
        return a
    elif a == 4:
        return a-1
    elif a == 6:
        return a-2
    elif 7<a<17:
        return a-3
    elif 18<a<21:
        return a-5
    elif a == 22:
        return a-6
    elif a == 24:
        return a-7
    elif 25<a<30:
        return a-8
    elif 30<a<43:
        return a-9
    elif 43<a<51:
        return a-10
    elif 51<a<55:
        return a-11
    elif 55<a<59:
        return a-12
    elif 59<a<65:
        return a-13
    elif 65<a<68:
        return a-14
    elif a>71:
        return a-15
    else:
        return 0
with open("parsing.csv","r") as f:
    reader = csv.reader(f)
    header = next(reader)
    print(header)
    for i,row in enumerate(reader):
        for j,item in enumerate(row):
            if item != "" and j>0:
                print("put(new key(" + str(i+1) + ",TokenType." + header[j].strip().upper() + "),grammar_rules.get(" +str(convert(int(item)))+"));")
