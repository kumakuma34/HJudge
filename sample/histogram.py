import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

filename = input() #파일 읽는 값 
r = open(filename+".txt", 'r')
lines = r.readlines()
grade = []
for line in lines:
    grade.append(line)
plt.title("Histogram")    # 그래프 제목
plt.xlabel("Grade")               # x축 이름
plt.ylabel("Count")               # y축 이름

plt.hist(grade, normed=1, facecolor="gray", edgecolor="black")
plt.savefig(filename+".png", dpi = 350) # 파일 저장 위
plt.show()

r.close()