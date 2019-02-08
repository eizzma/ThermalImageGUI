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


directory = csvpath[:-8]
# df['DateTime'] = df['DateTime'].map(lambd
y = df[1]
x = df[2]
# plot
fig = plt.figure()
axes = plt.axes()
plt.plot(x, y, label="Abkühlung", color="orange")
plt.ylabel("Temperatur in [°C]")
plt.xlabel("Zeit in [Sekunden]")
plt.legend()

fig.savefig(directory + 'plot.png')
