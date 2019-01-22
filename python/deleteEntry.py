import pandas as pd
import sys
import os
import matplotlib.pyplot as plt


def plot(df, path):
    y = df[1]
    x = df[2]
    fig = plt.figure()
    ax = plt.subplot(111)
    ax.plot(x, y, label='$y = numbers')
    fig.savefig(path + '/plot.png')


(name, path) = sys.argv[1].split(" ")  # cheap argument parsing

os.remove(path + "/" + name + ".png")  # delete the file

csvpath = path + "/data.csv"  # remove the measurement from the csv
df = pd.read_csv(csvpath, header=None).set_index([0])
df = df.drop(name)
df.to_csv(path + "/data.csv", header=False)

plot(df, path)  # draw the new plot
