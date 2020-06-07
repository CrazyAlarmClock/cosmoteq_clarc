p = open("in_1.txt", "r")
r = p.read().replace("\n", " ")
p.close()

t = open("in_1.txt", "r")
t.write(str(r))
t.close()