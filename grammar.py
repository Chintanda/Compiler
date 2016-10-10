import csv

with open("grammar.txt","r") as f:
    reader = csv.reader(f,delimiter = " ")
    for row in reader:
        line = list(map(lambda x: "TokenType." + x.strip().upper() if x.isalpha() else x,list(filter(None,row))))        
        print("add(Arrays.asList(" + ",".join(filter(None,line)) +"));")
